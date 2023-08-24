package info.tritusk.adventure.platform.forge.adventure

import info.tritusk.adventure.platform.forge.adventure.impl.BossEventListener
import info.tritusk.adventure.platform.forge.adventure.impl.ForgePlatform
import info.tritusk.adventure.platform.forge.adventure.impl.audience.AllServerPlayerAudience
import info.tritusk.adventure.platform.forge.adventure.impl.audience.FilteredServerPlayerAudience
import info.tritusk.adventure.platform.forge.adventure.impl.audience.ServerAudience
import info.tritusk.adventure.platform.forge.adventure.impl.audience.ServerPlayerAudience
import info.tritusk.adventure.platform.forge.adventure.impl.mapper.BossBarMapper
import info.tritusk.adventure.platform.forge.adventure.impl.mapper.KeyMapper
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.key.Key
import net.kyori.adventure.platform.AudienceProvider
import net.kyori.adventure.text.flattener.ComponentFlattener
import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerBossEvent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import java.util.*

class ForgeServerAudiences(private val server: MinecraftServer) : AudienceProvider {

    private val serverAudience: ServerAudience = ServerAudience(server)
    private val trackedBossBars = IdentityHashMap<BossBar, ServerBossEvent>()
    private val listener = BossEventListener(this::getOrCreateFrom)

    fun of(): ForgeServerAudiences {
        return ForgePlatform.audienceProvider
    }

    fun getOrCreateFrom(bossBar: BossBar): ServerBossEvent {
        return trackedBossBars.computeIfAbsent(bossBar) {
            it.addListener(listener)
            return@computeIfAbsent BossBarMapper.toNative(it)
        }
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun all(): Audience {
        return Audience.audience(players(), console())
    }

    override fun console(): Audience {
        return serverAudience
    }

    override fun players(): Audience {
        return AllServerPlayerAudience(server.playerList, this::getOrCreateFrom)
    }

    override fun player(playerId: UUID): Audience {
        val p = server.playerList.getPlayer(playerId)
        return if (p == null) Audience.empty() else ServerPlayerAudience(p, this::getOrCreateFrom)
    }

    private val luckpermsAPI: LuckPerms by lazy {
        LuckPermsProvider.get()
    }

    private fun adapter() = luckpermsAPI.getPlayerAdapter(ServerPlayer::class.java)

    override fun permission(permission: String): Audience {
        return FilteredServerPlayerAudience(server.playerList, this::getOrCreateFrom) {
            adapter().getPermissionData(it).checkPermission(permission).asBoolean()
        }
    }

    override fun world(world: Key): Audience {
        val worldKey: ResourceKey<Level> = ResourceKey.create(Registries.DIMENSION, KeyMapper.toNative(world))
        return if (server.getLevel(worldKey) == null) Audience.empty() else FilteredServerPlayerAudience(
            server.playerList,
            this::getOrCreateFrom
        ) { p -> p.level().dimension() == worldKey }
    }

    override fun server(serverName: String): Audience {
        return all()
    }

    override fun flattener(): ComponentFlattener {
        TODO("Not yet implemented")
    }

}
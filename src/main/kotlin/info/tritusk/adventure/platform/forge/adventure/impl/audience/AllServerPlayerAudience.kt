package info.tritusk.adventure.platform.forge.adventure.impl.audience

import info.tritusk.adventure.platform.forge.adventure.impl.audience.ServerPlayerAudience
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.audience.ForwardingAudience
import net.kyori.adventure.bossbar.BossBar
import net.minecraft.server.level.ServerBossEvent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import java.lang.ref.WeakReference
import java.util.function.Function
import java.util.stream.Stream

open class AllServerPlayerAudience(players: PlayerList, private val bossBarMapper: Function<BossBar, ServerBossEvent>) :
    ForwardingAudience {

    private val players: WeakReference<PlayerList> = WeakReference(players)

    open fun players(): Stream<ServerPlayer> {
        return players.get()?.players?.stream() ?: Stream.empty()
    }

    override fun audiences(): MutableIterable<Audience> {
        return players().map {
            ServerPlayerAudience(it, bossBarMapper)
        }.toList()
    }
}
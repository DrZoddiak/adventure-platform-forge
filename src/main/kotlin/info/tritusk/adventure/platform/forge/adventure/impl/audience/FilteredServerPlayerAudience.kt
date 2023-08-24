package info.tritusk.adventure.platform.forge.adventure.impl.audience

import net.kyori.adventure.bossbar.BossBar
import net.minecraft.server.level.ServerBossEvent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.players.PlayerList
import java.util.function.Function
import java.util.function.Predicate
import java.util.stream.Stream

class FilteredServerPlayerAudience(
    players: PlayerList,
    bossBarMapper: Function<BossBar, ServerBossEvent>,
    private val filter: Predicate<ServerPlayer>
) : AllServerPlayerAudience(players, bossBarMapper) {

    override fun players(): Stream<ServerPlayer> {
        return super.players().filter(filter)
    }
}
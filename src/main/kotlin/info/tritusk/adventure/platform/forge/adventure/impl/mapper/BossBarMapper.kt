package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import net.kyori.adventure.bossbar.BossBar
import net.minecraft.server.level.ServerBossEvent
import net.minecraft.world.BossEvent

object BossBarMapper {

    fun toNative(bar: BossBar): ServerBossEvent {
        val nativeBossBar =
            ServerBossEvent(
                TextComponentMapper.toNative(bar.name()),
                toNative(bar.color()),
                toNative(bar.overlay())
            )
        nativeBossBar.progress = bar.progress()
        nativeBossBar.setCreateWorldFog(bar.hasFlag(BossBar.Flag.CREATE_WORLD_FOG))
            .setDarkenScreen(bar.hasFlag(BossBar.Flag.DARKEN_SCREEN))
            .setPlayBossMusic(bar.hasFlag(BossBar.Flag.PLAY_BOSS_MUSIC))
        return nativeBossBar
    }

    fun toNative(color: BossBar.Color): BossEvent.BossBarColor {
        return when (color) {
            BossBar.Color.RED -> BossEvent.BossBarColor.RED
            BossBar.Color.PINK -> BossEvent.BossBarColor.PINK
            BossBar.Color.BLUE -> BossEvent.BossBarColor.BLUE
            BossBar.Color.GREEN -> BossEvent.BossBarColor.GREEN
            BossBar.Color.YELLOW -> BossEvent.BossBarColor.YELLOW
            BossBar.Color.PURPLE -> BossEvent.BossBarColor.PURPLE
            BossBar.Color.WHITE -> BossEvent.BossBarColor.WHITE
        }
    }

    fun toNative(original: BossBar.Overlay): BossEvent.BossBarOverlay {
        return when (original) {
            BossBar.Overlay.PROGRESS -> BossEvent.BossBarOverlay.PROGRESS
            BossBar.Overlay.NOTCHED_6 -> BossEvent.BossBarOverlay.NOTCHED_6
            BossBar.Overlay.NOTCHED_10 -> BossEvent.BossBarOverlay.NOTCHED_10
            BossBar.Overlay.NOTCHED_12 -> BossEvent.BossBarOverlay.NOTCHED_12
            BossBar.Overlay.NOTCHED_20 -> BossEvent.BossBarOverlay.NOTCHED_20
        }
    }

}
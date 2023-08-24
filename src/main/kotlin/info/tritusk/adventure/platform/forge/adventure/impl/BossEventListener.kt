package info.tritusk.adventure.platform.forge.adventure.impl

import info.tritusk.adventure.platform.forge.adventure.impl.mapper.BossBarMapper
import info.tritusk.adventure.platform.forge.adventure.impl.mapper.TextComponentMapper
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.minecraft.world.BossEvent
import java.util.function.Function

class BossEventListener(val mapper: Function<BossBar, BossEvent>) : BossBar.Listener {

    override fun bossBarNameChanged(bar: BossBar, oldName: Component, newName: Component) {
        if (oldName != newName) {
            mapper.apply(bar).name = TextComponentMapper.toNative(newName)
        }
    }

    override fun bossBarProgressChanged(bar: BossBar, oldProgress: Float, newProgress: Float) {
        if (oldProgress != newProgress) {
            mapper.apply(bar).progress = newProgress
        }
    }

    override fun bossBarColorChanged(bar: BossBar, oldColor: BossBar.Color, newColor: BossBar.Color) {
        if (oldColor != newColor) {
            mapper.apply(bar).color = BossBarMapper.toNative(newColor)
        }
    }

    override fun bossBarOverlayChanged(bar: BossBar, oldOverlay: BossBar.Overlay, newOverlay: BossBar.Overlay) {
        if (oldOverlay != newOverlay) {
            mapper.apply(bar).overlay = BossBarMapper.toNative(newOverlay)
        }
    }

    override fun bossBarFlagsChanged(
        bar: BossBar,
        flagsAdded: MutableSet<BossBar.Flag>,
        flagsRemoved: MutableSet<BossBar.Flag>
    ) {
        val bossEvent = mapper.apply(bar)
        when {
            flagsAdded.contains(BossBar.Flag.PLAY_BOSS_MUSIC) -> bossEvent.setPlayBossMusic(true)
            flagsAdded.contains(BossBar.Flag.DARKEN_SCREEN) -> bossEvent.setDarkenScreen(true)
            flagsAdded.contains(BossBar.Flag.CREATE_WORLD_FOG) -> bossEvent.setCreateWorldFog(true)

            flagsRemoved.contains(BossBar.Flag.PLAY_BOSS_MUSIC) -> bossEvent.setPlayBossMusic(false)
            flagsRemoved.contains(BossBar.Flag.DARKEN_SCREEN) -> bossEvent.setDarkenScreen(false)
            flagsRemoved.contains(BossBar.Flag.CREATE_WORLD_FOG) -> bossEvent.setCreateWorldFog(false)
        }

    }

}
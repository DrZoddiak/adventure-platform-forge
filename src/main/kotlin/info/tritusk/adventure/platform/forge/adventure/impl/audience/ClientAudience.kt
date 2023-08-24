package info.tritusk.adventure.platform.forge.adventure.impl.audience

import info.tritusk.adventure.platform.forge.adventure.impl.AdventureBookInfo
import info.tritusk.adventure.platform.forge.adventure.impl.mapper.KeyMapper
import info.tritusk.adventure.platform.forge.adventure.impl.mapper.SoundMapper
import info.tritusk.adventure.platform.forge.adventure.impl.toNative
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.SoundStop
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.inventory.BookViewScreen
import net.minecraft.network.protocol.game.ClientboundBossEventPacket
import net.minecraft.world.BossEvent
import net.minecraftforge.server.command.TextComponentHelper
import java.time.Duration
import java.util.function.Function

class ClientAudience(val bossBarMapper: Function<BossBar, BossEvent>) : Audience {

    private val instance: Minecraft
        get() = Minecraft.getInstance()

    override fun sendMessage(message: Component) {
        val p = instance.player
        p?.sendSystemMessage(TextComponentHelper.createComponentTranslation(p, "chat.type.text", message))
    }

    override fun sendActionBar(message: Component) {
        instance.player?.displayClientMessage(message.toNative(), true)
    }

    override fun sendPlayerListHeader(header: Component) {
        instance.gui.tabList.setHeader(header.toNative())
    }

    override fun sendPlayerListFooter(footer: Component) {
        instance.gui.tabList.setFooter(footer.toNative())
    }

    override fun sendPlayerListHeaderAndFooter(header: Component, footer: Component) {
        sendPlayerListHeader(header)
        sendPlayerListFooter(header)
    }

    override fun showTitle(title: Title) {
        val titleTimes = title.times()
        if (titleTimes == null) clearTitle()
        else {
            val gui = instance.gui
            gui.setTitle(title.title().toNative())
            gui.setSubtitle(title.subtitle().toNative())
            gui.setTimes(
                calcTitleTime(titleTimes.fadeIn()),
                calcTitleTime(titleTimes.stay()),
                calcTitleTime(titleTimes.fadeOut())
            )
        }

    }

    private fun calcTitleTime(time: Duration): Int {
        return (time.toMillis() / Ticks.SINGLE_TICK_DURATION_MS).toInt()
    }

    override fun clearTitle() {
        val gui = instance.gui
        gui.setTitle(Component.empty().toNative())
        gui.setSubtitle(Component.empty().toNative())
        gui.setTimes(0, 0, 0)
    }

    override fun resetTitle() {
        instance.gui.resetTitleTimes()
    }

    override fun showBossBar(bar: BossBar) {
        val native = bossBarMapper.apply(bar)
        val packet = ClientboundBossEventPacket.createAddPacket(native)
        instance.gui.bossOverlay.update(packet)
    }

    override fun hideBossBar(bar: BossBar) {
        val native = bossBarMapper.apply(bar)
        val packet = ClientboundBossEventPacket.createRemovePacket(native.id)
        instance.gui.bossOverlay.update(packet)
    }

    override fun playSound(sound: Sound) {
        val p = instance.player
        if (p == null) {
            instance.soundManager.play(SoundMapper.toNative(sound))
        } else {
            playSound(sound, p.x, p.y, p.z)
        }
    }

    override fun playSound(sound: Sound, x: Double, y: Double, z: Double) {
        instance.soundManager.play(SoundMapper.toNative(sound, x, y, z, true))
    }

    override fun stopSound(stop: SoundStop) {
        instance.soundManager.stop(stop.sound()?.let { KeyMapper.toNative(it) },
            stop.source()?.let { SoundMapper.toNative(it) })
    }

    override fun openBook(book: Book) {
        instance.pushGuiLayer(BookViewScreen(AdventureBookInfo(book)))
    }

}
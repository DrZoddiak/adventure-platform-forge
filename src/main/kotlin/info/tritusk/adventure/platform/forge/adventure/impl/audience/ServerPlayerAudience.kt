package info.tritusk.adventure.platform.forge.adventure.impl.audience

import info.tritusk.adventure.platform.forge.adventure.impl.toNative
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.minecraft.server.level.ServerBossEvent
import net.minecraft.server.level.ServerPlayer
import java.lang.ref.WeakReference
import java.util.function.Function

class ServerPlayerAudience(p: ServerPlayer, val bossBarMapper: Function<BossBar, ServerBossEvent>) : Audience {
    private val player = WeakReference(p)

    override fun sendActionBar(message: Component) {
        player.get()?.displayClientMessage(message.toNative(), true)
    }




}
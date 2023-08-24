package info.tritusk.adventure.platform.forge.adventure.impl.audience

import info.tritusk.adventure.platform.forge.adventure.impl.mapper.TextComponentMapper
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.minecraft.server.MinecraftServer

class ServerAudience(private val server: MinecraftServer) : Audience {

    override fun sendMessage(message: Component) {
        server.sendSystemMessage(TextComponentMapper.toNative(message))
    }

}
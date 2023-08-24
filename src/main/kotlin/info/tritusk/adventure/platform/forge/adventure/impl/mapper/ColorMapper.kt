package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import net.kyori.adventure.text.format.TextColor

object ColorMapper {
    fun toNative(color: TextColor): net.minecraft.network.chat.TextColor {
        return net.minecraft.network.chat.TextColor.fromRgb(color.value())
    }

    fun toAdventure(color: net.minecraft.network.chat.TextColor): TextColor {
        return TextColor.color(color.value)
    }
}
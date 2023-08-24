package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import info.tritusk.adventure.platform.forge.adventure.impl.visitor.ToNativeConverter
import net.kyori.adventure.text.Component
import net.minecraft.network.chat.MutableComponent

object TextComponentMapper {

    fun toNative(component: Component): MutableComponent {
        val converter = ToNativeConverter()
        converter.accept(component)
        return converter.getNative()
    }

}
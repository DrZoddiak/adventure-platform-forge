package info.tritusk.adventure.platform.forge.adventure.impl.visitor

import info.tritusk.adventure.platform.forge.adventure.impl.mapper.StyleMapper
import net.kyori.adventure.text.Component
import net.minecraft.network.chat.MutableComponent

class ToNativeConverter : ComponentVisitor {

    private lateinit var nativeComponent: MutableComponent

    fun getNative(): MutableComponent {
        return nativeComponent
    }

    fun handleStyleAndChildren(original: Component, converted: MutableComponent) {
        converted.setStyle(StyleMapper.toNative(original.style()))
    }

}
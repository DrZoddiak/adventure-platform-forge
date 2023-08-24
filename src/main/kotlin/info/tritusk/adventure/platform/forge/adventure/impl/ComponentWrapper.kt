package info.tritusk.adventure.platform.forge.adventure.impl

import info.tritusk.adventure.platform.forge.adventure.impl.visitor.ToNativeConverter
import net.kyori.adventure.text.Component
import net.minecraft.network.chat.ComponentContents
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.MutableComponent
import net.minecraft.util.FormattedCharSequence
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*

class ComponentWrapper(wrapped: Component) : net.minecraft.network.chat.Component {

    private val converter = ToNativeConverter()
    private var deepConverted: MutableComponent

    init {
        converter.accept(wrapped)
        deepConverted = converter.getNative()
    }

    override fun getStyle(): net.minecraft.network.chat.Style {
        return deepConverted.style
    }

    override fun copy(): MutableComponent {
        return deepConverted.copy()
    }

    override fun plainCopy(): MutableComponent {
        return deepConverted.plainCopy()
    }

    override fun getContents(): ComponentContents {
        return deepConverted.contents
    }

    override fun getSiblings(): MutableList<net.minecraft.network.chat.Component> {
        return deepConverted.siblings
    }

    override fun getVisualOrderText(): FormattedCharSequence {
        return deepConverted.visualOrderText
    }

    @OnlyIn(Dist.CLIENT)
    override fun <T : Any?> visit(
        acceptor: FormattedText.StyledContentConsumer<T>,
        style: net.minecraft.network.chat.Style
    ): Optional<T> {
        return deepConverted.visit(acceptor, style)
    }

    override fun <T : Any?> visit(acceptor: FormattedText.ContentConsumer<T>): Optional<T> {
        return deepConverted.visit(acceptor)
    }
}

fun Component.toNative() = ComponentWrapper(this)

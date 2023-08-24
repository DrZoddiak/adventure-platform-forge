package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import net.kyori.adventure.text.format.TextDecoration
import net.minecraft.network.chat.Style

object StyleMapper {

    private fun toBoolean(state: TextDecoration.State): Boolean? {
        return when (state) {
            TextDecoration.State.TRUE -> true
            TextDecoration.State.FALSE -> false
            TextDecoration.State.NOT_SET -> null
        }
    }

    fun toNative(style: net.kyori.adventure.text.format.Style?): Style {
        var nativeStyle = Style.EMPTY
        if (style == null) return nativeStyle
        nativeStyle = nativeStyle.apply {
            withBold(TextDecoration.BOLD.bool(style))
            withItalic(TextDecoration.ITALIC.bool(style))
            withObfuscated(TextDecoration.OBFUSCATED.bool(style))
            withStrikethrough(TextDecoration.STRIKETHROUGH.bool(style))
            withUnderlined(TextDecoration.STRIKETHROUGH.bool(style))
            withInsertion(style.insertion())
            withColor(style.color()?.let { ColorMapper.toNative(it) })
            withFont(style.font()?.let { KeyMapper.toNative(it) })
            withClickEvent(style.clickEvent()?.let { ClickEventMapper.toNative(it) })
            withHoverEvent(style.hoverEvent()?.let { HoverEventMapper.toNative(it) })
        }

        return nativeStyle
    }

    private fun TextDecoration.bool(style: net.kyori.adventure.text.format.Style): Boolean? =
        toBoolean(style.decoration(this))

}
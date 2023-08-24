package info.tritusk.adventure.platform.forge.adventure.impl.visitor

import net.kyori.adventure.text.BlockNBTComponent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.EntityNBTComponent
import net.kyori.adventure.text.KeybindComponent
import net.kyori.adventure.text.ScoreComponent
import net.kyori.adventure.text.SelectorComponent
import net.kyori.adventure.text.StorageNBTComponent
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TranslatableComponent

interface ComponentVisitor {
    fun accept(c: Component) {
        when (c) {
            is BlockNBTComponent -> accept(c)
            is EntityNBTComponent -> accept(c)
            is KeybindComponent -> accept(c)
            is ScoreComponent -> accept(c)
            is SelectorComponent -> accept(c)
            is StorageNBTComponent -> accept(c)
            is TextComponent -> accept(c)
            is TranslatableComponent -> accept(c)
        }
    }

    fun accept(c: BlockNBTComponent) = c.children().forEach(this::accept)

    fun accept(c: EntityNBTComponent) = c.children().forEach(this::accept)

    fun accept(c: KeybindComponent) = c.children().forEach(this::accept)

    fun accept(c: ScoreComponent) = c.children().forEach(this::accept)

    fun accept(c: SelectorComponent) = c.children().forEach(this::accept)

    fun accept(c: StorageNBTComponent) = c.children().forEach(this::accept)

    fun accept(c: TextComponent) = c.children().forEach(this::accept)

    fun accept(c: TranslatableComponent) = c.children().forEach(this::accept)

}
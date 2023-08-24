package info.tritusk.adventure.platform.forge.adventure.impl

import net.kyori.adventure.inventory.Book
import net.minecraft.client.gui.screens.inventory.BookViewScreen
import net.minecraft.network.chat.FormattedText

class AdventureBookInfo(private val book: Book) : BookViewScreen.BookAccess {

    private val pages = book.pages().map { it.toNative() }

    override fun getPageCount(): Int {
        return book.pages().size
    }

    override fun getPageRaw(index: Int): FormattedText {
        return pages[index]
    }

}
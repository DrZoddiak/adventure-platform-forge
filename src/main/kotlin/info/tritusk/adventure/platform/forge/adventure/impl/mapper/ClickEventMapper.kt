package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import net.minecraft.network.chat.ClickEvent
import java.util.EnumMap

typealias AdventureClickEvent = net.kyori.adventure.text.event.ClickEvent
typealias AdventureClickEventAction = net.kyori.adventure.text.event.ClickEvent.Action

object ClickEventMapper {

    val NATIVE_ACTIONS_TO_ADVENTURE: EnumMap<ClickEvent.Action, AdventureClickEventAction> =
        EnumMap(ClickEvent.Action::class.java)
    lateinit var ADVENTURE_ACTIONS_TO_NATIVE: EnumMap<AdventureClickEventAction, ClickEvent.Action>

    init {
        NATIVE_ACTIONS_TO_ADVENTURE[ClickEvent.Action.CHANGE_PAGE] = AdventureClickEventAction.CHANGE_PAGE
        NATIVE_ACTIONS_TO_ADVENTURE[ClickEvent.Action.COPY_TO_CLIPBOARD] = AdventureClickEventAction.COPY_TO_CLIPBOARD
        NATIVE_ACTIONS_TO_ADVENTURE[ClickEvent.Action.OPEN_FILE] = AdventureClickEventAction.OPEN_FILE
        NATIVE_ACTIONS_TO_ADVENTURE[ClickEvent.Action.OPEN_URL] = AdventureClickEventAction.OPEN_URL
        NATIVE_ACTIONS_TO_ADVENTURE[ClickEvent.Action.RUN_COMMAND] = AdventureClickEventAction.RUN_COMMAND
        NATIVE_ACTIONS_TO_ADVENTURE[ClickEvent.Action.SUGGEST_COMMAND] = AdventureClickEventAction.SUGGEST_COMMAND
        NATIVE_ACTIONS_TO_ADVENTURE.entries.forEach {
            ADVENTURE_ACTIONS_TO_NATIVE[it.value] = it.key
        }
    }

    fun toNative(event: AdventureClickEvent): ClickEvent? {
        return ADVENTURE_ACTIONS_TO_NATIVE[event.action()]?.let { ClickEvent(it, event.value()) }
    }

    fun toAdventure(event: ClickEvent): AdventureClickEvent? {
        return NATIVE_ACTIONS_TO_ADVENTURE[event.action]?.let { AdventureClickEvent.clickEvent(it, event.value) }
    }

}
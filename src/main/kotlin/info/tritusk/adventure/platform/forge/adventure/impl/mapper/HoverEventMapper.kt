package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import info.tritusk.adventure.platform.forge.adventure.impl.JsonLikeNBTCodec
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent.ShowEntity
import net.kyori.adventure.text.event.HoverEvent.ShowItem
import net.minecraft.network.chat.HoverEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraftforge.registries.ForgeRegistries

object HoverEventMapper {

    fun toNative(event: net.kyori.adventure.text.event.HoverEvent<*>): HoverEvent? {
        val nativeAction = HoverEvent.Action.getByName(event.action().toString())
        when (nativeAction) {
            HoverEvent.Action.SHOW_ENTITY -> {
                val showEntityEvent = event as net.kyori.adventure.text.event.HoverEvent<ShowEntity>
                val original = showEntityEvent.value()
                val nativeValue = lookup(original.type())?.let { entity ->
                    HoverEvent.EntityTooltipInfo(
                        entity,
                        original.id(),
                        original.name()?.let { component -> TextComponentMapper.toNative(component) }
                    )
                }
                return nativeValue?.let { HoverEvent(HoverEvent.Action.SHOW_ENTITY, it) }
            }

            HoverEvent.Action.SHOW_ITEM -> {
                val showEntityEvent = event as net.kyori.adventure.text.event.HoverEvent<ShowItem>
                val original = showEntityEvent.value()
                val item = lookupItem(original.item())?.let { ItemStack(it, original.count()) }
                if (original.nbt() != null) {
                    item?.tag = original.nbt()?.get(JsonLikeNBTCodec)
                }
            }

            HoverEvent.Action.SHOW_TEXT -> {
                val showTextEvent = event as net.kyori.adventure.text.event.HoverEvent<Component>
                return HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponentMapper.toNative(showTextEvent.value()))
            }

            else -> {
                throw IllegalArgumentException("Cannot handle unknown hover event type ${event.action()}")
            }
        }
        TODO()
    }

    private fun lookup(id: Key): EntityType<*>? {
        return ForgeRegistries.ENTITY_TYPES.getValue(KeyMapper.toNative(id))
    }

    private fun lookupItem(id: Key): Item? {
        return ForgeRegistries.ITEMS.getValue(KeyMapper.toNative(id))
    }


}
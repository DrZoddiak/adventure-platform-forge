package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import net.kyori.adventure.key.Key
import net.minecraft.resources.ResourceLocation
import java.util.MissingResourceException

object KeyMapper {
    fun toNative(key: Key): ResourceLocation {
        return ResourceLocation(key.namespace(), key.value())
    }

    fun toAdventure(id: ResourceLocation): Key {
        return Key.key(id.namespace, id.path)
    }
}
package info.tritusk.adventure.platform.forge.adventure.impl

import net.kyori.adventure.util.Codec
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.TagParser

object JsonLikeNBTCodec : Codec<CompoundTag, String, Exception, Exception> {

    override fun decode(encoded: String): CompoundTag {
        return TagParser.parseTag(encoded)
    }

    override fun encode(decoded: CompoundTag): String {
        return decoded.toString()
    }
}
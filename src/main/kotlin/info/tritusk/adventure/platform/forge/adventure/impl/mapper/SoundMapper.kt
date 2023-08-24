package info.tritusk.adventure.platform.forge.adventure.impl.mapper

import net.kyori.adventure.sound.Sound
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance.Attenuation
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import java.util.EnumMap

object SoundMapper {

    private val ADVENTURE_TYPE_TO_NATIVE: EnumMap<Sound.Source, SoundSource> = EnumMap(Sound.Source::class.java)

    init {
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.MASTER] = SoundSource.MASTER
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.MUSIC] = SoundSource.MUSIC
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.RECORD] = SoundSource.RECORDS
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.WEATHER] = SoundSource.WEATHER
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.BLOCK] = SoundSource.BLOCKS
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.HOSTILE] = SoundSource.HOSTILE
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.NEUTRAL] = SoundSource.NEUTRAL
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.PLAYER] = SoundSource.PLAYERS
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.AMBIENT] = SoundSource.AMBIENT
        ADVENTURE_TYPE_TO_NATIVE[Sound.Source.VOICE] = SoundSource.VOICE
    }

    fun toNative(source: Sound.Source): SoundSource? {
        return ADVENTURE_TYPE_TO_NATIVE[source]
    }

    fun toNative(sound: Sound): SimpleSoundInstance {
        return toNative(sound, 0.0, 0.0, 0.0, false)
    }

    fun toNative(sound: Sound, x: Double, y: Double, z: Double, global: Boolean): SimpleSoundInstance {
        val id = KeyMapper.toNative(sound.name())
        val category = toNative(sound.source())!!
        val pitch = sound.pitch()
        val volume = sound.volume()

        return SimpleSoundInstance(
            id,
            category,
            pitch,
            volume,
            RandomSource.create(),
            false,
            0,
            Attenuation.NONE,
            x,
            y,
            z,
            global
        )
    }

}
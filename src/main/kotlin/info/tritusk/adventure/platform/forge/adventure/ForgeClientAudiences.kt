package info.tritusk.adventure.platform.forge.adventure

import info.tritusk.adventure.platform.forge.adventure.impl.BossEventListener
import info.tritusk.adventure.platform.forge.adventure.impl.audience.ClientAudience
import info.tritusk.adventure.platform.forge.adventure.impl.mapper.BossBarMapper
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.minecraft.client.gui.components.LerpingBossEvent
import java.util.IdentityHashMap

object ForgeClientAudiences {
    private val bossBars = IdentityHashMap<BossBar, LerpingBossEvent>()
    private val listener = BossEventListener(this::getOrCreateFrom)

    fun audience(): Audience {
        return ClientAudience(this::getOrCreateFrom)
    }

    private fun getOrCreateFrom(original: BossBar): LerpingBossEvent {
        return bossBars.computeIfAbsent(original) { bar ->
            bar.addListener(listener)
            val mapper = BossBarMapper.toNative(bar)
            LerpingBossEvent(
                mapper.id,
                mapper.name,
                mapper.progress,
                mapper.color,
                mapper.overlay,
                mapper.shouldDarkenScreen(), //TODO Ensure these are in the correct order
                mapper.shouldPlayBossMusic(),
                mapper.shouldCreateWorldFog()
            )
        }
    }
}
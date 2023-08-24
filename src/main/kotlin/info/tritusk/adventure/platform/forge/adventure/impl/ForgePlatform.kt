package info.tritusk.adventure.platform.forge.adventure.impl

import info.tritusk.adventure.platform.forge.adventure.ForgeServerAudiences
import net.minecraftforge.event.server.ServerAboutToStartEvent
import net.minecraftforge.event.server.ServerStoppingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod("adventureplatformforge")
@Mod.EventBusSubscriber(modid = "adventureplatformforge")
class ForgePlatform {

    companion object Audience {
        lateinit var audienceProvider: ForgeServerAudiences
    }

    @SubscribeEvent
    fun serverStart(event: ServerAboutToStartEvent) {
        audienceProvider = ForgeServerAudiences(event.server)
    }

    @SubscribeEvent
    fun serverStop(event: ServerStoppingEvent) {
        audienceProvider.close()
    }

}
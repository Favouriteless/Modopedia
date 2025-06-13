package net.favouriteless.modopedia.neoforge.client;

import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.StudyManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = Modopedia.MOD_ID, bus = Bus.GAME, value = Dist.CLIENT)
public class NeoClientEvents {

    @SubscribeEvent
    public static void gatherTooltips(RenderTooltipEvent.GatherComponents event) {
        StudyManager.getTooltips(event.getItemStack().getItem()).forEach(c -> event.getTooltipElements().add(Either.left(c)));
    }

}

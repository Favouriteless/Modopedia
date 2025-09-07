package net.favouriteless.modopedia.neoforge.client;

import com.mojang.datafixers.util.Either;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.StudyManager;
import net.favouriteless.modopedia.client.multiblock.render.MultiblockVisualiserImpl;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = Modopedia.MOD_ID, value = Dist.CLIENT)
public class NeoClientEvents {

    @SubscribeEvent
    public static void onGatherTooltips(RenderTooltipEvent.GatherComponents event) {
        StudyManager.getTooltips(event.getItemStack().getItem()).forEach(c -> event.getTooltipElements().add(Either.left(c)));
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if(event.getStage() != Stage.AFTER_TRIPWIRE_BLOCKS)
            return;

        Minecraft mc = Minecraft.getInstance();
        MultiblockVisualiserImpl.INSTANCE.render(mc.level, event.getPoseStack(), mc.renderBuffers().bufferSource(), event.getPartialTick().getGameTimeDeltaPartialTick(true));
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        MultiblockVisualiserImpl.INSTANCE.tick();
    }

}

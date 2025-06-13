package net.favouriteless.modopedia.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.client.MBookModel;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.util.ResourceUtils;
import net.minecraft.client.Minecraft;

public class ModopediaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModopediaClient.init();

        ModelLoadingPlugin.register(context -> {
            ResourceUtils.getBookModels().forEach(context::addModels);

            context.modifyModelAfterBake().register(
                    (oldModel, ctx) -> {
                        if(oldModel == null || ctx.topLevelId() == null || !Modopedia.id("book").equals(ctx.topLevelId().id()))
                            return oldModel;

                        return new MBookModel(oldModel, m -> Minecraft.getInstance().getModelManager().getModel(m));
                    }
            );
        });

        ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> {
            lines.addAll(ModopediaClient.getStudyTooltips(stack));
        });
    }

}

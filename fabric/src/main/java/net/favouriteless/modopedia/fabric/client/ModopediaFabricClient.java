package net.favouriteless.modopedia.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.StudyManager;
import net.favouriteless.modopedia.client.MBookModel;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.common.network.packets.client.*;
import net.favouriteless.modopedia.util.ResourceUtils;
import net.minecraft.client.Minecraft;

public class ModopediaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModopediaClient.init();
        registerPackets();

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

        ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> lines.addAll(StudyManager.getTooltips(stack.getItem())));
    }

    public void registerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ClearBooksPayload.TYPE, (payload, context) -> payload.handle());
        ClientPlayNetworking.registerGlobalReceiver(SyncBookPayload.TYPE, (payload, context) -> payload.handle());
        ClientPlayNetworking.registerGlobalReceiver(ReloadBookContentPayload.TYPE, (payload, context) -> payload.handle());

        ClientPlayNetworking.registerGlobalReceiver(OpenBookPayload.TYPE, (payload, context) -> payload.handle());
        ClientPlayNetworking.registerGlobalReceiver(OpenCategoryPayload.TYPE, (payload, context) -> payload.handle());
        ClientPlayNetworking.registerGlobalReceiver(OpenEntryPayload.TYPE, (payload, context) -> payload.handle());
    }

}

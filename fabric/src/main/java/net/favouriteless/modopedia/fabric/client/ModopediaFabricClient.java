package net.favouriteless.modopedia.fabric.client;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.StudyManager;
import net.favouriteless.modopedia.client.ClientConfig;
import net.favouriteless.modopedia.client.MBookModel;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.client.init.MKeyMappings;
import net.favouriteless.modopedia.common.network.packets.client.*;
import net.favouriteless.modopedia.util.client.ResourceUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.config.ModConfig.Type;

public class ModopediaFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModopediaClient.init();
        registerKeyMappings();
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

        NeoForgeConfigRegistry.INSTANCE.register(Modopedia.MOD_ID, Type.CLIENT, ClientConfig.SPEC, "modopedia-client.toml");
    }

    public void registerKeyMappings() {
        KeyBindingHelper.registerKeyBinding(MKeyMappings.KEY_STUDY);
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

package net.favouriteless.modopedia.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.client.MBookModel;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.favouriteless.modopedia.common.items.MBookItem;
import net.favouriteless.modopedia.util.ResourceUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

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
    }

}

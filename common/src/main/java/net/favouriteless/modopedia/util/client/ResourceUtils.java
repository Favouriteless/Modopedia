package net.favouriteless.modopedia.util.client;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.stream.Stream;

public class ResourceUtils {

    private static final String BOOK_MODEL_DIRECTORY = "models/item/" + Modopedia.MOD_ID + "_books";

    public static Stream<ResourceLocation> getBookModels() {
        return Minecraft.getInstance().getResourceManager().listResources(BOOK_MODEL_DIRECTORY, l -> true).keySet().stream()
                .map(l -> ResourceLocation.fromNamespaceAndPath(l.getNamespace(), l.getPath().substring(7, l.getPath().length()-5)));
    }

}

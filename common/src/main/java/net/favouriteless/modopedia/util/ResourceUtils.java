package net.favouriteless.modopedia.util;

import net.favouriteless.modopedia.Modopedia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class ResourceUtils {

    private static final String BOOK_MODEL_DIRECTORY = "models/item/" + Modopedia.MOD_ID + "_books";

    public static Stream<ResourceLocation> getBookModels() {
        return Minecraft.getInstance().getResourceManager().listResources(BOOK_MODEL_DIRECTORY, l -> true).keySet().stream()
                .map(l -> ResourceLocation.fromNamespaceAndPath(l.getNamespace(), l.getPath().substring(7, l.getPath().length()-5)));
    }

}

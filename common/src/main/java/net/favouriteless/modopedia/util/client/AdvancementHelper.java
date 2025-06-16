package net.favouriteless.modopedia.util.client;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.resources.ResourceLocation;

public class AdvancementHelper {

    public static boolean hasAdvancement(ResourceLocation id) {
        ClientAdvancements advancements = Minecraft.getInstance().getConnection().getAdvancements();

        AdvancementHolder holder = advancements.get(id);
        if(holder == null)
            return false;

        AdvancementProgress progress = advancements.progress.get(holder);
        return progress != null && progress.isDone();
    }

}

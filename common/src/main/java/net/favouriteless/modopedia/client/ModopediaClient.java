package net.favouriteless.modopedia.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.ModopediaApi.EntryAssociation;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry;
import net.favouriteless.modopedia.client.init.MBookScreenFactories;
import net.favouriteless.modopedia.client.init.MPageComponents;
import net.favouriteless.modopedia.platform.ClientServices;
import net.favouriteless.modopedia.platform.services.IClientRegistryHelper.KeyConflictContext;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModopediaClient {

    public static final int STUDY_TICKS_MAX = 20;
    public static int STUDY_TICKS;

    public static final KeyMapping KEY_MAPPING = ClientServices.CLIENT_REGISTRY.register("study", InputConstants.KEY_LCONTROL,
            KeyMapping.CATEGORY_INVENTORY, KeyConflictContext.GUI);

    public static void init() {
        MPageComponents.load();
        MBookScreenFactories.load();
    }

    public static List<Component> getStudyTooltips(ItemStack stack) {
        List<Component> out = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        if(!(mc.screen instanceof AbstractContainerScreen<?>))
            return out;

        String langCode = Minecraft.getInstance().options.languageCode;
        EntryAssociation association = ItemAssociationRegistry.getAssociation(langCode, BuiltInRegistries.ITEM.getKey(stack.getItem()));

        if(association != null) {
            Component study = Component.translatable(Modopedia.translation("tooltip", "study"), KEY_MAPPING.getTranslatedKeyMessage().getString()).withStyle(ChatFormatting.DARK_GRAY);
            out.add(study);

            int width = mc.font.width(study);
            int studyBars = width / mc.font.width("|");

            int g = Math.round(STUDY_TICKS / (float)STUDY_TICKS_MAX * studyBars);
            int r = studyBars - g;

            if(g > 0) {
                MutableComponent bars = Component.literal("|".repeat(g)).withStyle(ChatFormatting.GREEN);
                if(r > 0)
                    bars.append(Component.literal("|".repeat(r)).withStyle(ChatFormatting.RED));

                out.add(bars);
            }
        }
        return out;
    }

}

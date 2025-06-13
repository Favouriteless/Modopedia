package net.favouriteless.modopedia.book;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.ModopediaApi.EntryAssociation;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.ModopediaClient;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class StudyManager {

    public static final int STUDY_TICKS_MAX = 20;

    private static int studyTicks = 0;

    public static void study(Item item) {
        String langCode = Minecraft.getInstance().options.languageCode;
        EntryAssociation association = ItemAssociationRegistry.getAssociation(langCode, BuiltInRegistries.ITEM.getKey(item));

        if(association != null && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), ModopediaClient.KEY_STUDY.getDefaultKey().getValue())) {
            if(++studyTicks < STUDY_TICKS_MAX)
                return;
            stopStudying();
            BookOpenHandler.tryOpenEntry(association.book(), association.entryId());
        }
        else {
            studyTicks = Math.max(studyTicks - 1, 0);
        }
    }

    public static int getStudyTicks() {
        return studyTicks;
    }

    public static void stopStudying() {
        studyTicks = 0;
    }

    public static List<Component> getTooltips(Item item) {
        Minecraft mc = Minecraft.getInstance();
        if(!(mc.screen instanceof AbstractContainerScreen<?>))
            return List.of();

        EntryAssociation association = ItemAssociationRegistry.getAssociation(mc.options.languageCode, BuiltInRegistries.ITEM.getKey(item));

        if(association == null)
            return List.of();

        List<Component> out = new ArrayList<>();

        Component study = Component.translatable(
                Modopedia.translation("tooltip", "study"),
                ModopediaClient.KEY_STUDY.getTranslatedKeyMessage().getString()
        ).withStyle(ChatFormatting.DARK_GRAY);

        out.add(study);

        int width = mc.font.width(study);
        int studyBars = width / mc.font.width("|");

        int g = Math.round(studyTicks / (float)STUDY_TICKS_MAX * studyBars);
        int r = studyBars - g;

        if(g > 0) {
            MutableComponent bars = Component.literal("|".repeat(g)).withStyle(ChatFormatting.GREEN);
            if(r > 0)
                bars.append(Component.literal("|".repeat(r)).withStyle(ChatFormatting.RED));

            out.add(bars);
        }

        return out;
    }

}

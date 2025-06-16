package net.favouriteless.modopedia.book;

import com.mojang.blaze3d.platform.InputConstants;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry;
import net.favouriteless.modopedia.book.registries.client.ItemAssociationRegistry.EntryAssociation;
import net.favouriteless.modopedia.client.BookOpenHandler;
import net.favouriteless.modopedia.client.init.MKeyMappings;
import net.favouriteless.modopedia.client.screens.books.BookScreen;
import net.favouriteless.modopedia.common.ServerConfig;
import net.favouriteless.modopedia.util.common.InventoryUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class StudyManager {

    public static final int STUDY_TICKS_MAX = 20;

    private static int studyTicks = 0;

    public static void study(Item item) {
        String lang = Minecraft.getInstance().options.languageCode;
        EntryAssociation association = getAssociation(lang, item);

        if(association != null && InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), MKeyMappings.KEY_STUDY.key.getValue())) {
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
        if(!(mc.screen instanceof AbstractContainerScreen<?>) && !(mc.screen instanceof BookScreen))
            return List.of();

        EntryAssociation association = getAssociation(mc.options.languageCode, item);

        if(association == null)
            return List.of();

        List<Component> out = new ArrayList<>();

        Component study = Component.translatable(
                Modopedia.translation("tooltip", "study"),
                MKeyMappings.KEY_STUDY.getTranslatedKeyMessage().getString()
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

    public static EntryAssociation getAssociation(String lang, Item item) {
        Minecraft mc = Minecraft.getInstance();

        EntryAssociation association = ItemAssociationRegistry.getAssociation(mc.options.languageCode, item);
        if(association == null || ServerConfig.INSTANCE.studyRequiresBooks.get() && !InventoryUtils.hasBook(mc.player, association.book()))
            return null;

        return association;
    }

}

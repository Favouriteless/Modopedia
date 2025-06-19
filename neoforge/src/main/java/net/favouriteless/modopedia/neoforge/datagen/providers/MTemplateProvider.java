package net.favouriteless.modopedia.neoforge.datagen.providers;

import com.google.gson.JsonElement;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.components.*;
import net.favouriteless.modopedia.api.datagen.builders.page_components.templates.base.*;
import net.favouriteless.modopedia.api.datagen.builders.page_components.templates.recipes.*;
import net.favouriteless.modopedia.api.datagen.providers.TemplateProvider;
import net.favouriteless.modopedia.client.template_processors.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MTemplateProvider extends TemplateProvider {

    public MTemplateProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super(Modopedia.MOD_ID, registries, output);
    }

    @Override
    protected void build(BiConsumer<String, JsonElement> output) {
        buildBaseTemplates(output);
        buildRecipeTemplates(output);
    }

    public void buildBaseTemplates(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of(FramedItemBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_SMALL)
                .components(
                        SmallFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        ItemBuilder.of("#items")
                )
                .defaultValue("width", 16)
                .build(output);

        TemplateBuilder.of(MediumFramedImageBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_MEDIUM)
                .components(
                        MediumFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        ImageBuilder.of("#images")
                                .width("#width")
                                .height("#width")
                )
                .defaultValue("width", 50)
                .build(output);

        TemplateBuilder.of(LargeFramedImageBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_LARGE)
                .components(
                        LargeFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        ImageBuilder.of("#images")
                                .width("#width")
                                .height("#width")
                )
                .defaultValue("width", 100)
                .build(output);

        TemplateBuilder.of(MediumFramedMultiblockBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_MEDIUM)
                .components(
                        MediumFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        MultiblockBuilder.of()
                                .multiblock("#multiblock")
                                .multiblockId("#multiblock_id")
                                .width("#width")
                                .height("#width")
                                .offsetX("#offset_x")
                                .offsetY("#offset_y")
                                .scale("#scale")
                                .noOffsets("#no_offsets")
                )
                .defaultValue("width", 50)
                .build(output);

        TemplateBuilder.of(LargeFramedMultiblockBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_LARGE)
                .components(
                        LargeFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        MultiblockBuilder.of()
                                .multiblock("#multiblock")
                                .multiblockId("#multiblock_id")
                                .width("#width")
                                .height("#width")
                                .offsetX("#offset_x")
                                .offsetY("#offset_y")
                                .scale("#scale")
                                .noOffsets("#no_offsets")
                )
                .defaultValue("width", 100)
                .build(output);

        TemplateBuilder.of(HeaderedTextBuilder.ID.getPath())
                .processor(HeaderedTextProcessor.ID)
                .components(
                        SeparatorBuilder.of()
                                .y(10),
                        HeaderBuilder.of("#header")
                                .centered("#centered")
                                .bold("#bold")
                                .underline("#underline")
                                .colour("#colour"),
                        TextBuilder.of("#text")
                                .width("#width")
                                .lineHeight("#line_height")
                                .y("#text_y")
                                .justify("#justify")
                )
                .build(output);

        TemplateBuilder.of(FramedCraftingGridBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_CRAFTING)
                .components(
                        CraftingGridBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        ItemBuilder.of("#items")
                                .rowMax(3)
                                .padding(17)
                )
                .defaultValue("width", 50)
                .build(output);

        TemplateBuilder.of(MediumFramedEntityBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_MEDIUM)
                .components(
                        MediumFrameBuilder.of()
                            .x("#frame_offset")
                            .y("#frame_offset"),
                        EntityBuilder.of("#entity")
                                .tag("#tag")
                                .width("#width")
                                .height("#width")
                                .offsetY("#offset_y")
                                .scale("#scale")
                )
                .defaultValue("width",50)
                .build(output);

        TemplateBuilder.of(LargeFramedEntityBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_LARGE)
                .components(
                        LargeFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        EntityBuilder.of("#entity")
                                .tag("#tag")
                                .width("#width")
                                .height("#width")
                                .offsetY("#offset_y")
                                .scale("#scale")
                )
                .defaultValue("width",100)
                .build(output);

        TemplateBuilder.of(MediumFramedShowcaseBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_MEDIUM)
                .components(
                        MediumFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        ShowcaseBuilder.of("#items")
                                .width("#width")
                                .height("#width")
                                .scale("#scale")
                )
                .defaultValue("width", 50)
                .build(output);

        TemplateBuilder.of(LargeFramedShowcaseBuilder.ID.getPath())
                .processor(FrameSpacingProcessor.ID_LARGE)
                .components(
                        LargeFrameBuilder.of()
                                .x("#frame_offset")
                                .y("#frame_offset"),
                        ShowcaseBuilder.of("#items")
                                .width("#width")
                                .height("#width")
                                .scale("#scale")
                )
                .defaultValue("width", 100)
                .build(output);
    }

    public void buildRecipeTemplates(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of(CookingRecipeBuilder.ID.getPath())
                .processor(CookingRecipeProcessor.ID)
                .components(
                        FramedItemBuilder.of("#input"),
                        CraftingArrowBuilder.of()
                                .x("#arrow_x")
                                .y("#arrow_y"),
                        CraftingFlameBuilder.of()
                                .x("#flame_x")
                                .y("#flame_y"),
                        TooltipBuilder.of("#tooltip")
                                .x("#arrow_x")
                                .y("#arrow_y")
                                .width("#arrow_width")
                                .height("#arrow_height"),
                        FramedItemBuilder.of("#output")
                                .x("#output_x")
                )
                .build(output);

        TemplateBuilder.of(SmeltingRecipeBuilder.ID.getPath())
                .components(
                        CookingRecipeBuilder.of("#recipe")
                                .tooltip(new String[] { "template.modopedia.smelting_recipe" })
                )
                .build(output);

        TemplateBuilder.of(BlastingRecipeBuilder.ID.getPath())
                .components(
                        CookingRecipeBuilder.of("#recipe")
                                .tooltip(new String[] { "template.modopedia.blasting_recipe" })
                )
                .build(output);

        TemplateBuilder.of(SmokingRecipeBuilder.ID.getPath())
                .components(
                        CookingRecipeBuilder.of("#recipe")
                                .tooltip(new String[] { "template.modopedia.smoking_recipe" })
                )
                .build(output);

        TemplateBuilder.of(CraftingTableRecipeBuilder.ID.getPath())
                .processor(CraftingTableRecipeProcessor.ID)
                .components(
                        FramedCraftingGridBuilder.of("#inputs"),
                        CraftingArrowBuilder.of()
                                .x("#arrow_x")
                                .y("#arrow_y"),
                        TooltipBuilder.of("#tooltip")
                                .x("#arrow_x")
                                .y("#arrow_y")
                                .width("#arrow_width")
                                .height("#arrow_height"),
                        FramedItemBuilder.of("#output")
                                .x("#output_x")
                                .y(17)
                )
                .build(output);
    }

}

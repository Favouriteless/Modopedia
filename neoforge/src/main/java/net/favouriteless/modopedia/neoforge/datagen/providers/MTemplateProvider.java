package net.favouriteless.modopedia.neoforge.datagen.providers;

import com.google.gson.JsonElement;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.components.*;
import net.favouriteless.modopedia.api.datagen.builders.templates.*;
import net.favouriteless.modopedia.api.datagen.builders.templates.page.DoubleCraftingRecipeBuilder;
import net.favouriteless.modopedia.api.datagen.builders.templates.page.HeaderedTextBuilder;
import net.favouriteless.modopedia.api.datagen.builders.templates.recipes.*;
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
        buildPageTemplates(output);
    }

    public void buildBaseTemplates(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of(FramedItemBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        SmallFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ItemBuilder.of("#items")
                )
                .defaultValue("p_width", 16)
                .defaultValue("p_widget", "small_frame")
                .build(output);

        TemplateBuilder.of(MediumFramedImageBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        MediumFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ImageBuilder.of("#images")
                                .width("#width")
                                .height("#width")
                )
                .defaultValue("p_width", 50)
                .defaultValue("p_widget", "medium_frame")
                .build(output);

        TemplateBuilder.of(LargeFramedImageBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        LargeFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ImageBuilder.of("#images")
                                .width("#width")
                                .height("#width")
                )
                .defaultValue("p_width", 100)
                .defaultValue("p_widget", "large_frame")
                .build(output);

        TemplateBuilder.of(MediumFramedMultiblockBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        MediumFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
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
                .defaultValue("p_width", 50)
                .defaultValue("p_widget", "medium_frame")
                .build(output);

        TemplateBuilder.of(LargeFramedMultiblockBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        LargeFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
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
                .defaultValue("p_width", 100)
                .defaultValue("p_widget", "large_frame")
                .build(output);

        TemplateBuilder.of(FramedCraftingGridBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        CraftingGridBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ItemBuilder.of("#items")
                                .rowMax(3)
                                .padding(17)
                )
                .defaultValue("p_width", 50)
                .defaultValue("p_widget", "crafting_grid")
                .build(output);

        TemplateBuilder.of(MediumFramedEntityBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        MediumFrameBuilder.of()
                            .x("#p_offset")
                            .y("#p_offset"),
                        EntityBuilder.of("#entity")
                                .tag("#tag")
                                .width("#width")
                                .height("#width")
                                .offsetY("#offset_y")
                                .scale("#scale")
                )
                .defaultValue("p_width", 50)
                .defaultValue("p_widget", "medium_frame")
                .build(output);

        TemplateBuilder.of(LargeFramedEntityBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        LargeFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        EntityBuilder.of("#entity")
                                .tag("#tag")
                                .width("#width")
                                .height("#width")
                                .offsetY("#offset_y")
                                .scale("#scale")
                )
                .defaultValue("p_width", 100)
                .defaultValue("p_widget", "large_frame")
                .build(output);

        TemplateBuilder.of(MediumFramedShowcaseBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        MediumFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ShowcaseBuilder.of("#items")
                                .width("#width")
                                .height("#width")
                                .scale("#scale")
                )
                .defaultValue("p_width", 50)
                .defaultValue("p_widget", "medium_frame")
                .build(output);

        TemplateBuilder.of(LargeFramedShowcaseBuilder.ID.getPath())
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        LargeFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ShowcaseBuilder.of("#items")
                                .width("#width")
                                .height("#width")
                                .scale("#scale")
                )
                .defaultValue("p_width", 100)
                .defaultValue("p_widget", "large_frame")
                .build(output);
    }

    public void buildRecipeTemplates(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of(CraftingRecipeBuilder.ID.getPath())
                .processor(CraftingRecipeProcessor.ID)
                .components(
                        FramedCraftingGridBuilder.of("#p_inputs")
                                .x("#p_grid_x"),
                        CraftingArrowBuilder.of()
                                .x("#p_arrow_x")
                                .y("#p_arrow_y"),
                        TooltipBuilder.of("#p_tooltip")
                                .x("#p_arrow_x")
                                .y("#p_arrow_y")
                                .width("#p_arrow_width")
                                .height("#p_arrow_height"),
                        FramedItemBuilder.of("#p_output")
                                .x("#p_output_x")
                                .y(17)
                )
                .build(output);

        TemplateBuilder.of(CookingRecipeBuilder.ID.getPath())
                .processor(CookingRecipeProcessor.ID)
                .components(
                        FramedItemBuilder.of("#p_inputs")
                                .x("#p_input_x"),
                        CraftingArrowBuilder.of()
                                .x("#p_arrow_x")
                                .y("#p_arrow_y"),
                        CraftingFlameBuilder.of()
                                .x("#p_flame_x")
                                .y("#p_flame_y"),
                        TooltipBuilder.of("#p_tooltip")
                                .x("#p_arrow_x")
                                .y("#p_arrow_y")
                                .width("#p_arrow_width")
                                .height("#p_arrow_height"),
                        FramedItemBuilder.of("#p_output")
                                .x("#p_output_x")
                )
                .build(output);
    }

    public void buildPageTemplates(BiConsumer<String, JsonElement> output) {
        TemplateBuilder.of(HeaderedTextBuilder.ID.getPath())
                .processor(HeaderedTextProcessor.ID)
                .components(
                        SeparatorBuilder.of().y(10),
                        HeaderBuilder.of("#header")
                                .centered("#centered")
                                .bold("#bold")
                                .underline("#underline")
                                .colour("#colour"),
                        TextBuilder.of("#text")
                                .y("#text_y")
                                .width("#width")
                                .lineHeight("#line_height")
                                .justify("#justify")
                )
                .build(output);

        TemplateBuilder.of(DoubleCraftingRecipeBuilder.ID.getPath())
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "crafting_recipe")),
                        SeparatorBuilder.of().y(10),
                        CraftingRecipeBuilder.of("#recipe1")
                                .y("#p_vertical_y1"),
                        CraftingRecipeBuilder.of("#recipe2")
                                .y("#p_vertical_y2")
                )
                .defaultValue("p_with_header", true)
                .defaultValue("p_vertical_items", 2)
                .defaultValue("p_vertical_size_widget", "crafting_grid")
                .build(output);


    }

}

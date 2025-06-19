package net.favouriteless.modopedia.neoforge.datagen.providers;

import com.google.gson.JsonElement;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.builders.TemplateBuilder;
import net.favouriteless.modopedia.api.datagen.builders.page_components.components.*;
import net.favouriteless.modopedia.api.datagen.builders.templates.*;
import net.favouriteless.modopedia.api.datagen.builders.templates.page.*;
import net.favouriteless.modopedia.api.datagen.builders.templates.recipes.CookingRecipeBuilder;
import net.favouriteless.modopedia.api.datagen.builders.templates.recipes.CraftingRecipeBuilder;
import net.favouriteless.modopedia.api.datagen.providers.TemplateProvider;
import net.favouriteless.modopedia.book.text.Justify;
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
                .defaultValue("width", 16)
                .defaultValue("widget", "small_frame")
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
                .defaultValue("width", 50)
                .defaultValue("widget", "medium_frame")
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
                .defaultValue("width", 100)
                .defaultValue("widget", "large_frame")
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
                                .viewAngle("#view_angle")
                )
                .defaultValue("width", 50)
                .defaultValue("widget", "medium_frame")
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
                                .viewAngle("#view_angle")
                )
                .defaultValue("width", 100)
                .defaultValue("widget", "large_frame")
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
                .defaultValue("width", 50)
                .defaultValue("widget", "crafting_grid")
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
                .defaultValue("width", 50)
                .defaultValue("widget", "medium_frame")
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
                .defaultValue("width", 100)
                .defaultValue("widget", "large_frame")
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
                .defaultValue("width", 50)
                .defaultValue("widget", "medium_frame")
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
                .defaultValue("width", 100)
                .defaultValue("widget", "large_frame")
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

        TemplateBuilder.of(CraftingPageBuilder.ID.getPath())
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "crafting_recipe")),
                        SeparatorBuilder.of().y(10),
                        CraftingRecipeBuilder.of("#recipe")
                                .y("#p_vertical_y1")
                )
                .defaultValue("with_header", true)
                .defaultValue("vertical_items", 1)
                .defaultValue("vertical_size_widget", "crafting_grid")
                .build(output);

        TemplateBuilder.of(DoubleCraftingPageBuilder.ID.getPath())
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "crafting_recipe")),
                        SeparatorBuilder.of().y(10),
                        CraftingRecipeBuilder.of("#recipe1")
                                .y("#p_vertical_y1"),
                        CraftingRecipeBuilder.of("#recipe2")
                                .y("#p_vertical_y2")
                )
                .defaultValue("with_header", true)
                .defaultValue("vertical_items", 2)
                .defaultValue("vertical_size_widget", "crafting_grid")
                .build(output);

        TemplateBuilder.of(CookingPageBuilder.ID.getPath())
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "recipe")),
                        SeparatorBuilder.of().y(10),
                        CookingRecipeBuilder.of("#recipe")
                                .y("#p_vertical_y1")
                )
                .defaultValue("with_header", true)
                .defaultValue("vertical_items", 1)
                .defaultValue("vertical_size", 33)
                .build(output);

        TemplateBuilder.of(DoubleCookingPageBuilder.ID.getPath())
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "recipe")),
                        SeparatorBuilder.of().y(10),
                        CookingRecipeBuilder.of("#recipe1")
                                .y("#p_vertical_y1"),
                        CookingRecipeBuilder.of("#recipe2")
                                .y("#p_vertical_y2")
                )
                .defaultValue("with_header", true)
                .defaultValue("vertical_items", 2)
                .defaultValue("vertical_size", 33)
                .build(output);

        TemplateBuilder.of(MultiblockPageBuilder.ID.getPath())
                .processor(DescriptionPageProcessor.ID)
                .components(
                        MultiblockBuilder.of()
                                .x("#p_x")
                                .multiblock("#multiblock")
                                .multiblockId("#multiblock_id")
                                .width("#width")
                                .height("#height")
                                .offsetX("#offset_x")
                                .offsetY("#offset_y")
                                .scale("#scale")
                                .viewAngle("#view_angle")
                                .noOffsets("#no_offsets"),
                        TextBuilder.of("#text")
                                .y("#p_text_y")
                                .width("#width")
                                .justify(Justify.CENTER)
                )
                .defaultValue("width", 100)
                .defaultValue("height", 100)
                .build(output);

        TemplateBuilder.of(BlockPageBuilder.ID.getPath())
                .processor(BlockMultiblockProcessor.ID)
                .components(
                        MultiblockPageBuilder.of("#text")
                                .multiblock("#p_multiblock")
                                .width("#width")
                                .height("#height")
                                .offsetX("#offset_x")
                                .offsetY("#offset_y")
                                .scale("#scale")
                                .viewAngle("#view_angle")
                                .noOffsets(true)
                )
                .defaultValue("width", 100)
                .defaultValue("height", 100)
                .build(output);
    }

}

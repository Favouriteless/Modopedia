package net.favouriteless.modopedia.neoforge.datagen.providers;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.datagen.BookOutput;
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

public class MTemplateProvider extends TemplateProvider {

    public MTemplateProvider(CompletableFuture<Provider> registries, PackOutput output) {
        super(Modopedia.MOD_ID, registries, output);
    }

    @Override
    protected void build(Provider registries, BookOutput output) {
        buildBaseTemplates(output);
        buildRecipeTemplates(output);
        buildPageTemplates(output);
    }

    public void buildBaseTemplates(BookOutput output) {
        TemplateBuilder.of()
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        SmallFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ItemBuilder.of("#items")
                )
                .defaultValue("width", 16)
                .defaultValue("widget", "small_frame")
                .build(FramedItemBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        SmallFrameBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ItemGalleryBuilder.of("#display")
                )
                .defaultValue("width", 16)
                .defaultValue("widget", "small_frame")
                .build(FramedItemGalleryBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(MediumFramedImageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(LargeFramedImageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(MediumFramedMultiblockBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(LargeFramedMultiblockBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(FramedCraftingGridBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(WidgetSpacingProcessor.ID)
                .components(
                        CraftingGridBuilder.of()
                                .x("#p_offset")
                                .y("#p_offset"),
                        ItemGalleryBuilder.of("#display")
                )
                .defaultValue("width", 50)
                .defaultValue("widget", "crafting_grid")
                .build(GridFramedItemGalleryBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(MediumFramedEntityBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(LargeFramedEntityBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(MediumFramedShowcaseBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(LargeFramedShowcaseBuilder.ID.getPath(), output);
    }

    public void buildRecipeTemplates(BookOutput output) {
        TemplateBuilder.of()
                .processor(CraftingRecipeProcessor.ID)
                .components(
                        GridFramedItemGalleryBuilder.of("#p_inputs")
                                .x("#p_grid_x"),
                        CraftingArrowBuilder.of()
                                .x("#p_arrow_x")
                                .y("#p_arrow_y"),
                        TooltipBuilder.of("#p_tooltip")
                                .x("#p_arrow_x")
                                .y("#p_arrow_y")
                                .width("#p_arrow_width")
                                .height("#p_arrow_height"),
                        FramedItemGalleryBuilder.of("#p_output")
                                .x("#p_output_x")
                                .y(17)
                )
                .build(CraftingRecipeBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(CookingRecipeProcessor.ID)
                .components(
                        FramedItemGalleryBuilder.of("#p_inputs")
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
                        FramedItemGalleryBuilder.of("#p_output")
                                .x("#p_output_x")
                )
                .build(CookingRecipeBuilder.ID.getPath(), output);
    }

    public void buildPageTemplates(BookOutput output) {
        TemplateBuilder.of()
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
                .build(HeaderedTextBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(CraftingPageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
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
                .build(DoubleCraftingPageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "cooking_recipe")),
                        SeparatorBuilder.of().y(10),
                        CookingRecipeBuilder.of("#recipe")
                                .y("#p_vertical_y1")
                )
                .defaultValue("with_header", true)
                .defaultValue("vertical_items", 1)
                .defaultValue("vertical_size", 33)
                .build(CookingPageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(VerticalSpacingProcessor.ID)
                .components(
                        HeaderBuilder.of(Modopedia.translation("template", "cooking_recipe")),
                        SeparatorBuilder.of().y(10),
                        CookingRecipeBuilder.of("#recipe1")
                                .y("#p_vertical_y1"),
                        CookingRecipeBuilder.of("#recipe2")
                                .y("#p_vertical_y2")
                )
                .defaultValue("with_header", true)
                .defaultValue("vertical_items", 2)
                .defaultValue("vertical_size", 33)
                .build(DoubleCookingPageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(DescriptionPageProcessor.ID)
                .components(
                        MultiblockBuilder.of()
                                .multiblock("#multiblock")
                                .multiblockId("#multiblock_id")
                                .width("#p_width")
                                .height("#height")
                                .offsetX("#offset_x")
                                .offsetY("#offset_y")
                                .scale("#scale")
                                .viewAngle("#view_angle")
                                .noOffsets("#no_offsets"),
                        TextBuilder.of("#text")
                                .y("#p_text_y")
                                .width("#p_width")
                                .justify(Justify.CENTER)
                )
                .defaultValue("height", 100)
                .build(MultiblockPageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(BlockMultiblockProcessor.ID)
                .components(
                        MultiblockPageBuilder.of("#text")
                                .multiblock("#p_multiblock")
                                .height("#height")
                                .offsetX("#offset_x")
                                .offsetY("#offset_y")
                                .scale("#scale")
                                .viewAngle("#view_angle")
                                .noOffsets(true)
                )
                .defaultValue("height", 100)
                .build(BlockPageBuilder.ID.getPath(), output);

        TemplateBuilder.of()
                .processor(DescriptionPageProcessor.ID)
                .components(
                        EntityBuilder.of("#entity")
                                .tag("#tag")
                                .width("#p_width")
                                .height("#height")
                                .scale("#scale")
                                .offsetY("#offsetY"),
                        TextBuilder.of("#text")
                                .y("#p_text_y")
                                .width("#p_width")
                                .justify(Justify.CENTER)
                )
                .defaultValue("height", 100)
                .build(EntityPageBuilder.ID.getPath(), output);
    }

}

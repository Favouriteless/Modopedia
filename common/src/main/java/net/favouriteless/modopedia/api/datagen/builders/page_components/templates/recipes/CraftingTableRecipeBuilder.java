package net.favouriteless.modopedia.api.datagen.builders.page_components.templates.recipes;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.datagen.TemplateComponentBuilder;
import net.minecraft.resources.ResourceLocation;

public class CraftingTableRecipeBuilder extends TemplateComponentBuilder {

    public static final ResourceLocation ID = Modopedia.id("crafting_table_recipe");

    private final Either<ResourceLocation, String> recipe;

    protected CraftingTableRecipeBuilder(ResourceLocation recipe) {
        super(ID);
        this.recipe = Either.left(recipe);
    }

    protected CraftingTableRecipeBuilder(String recipe) {
        super(ID);
        this.recipe = Either.right(recipe);
    }

    public static CraftingTableRecipeBuilder of(ResourceLocation recipe) {
        return new CraftingTableRecipeBuilder(recipe);
    }

    public static CraftingTableRecipeBuilder of(String recipe) {
        return new CraftingTableRecipeBuilder(recipe);
    }

    @Override
    public CraftingTableRecipeBuilder x(int x) {
        return (CraftingTableRecipeBuilder)super.x(x);
    }

    @Override
    public CraftingTableRecipeBuilder x(String x) {
        return (CraftingTableRecipeBuilder)super.x(x);
    }

    @Override
    public CraftingTableRecipeBuilder y(int y) {
        return (CraftingTableRecipeBuilder)super.y(y);
    }

    @Override
    public CraftingTableRecipeBuilder y(String y) {
        return (CraftingTableRecipeBuilder)super.y(y);
    }

    @Override
    protected void build(JsonObject json) {
        json.add("recipe", resolve(recipe).orElseGet(() -> ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, orThrow(recipe)).getOrThrow()));
    }

}

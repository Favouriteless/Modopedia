package net.favouriteless.modopedia.client;

import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.registries.BookRegistry;
import net.favouriteless.modopedia.common.items.MBookItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BookItemModel implements BakedModel {

    private final BakedModel defaultModel;
    private final ItemOverrides overrides;

    public BookItemModel(BakedModel defaultModel, Function<ResourceLocation, BakedModel> modelGetter) {
        this.defaultModel = defaultModel;

        this.overrides = new ItemOverrides(FakeModelBaker.INSTANCE, null, Collections.emptyList()) {

            @Override
            public BakedModel resolve(@NotNull BakedModel original, @NotNull ItemStack stack,
                                      @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
                Book book = BookRegistry.get().getBook(MBookItem.getBookId(stack));
                return book == null ? original : modelGetter.apply(book.getItemModelLocation());
            }

        };
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random) {
        return defaultModel.getQuads(state, direction, random);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return defaultModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return defaultModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return defaultModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return defaultModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return defaultModel.getParticleIcon();
    }

    @Override
    public ItemTransforms getTransforms() {
        return defaultModel.getTransforms();
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }



    private static class FakeModelBaker implements ModelBaker {

        private static final ModelBaker INSTANCE = new FakeModelBaker();

        @Override
        public UnbakedModel getModel(ResourceLocation location) {
            return null;
        }

        @Override
        public @Nullable BakedModel bake(ResourceLocation location, ModelState transform) {
            return null;
        }

        public UnbakedModel getTopLevelModel(ModelResourceLocation location) {
            return null;
        }

        public BakedModel bake(ResourceLocation location, ModelState state, Function<Material, TextureAtlasSprite> sprites) {
            return null;
        }

        public BakedModel bakeUncached(UnbakedModel model, ModelState state, Function<Material, TextureAtlasSprite> sprites) {
            return null;
        }

        public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
            return null;
        }

    }

}

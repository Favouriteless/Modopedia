package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.Category;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryImpl implements Category {

    private final String title;
    private final String subtitle;
    private final String rawLandingText;
    private final Component landingText;
    private final ItemStack iconStack;
    private final ResourceLocation texture;

    private final List<String> entries;
    private final List<String> children;


    public CategoryImpl(String title, String subtitle, String rawLandingText, ItemStack iconStack,
                        ResourceLocation texture, List<String> entries, List<String> children) {
        this.title = title;
        this.subtitle = subtitle;
        this.rawLandingText = rawLandingText;
        this.landingText = Component.literal(rawLandingText);
        this.texture = texture;
        this.iconStack = iconStack;
        this.entries = entries;
        this.children = children;
    }

    @Override
    public String getTitle() {
        return title;
    }


    @Override
    public @Nullable String getSubtitle() {
        return subtitle;
    }

    @Override
    public @Nullable Component getLandingText() {
        return landingText;
    }

    @Override
    public @Nullable String getRawLandingText() {
        return rawLandingText;
    }

    @Override
    public ItemStack getIcon() {
        return iconStack;
    }

    @Override
    public @Nullable ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public List<String> getEntries() {
        return entries;
    }

    @Override
    public List<String> getChildren() {
        return children;
    }

    // ------------------------------------ Below this point is non-API functions ------------------------------------

    public static final Codec<CategoryImpl> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Category::getTitle),
            Codec.STRING.optionalFieldOf("subtitle").forGetter(c -> Optional.ofNullable(c.getSubtitle())),
            Codec.STRING.optionalFieldOf("landing_text").forGetter(c -> Optional.ofNullable(c.getRawLandingText())),
            ItemStack.CODEC.optionalFieldOf("icon").forGetter(c -> Optional.ofNullable(c.getIcon())),
            ResourceLocation.CODEC.optionalFieldOf("texture").forGetter(c -> Optional.ofNullable(c.getTexture())),
            Codec.STRING.listOf().optionalFieldOf("entries", new ArrayList<>()).forGetter(Category::getEntries),
            Codec.STRING.listOf().optionalFieldOf("children", new ArrayList<>()).forGetter(Category::getChildren)
    ).apply(instance, (title, subtitle, landingText, iconStack, texture, entries, children) ->
            new CategoryImpl(title, subtitle.orElse(null), landingText.orElse(null),
                             iconStack.orElse(null), texture.orElse(null), entries, children))
    );

}

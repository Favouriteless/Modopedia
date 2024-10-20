package net.favouriteless.modopedia.book;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.books.Book;
import net.favouriteless.modopedia.api.books.Category;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryImpl implements Category {

    private Book book; // This gets set after the Category is instantiated because we don't want it to be present in the persistent codec.
    
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
    public Book getBook() {
        return book;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Nullable
    @Override
    public Component getLandingText() {
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

    @Nullable
    @Override
    public ResourceLocation getTexture() {
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

    // --------------------------------- Below this point is non-API functions ---------------------------------

    public static final Codec<Category> PERSISTENT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("title").forGetter(Category::getTitle),
            Codec.STRING.optionalFieldOf("subtitle", null).forGetter(Category::getSubtitle),
            Codec.STRING.optionalFieldOf("landingText", null).forGetter(Category::getRawLandingText),
            ItemStack.CODEC.optionalFieldOf("iconStack", null).forGetter(Category::getIcon),
            ResourceLocation.CODEC.optionalFieldOf("texture", null).forGetter(Category::getTexture),
            Codec.STRING.listOf().optionalFieldOf("entries", new ArrayList<>()).forGetter(Category::getEntries),
            Codec.STRING.listOf().optionalFieldOf("children", new ArrayList<>()).forGetter(Category::getChildren)
    ).apply(instance, CategoryImpl::new));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, Category> STREAM_CODEC = new StreamCodec<>() { // The overloads don't provide enough fields :(
        
        @Override
        public Category decode(RegistryFriendlyByteBuf buf) {
            return new CategoryImpl(
                    ByteBufCodecs.STRING_UTF8.decode(buf),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null),
                    ByteBufCodecs.optional(ItemStack.STREAM_CODEC).decode(buf).orElse(null),
                    ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).decode(buf).orElse(null),
                    ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).decode(buf),
                    ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).decode(buf)
            );
        }
        
        @Override
        public void encode(RegistryFriendlyByteBuf buf, Category book) {
            ByteBufCodecs.STRING_UTF8.encode(buf, book.getTitle());
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getSubtitle()));
            ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(book.getRawLandingText()));
            ByteBufCodecs.optional(ItemStack.STREAM_CODEC).encode(buf, Optional.ofNullable(book.getIcon()));
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC).encode(buf, Optional.ofNullable(book.getTexture()));
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).encode(buf, book.getEntries());
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()).encode(buf, book.getChildren());
        }
        
    };

}

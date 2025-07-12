package net.favouriteless.modopedia.common.book_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public record PamphletBookType(LockedViewType lockedViewType, int titleX, int titleY, int subtitleX) implements BookType, LockedViewProvider {

    public static MapCodec<PamphletBookType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LockedViewType.CODEC.optionalFieldOf("locked_view_type", LockedViewType.HIDDEN).forGetter(PamphletBookType::lockedViewType),
            Codec.INT.optionalFieldOf("title_x", 37).forGetter(PamphletBookType::titleX),
            Codec.INT.optionalFieldOf("title_y", 7).forGetter(PamphletBookType::titleX),
            Codec.INT.optionalFieldOf("subtitle_x", 10).forGetter(PamphletBookType::titleX)
    ).apply(instance, PamphletBookType::new));

    @Override
    public Type<PamphletBookType> type() {
        return MBookTypes.PAMPHLET;
    }

}

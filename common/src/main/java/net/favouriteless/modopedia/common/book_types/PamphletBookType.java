package net.favouriteless.modopedia.common.book_types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public record PamphletBookType(LockedViewType lockedViewType) implements BookType {

    public static MapCodec<PamphletBookType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LockedViewType.CODEC.optionalFieldOf("locked_view_type", LockedViewType.HIDDEN).forGetter(PamphletBookType::lockedViewType)
    ).apply(instance, PamphletBookType::new));

    @Override
    public Type<PamphletBookType> type() {
        return MBookTypes.PAMPHLET;
    }

}

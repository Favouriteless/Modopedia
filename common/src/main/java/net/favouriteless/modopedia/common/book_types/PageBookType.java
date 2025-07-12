package net.favouriteless.modopedia.common.book_types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public record PageBookType(LockedViewType lockedViewType) implements BookType, LockedViewProvider {

    public static MapCodec<PageBookType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LockedViewType.CODEC.optionalFieldOf("locked_view_type", LockedViewType.HIDDEN).forGetter(PageBookType::lockedViewType)
    ).apply(instance, PageBookType::new));

    @Override
    public Type<PageBookType> type() {
        return MBookTypes.PAGE;
    }

}

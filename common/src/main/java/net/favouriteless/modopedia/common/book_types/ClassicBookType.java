package net.favouriteless.modopedia.common.book_types;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public record ClassicBookType(LockedViewType lockedViewType) implements BookType {

    public static MapCodec<ClassicBookType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        LockedViewType.CODEC.optionalFieldOf("locked_view_type", LockedViewType.HIDDEN).forGetter(ClassicBookType::lockedViewType)
    ).apply(instance, ClassicBookType::new));

    @Override
    public Type<ClassicBookType> type() {
        return MBookTypes.CLASSIC;
    }

}

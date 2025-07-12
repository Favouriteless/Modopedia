package net.favouriteless.modopedia.common.book_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.book.BookType;
import net.favouriteless.modopedia.common.init.MBookTypes;

public record ClassicBookType(LockedViewType lockedViewType, int titleX, int titleY, int subtitleX) implements BookType, LockedViewProvider {

    public static MapCodec<ClassicBookType> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LockedViewType.CODEC.optionalFieldOf("locked_view_type", LockedViewType.HIDDEN).forGetter(ClassicBookType::lockedViewType),
            Codec.INT.optionalFieldOf("title_x", 37).forGetter(ClassicBookType::titleX),
            Codec.INT.optionalFieldOf("title_y", 7).forGetter(ClassicBookType::titleX),
            Codec.INT.optionalFieldOf("subtitle_x", 10).forGetter(ClassicBookType::titleX)
    ).apply(instance, ClassicBookType::new));

    @Override
    public Type<ClassicBookType> type() {
        return MBookTypes.CLASSIC;
    }

}

package net.favouriteless.modopedia.common.book_types;

import net.minecraft.util.StringRepresentable;

@SuppressWarnings("deprecation")
public enum LockedViewType implements StringRepresentable {
    HIDDEN("hidden"),
    TRANSLUCENT("translucent");

    public static final EnumCodec<LockedViewType> CODEC = StringRepresentable.fromEnum(LockedViewType::values);

    private final String name;

    LockedViewType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

}

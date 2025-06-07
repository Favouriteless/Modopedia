package net.favouriteless.modopedia.book.text;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum Justify implements StringRepresentable {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right");

    public static final Codec<Justify> CODEC = StringRepresentable.fromEnum(Justify::values);

    private final String name;

    Justify(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

}

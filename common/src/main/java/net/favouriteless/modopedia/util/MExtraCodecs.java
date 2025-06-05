package net.favouriteless.modopedia.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public class MExtraCodecs {

    public static final Codec<Character> CHAR = Codec.STRING.comapFlatMap(
            s -> s.length() == 1 ? DataResult.success(s.charAt(0)) : DataResult.error(() -> (s + " is not a valid character")),
            String::valueOf
    );

}

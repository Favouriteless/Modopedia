package net.favouriteless.modopedia.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.api.multiblock.StateMatcher;
import net.favouriteless.modopedia.util.MExtraCodecs;
import net.favouriteless.modopedia.util.PosUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

import java.util.List;
import java.util.Map;

public class DenseMultiblock implements Multiblock {

    public static final MapCodec<DenseMultiblock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.listOf().listOf().fieldOf("pattern").forGetter(m -> m.pattern),
            Codec.unboundedMap(MExtraCodecs.CHAR, StateMatcher.codec()).fieldOf("key").forGetter(m -> m.key)
    ).apply(instance, DenseMultiblock::new));

    protected final List<List<String>> pattern;
    protected final Map<Character, StateMatcher> key;

    protected final StateMatcher[] states;
    protected final Vec3i dimensions;

    public DenseMultiblock(List<List<String>> pattern, Map<Character, StateMatcher> key) {
        this.pattern = pattern;
        this.key = key;

        int xSize = pattern.getFirst().getFirst().length();
        int ySize = pattern.size();
        int zSize = pattern.getFirst().size();

        dimensions = new Vec3i(xSize, ySize, zSize);
        states = new StateMatcher[xSize * ySize * zSize];

        for(int y = 0; y < ySize; y++) {
            List<String> layer = pattern.get(y);

            if(layer.size() != zSize)
                throw new IllegalArgumentException("DenseMultiblock must have a rectangle footprint.");

            for(int z = 0; z < layer.size(); z++) {
                String row = layer.get(z);

                if(row.length() != xSize)
                    throw new IllegalArgumentException("DenseMultiblock must have a rectangle footprint.");

                for(int x = 0; x < row.length(); x++) {
                    states[PosUtils.posToIndex(x, y, z, xSize, ySize)] = key.get(row.charAt(x));
                }
            }
        }
    }

    @Override
    public Vec3i getDimensions() {
        return dimensions;
    }

    @Override
    public StateMatcher getStateMatcher(BlockPos pos) {
        return states[PosUtils.posToIndex(pos, dimensions)];
    }

    @Override
    public MapCodec<? extends Multiblock> typeCodec() {
        return CODEC;
    }

}

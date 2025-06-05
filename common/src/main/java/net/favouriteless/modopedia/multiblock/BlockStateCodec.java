package net.favouriteless.modopedia.multiblock;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Traister101 - <a href="https://gitlab.com/Traister101/alekiships/-/blob/dev/data-driven-boat-material/src/main/java/com/alekiponi/alekiships/util/AlekiShipsExtraCodecs.java">GitLab</a>
 */
public class BlockStateCodec {

    private static final Splitter.MapSplitter PROPERTY_VALUE_SPLITTER = Splitter.on(',').withKeyValueSeparator('=');

    /**
     * A codec for the more common {@link BlockStateParser} blockstate syntax (used in commands)
     * Unnamed properties use the default
     */
    public static final Codec<BlockState> CODEC = ExtraCodecs.NON_EMPTY_STRING.comapFlatMap(
            BlockStateCodec::getResult,
            BlockStateCodec::getEncoded
    );

    private static String getEncoded(BlockState state) {
        final var block = state.getBlock();
        final var defaultState = block.defaultBlockState();
        if (state == defaultState) return BuiltInRegistries.BLOCK.getKey(block).toString();

        final var stringBuilder = new StringBuilder();
        stringBuilder.append(BuiltInRegistries.BLOCK.getKey(block));
        final var values = state.getValues();
        if (!values.isEmpty()) {
            stringBuilder.append('[');
            stringBuilder.append(values.entrySet()
                    .stream()
                    .filter(entry -> {
                        final var key = entry.getKey();
                        return state.getValue(key) != defaultState.getValue(key);
                    })
                    .map(entry -> {
                        @SuppressWarnings("rawtypes") final Property property = entry.getKey();
                        @SuppressWarnings("unchecked") final var valueName = property.getName(entry.getValue());
                        return property.getName() + "=" + valueName;
                    })
                    .collect(Collectors.joining(",")));
            stringBuilder.append(']');
        }

        return stringBuilder.toString();
    }

    private static DataResult<BlockState> getResult(String string) {
        final var propertiesStart = string.indexOf('[');
        final var propertiesEnd = string.indexOf(']');
        // No encoded properties, use default blockstate
        if (propertiesStart == -1) {
            // Ensure there isn't a closing bracket
            if (propertiesEnd != -1) return DataResult.error(() -> "Missing opening '['");

            return parseBlock(string).map(Block::defaultBlockState);
        }
        // No closing bracket
        if (propertiesEnd == -1) return DataResult.error(() -> "Missing closing ']'");
        // Closing bracket is not the last character
        if (propertiesEnd != string.length() - 1) {
            return DataResult.error(() -> "No characters allowed after closing ']'");
        }

        final var blockResult = parseBlock(string.substring(0, propertiesStart));
        if (blockResult.isError()) return DataResult.error(((DataResult.Error<?>) blockResult)::message);

        final Block block = blockResult.getOrThrow();
        return parsePropertyValuesMap(string.substring(propertiesStart + 1, propertiesEnd),
                block.getStateDefinition()).map(map -> {
            var blockState = block.defaultBlockState();
            for (final var entry : map.entrySet()) {
                //noinspection unchecked,rawtypes
                blockState = blockState.setValue((Property) entry.getKey(), (Comparable) entry.getValue());
            }
            return blockState;
        });
    }

    private static DataResult<Block> parseBlock(final String registryName) {
        return ResourceLocation.read(registryName)
                .flatMap(id -> BuiltInRegistries.BLOCK.getOptional(id)
                        .map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Unknown block " + id)));
    }

    private static DataResult<Map<Property<?>, Comparable<?>>> parsePropertyValuesMap(final String properties,
                                                                                     final StateDefinition<?, ?> stateDefinition) {
        final Map<String, String> stringPropertyValuesMap;
        try { // Splitting can fail
            stringPropertyValuesMap = PROPERTY_VALUE_SPLITTER.split(properties);
        } catch (final IllegalArgumentException e) {
            return DataResult.error(() -> MessageFormat.format(
                    "Failure during parsing of properties. Reported cause: {0}. Ensure properties are formated like [property_a=value,property_b=value]",
                    e.getMessage()));
        }

        final var builder = ImmutableMap.<Property<?>, Comparable<?>>builderWithExpectedSize(
                stringPropertyValuesMap.size());

        for (final var propertyValuePair : stringPropertyValuesMap.entrySet()) {
            final var propertyName = propertyValuePair.getKey();
            final var property = stateDefinition.getProperty(propertyName);
            if (property == null) {
                return DataResult.error(() -> MessageFormat.format("Unknown property: ''{0}''", propertyName));
            }

            final var valueName = propertyValuePair.getValue();
            final var propertyValue = property.getValue(valueName);
            if (propertyValue.isEmpty()) {
                return DataResult.error(
                        () -> MessageFormat.format("Unknown value: ''{0}'' for property: ''{1}'' {2}", valueName,
                                propertyName, property.getPossibleValues()));
            }
            builder.put(property, propertyValue.get());
        }

        return DataResult.success(builder.build());
    }


}

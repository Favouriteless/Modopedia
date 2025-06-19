package net.favouriteless.modopedia.client.template_processors;

import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.api.Lookup.MutableLookup;
import net.favouriteless.modopedia.api.Variable;
import net.favouriteless.modopedia.api.book.Book;
import net.favouriteless.modopedia.api.book.TemplateProcessor;
import net.favouriteless.modopedia.api.multiblock.Multiblock;
import net.favouriteless.modopedia.client.multiblock.DenseMultiblock;
import net.favouriteless.modopedia.client.multiblock.state_matchers.SimpleStateMatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public class BlockMultiblockProcessor implements TemplateProcessor {

    public static final ResourceLocation ID = Modopedia.id("block_multiblock");

    @Override
    public void init(Book book, MutableLookup lookup, Level level) {
        BlockState state = lookup.get("block").as(BlockState.class);

        Multiblock multiblock = new DenseMultiblock(
                List.of(List.of("B")),
                Map.of('B', new SimpleStateMatcher(List.of(state)))
        );

        lookup.set("p_multiblock", Variable.of(multiblock));
    }

}

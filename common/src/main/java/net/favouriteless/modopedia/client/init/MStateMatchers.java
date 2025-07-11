package net.favouriteless.modopedia.client.init;

import net.favouriteless.modopedia.api.registries.client.StateMatcherRegistry;
import net.favouriteless.modopedia.client.multiblock.state_matchers.*;

public class MStateMatchers {

    public static void load() {
        StateMatcherRegistry registry = StateMatcherRegistry.get();

        registry.register(AirStateMatcher.ID, AirStateMatcher.CODEC);
        registry.register(SimpleStateMatcher.ID, SimpleStateMatcher.CODEC);
        registry.register(TagStateMatcher.ID, TagStateMatcher.CODEC);
        registry.register(EitherStateMatcher.ID, EitherStateMatcher.CODEC);
        registry.register(BothStateMatcher.ID, BothStateMatcher.CODEC);
    }

}

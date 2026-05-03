package net.favouriteless.modopedia.api.multiblock;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * An optional extension to {@link StateMatcher} used to perform post-init operations and/or matching on block entities.
 */
public interface BEStateMatcher<T extends BlockEntity> extends StateMatcher {

    /**
     * @return The {@link BlockEntityType} this StateMatcher is matching against. {@code null} is allowed, but the
     * matcher will not be able to use the type safe methods.
     */
    @Nullable default BlockEntityType<? extends T> getType() {
        return null;
    }

    /**
     * Check if a given {@link BlockEntity} is a valid match.
     *
     * @param be the {@link BlockEntity} being checked.
     *
     * @return {@code true} if the BEs match, otherwise {@code false}.
     */
    default boolean matches(T be) {
        return false;
    }

    /**
     * Check if a given {@link BlockEntity} is a valid match. If possible, overriding
     * {@link BEStateMatcher#matches(BlockEntity)} is preferred.
     *
     * @param be the {@link BlockEntity} being checked.
     *
     * @return {@code true} if the BEs match, otherwise {@code false}.
     */
    default boolean genericMatches(BlockEntity be) {
        return cast(be).map(this::matches).orElse(false);
    }

    /**
     * Called immediately after a {@link BlockEntity} is first created in a {@link MultiblockInstance}. Use this method
     * to do any setup such as setting fields.
     */
    default void init(T be) {}

    /**
     * Called immediately after a {@link BlockEntity} is first created in a {@link MultiblockInstance}. Use this method
     * to do any setup such as setting fields. If possible, overriding {@link BEStateMatcher#init(BlockEntity)} is
     * preferred.
     */
    default void genericInit(BlockEntity be) {
        cast(be).ifPresent(this::init);
    }

    @SuppressWarnings("unchecked")
    private Optional<T> cast(BlockEntity be) {
        return getType() == be.getType() ? Optional.of((T)be) : Optional.empty();
    }

}
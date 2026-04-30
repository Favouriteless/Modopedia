package net.favouriteless.modopedia.api.multiblock;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;

/**
 * An optional extension to {@link StateMatcher} used to perform post-init operations and/or matching on block entities.
 */
public interface BEStateMatcher<T extends BlockEntity> extends StateMatcher {

    /**
     * @return The {@link BlockEntityType} this StateMatcher is matching against. If the BE is not of this type,
     * matching will be skipped.
     */
    BlockEntityType<? extends T> getType();

    /**
     * Check if a given {@link BlockEntity} is a valid match.
     *
     * @param other the {@link BlockEntity} being checked.
     *
     * @return {@code true} if the BEs match, otherwise {@code false}.
     */
    boolean matches(T other);

    /**
     * Called immediately after a {@link BlockEntity} is first created in a {@link MultiblockInstance}. Use this method
     * to do any setup such as setting fields.
     */
    void postInit(T be);

    default void init(BlockEntity be) {
        cast(be).ifPresent(this::postInit);
    }

    default boolean tryMatches(BlockEntity other) {
        return cast(other).map(this::matches).orElse(false);
    }

    @SuppressWarnings("unchecked")
    default Optional<T> cast(BlockEntity be) {
        return getType() == be.getType() ? Optional.of((T)be) : Optional.empty();
    }

}
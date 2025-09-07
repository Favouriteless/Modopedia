package net.favouriteless.modopedia.api.multiblock;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Optional;

/**
 * An optional extension to {@link StateMatcher} used to perform post-init operations and/or matching on block entities.
 */
public interface BEStateMatcher<T extends BlockEntity> extends StateMatcher {

    /**
     * @return {@link BlockEntityType} this StateMatcher is matching against. If the BE does not have this type, matching
     * will be skipped.
     */
    BlockEntityType<? extends T> getType();

    /**
     * @param self {@link BlockEntity} belonging to this StateMatcher.
     * @param other {@link BlockEntity} being checked against.
     *
     * @return True if the BEs match, otherwise false.
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
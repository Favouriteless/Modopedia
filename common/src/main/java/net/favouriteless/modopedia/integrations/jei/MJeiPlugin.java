package net.favouriteless.modopedia.integrations.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.favouriteless.modopedia.Modopedia;
import net.favouriteless.modopedia.common.init.MDataComponents;
import net.favouriteless.modopedia.common.init.MItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@JeiPlugin
public class MJeiPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(MItems.BOOK.get(), new ISubtypeInterpreter<>() {

            @Override
            public @Nullable Object getSubtypeData(ItemStack item, UidContext context) {
                return item.get(MDataComponents.BOOK.get());
            }

            @Override
            public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                return "";
            }

        });
    }

    @Override
    public ResourceLocation getPluginUid() {
        return Modopedia.id("modopedia");
    }

}

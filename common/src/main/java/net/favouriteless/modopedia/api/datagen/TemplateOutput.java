package net.favouriteless.modopedia.api.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;

import net.minecraft.resources.RegistryOps;

public interface TemplateOutput {

	<T> RegistryOps<T> registryOps(DynamicOps<T> ops);

	void accept(String id, JsonElement template);
}
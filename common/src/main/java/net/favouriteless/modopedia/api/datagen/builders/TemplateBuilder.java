package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TemplateBuilder extends PageBuilder {

    private final String id;
    private final Map<String, Supplier<JsonElement>> defaults = new HashMap<>();
    private ResourceLocation processor;

    private TemplateBuilder(String id) {
        this.id = id;
    }

    public static TemplateBuilder of(String id) {
        return new TemplateBuilder(id);
    }

    public TemplateBuilder processor(ResourceLocation processor) {
        this.processor = processor;
        return this;
    }

    @Override
    public TemplateBuilder components(PageComponentBuilder... components) {
        return (TemplateBuilder)super.components(components);
    }

    public TemplateBuilder defaultValue(String key, Supplier<JsonElement> value) {
        defaults.put(key, value);
        return this;
    }

    public TemplateBuilder defaultValue(String key, Number number) {
        defaults.put(key, () -> new JsonPrimitive(number));
        return this;
    }

    public TemplateBuilder defaultValue(String key, String string) {
        defaults.put(key, () -> new JsonPrimitive(string));
        return this;
    }

    @Override
    protected JsonElement build() {
        JsonObject json = super.build().getAsJsonObject();

        if(processor != null)
            json.add("processor", ResourceLocation.CODEC.encodeStart(JsonOps.INSTANCE, processor).getOrThrow());

        for(Entry<String, Supplier<JsonElement>> entry : this.defaults.entrySet()) {
            json.add(entry.getKey(), entry.getValue().get());
        }

        return json;
    }

    public void build(BiConsumer<String, JsonElement> output) {
        output.accept(id, build());
    }

}

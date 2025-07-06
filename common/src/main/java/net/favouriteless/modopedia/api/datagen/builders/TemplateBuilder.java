package net.favouriteless.modopedia.api.datagen.builders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

public class TemplateBuilder extends PageBuilder  {

    private final Map<String, Supplier<JsonElement>> defaults = new HashMap<>();
    private ResourceLocation processor;

    private TemplateBuilder() {}

    public static TemplateBuilder of() {
        return new TemplateBuilder();
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

    public TemplateBuilder defaultValue(String key, boolean string) {
        defaults.put(key, () -> new JsonPrimitive(string));
        return this;
    }

    @Override
    public JsonElement build(RegistryOps<JsonElement> ops) {
        JsonObject json = super.build(ops).getAsJsonObject();

        if(processor != null)
            json.add("processor", ResourceLocation.CODEC.encodeStart(ops, processor).getOrThrow());

        for(Entry<String, Supplier<JsonElement>> entry : this.defaults.entrySet()) {
            json.add(entry.getKey(), entry.getValue().get());
        }

        return json;
    }

}

package net.favouriteless.modopedia.book.loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class MultiblockLoader extends JsonResourceLoader {

    public MultiblockLoader(Gson gson, String dir) {
        super(gson, dir);
    }

    @Override
    protected void load(Map<ResourceLocation, JsonElement> jsonMap) {

    }

}

package net.favouriteless.modopedia.book.loading;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.favouriteless.modopedia.multiblock.BlockStateCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class MultiblockLoader extends JsonResourceLoader {

    public MultiblockLoader(Gson gson, String dir) {
        super(gson, dir);
    }

    @Override
    protected void load(Map<ResourceLocation, JsonElement> jsonMap) {
        jsonMap.forEach((location, element) -> {
            Map<Character, BlockState> keyMap = new HashMap<>();

            JsonObject keyObj = element.getAsJsonObject().getAsJsonObject("key");

            for(String key : keyObj.keySet()) {

                if(!key.matches("[A-Za-z]"))
                    throw new IllegalArgumentException("Multiblock ");

                BlockState state = BlockStateCodec.CODEC.parse(JsonOps.INSTANCE, keyObj.get(key)).getOrThrow();
                keyMap.put(key.charAt(0), state);
            }
        });
    }

}

package net.favouriteless.modopedia.book.registries.client;

import net.favouriteless.modopedia.api.ModopediaApi.EntryAssociation;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ItemAssociationRegistry {

    private static final Map<String, Map<ResourceLocation, EntryAssociation>> associations = new HashMap<>();

    public static EntryAssociation getAssociation(String langCode, ResourceLocation itemId) {
        return associations.containsKey(langCode) ? associations.get(langCode).get(itemId) : null;
    }

    public static void register(String langCode, ResourceLocation item, EntryAssociation association) {
        associations.computeIfAbsent(langCode, k -> new HashMap<>()).put(item, association);
    }

    public static void removeBook(ResourceLocation id) {
        for(Map<ResourceLocation, EntryAssociation> map : associations.values()) {
            Iterator<Entry<ResourceLocation, EntryAssociation>> iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
                EntryAssociation assoc = iterator.next().getValue();
                if(assoc.book().equals(id))
                    iterator.remove();
            }
        }
    }

}

package dev.rollczi.liteenchants.util;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.Tag;
import io.papermc.paper.registry.tag.TagKey;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

@SuppressWarnings("UnstableApiUsage")
public final class ItemTypeUtil {

    private static final Registry<ItemType> ITEM_REGISTRY = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM);
    private static final Map<TagKey<ItemType>, Set<Material>> MATERIALS_CACHE = new HashMap<>();

    private ItemTypeUtil() {
    }

    public static boolean isIncluded(ItemStack itemStack, TagKey<ItemType> type) {
        return isIncluded(itemStack.getType(), type);
    }

    public static boolean isIncluded(Material material, TagKey<ItemType> type) {
        return toMaterials(type).contains(material);
    }

    public static Set<Material> toMaterials(TagKey<ItemType> tagKey) {
        return MATERIALS_CACHE.computeIfAbsent(tagKey, ignore -> toMaterials0(tagKey));
    }
    
    private static Set<Material> toMaterials0(TagKey<ItemType> tagKey) {
        Set<Material> materials = new HashSet<>();
        Tag<ItemType> tag = ITEM_REGISTRY.getTag(tagKey);

        for (TypedKey<ItemType> itemTypeTypedKey : tag) {
            ItemType type = ITEM_REGISTRY.get(itemTypeTypedKey);
            materials.add(type.asMaterial());
        }

        return materials;
    }

}

package dev.rollczi.liteenchants.enchant;

import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.TagKey;
import java.util.function.Function;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;

@SuppressWarnings("UnstableApiUsage")
public record Enchant<CONFIG extends EnchantConfig>(
    TypedKey<Enchantment> key,
    TagKey<ItemType> supportedItems,
    EquipmentSlotGroup activeSlots,
    Function<EnchantsConfiguration, CONFIG> configProvider,
    EnchantCreator<CONFIG> creator
) {

    public CONFIG config(EnchantsConfiguration configuration) {
        return configProvider.apply(configuration);
    }

    public Enchantment toEnchantment() {
        return Enchants.getEnchantment(key);
    }

}

package dev.rollczi.liteenchants.enchant.effect;

import dev.rollczi.liteenchants.enchant.Enchant;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.AbstractLeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import io.papermc.paper.registry.tag.TagKey;
import java.util.function.Function;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;


@SuppressWarnings("UnstableApiUsage")
public class EffectEnchant<C extends LeveledEnchantConfig<? extends EffectEnchantLevel>> extends Enchant<C> {

    public EffectEnchant(@KeyPattern.Value String key, TagKey<ItemType> supportedItems, EquipmentSlotGroup activeSlots, Function<EnchantsConfiguration, C> configProvider) {
        super(key, supportedItems, activeSlots, configProvider);
    }

}

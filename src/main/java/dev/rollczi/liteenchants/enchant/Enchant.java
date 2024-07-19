package dev.rollczi.liteenchants.enchant;

import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.TagKey;
import java.util.Objects;
import java.util.function.Function;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;

@SuppressWarnings("UnstableApiUsage")
public class Enchant<CONFIG extends EnchantConfig> {
    private final TypedKey<Enchantment> key;
    private final TagKey<ItemType> supportedItems;
    private final EquipmentSlotGroup activeSlots;
    private final Function<EnchantsConfiguration, CONFIG> configProvider;
    private final EnchantCreator<CONFIG> creator;

    public Enchant(
        TypedKey<Enchantment> key,
        TagKey<ItemType> supportedItems,
        EquipmentSlotGroup activeSlots,
        Function<EnchantsConfiguration, CONFIG> configProvider,
        EnchantCreator<CONFIG> creator
    ) {
        this.key = key;
        this.supportedItems = supportedItems;
        this.activeSlots = activeSlots;
        this.configProvider = configProvider;
        this.creator = creator;
    }


    public Enchant(
        @KeyPattern.Value String key,
        TagKey<ItemType> supportedItems,
        EquipmentSlotGroup activeSlots,
        Function<EnchantsConfiguration, CONFIG> configProvider
    ) {
        this(Enchants.keyOf(key), supportedItems, activeSlots, configProvider, EnchantCreator.defaultCreator());
    }

    public CONFIG config(EnchantsConfiguration configuration) {
        return configProvider.apply(configuration);
    }

    public Enchantment toEnchantment() {
        return Enchants.getEnchantment(key);
    }

    public TypedKey<Enchantment> key() {
        return key;
    }

    public TagKey<ItemType> supportedItems() {
        return supportedItems;
    }

    public EquipmentSlotGroup activeSlots() {
        return activeSlots;
    }

    public Function<EnchantsConfiguration, CONFIG> configProvider() {
        return configProvider;
    }

    public EnchantCreator<CONFIG> creator() {
        return creator;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Enchant) obj;
        return Objects.equals(this.key, that.key) &&
            Objects.equals(this.supportedItems, that.supportedItems) &&
            Objects.equals(this.activeSlots, that.activeSlots) &&
            Objects.equals(this.configProvider, that.configProvider) &&
            Objects.equals(this.creator, that.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, supportedItems, activeSlots, configProvider, creator);
    }

    @Override
    public String toString() {
        return "Enchant[" +
            "key=" + key + ", " +
            "supportedItems=" + supportedItems + ", " +
            "activeSlots=" + activeSlots + ", " +
            "configProvider=" + configProvider + ", " +
            "creator=" + creator + ']';
    }


}

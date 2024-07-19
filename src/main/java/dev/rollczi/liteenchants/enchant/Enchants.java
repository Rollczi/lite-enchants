package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.enchant.potion.EnchantPotionPoisonConfig;
import dev.rollczi.liteenchants.enchant.potion.EnchantPotionSlownessConfig;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import static io.papermc.paper.registry.keys.tags.ItemTypeTagKeys.SWORDS;
import io.papermc.paper.registry.tag.TagKey;
import java.util.Set;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import static org.bukkit.inventory.EquipmentSlotGroup.MAINHAND;
import org.bukkit.inventory.ItemType;
import panda.std.Lazy;

@SuppressWarnings("UnstableApiUsage")
public final class Enchants {

    private final static Lazy<Registry<Enchantment>> REGISTRY = new Lazy<>(() -> RegistryAccess
        .registryAccess()
        .getRegistry(RegistryKey.ENCHANTMENT)
    );

    private Enchants() {
    }

    private static final String NAMESPACE = "liteenchants";

    public static final Enchant<EnchantPotionPoisonConfig> POISON = create("poison", SWORDS, MAINHAND, config -> config.poison);
    public static final Enchant<EnchantPotionSlownessConfig> SLOWNESS = create("slowness", SWORDS, MAINHAND, config -> config.slowness);

    public static final Set<Enchant<?>> ALL_ENCHANTS = Set.of(
        POISON, SLOWNESS
    );

    public static final Set<Enchant<? extends LeveledEnchantConfig<? extends EnchantLevelPotionConfig>>> POTION_ENCHANTS = Set.of(
        POISON, SLOWNESS
    );

    public static Enchantment getEnchantment(TypedKey<Enchantment> key) {
        return REGISTRY.get().getOrThrow(key);
    }

    private static <C extends EnchantConfig> Enchant<C> create(
        @KeyPattern.Value String key,
        TagKey<ItemType> supportedItems,
        EquipmentSlotGroup activeSlots,
        Function<EnchantsConfiguration, C> configProvider
    ) {
        return new Enchant<>(keyOf(key), supportedItems, activeSlots, configProvider, EnchantCreator.defaultCreator());
    }

    private static TypedKey<Enchantment> keyOf(@KeyPattern.Value String name) {
        return TypedKey.create(RegistryKey.ENCHANTMENT, Key.key(NAMESPACE, name));
    }

}

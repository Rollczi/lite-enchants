package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.enchant.dodge.DodgeEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchant;
import dev.rollczi.liteenchants.enchant.effect.armor.ArmorEffectEnchantFireProtectionConfig;
import dev.rollczi.liteenchants.enchant.effect.armor.ArmorEffectEnchantSpeedConfig;
import dev.rollczi.liteenchants.enchant.effect.haste.HasteEffectEnchantConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionEnchantLevelConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionPoisonEnchantConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionSlownessEnchantConfig;
import dev.rollczi.liteenchants.enchant.regeneration.RegenerationEnchantConfig;
import dev.rollczi.liteenchants.enchant.vampire.VampireEnchantConfig;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import static io.papermc.paper.registry.keys.tags.ItemTypeTagKeys.CHEST_ARMOR;
import static io.papermc.paper.registry.keys.tags.ItemTypeTagKeys.HEAD_ARMOR;
import static io.papermc.paper.registry.keys.tags.ItemTypeTagKeys.LEG_ARMOR;
import static io.papermc.paper.registry.keys.tags.ItemTypeTagKeys.SWORDS;
import io.papermc.paper.registry.tag.TagKey;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import static org.bukkit.inventory.EquipmentSlotGroup.CHEST;
import static org.bukkit.inventory.EquipmentSlotGroup.HEAD;
import static org.bukkit.inventory.EquipmentSlotGroup.LEGS;
import static org.bukkit.inventory.EquipmentSlotGroup.MAINHAND;
import org.bukkit.inventory.ItemType;
import panda.std.Lazy;

@SuppressWarnings("UnstableApiUsage")
public final class Enchants {
;
    private final static Lazy<Registry<Enchantment>> REGISTRY = new Lazy<>(() -> RegistryAccess
        .registryAccess()
        .getRegistry(RegistryKey.ENCHANTMENT)
    );

    private Enchants() {
    }

    public static final String NAMESPACE = "liteenchants";

    public static final EffectEnchant<ArmorEffectEnchantFireProtectionConfig> FIRE_PROTECTION = new EffectEnchant<>("fire-protection", HEAD_ARMOR, HEAD, config -> config.fireProtection);
    public static final EffectEnchant<HasteEffectEnchantConfig> HASTE = new EffectEnchant<>("haste", ItemTypeTagKeys.ENCHANTABLE_DURABILITY, MAINHAND, config -> config.haste);
    public static final Enchant<RegenerationEnchantConfig> REGENERATION = create("regeneration", CHEST_ARMOR, CHEST, config -> config.regeneration);
    public static final EffectEnchant<ArmorEffectEnchantSpeedConfig> SPEED = new EffectEnchant<>("speed", CHEST_ARMOR, CHEST, config -> config.speed);

    public static final Enchant<PotionPoisonEnchantConfig> POISON = create("poison", SWORDS, MAINHAND, config -> config.poison);
    public static final Enchant<PotionSlownessEnchantConfig> SLOWNESS = create("slowness", SWORDS, MAINHAND, config -> config.slowness);

    public static final Enchant<VampireEnchantConfig> VAMPIRE = create("vampire", SWORDS, MAINHAND, config -> config.vampire);
    public static final Enchant<DodgeEnchantConfig> DODGE = create("dodge", LEG_ARMOR, LEGS, config -> config.dodge);

    public static final Set<Enchant<?>> ALL_ENCHANTS = allEnchants();
    private static final Map<Key, Enchant<?>> ENCHANTS_BY_KEY = ALL_ENCHANTS.stream()
        .collect(Collectors.toMap(enchant -> enchant.key().key(), Function.identity()));

    public static final Set<Enchant<? extends LeveledEnchantConfig<? extends PotionEnchantLevelConfig>>> POTION_ENCHANTS = Set.of(
        POISON, SLOWNESS
    );

    public static final Set<EffectEnchant<?>> ARMOR_EFFECT_ENCHANTS = Set.of(
        FIRE_PROTECTION, SPEED
    );

    public static Enchantment getEnchantment(TypedKey<Enchantment> key) {
        return REGISTRY.get().getOrThrow(key);
    }

    public static boolean isCustomEnchant(Enchantment enchantment) {
        return enchantment.getKey().getNamespace().equals(NAMESPACE);
    }

    private static <C extends EnchantConfig> Enchant<C> create(
        @KeyPattern.Value String key,
        TagKey<ItemType> supportedItems,
        EquipmentSlotGroup activeSlots,
        Function<EnchantsConfiguration, C> configProvider
    ) {
        return new Enchant<>(keyOf(key), supportedItems, activeSlots, configProvider, EnchantCreator.defaultCreator());
    }

    static TypedKey<Enchantment> keyOf(@KeyPattern.Value String name) {
        return TypedKey.create(RegistryKey.ENCHANTMENT, Key.key(NAMESPACE, name));
    }

    private static Set<Enchant<?>> allEnchants() {
        Set<Enchant<?>> enchants = new HashSet<>();

        for (Field field : Enchants.class.getDeclaredFields()) {
            if (!Enchant.class.isAssignableFrom(field.getType())) {
                continue;
            }

            try {
                enchants.add((Enchant<?>) field.get(null));
            }
            catch (Throwable exception) {
                exception.printStackTrace();
            }
        }

        return Set.copyOf(enchants);
    }

    public static Enchant<?> getEnchant(Enchantment enchantment) {
        return ENCHANTS_BY_KEY.get(enchantment.key());
    }

}

package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.enchant.effect.armor.ArmorEffectEnchantFireProtectionConfig;
import dev.rollczi.liteenchants.enchant.effect.armor.ArmorEffectEnchantSpeedConfig;
import dev.rollczi.liteenchants.enchant.effect.haste.HasteEffectEnchantConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionPoisonEnchantConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionSlownessEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;

public class EnchantsConfiguration extends OkaeriConfig {

    public PotionPoisonEnchantConfig poison = new PotionPoisonEnchantConfig();
    public PotionSlownessEnchantConfig slowness = new PotionSlownessEnchantConfig();
    public ArmorEffectEnchantFireProtectionConfig fireProtection = new ArmorEffectEnchantFireProtectionConfig();
    public ArmorEffectEnchantSpeedConfig speed = new ArmorEffectEnchantSpeedConfig();
    public HasteEffectEnchantConfig haste = new HasteEffectEnchantConfig();

}

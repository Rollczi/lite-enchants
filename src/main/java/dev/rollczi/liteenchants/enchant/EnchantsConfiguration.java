package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.enchant.critical.CriticalEnchantConfig;
import dev.rollczi.liteenchants.enchant.dodge.DodgeEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.armor.ArmorEffectEnchantFireProtectionConfig;
import dev.rollczi.liteenchants.enchant.effect.armor.ArmorEffectEnchantSpeedConfig;
import dev.rollczi.liteenchants.enchant.effect.haste.HasteEffectEnchantConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionPoisonEnchantConfig;
import dev.rollczi.liteenchants.enchant.potion.PotionSlownessEnchantConfig;
import dev.rollczi.liteenchants.enchant.regeneration.RegenerationEnchantConfig;
import dev.rollczi.liteenchants.enchant.vampire.VampireEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;

public class EnchantsConfiguration extends OkaeriConfig {

    public ArmorEffectEnchantFireProtectionConfig fireProtection = new ArmorEffectEnchantFireProtectionConfig();
    public HasteEffectEnchantConfig haste = new HasteEffectEnchantConfig();
    public RegenerationEnchantConfig regeneration = new RegenerationEnchantConfig();
    public ArmorEffectEnchantSpeedConfig speed = new ArmorEffectEnchantSpeedConfig();

    public PotionPoisonEnchantConfig poison = new PotionPoisonEnchantConfig();
    public PotionSlownessEnchantConfig slowness = new PotionSlownessEnchantConfig();

    public VampireEnchantConfig vampire = new VampireEnchantConfig();
    public DodgeEnchantConfig dodge = new DodgeEnchantConfig();
    public CriticalEnchantConfig critical = new CriticalEnchantConfig();

}


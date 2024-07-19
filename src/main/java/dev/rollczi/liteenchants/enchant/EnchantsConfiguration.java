package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.enchant.potion.EnchantPotionPoisonConfig;
import dev.rollczi.liteenchants.enchant.potion.EnchantPotionSlownessConfig;
import eu.okaeri.configs.OkaeriConfig;

public class EnchantsConfiguration extends OkaeriConfig {

    public EnchantPotionPoisonConfig poison = new EnchantPotionPoisonConfig();
    public EnchantPotionSlownessConfig slowness = new EnchantPotionSlownessConfig();

}

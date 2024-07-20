package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

public  interface PotionEnchantLevelConfig extends EnchantLevelChanceConfig {

    PotionEffectType getEffect();

    double getChance();

    int getDuration();

    int getAmplifier();

}

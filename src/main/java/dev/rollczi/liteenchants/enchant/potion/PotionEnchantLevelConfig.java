package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.potion.PotionEffectType;

public abstract class PotionEnchantLevelConfig extends OkaeriConfig implements EnchantLevelChanceConfig {

    public double chance = 0.1;
    public int duration = 20 * 5;
    public int amplifier = 0;

    public PotionEnchantLevelConfig() {
    }

    public PotionEnchantLevelConfig(double chance, int duration, int amplifier) {
        this.chance = chance;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public abstract PotionEffectType getEffect();

    @Override
    public double chance() {
        return this.chance;
    }

}

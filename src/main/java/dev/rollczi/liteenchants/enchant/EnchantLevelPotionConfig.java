package dev.rollczi.liteenchants.enchant;

import org.bukkit.potion.PotionEffectType;

public abstract class EnchantLevelPotionConfig extends EnchantLevelChanceConfig {

    public int duration = 20 * 5;
    public int amplifier = 0;

    public EnchantLevelPotionConfig() {
    }

    public EnchantLevelPotionConfig(int duration, int amplifier) {
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public abstract PotionEffectType getEffect();

}

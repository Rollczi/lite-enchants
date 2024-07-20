package dev.rollczi.liteenchants.enchant.effect.armor;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantLevel;
import eu.okaeri.configs.OkaeriConfig;
import java.util.Map;
import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

@Getter
public class ArmorEffectEnchantSpeedConfig extends OkaeriConfig implements LeveledEnchantConfig<ArmorEffectEnchantSpeedConfig.Level> {

    String name = "Szybkosc";
    int weight = 10;
    int anvilCost = 5;
    Map<Integer, Level> levels = Map.of(1, new Level());

    @Getter
    public static class Level extends OkaeriConfig implements EffectEnchantLevel {

        int amplifier = 0;

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.SPEED;
        }

    }

}

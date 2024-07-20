package dev.rollczi.liteenchants.enchant.effect.armor;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantLevel;
import eu.okaeri.configs.OkaeriConfig;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffectType;

@Getter
public class ArmorEffectEnchantFireProtectionConfig extends OkaeriConfig implements LeveledEnchantConfig<ArmorEffectEnchantFireProtectionConfig.Level> {

    String name = "Niepodpalanie";
    int weight = 10;
    int anvilCost = 5;

    @Override
    public Map<Integer, Level> getLevels() {
        return Map.of(1, new Level());
    }

    @Getter
    public static class Level implements EffectEnchantLevel {

        int amplifier = 0;

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.FIRE_RESISTANCE;
        }

    }

}

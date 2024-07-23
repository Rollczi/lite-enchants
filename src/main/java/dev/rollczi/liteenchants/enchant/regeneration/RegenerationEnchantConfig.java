package dev.rollczi.liteenchants.enchant.regeneration;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import java.util.Map;
import lombok.Getter;

@Getter
public class RegenerationEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<RegenerationEnchantConfig.Level> {

    public String name = "Regeneracja";
    public int weight = 10;
    public int anvilCost = 5;
    public Map<Integer, Level> levels = Map.of(1, new Level());

    @Getter
    public static class Level extends OkaeriConfig implements EnchantLevelChanceConfig {
        double chance;
        int amplifier = 1;
        int duration = 20 * 5;
    }

}

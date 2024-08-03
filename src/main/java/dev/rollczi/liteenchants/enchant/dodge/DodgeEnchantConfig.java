package dev.rollczi.liteenchants.enchant.dodge;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class DodgeEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<DodgeEnchantConfig.Level> {

    String name = "Unik";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    public Map<Integer, Level> levels = Map.of(
        1, new Level(0.05),
        2, new Level(0.1),
        3, new Level(0.2),
        4, new Level(0.5)
    );

    @Getter
    public static class Level extends OkaeriConfig implements EnchantLevelChanceConfig {
        double chance = 0.5;

        Level() {}

        Level(double chance ) {
            this.chance = chance;
        }
    }

}

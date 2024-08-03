package dev.rollczi.liteenchants.enchant.critical;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class CriticalEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<CriticalEnchantConfig.Level> {

    String name = "<red>Obrażenie krytyczne";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    @Comment({
        "chance - Szansa na zadanie obrażeń krytycznych",
        "criticalHitMultiplier - Mnożnik obrażeń krytycznych"
    })
    public Map<Integer, Level> levels = Map.of(
        1, new Level(0.2, 1.2),
        2, new Level(0.5, 1.5)
    );

    @Getter
    public static class Level extends OkaeriConfig implements EnchantLevelChanceConfig {
        double chance = 0.1;
        double criticalHitMultiplier = 1.5;

        Level() {}

        Level(double chance, double criticalHitMultiplier) {
            this.chance = chance;
            this.criticalHitMultiplier = criticalHitMultiplier;
        }
    }

}

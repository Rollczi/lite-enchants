package dev.rollczi.liteenchants.enchant.durability;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

import java.util.Map;

@Getter
public class DurabilityProtectEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<DurabilityProtectEnchantConfig.Level> {

    String name = "Niepodatność";
    int weight = 10;
    int anvilCost = 5;

    @Comment({
        "durabilityProtect - Ochrona przed zużyciem 0.20 = dodatkowej 20% ochrony"
    })
    public Map<Integer, Level> levels = Map.of(
        1, new Level(0.20),
        2, new Level(0.40)
    );

    @Getter
    public static class Level extends OkaeriConfig {
        double durabilityProtect = 0.20;

        Level() {}

        Level(double durabilityProtect) {
            this.durabilityProtect = durabilityProtect;
        }
    }

}

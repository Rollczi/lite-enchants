package dev.rollczi.liteenchants.enchant.durability;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;

import java.util.Map;

@Getter
public class DurabilityProtectEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<DurabilityProtectEnchantConfig.Level> {

    String name = "Niepodatność";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    @Comment({
        "durabilityProtect - Ochrona przed zużyciem 0.20 = dodatkowej 20% ochrony"
    })
    public Map<Integer, Level> levels = Map.of(
        1, new Level(0.20),
        2, new Level(0.40),
        3, new Level(1.00)
    );

    @Getter
    public static class Level extends OkaeriConfig implements EnchantLevelChanceConfig {
        double durabilityProtection = 0.20;

        Level() {}

        Level(double durabilityProtection) {
            this.durabilityProtection = durabilityProtection;
        }

        @Override
        public double getChance() {
            return durabilityProtection;
        }
    }

}

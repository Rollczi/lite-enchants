package dev.rollczi.liteenchants.enchant.vampire;

import dev.rollczi.liteenchants.enchant.EnchantLevelChanceConfig;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class VampireEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<VampireEnchantConfig.Level> {

    String name = "<gradient:#9a262a:#27222a>Wampiryzm</gradient>";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    @Comment("healthRegenMultiplier - Jaki % hp ma zostać przywrócony do gracza. Jeśli gracz zada 12hp a multipler jest ustawiony na 0.5 to gracz może odzyskać 6hp")
    public Map<Integer, Level> levels = Map.of(
        1, new Level(0.5, 0.3),
        2, new Level(0.9, 0.7)
    );

    @Getter
    public static class Level extends OkaeriConfig implements EnchantLevelChanceConfig {
        double chance = 0.5;
        double healthRegenMultiplier = 0.5;

        Level() {}

        Level(double chance, double healthRegenMultiplier) {
            this.chance = chance;
            this.healthRegenMultiplier = healthRegenMultiplier;
        }
    }

}

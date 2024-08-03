package dev.rollczi.liteenchants.enchant.drop;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;

import java.util.Map;

@Getter
public class DropEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<DropEnchantConfig.Level> {

    String name = "Dodatkowy drop";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    @Comment({
        "dropMultiplier - Mnożnik dropu 1.10 = 10% więcej dropu"
    })
    public Map<Integer, Level> levels = Map.of(
        1, new Level(1.10),
        2, new Level(1.25)
    );

    @Getter
    public static class Level extends OkaeriConfig {
        double dropMultiplier = 1.5;

        Level() {}

        Level(double dropMultiplier) {
            this.dropMultiplier = dropMultiplier;
        }
    }

}

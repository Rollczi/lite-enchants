package dev.rollczi.liteenchants.enchant.armorbuff;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class ArmorBuffEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<ArmorBuffEnchantConfig.Level> {

    String name = "Wzmocnienie pancerza";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    @Comment({
        "buffProtection - Wzmocnienie ochrony 0.1 = dodatkowej 10% ochrony przed obrażeniami"
    })
    public Map<Integer, Level> levels = Map.of(
        1, new Level(0.10),
        2, new Level(0.15)
    );

    @Getter
    public static class Level extends OkaeriConfig {
        double buffProtection = 0.1;

        Level() {}

        Level(double buffProtection) {
            this.buffProtection = buffProtection;
        }
    }

}

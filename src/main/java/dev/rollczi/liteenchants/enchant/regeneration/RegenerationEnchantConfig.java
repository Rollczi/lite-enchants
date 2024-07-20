package dev.rollczi.liteenchants.enchant.regeneration;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantLevel;
import eu.okaeri.configs.OkaeriConfig;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffectType;

@Getter
public class RegenerationEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<RegenerationEnchantConfig.Level> {

    public String name = "Regeneracja";
    public int weight = 10;
    public int anvilCost = 5;
    public Map<Integer, Level> levels = Map.of(1, new Level());

    public static class Level extends OkaeriConfig {
        int amplifier = 1;
        int duration = 20 * 5;
    }

}

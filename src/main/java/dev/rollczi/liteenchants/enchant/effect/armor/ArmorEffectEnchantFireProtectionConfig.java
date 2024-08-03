package dev.rollczi.liteenchants.enchant.effect.armor;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantLevel;
import eu.okaeri.configs.OkaeriConfig;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffectType;

@Getter
public class ArmorEffectEnchantFireProtectionConfig extends OkaeriConfig implements LeveledEnchantConfig<ArmorEffectEnchantFireProtectionConfig.Level> {

    String name = "<u><#ffa12e>Niepodpalanie</#ffa12e></u>";
    List<String> lore = List.of(
        "",
        "<gray>Unikalny enchantment, który zapewnia <u><#ffa12e>ochronę</#ffa12e></u>",
        "<gray>przed <u><#ffa12e>ogniem</#ffa12e></u> i <u><#ffa12e>lawą</#ffa12e></u>."
    );
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

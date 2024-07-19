package dev.rollczi.liteenchants.enchant.effect.haste;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantLevel;
import eu.okaeri.configs.OkaeriConfig;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.potion.PotionEffectType;

@Getter
public class HasteEffectEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<HasteEffectEnchantConfig.Level> {

    Component name = Component.text("Pospiech");
    int weight = 10;
    int anvilCost = 5;
    Map<Integer, Level> levels = Map.of(1, new Level());

    @Getter
    public static class Level extends OkaeriConfig implements EffectEnchantLevel {

        int amplifier = 0;

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.HASTE;
        }

    }

}

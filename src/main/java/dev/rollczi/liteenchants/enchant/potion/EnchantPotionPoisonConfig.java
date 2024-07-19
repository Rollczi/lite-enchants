package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.EnchantLevelPotionConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.Map;
import org.bukkit.potion.PotionEffectType;

public class EnchantPotionPoisonConfig extends LeveledEnchantConfig<EnchantPotionPoisonConfig.LevelConfig> {

    @Comment({
        "Poziom enchantu, chance to szansa od 0 do 1 (np. 0.1 to 10%, 0.55 to 55%)",
        "Duration to czas trwania efektu w tickach (20 ticków = 1 sekunda)",
        "Amplifier to poziom trucizny (np. 1 to trucizna I, 2 to trucizna II)"
    })
    public Map<Integer, LevelConfig> levels = Map.of(
        1, new LevelConfig(20 * 5, 0),
        2, new LevelConfig(20 * 3, 1),
        3, new LevelConfig(20 * 2, 2)
    );

    @Override
    protected Map<Integer, LevelConfig> levels() {
        return levels;
    }

    public static class LevelConfig extends EnchantLevelPotionConfig {

        LevelConfig() {
        }

        public LevelConfig(int duration, int amplifier) {
            super(duration, amplifier);
        }

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.POISON;
        }
    }

}

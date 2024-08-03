package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Include;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

@Getter
public class PotionSlownessEnchantConfig extends OkaeriConfig implements LeveledEnchantConfig<PotionSlownessEnchantConfig.LevelConfig> {

    String name = "<dark_purple>Spowolnienie";
    List<String> lore = List.of();
    int weight = 10;
    int anvilCost = 5;

    @Comment({
        "Poziom enchantu, chance to szansa od 0 do 1 (np. 0.1 to 10%, 0.55 to 55%)",
        "Duration to czas trwania efektu w tickach (20 ticków = 1 sekunda)",
        "Amplifier to poziom spowolnienia"
    })
    public Map<Integer, LevelConfig> levels = Map.of(
        1, new LevelConfig(0.1, 20 * 3, 0),
        2, new LevelConfig(0.4, 20 * 4, 1),
        3, new LevelConfig(0.55, 20 * 5, 2),
        4, new LevelConfig(0.95, 20 * 6, 2)
    );

    @Getter
    public static class LevelConfig extends OkaeriConfig implements PotionEnchantLevelConfig {

        public double chance = 0.1;
        public int duration = 20 * 5;
        public int amplifier = 0;

        LevelConfig() {
        }

        public LevelConfig(double chance, int duration, int amplifier) {
            this.chance = chance;
            this.duration = duration;
            this.amplifier = amplifier;
        }

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.SLOWNESS;
        }
    }

}

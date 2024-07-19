package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.AbstractLeveledEnchantConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Include;
import java.util.Map;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.potion.PotionEffectType;

@Include(AbstractLeveledEnchantConfig.class)
@Getter
public class PotionPoisonEnchantConfig extends AbstractLeveledEnchantConfig<PotionPoisonEnchantConfig.LevelConfig> {

    @Comment({
        "Poziom enchantu, chance to szansa od 0 do 1 (np. 0.1 to 10%, 0.55 to 55%)",
        "Duration to czas trwania efektu w tickach (20 ticków = 1 sekunda)",
        "Amplifier to poziom trucizny (np. 1 to trucizna I, 2 to trucizna II)"
    })
    public Map<Integer, LevelConfig> levels = Map.of(
        1, new LevelConfig(0.1, 20 * 3, 0),
        2, new LevelConfig(0.4, 20 * 4, 1),
        3, new LevelConfig(0.5, 20 * 5, 2),
        4, new LevelConfig(0.9, 20 * 6, 2)
    );

    public PotionPoisonEnchantConfig() {
        super(Component.text("Poison").color(NamedTextColor.DARK_GREEN));
    }

    @Include(PotionEnchantLevelConfig.class)
    public static class LevelConfig extends PotionEnchantLevelConfig {

        LevelConfig() {
        }

        public LevelConfig(double chance, int duration, int amplifier) {
            super(chance, duration, amplifier);
        }

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.POISON;
        }
    }

}

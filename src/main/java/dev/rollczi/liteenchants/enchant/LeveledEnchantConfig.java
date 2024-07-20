package dev.rollczi.liteenchants.enchant;

import java.util.Comparator;
import java.util.Map;
import net.kyori.adventure.text.Component;

public interface LeveledEnchantConfig<L> extends EnchantConfig {

    Map<Integer, L> getLevels();

    @Override
    String getName();

    @Override
    default int maxLevel() {
        return this.getLevels().keySet().stream()
            .max(Comparator.naturalOrder())
            .orElseThrow(() -> new IllegalStateException("No levels found for " + this.getClass().getSimpleName()));
    }

    default L level(int level) {
        L currentLevel = this.getLevels().get(level);

        if (currentLevel != null) {
            return currentLevel;
        }

        int maxed = this.maxLevel();

        if (level <= maxed) {
            throw new IllegalStateException("Missing level " + level + " for " + this.getClass().getSimpleName());
        }

        L maxLevel = this.getLevels().get(maxed);

        if (maxLevel == null) {
            throw new IllegalStateException("Missing level " + level + " for " + this.getClass().getSimpleName() + ", using max level instead");
        }

        return maxLevel;
    }

    @Override
    int getWeight();

    @Override
    int getAnvilCost();

}

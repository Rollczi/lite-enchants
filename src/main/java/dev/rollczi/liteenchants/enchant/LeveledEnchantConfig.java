package dev.rollczi.liteenchants.enchant;

import eu.okaeri.configs.OkaeriConfig;
import java.util.Comparator;
import java.util.Map;
import net.kyori.adventure.text.Component;

public abstract class LeveledEnchantConfig<L> extends OkaeriConfig implements EnchantConfig {

    public Component name;
    public int weight = 10;
    public int anvilCost = 5;

    protected abstract Map<Integer, L> levels();

    @Override
    public Component name() {
        return this.name;
    }

    @Override
    public int maxLevel() {
        return this.levels().keySet().stream()
            .max(Comparator.naturalOrder())
            .orElseThrow(() -> new IllegalStateException("No levels found for " + this.getClass().getSimpleName()));
    }

    public L level(int level) {
        L l = this.levels().get(level);

        if (l == null) {
            throw new IllegalStateException("Missing level " + level + " for " + this.getClass().getSimpleName());
        }

        return l;
    }

    @Override
    public int weight() {
        return this.weight;
    }

    @Override
    public int anvilCost() {
        return this.anvilCost;
    }

}

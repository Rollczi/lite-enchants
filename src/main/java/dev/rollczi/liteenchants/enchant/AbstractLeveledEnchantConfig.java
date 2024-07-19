package dev.rollczi.liteenchants.enchant;

import eu.okaeri.configs.OkaeriConfig;
import java.util.Comparator;
import java.util.Map;
import net.kyori.adventure.text.Component;

public abstract class AbstractLeveledEnchantConfig<L> extends OkaeriConfig implements LeveledEnchantConfig<L> {

    public Component name;
    public int weight;
    public int anvilCost;

    public AbstractLeveledEnchantConfig(Component name, int weight, int anvilCost) {
        this.name = name;
        this.weight = weight;
        this.anvilCost = anvilCost;
    }

    public AbstractLeveledEnchantConfig(Component name) {
        this(name, 10, 5);
    }

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public int getAnvilCost() {
        return this.anvilCost;
    }

}

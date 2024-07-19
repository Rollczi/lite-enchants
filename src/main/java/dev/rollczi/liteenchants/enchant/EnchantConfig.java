package dev.rollczi.liteenchants.enchant;

import net.kyori.adventure.text.Component;

public interface EnchantConfig {

    Component getName();

    int maxLevel();

    int getWeight();

    int getAnvilCost();

}

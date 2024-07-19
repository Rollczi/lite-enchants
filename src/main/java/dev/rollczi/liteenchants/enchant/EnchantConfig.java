package dev.rollczi.liteenchants.enchant;

import net.kyori.adventure.text.Component;

public interface EnchantConfig {

    Component name();

    int maxLevel();

    int weight();

    int anvilCost();

}

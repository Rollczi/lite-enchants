package dev.rollczi.liteenchants.enchant;

import java.util.List;

public interface EnchantConfig {

    String getName();

    List<String> getLore();

    int maxLevel();

    int getWeight();

    int getAnvilCost();

}

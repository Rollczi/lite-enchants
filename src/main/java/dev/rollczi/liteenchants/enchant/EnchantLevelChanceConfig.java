package dev.rollczi.liteenchants.enchant;

import java.util.concurrent.ThreadLocalRandom;

public interface EnchantLevelChanceConfig {

    double getChance();

    default boolean randomChance() {
        return ThreadLocalRandom.current().nextDouble() <= this.getChance();
    }

}

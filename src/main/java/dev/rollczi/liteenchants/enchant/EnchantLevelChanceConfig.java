package dev.rollczi.liteenchants.enchant;

import java.util.concurrent.ThreadLocalRandom;

public interface EnchantLevelChanceConfig {

    double chance();

    default boolean randomChance() {
        return ThreadLocalRandom.current().nextDouble() <= this.chance();
    }

}

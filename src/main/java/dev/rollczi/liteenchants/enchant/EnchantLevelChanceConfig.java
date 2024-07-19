package dev.rollczi.liteenchants.enchant;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.concurrent.ThreadLocalRandom;

public class EnchantLevelChanceConfig extends OkaeriConfig {

    @Comment("Szansa na enchant (0.1 to 10%, 0.55 to 55%)")
    public double chance = 0.1;

    public boolean randomChance() {
        return ThreadLocalRandom.current().nextDouble() <= this.chance;
    }

}

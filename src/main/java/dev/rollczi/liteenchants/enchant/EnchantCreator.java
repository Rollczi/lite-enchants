package dev.rollczi.liteenchants.enchant;

import io.papermc.paper.registry.data.EnchantmentRegistryEntry;

@SuppressWarnings("UnstableApiUsage")
public interface EnchantCreator<C extends EnchantConfig> {

    void create(C config, EnchantmentRegistryEntry.Builder builder);

    static <C extends EnchantConfig> EnchantCreator<C> defaultCreator() {
        return (config, builder) -> {};
    }

}

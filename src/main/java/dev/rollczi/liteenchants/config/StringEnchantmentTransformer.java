package dev.rollczi.liteenchants.config;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

class StringEnchantmentTransformer extends BidirectionalTransformer<String, Enchantment> {

    private final Registry<Enchantment> registry;

    StringEnchantmentTransformer() {
        this.registry = RegistryAccess
            .registryAccess()
            .getRegistry(RegistryKey.ENCHANTMENT);
    }

    @Override
    public GenericsPair<String, Enchantment> getPair() {
        return this.genericsPair(String.class, Enchantment.class);
    }

    @Override
    public Enchantment leftToRight(@NotNull String data, @NotNull SerdesContext serdesContext) {
        return registry.stream()
            .filter(someEnchantment -> someEnchantment.getKey().value().equalsIgnoreCase(data))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Enchantment not found: " + data));
    }

    @Override
    public String rightToLeft(Enchantment data, @NotNull SerdesContext serdesContext) {
        return data.getKey().value();
    }

}

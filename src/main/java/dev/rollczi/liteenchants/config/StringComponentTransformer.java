package dev.rollczi.liteenchants.config;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

class StringComponentTransformer extends BidirectionalTransformer<String, Component> {

    private final MiniMessage miniMessage;

    StringComponentTransformer(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @Override
    public GenericsPair<String, Component> getPair() {
        return this.genericsPair(String.class, Component.class);
    }

    @Override
    public Component leftToRight(@NotNull String data, @NotNull SerdesContext serdesContext) {
        return this.miniMessage.deserialize(data);
    }

    @Override
    public String rightToLeft(Component data, @NotNull SerdesContext serdesContext) {
        return this.miniMessage.serialize(data);
    }

}

package dev.rollczi.liteenchants.config;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;

class ConfigSerdes implements OkaeriSerdesPack {

    private final MiniMessage miniMessage;

    ConfigSerdes(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @Override
    public void register(SerdesRegistry registry) {
        registry.register(new StringComponentTransformer(miniMessage));
    }

}

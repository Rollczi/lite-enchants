package dev.rollczi.liteenchants.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class PluginConfig extends OkaeriConfig {

    @Exclude
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

}

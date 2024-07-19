package dev.rollczi.liteenchants;

import dev.rollczi.liteenchants.config.ConfigService;
import dev.rollczi.liteenchants.config.PluginConfig;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.wip.EnchantmentArgument;
import dev.rollczi.liteenchants.wip.EnchantCommand;
import dev.rollczi.liteenchants.enchant.potion.EnchantPotionController;
import dev.rollczi.liteenchants.message.MessageService;
import dev.rollczi.liteenchants.reload.ReloadManager;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.adventure.LiteAdventureExtension;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import java.util.stream.Stream;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LiteEnchants extends JavaPlugin {

    private final MiniMessage miniMessage;
    private final ReloadManager reloadManager;
    private final ConfigService configManager;

    private LiteCommands<CommandSender> liteCommands;

    public LiteEnchants(MiniMessage miniMessage, ReloadManager reloadManager, ConfigService configManager) {
        this.miniMessage = miniMessage;
        this.reloadManager = reloadManager;
        this.configManager = configManager;
    }

    @Override
    public void onEnable() {
        PluginConfig pluginConfig = configManager.getConfig(PluginConfig.class);
        EnchantsConfiguration enchantsConfiguration = configManager.getConfig(EnchantsConfiguration.class);

        MessageService messageService = new MessageService(pluginConfig, miniMessage);

        PluginManager pluginManager = this.getServer().getPluginManager();

        Stream.of(
            new EnchantPotionController(enchantsConfiguration)
        ).forEach(listener -> pluginManager.registerEvents(listener, this));

        this.liteCommands = LiteBukkitFactory.builder("lite-enchants", this)
            .extension(new LiteAdventureExtension<>(), settings -> settings
                .serializer(miniMessage)
                .colorizeArgument(true)
            )

            .commands(
                new LiteEnchantsAdminCommand(reloadManager),
                new EnchantCommand()
            )

            .argument(Enchantment.class, new EnchantmentArgument())
            .message(LiteBukkitMessages.PLAYER_ONLY, "&cOnly player can execute this command!")

            .build();
    }

    @Override
    public void onDisable() {
        // unregister all commands from bukkit
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}

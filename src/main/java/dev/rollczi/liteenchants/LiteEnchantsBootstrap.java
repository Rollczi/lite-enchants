package dev.rollczi.liteenchants;

import dev.rollczi.liteenchants.adventure.LegacyPostProcessor;
import dev.rollczi.liteenchants.adventure.LegacyPreProcessor;
import dev.rollczi.liteenchants.config.ConfigService;
import dev.rollczi.liteenchants.config.PluginConfig;
import dev.rollczi.liteenchants.enchant.Enchant;
import dev.rollczi.liteenchants.enchant.EnchantConfig;
import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.reload.ReloadManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.event.RegistryFreezeEvent;
import io.papermc.paper.registry.event.WritableRegistry;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class LiteEnchantsBootstrap implements PluginBootstrap {

    private MiniMessage miniMessage;
    private ReloadManager reloadManager;
    private ConfigService configManager;

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyPostProcessor())
            .preProcessor(new LegacyPreProcessor())
            .build();

        reloadManager = new ReloadManager();
        configManager = reloadManager.register(new ConfigService(context.getDataDirectory().toFile(), miniMessage));
        configManager.load(new PluginConfig(), "config.yml");

        EnchantsConfiguration configuration = configManager.load(new EnchantsConfiguration(), "enchants.yml");

        context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze()
            .newHandler(event -> registerEnchants(event, configuration, context))
        );
    }

    private void registerEnchants(RegistryFreezeEvent<Enchantment, EnchantmentRegistryEntry.Builder> event, EnchantsConfiguration configuration, BootstrapContext context) {
        try {
            Enchants.ALL_ENCHANTS.forEach(enchant -> registerEnchant(event, configuration, enchant));
        }
        catch (Exception exception) {
            context.getLogger().error("Failed to register enchants", exception);
        }
    }

    private <C extends EnchantConfig> void registerEnchant(
        RegistryFreezeEvent<Enchantment, EnchantmentRegistryEntry.Builder> event,
        EnchantsConfiguration configuration,
        Enchant<C> enchant
    ) {
        C config = enchant.configProvider().apply(configuration);
        WritableRegistry<Enchantment, EnchantmentRegistryEntry.Builder> registry = event.registry();
        registry.register(
            enchant.key(),
            builder -> builder
                .description(config.getName())
                .supportedItems(event.getOrCreateTag(enchant.supportedItems()))
                .primaryItems(event.getOrCreateTag(enchant.supportedItems()))
                .maxLevel(config.maxLevel())
                .weight(config.getWeight())
                .anvilCost(config.getAnvilCost())
                .minimumCost(EnchantmentRegistryEntry.EnchantmentCost.of(1, 1))
                .maximumCost(EnchantmentRegistryEntry.EnchantmentCost.of(3, 1))
                .activeSlots(enchant.activeSlots())
        );
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        return new LiteEnchants(miniMessage, reloadManager, configManager);
    }

}
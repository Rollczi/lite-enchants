package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.config.PluginConfig;
import dev.rollczi.liteenchants.util.ItemTypeUtil;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class EnchantLimitController implements Listener {

    private final PluginConfig pluginConfig;

    public EnchantLimitController(PluginConfig pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEnchant(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack firstItem = inventory.getFirstItem();
        if (firstItem == null) {
            return;
        }

        ItemStack secondItem = inventory.getSecondItem();
        if (secondItem == null) {
            return;
        }

        Set<Enchantment> customEnchantments = this.getCustomEnchantments(secondItem);

        if (secondItem.getType() == Material.ENCHANTED_BOOK) {
            for (Enchantment enchantment : customEnchantments) {
                Enchant<?> enchant = Enchants.getEnchant(enchantment);

                if (enchant == null) {
                    continue;
                }

                if (ItemTypeUtil.isIncluded(firstItem, enchant.supportedItems())) {
                    continue;
                }

                event.setResult(null);
                return;
            }
        }

        if (customEnchantments.isEmpty()) {
            return;
        }

        customEnchantments.addAll(this.getCustomEnchantments(firstItem));

        if (customEnchantments.size() > pluginConfig.enchantLimit) {
            event.setResult(null);
        }
    }

    private Set<Enchantment> getCustomEnchantments(ItemStack item) {
        if (item.getItemMeta() instanceof EnchantmentStorageMeta storageMeta) {
            return storageMeta.getStoredEnchants().keySet()
                .stream()
                .filter(enchantment -> Enchants.isCustomEnchant(enchantment))
                .collect(Collectors.toCollection(() -> new HashSet<>()));
        }

        return item.getEnchantments().keySet()
            .stream()
            .filter(enchantment -> Enchants.isCustomEnchant(enchantment))
            .collect(Collectors.toCollection(() -> new HashSet<>()));
    }

}

package dev.rollczi.liteenchants.enchant;

import dev.rollczi.liteenchants.config.PluginConfig;
import dev.rollczi.liteenchants.util.ItemTypeUtil;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

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

        if (!(secondItem.getItemMeta() instanceof EnchantmentStorageMeta storageMeta)) {
            return;
        }

        for (Enchantment enchantment : storageMeta.getStoredEnchants().keySet()) {
            if (!Enchants.isCustomEnchant(enchantment)) {
                continue;
            }

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

        Set<Enchantment> summedEnchants = new HashSet<>();

        summedEnchants.addAll(firstItem.getEnchantments().keySet());
        summedEnchants.addAll(storageMeta.getStoredEnchants().keySet());

        if (summedEnchants.size() > pluginConfig.enchantLimit) {
            event.setResult(null);
        }
    }

}

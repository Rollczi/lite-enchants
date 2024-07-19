package dev.rollczi.liteenchants.enchant;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

public class EnchantLimitController implements Listener {

    @EventHandler
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

        if (!this.isSameNamespace(storageMeta.getStoredEnchants())) {
            return;
        }

        int sameNamespace = this.getSameNamespaceCount(storageMeta.getStoredEnchants());
        int namespaceLimit = 2;

        if (sameNamespace >= namespaceLimit) {
            event.setResult(null);
        }
    }

    private boolean isSameNamespace(Map<Enchantment, Integer> enchantments) {
        return getSameNamespaceCount(enchantments) > 0;
    }

    private int getSameNamespaceCount(@NotNull Map<Enchantment, Integer> enchantments) {
        int sameNamespace = 0;

        for (Enchantment enchantment : enchantments.keySet()) {
            if (Enchants.isSameNamespace(enchantment)) {
                sameNamespace++;
            }
        }

        return sameNamespace;
    }

}

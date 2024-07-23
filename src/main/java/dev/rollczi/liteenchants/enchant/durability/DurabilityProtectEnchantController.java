package dev.rollczi.liteenchants.enchant.durability;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.vampire.VampireEnchantConfig;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class DurabilityProtectEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public DurabilityProtectEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        int level = item.getEnchantmentLevel(Enchants.DURABILITY_PROTECT.toEnchantment());

        if (level == 0) {
            return;
        }

        DurabilityProtectEnchantConfig config = Enchants.DURABILITY_PROTECT.config(configuration);
        DurabilityProtectEnchantConfig.Level levelConfig = config.level(level);

        int damage = event.getDamage();

        for (int i = 0; i < event.getOriginalDamage(); i++) {
            if (levelConfig.randomChance()) {
                damage--;
            }
        }

        event.setDamage(damage);
    }

}

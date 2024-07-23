package dev.rollczi.liteenchants.enchant.armorbuff;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.durability.DurabilityProtectEnchantConfig;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

public class ArmorBuffEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public ArmorBuffEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("deprecation")
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onEffect(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        double buffProtection = 0.0;

        for (ItemStack armorContent : victim.getInventory().getArmorContents()) {
            if (armorContent == null) {
                continue;
            }

            int level = armorContent.getEnchantmentLevel(Enchants.ARMOR_BUFF.toEnchantment());

            if (level == 0) {
                continue;
            }

            ArmorBuffEnchantConfig config = Enchants.ARMOR_BUFF.config(configuration);
            ArmorBuffEnchantConfig.Level levelConfig = config.level(level);

            buffProtection += levelConfig.getBuffProtection();
        }

        if (buffProtection == 0.0) {
            return;
        }

        double protectionDamage = event.getFinalDamage() * buffProtection;
        double armorDamage = event.getDamage(DamageModifier.ARMOR) - protectionDamage;

        event.setDamage(DamageModifier.ARMOR, armorDamage);
    }

}

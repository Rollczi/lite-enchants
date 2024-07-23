package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.Enchant;
import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PotionEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public PotionEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity victim)) {
            return;
        }

        ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);

        for (Enchant<? extends LeveledEnchantConfig<? extends PotionEnchantLevelConfig>> enchant : Enchants.POTION_ENCHANTS) {
            int enchantmentLevel = item.getEnchantmentLevel(enchant.toEnchantment());

            if (enchantmentLevel > 0) {
                PotionEnchantLevelConfig level = enchant.config(configuration).level(enchantmentLevel);

                if (!level.randomChance()) {
                    continue;
                }

                PotionEffect effect = level.getEffect().createEffect(level.getDuration(), level.getAmplifier());

                victim.addPotionEffect(effect);
            }
        }
    }

}

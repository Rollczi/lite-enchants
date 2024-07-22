package dev.rollczi.liteenchants.enchant.critical;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CriticalEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public CriticalEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onEffect(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        ItemStack stack = damager.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            return;
        }

        int level = stack.getEnchantmentLevel(Enchants.CRITICAL.toEnchantment());

        if (level == 0) {
            return;
        }

        CriticalEnchantConfig config = Enchants.CRITICAL.config(configuration);
        CriticalEnchantConfig.Level levelConfig = config.level(level);

        if (!levelConfig.randomChance()) {
            return;
        }

        double damage = event.getDamage() * levelConfig.getCriticalHitMultiplier();
        event.setDamage(damage);
        victim.getWorld().spawnParticle(Particle.CRIT, victim.getLocation(), 15, 0, 0, 0);
        victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1, 1);
    }

}

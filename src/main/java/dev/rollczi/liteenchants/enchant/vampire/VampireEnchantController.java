package dev.rollczi.liteenchants.enchant.vampire;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.regeneration.RegenerationEnchantConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class VampireEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public VampireEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onEffect(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        ItemStack stack = damager.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            return;
        }

        int level = stack.getEnchantmentLevel(Enchants.VAMPIRE.toEnchantment());

        if (level == 0) {
            return;
        }

        AttributeInstance attribute = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (attribute == null) {
            throw new IllegalStateException("Max health attribute not found for player " + damager.getName());
        }

        VampireEnchantConfig config = Enchants.VAMPIRE.config(configuration);
        VampireEnchantConfig.Level levelConfig = config.level(level);

        if (!levelConfig.randomChance()) {
            return;
        }

        double damage = event.getFinalDamage();
        double health = damager.getHealth();

        double additional = damage * levelConfig.getHealthRegenMultiplier();
        double newHealth = Math.min(health + additional, attribute.getValue());

        damager.setHealth(newHealth);
    }

}

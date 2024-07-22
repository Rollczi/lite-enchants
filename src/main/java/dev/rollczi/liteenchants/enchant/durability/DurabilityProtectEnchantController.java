package dev.rollczi.liteenchants.enchant.durability;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.vampire.VampireEnchantConfig;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class DurabilityProtectEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public DurabilityProtectEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onEffect(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        for (ItemStack armorContent : victim.getInventory().getArmorContents()) {
            if (armorContent == null) {
                continue;
            }

            int level = armorContent.getEnchantmentLevel(Enchants.DURABILITY_PROTECT.toEnchantment());

            if (level == 0) {
                continue;
            }

            DurabilityProtectEnchantConfig config = Enchants.DURABILITY_PROTECT.config(configuration);
            DurabilityProtectEnchantConfig.Level levelConfig = config.level(level);

            event.getDamage(EntityDamageEvent.DamageModifier.ARMOR);
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

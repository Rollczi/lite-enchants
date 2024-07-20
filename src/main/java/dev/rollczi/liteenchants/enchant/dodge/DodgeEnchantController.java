package dev.rollczi.liteenchants.enchant.dodge;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class DodgeEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public DodgeEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onEffect(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!(event.getDamager() instanceof LivingEntity)) {
            return;
        }

        ItemStack stack = player.getInventory().getItem(EquipmentSlot.LEGS);

        if (stack.isEmpty()) {
            return;
        }

        int level = stack.getEnchantmentLevel(Enchants.DODGE.toEnchantment());

        if (level == 0) {
            return;
        }

        DodgeEnchantConfig config = Enchants.DODGE.config(configuration);
        DodgeEnchantConfig.Level levelConfig = config.level(level);

        if (levelConfig.randomChance()) {
            event.setDamage(0);
        }
    }

}

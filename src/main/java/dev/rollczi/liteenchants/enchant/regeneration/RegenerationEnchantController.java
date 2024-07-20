package dev.rollczi.liteenchants.enchant.regeneration;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import java.time.Duration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class RegenerationEnchantController implements Listener {

    public static final Enchantment REGENERATION_ENCHANTMENT = Enchants.REGENERATION.toEnchantment();

    private final EnchantsConfiguration configuration;

    public RegenerationEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onEffect(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack stack = player.getInventory().getItem(EquipmentSlot.CHEST);

        if (stack.isEmpty()) {
            return;
        }

        int level = stack.getEnchantmentLevel(REGENERATION_ENCHANTMENT);

        if (level == 0) {
            return;
        }

        if (player.getHealth() <= 0) {
            return;
        }

        RegenerationEnchantConfig.Level config = Enchants.REGENERATION.config(configuration).level(level);

        player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(config.duration, config.amplifier));
    }

}

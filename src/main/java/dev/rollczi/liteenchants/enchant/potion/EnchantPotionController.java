package dev.rollczi.liteenchants.enchant.potion;

import dev.rollczi.liteenchants.enchant.LeveledEnchantConfig;
import dev.rollczi.liteenchants.enchant.Enchant;
import dev.rollczi.liteenchants.enchant.EnchantLevelPotionConfig;
import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class EnchantPotionController implements Listener {

    private final EnchantsConfiguration configuration;

    public EnchantPotionController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        if (!(event.getEntity() instanceof LivingEntity victim)) {
            return;
        }

        ItemStack item = player.getInventory().getItem(EquipmentSlot.HAND);
        int enchantmentLevel = item.getEnchantmentLevel(Enchants.POISON.toEnchantment());

        for (Enchant<? extends LeveledEnchantConfig<? extends EnchantLevelPotionConfig>> enchant : Enchants.POTION_ENCHANTS) {
            if (enchantmentLevel > 0) {
                EnchantLevelPotionConfig level = enchant.config(configuration).level(enchantmentLevel);

                if (!level.randomChance()) {
                    continue;
                }

                PotionEffect effect = level.getEffect().createEffect(level.duration, level.amplifier);

                victim.addPotionEffect(effect);
            }
        }
    }

}

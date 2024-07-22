package dev.rollczi.liteenchants.enchant.durability;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.vampire.VampireEnchantConfig;
import java.util.logging.Logger;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

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

            armorContent.editMeta(Damageable.class, meta -> {
                int damage = meta.getDamage();

                if (levelConfig.randomChance() && damage > 0) {
                    meta.setDamage(damage - 1);
                }
            });
        }
    }

}

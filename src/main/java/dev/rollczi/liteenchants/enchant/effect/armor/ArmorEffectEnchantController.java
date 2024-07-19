package dev.rollczi.liteenchants.enchant.effect.armor;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchant;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantLevel;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantManager;
import java.time.Duration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ArmorEffectEnchantController implements Listener {

    private static final Duration ARMOR_CHECK_DURATION = Duration.ofSeconds(3);

    private final EnchantsConfiguration configuration;
    private final EffectEnchantManager manager;

    public ArmorEffectEnchantController(EnchantsConfiguration configuration, EffectEnchantManager manager) {
        this.configuration = configuration;
        this.manager = manager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onEffect(PlayerArmorChangeEvent event) {
        PlayerArmorChangeEvent.SlotType type = event.getSlotType();
        Player player = event.getPlayer();

        for (EffectEnchant<?> enchant : Enchants.ARMOR_EFFECT_ENCHANTS) {
            ItemStack newItem = event.getNewItem();
            int level = newItem.getEnchantmentLevel(enchant.toEnchantment());

            if (level > 0) {
                EffectEnchantLevel levelConfig = enchant.config(configuration).level(level);
                EquipmentSlot slot = this.toEquipmentSlot(type);

                manager.startListen(player, ARMOR_CHECK_DURATION, enchant.toEnchantment(), levelConfig,
                    current -> current.getInventory().getItem(slot).getEnchantmentLevel(enchant.toEnchantment()) == level
                );

                continue;
            }

            ItemStack oldItem = event.getOldItem();

            if (oldItem.getEnchantmentLevel(enchant.toEnchantment()) > 0) {
                manager.stopListener(player.getUniqueId(), enchant.toEnchantment());
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onQuit(PlayerQuitEvent event) {
        manager.stopAllListeners(event.getPlayer().getUniqueId());
    }

    private EquipmentSlot toEquipmentSlot(PlayerArmorChangeEvent.SlotType type) {
        return switch (type) {
            case HEAD -> EquipmentSlot.HEAD;
            case CHEST -> EquipmentSlot.CHEST;
            case LEGS -> EquipmentSlot.LEGS;
            case FEET -> EquipmentSlot.FEET;
        };
    }

}

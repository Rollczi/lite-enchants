package dev.rollczi.liteenchants.enchant.effect.haste;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.enchant.effect.EffectEnchantManager;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.tag.Tag;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.PlayerInventory;

@SuppressWarnings("UnstableApiUsage")
public class HasteEffectEnchantController implements Listener {

    private static final Duration CHECK_DURATION = Duration.ofSeconds(3);
    private static final Enchantment HASTE_ENCHANTMENT = Enchants.HASTE.toEnchantment();

    private final EnchantsConfiguration configuration;
    private final EffectEnchantManager manager;

    private final Set<Material> pickaxes = new HashSet<>();

    public HasteEffectEnchantController(EnchantsConfiguration configuration, EffectEnchantManager manager) {
        this.configuration = configuration;
        this.manager = manager;

        Registry<ItemType> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ITEM);
        Tag<ItemType> tag = registry.getTag(ItemTypeTagKeys.PICKAXES);

        for (TypedKey<ItemType> itemTypeTypedKey : tag) {
            ItemType type = registry.get(itemTypeTypedKey);
            pickaxes.add(type.asMaterial());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onSlotChange(PlayerInventorySlotChangeEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getHeldItemSlot() != event.getSlot()) {
            return;
        }

        ItemStack newItemStack = event.getNewItemStack();

        if (this.checkNewItem(player, newItemStack)) {
            return;
        }

        this.checkOldItem(player, event.getOldItemStack());
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        ItemStack newItemStack = inventory.getItem(event.getNewSlot());

        if (newItemStack != null && this.checkNewItem(player, newItemStack)) {
            return;
        }

        ItemStack oldItemStack = inventory.getItem(event.getPreviousSlot());

        if (oldItemStack != null) {
            this.checkOldItem(player, oldItemStack);
        }
    }

    private void checkOldItem(Player player, ItemStack oldItemStack) {
        if (pickaxes.contains(oldItemStack.getType())) {
            int level = oldItemStack.getEnchantmentLevel(HASTE_ENCHANTMENT);

            if (level > 0) {
                manager.stopListener(player.getUniqueId(), HASTE_ENCHANTMENT);
            }
        }
    }

    private boolean checkNewItem(Player player, ItemStack newItemStack) {
        if (pickaxes.contains(newItemStack.getType())) {
            int level = newItemStack.getEnchantmentLevel(HASTE_ENCHANTMENT);

            if (level > 0) {
                HasteEffectEnchantConfig.Level config = Enchants.HASTE.config(configuration).level(level);

                manager.startListen(player, CHECK_DURATION, HASTE_ENCHANTMENT, config,
                    current -> current.getInventory().getItemInMainHand().getEnchantmentLevel(HASTE_ENCHANTMENT) == level
                );

                return true;
            }
        }

        return false;
    }

}

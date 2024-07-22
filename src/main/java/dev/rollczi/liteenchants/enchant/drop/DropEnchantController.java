package dev.rollczi.liteenchants.enchant.drop;

import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DropEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public DropEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onBlockBreak(BlockDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            return;
        }

        int level = stack.getEnchantmentLevel(Enchants.DROP.toEnchantment());

        if (level == 0) {
            return;
        }

        DropEnchantConfig config = Enchants.DROP.config(configuration);
        DropEnchantConfig.Level levelConfig = config.level(level);

        List<Item> items = event.getItems();

        for (Item item : items) {
            ItemStack itemStack = item.getItemStack();
            itemStack.setAmount((int) (itemStack.getAmount() * levelConfig.getDropMultiplier()));
        }
    }

}

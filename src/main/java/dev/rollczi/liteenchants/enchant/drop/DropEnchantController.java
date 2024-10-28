package dev.rollczi.liteenchants.enchant.drop;

import com.destroystokyo.paper.MaterialTags;
import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import org.jetbrains.annotations.Nullable;

public class DropEnchantController implements Listener {

    private final EnchantsConfiguration configuration;

    public DropEnchantController(EnchantsConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onBLockBreak(BlockBreakEvent event) {
        Integer level = this.getLevel(event.getBlock(), event.getPlayer());
        if (level == null) {
            return;
        }

        DropEnchantConfig config = Enchants.DROP.config(configuration);
        DropEnchantConfig.Level levelConfig = config.level(level);

        double expInSuperposition = event.getExpToDrop() * levelConfig.getDropMultiplier();
        int exp = (int) expInSuperposition;
        double chance = expInSuperposition - exp;

        if (ThreadLocalRandom.current().nextDouble() < chance) {
            exp++;
        }

        event.setExpToDrop(exp);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    void onBlockBreak(BlockDropItemEvent event) {
        Integer level = this.getLevel(event.getBlock(), event.getPlayer());
        if (level == null) {
            return;
        }

        DropEnchantConfig config = Enchants.DROP.config(configuration);
        DropEnchantConfig.Level levelConfig = config.level(level);

        List<Item> items = event.getItems();

        for (Item item : items) {
            ItemStack itemStack = item.getItemStack();

            double amountInSuperposition = itemStack.getAmount() * levelConfig.getDropMultiplier();
            int amount = (int) amountInSuperposition;
            double chance = amountInSuperposition - amount;

            if (ThreadLocalRandom.current().nextDouble() < chance) {
                amount++;
            }

            itemStack.setAmount(amount);
        }
    }

    @Nullable
    private Integer getLevel(Block block, Player player) {
        if (!this.isOre(block)) {
            return null;
        }

        ItemStack stack = player.getInventory().getItem(EquipmentSlot.HAND);

        if (stack.isEmpty()) {
            return null;
        }

        int level = stack.getEnchantmentLevel(Enchants.DROP.toEnchantment());

        if (level == 0) {
            return null;
        }
        return level;
    }

    private boolean isOre(Block block) {
        return MaterialTags.ORES.isTagged(block);
    }

}

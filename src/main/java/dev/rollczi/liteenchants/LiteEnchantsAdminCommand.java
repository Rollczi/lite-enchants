package dev.rollczi.liteenchants;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.liteenchants.reload.ReloadManager;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

@Command(name = "liteenchants")
@Permission("liteenchants.admin")
class LiteEnchantsAdminCommand {

    private final ReloadManager reloadManager;

    public LiteEnchantsAdminCommand(ReloadManager reloadManager) {
        this.reloadManager = reloadManager;
    }

    @Execute(name = "enchant")
    void enchant(@Arg Player player, @Arg Enchantment enchantment, @OptionalArg Integer level) {
        if (level == null) {
            level = 1;
        }

        player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, level);
    }

    @Execute(name = "give")
    void give(@Arg Player player, @Arg Enchantment enchantment, @OptionalArg Integer level) {
        int finalLevel = level == null ? 1 : level;

        PlayerInventory inventory = player.getInventory();
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        itemStack.editMeta(EnchantmentStorageMeta.class, meta -> meta.addStoredEnchant(enchantment, finalLevel, true));

        if (inventory.firstEmpty() == -1) {
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            return;
        }

        inventory.addItem(itemStack);
    }

    @Async
    @Execute(name = "reload")
    void reload() {
        reloadManager.reload();
    }

}

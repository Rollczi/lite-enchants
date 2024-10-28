package dev.rollczi.liteenchants;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.liteenchants.enchant.EnchantConfig;
import dev.rollczi.liteenchants.enchant.Enchants;
import dev.rollczi.liteenchants.enchant.EnchantsConfiguration;
import dev.rollczi.liteenchants.reload.ReloadManager;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

@Command(name = "liteenchants")
@Permission("liteenchants.admin")
class LiteEnchantsAdminCommand {

    private static final Component RESET_ITALIC = Component.text()
        .decoration(TextDecoration.ITALIC, false)
        .build();

    private final ReloadManager reloadManager;
    private final EnchantsConfiguration enchantsConfiguration;
    private final MiniMessage miniMessage;

    public LiteEnchantsAdminCommand(ReloadManager reloadManager, EnchantsConfiguration enchantsConfiguration, MiniMessage miniMessage) {
        this.reloadManager = reloadManager;
        this.enchantsConfiguration = enchantsConfiguration;
        this.miniMessage = miniMessage;
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

        EnchantConfig config = Enchants.getEnchant(enchantment).config(enchantsConfiguration);
        PlayerInventory inventory = player.getInventory();
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);

        itemStack.editMeta(EnchantmentStorageMeta.class, meta -> {
            meta.addStoredEnchant(enchantment, finalLevel, true);
            List<Component> lore = config.getLore().stream()
                .map(line -> RESET_ITALIC.append(miniMessage.deserialize(line)))
                .toList();

            meta.lore(lore);
        });

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

package dev.rollczi.liteenchants.enchant;import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.liteenchants.config.PluginConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Command(name = "zdejmij")
@Permission("liteenchants.remove.enchant")
public class EnchantRemoveCommand {

    private final PluginConfig config;
    private final MiniMessage miniMessage;

    public EnchantRemoveCommand(PluginConfig config, MiniMessage miniMessage) {
        this.config = config;
        this.miniMessage = miniMessage;
    }

    @Execute
    void removeEnchant(@Context Player player) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (mainHand.isEmpty()) {
            player.sendMessage(miniMessage.deserialize(config.needItemInHand));
            return;
        }

        mainHand.removeEnchantments();
        player.sendMessage(miniMessage.deserialize(config.enchantRemoved));
    }

}

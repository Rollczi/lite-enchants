package dev.rollczi.liteenchants.wip;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

@Command(name = "liteenchant", aliases = {"le"})
public class EnchantCommand {

    @Execute
    void executeLiteEnchant(@Context Player player, @Arg Enchantment enchantment, @Arg int level) {
        player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, level);
    }

}

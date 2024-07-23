package dev.rollczi.liteenchants.enchant;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

public class EnchantmentArgument extends ArgumentResolver<CommandSender, Enchantment> {

    @Override
    protected ParseResult<Enchantment> parse(Invocation invocation, Argument argument, String string) {
        return Enchants.ALL_ENCHANTS.stream()
            .filter(enchant -> enchant.key().key().value().equalsIgnoreCase(string))
            .findFirst()
            .map(enchant -> ParseResult.success(enchant.toEnchantment()))
            .orElseGet(() -> ParseResult.failure("&cCan not find enchant: " + string));
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Enchantment> argument, SuggestionContext context) {
        return Enchants.ALL_ENCHANTS.stream()
            .map(enchantment -> enchantment.toEnchantment().getKey().value())
            .collect(SuggestionResult.collector());
    }

}

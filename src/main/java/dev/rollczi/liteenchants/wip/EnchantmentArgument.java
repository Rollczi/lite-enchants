package dev.rollczi.liteenchants.wip;

import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.argument.resolver.ArgumentResolver;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

public class EnchantmentArgument extends ArgumentResolver<CommandSender, Enchantment> {

    private final Registry<Enchantment> registry;

    public EnchantmentArgument() {
        this.registry = RegistryAccess
            .registryAccess()
            .getRegistry(RegistryKey.ENCHANTMENT);
    }


    @Override
    protected ParseResult<Enchantment> parse(Invocation invocation, Argument argument, String string) {
        Enchantment enchantment = registry.stream()
            .filter(someEnchantment -> someEnchantment.getKey().value().equalsIgnoreCase(string))
            .findFirst()
            .orElse(null);

        if (enchantment == null) {
            return ParseResult.failure("Enchantment not found");
        }

        return ParseResult.success(enchantment);
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<Enchantment> argument, SuggestionContext context) {
        return registry.stream()
            .map(enchantment -> enchantment.getKey())
            .map(namespacedKey -> namespacedKey.value())
            .collect(SuggestionResult.collector());
    }

}

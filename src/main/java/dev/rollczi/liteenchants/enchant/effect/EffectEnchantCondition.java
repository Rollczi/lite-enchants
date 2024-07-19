package dev.rollczi.liteenchants.enchant.effect;

import java.util.UUID;
import java.util.function.Function;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

record EffectEnchantCondition(
    UUID playerUuid,
    Enchantment enchantment,
    Function<Player, Boolean> condition,
    long ticks,
    PotionEffectType effect,
    int amplifier
) {

    public boolean canNotApply(Player player) {
        return !condition.apply(player);
    }

    public @NotNull PotionEffect createEffect() {
        return new PotionEffect(effect, (int) (ticks + 10), amplifier);
    }

}

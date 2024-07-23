package dev.rollczi.liteenchants.enchant.effect;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class EffectEnchantManager {

    private final Server server;
    private final Plugin plugin;

    private final Map<UUID, EffectEnchantCondition> conditions = new HashMap<>();
    private final Map<UUID, Set<UUID>> conditionsByPlayer = new HashMap<>();
    private final Map<Enchantment, UUID> conditionsByEnchantment = new HashMap<>();

    public EffectEnchantManager(Server server, Plugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    public void startListen(Player player, Duration checkEvery, Enchantment enchantment, EffectEnchantLevel level, Function<Player, Boolean> condition) {
        UUID playerUuid = player.getUniqueId();
        UUID conditionId = UUID.randomUUID();
        long ticks = checkEvery.toMillis() / 50L;

        EffectEnchantCondition enchantCondition = new EffectEnchantCondition(playerUuid, enchantment, condition, ticks, level.getEffect(), level.getAmplifier());

        player.addPotionEffect(enchantCondition.createEffect());
        this.conditions.put(conditionId, enchantCondition);
        this.conditionsByPlayer.computeIfAbsent(playerUuid, uuid -> new HashSet<>()).add(conditionId);
        this.conditionsByEnchantment.put(enchantment, conditionId);

        this.nextCycle(conditionId);
    }

    private void nextCycle(UUID conditionId) {
        EffectEnchantCondition condition = this.conditions.get(conditionId);

        if (condition == null) {
            return;
        }

        server.getScheduler().runTaskLater(plugin, () -> {
            try {
                if (!this.conditions.containsKey(conditionId)) {
                    return;
                }

                UUID playerUuid = condition.playerUuid();
                Player player = server.getPlayer(playerUuid);

                if (player == null) {
                    this.stopListener(playerUuid, condition.enchantment());
                    return;
                }

                if (condition.canNotApply(player)) {
                    this.stopListener(playerUuid, condition.enchantment());
                    return;
                }

                if (this.canModifyEffect(player, condition)) {
                    player.addPotionEffect(condition.createEffect());
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }

            this.nextCycle(conditionId);
        }, condition.duration());
    }

    public void stopListener(UUID playerUuid, Enchantment enchantment) {
        UUID conditionId = this.conditionsByEnchantment.remove(enchantment);

        if (conditionId == null) {
            return;
        }

        EffectEnchantCondition enchantCondition = this.conditions.remove(conditionId);
        Set<UUID> uuids = this.conditionsByPlayer.get(playerUuid);

        if (uuids != null) {
            uuids.remove(conditionId);

            if (uuids.size() == 1) {
                this.conditionsByPlayer.remove(playerUuid);
            }
        }

        Player player = server.getPlayer(playerUuid);

        if (player != null && this.canModifyEffect(player, enchantCondition)) {
            player.removePotionEffect(enchantCondition.effect());
        }
    }

    public void stopAllListeners(UUID playerUuid) {
        Set<UUID> conditions = this.conditionsByPlayer.remove(playerUuid);

        if (conditions == null) {
            return;
        }

        Player player = server.getPlayer(playerUuid);

        for (UUID condition : conditions) {
            EffectEnchantCondition enchantCondition = this.conditions.remove(condition);
            this.conditionsByEnchantment.remove(enchantCondition.enchantment());

            if (player != null && this.canModifyEffect(player, enchantCondition)) {
                player.removePotionEffect(enchantCondition.effect());
            }
        }

    }

    private boolean canModifyEffect(Player player, EffectEnchantCondition condition) {
        PotionEffect potionEffect = player.getPotionEffect(condition.effect());

        if (potionEffect == null) {
            return true;
        }

        if (potionEffect.isInfinite()) {
            return false;
        }

        if (potionEffect.getAmplifier() > condition.amplifier()) {
            return false;
        }

        if (potionEffect.getDuration() > condition.duration() + EffectEnchantCondition.DURATION_BUFFER) {
            return false;
        }

        return true;
    }

}

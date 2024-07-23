package dev.rollczi.liteenchants.enchant.effect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EffectEnchantController implements Listener {

    private final EffectEnchantManager manager;

    public EffectEnchantController(EffectEnchantManager manager) {
        this.manager = manager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onQuit(PlayerQuitEvent event) {
        manager.stopAllListeners(event.getPlayer().getUniqueId());
    }

}

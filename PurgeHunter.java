package com.example.purgehunter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class PurgeHunter extends JavaPlugin implements Listener {
    private UUID hunter;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("PurgeHunter enabled.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // A player kills another player -> start/replace the active purge hunter.
        if (killer != null && killer != victim) {
            hunter = killer.getUniqueId();
            Bukkit.broadcastMessage(ChatColor.DARK_RED + "⚔ PURGE STARTED! "
                    + ChatColor.WHITE + killer.getName()
                    + ChatColor.RED + " killed " + victim.getName() + "!");
            killer.sendMessage(ChatColor.RED + "Purge active! Stay alive until you die.");
            return;
        }

        // The active hunter dies -> end purge.
        if (hunter != null && victim.getUniqueId().equals(hunter)) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "✓ PURGE ENDED! "
                    + ChatColor.WHITE + victim.getName()
                    + ChatColor.GREEN + " has died.");
            hunter = null;
        }
    }
}

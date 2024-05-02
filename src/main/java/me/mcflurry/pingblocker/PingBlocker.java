package me.mcflurry.pingblocker;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.CachedServerIcon;

import java.util.*;

public final class PingBlocker extends JavaPlugin implements Listener {
    private final List<String> list_of_addresses = new ArrayList<String>();

    private void cached_reset() {
        list_of_addresses.addAll(getConfig().getStringList("allowed-addresses"));

        if(getConfig().getBoolean("respond-after-player-quits") && getConfig().getInt("cached-address-timer") <= 0) {
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    list_of_addresses.clear();
                    cached_reset();
                }
            }, 60*getConfig().getInt("cached-address-timer")*20L);
        }
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();

        if(!Objects.equals(getConfig().getString("version"), "1.0")) {
            getLogger().warning("Error: Invalid Version.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if(!Objects.equals(getConfig().getString("mode"), "OFF")) cached_reset();
        if(!Objects.equals(getConfig().getString("mode"), "OFF") && !Objects.equals(getConfig().getString("mode"), "BLOCK") && !Objects.equals(getConfig().getString("mode"), "CLOAK")) {
            getLogger().warning("Error: Invalid Mode.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @EventHandler
    public void OnPlayerQuitEvent(PlayerQuitEvent event) {
        if(getConfig().getBoolean("respond-after-player-quits") && !list_of_addresses.contains(Objects.requireNonNull(event.getPlayer().getAddress()).toString().substring(0, event.getPlayer().getAddress().toString().indexOf(":")).replace("/", "")))
            list_of_addresses.add(event.getPlayer().getAddress().toString().substring(0, event.getPlayer().getAddress().toString().indexOf(":")).replace("/", ""));
    }

    @EventHandler
    public void onPaperServerListPingEvent(PaperServerListPingEvent event) {
        if(getConfig().getBoolean("debug")) Bukkit.getLogger().info(event.getAddress().toString().replace("/", "") + " is attempting to ping the server.");

        if(!list_of_addresses.contains(event.getAddress().toString().replace("/", "")) && Objects.equals(getConfig().getString("mode"), "BLOCK") && !event.isCancelled()) {
            event.setCancelled(true);
        }
        else if(!list_of_addresses.contains(event.getAddress().toString().replace("/", "")) && Objects.equals(getConfig().getString("mode"), "CLOAK") && !event.isCancelled()) {
            event.setNumPlayers(getConfig().getInt("cloak-player-count"));
            event.setMaxPlayers(getConfig().getInt("cloak-player-max"));

            event.setVersion(Objects.requireNonNull(getConfig().getString("cloak-version")));
            event.setProtocolVersion(getConfig().getInt("cloak-protocol-version"));

            event.setMotd(Objects.requireNonNull(getConfig().getString("cloak-motd")));

            event.setServerIcon(null);
        }
    }
}
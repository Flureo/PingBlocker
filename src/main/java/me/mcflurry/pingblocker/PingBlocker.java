package me.mcflurry.pingblocker;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class PingBlocker extends JavaPlugin implements Listener {
    public List<String> ListOfAddresses = new ArrayList<String>();
    public String Version = getConfig().getString("version");
    public boolean Debug = getConfig().getBoolean("debug");
    public String Mode = getConfig().getString("mode");
    public boolean WhitelistAfterPlayerQuits = getConfig().getBoolean("whitelist-after-player-quits");
    public List<String> WhitelistedAddresses = getConfig().getStringList("whitelisted-addresses");
    public int ResetAddressesTimer = getConfig().getInt("reset-addresses-timer");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();

        if(Objects.equals(Mode, "OFF")) Bukkit.getPluginManager().disablePlugin(this);

        if(!Objects.equals(Version, "1.1")) {
            getLogger().warning("Error: Invalid Version.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        else if(!Objects.equals(Mode, "BLOCK") && !Objects.equals(Mode, "CLOAK")) {
            getLogger().warning("Error: Invalid Mode.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        new ResetAddresses(this);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        new AddAddress(Objects.requireNonNull(event.getPlayer().getAddress()).toString().substring(0, event.getPlayer().getAddress().toString().indexOf(":")).replace("/", ""), this);
    }

    @EventHandler
    public void onPaperServerListPingEvent(PaperServerListPingEvent event) {
        new PingResponse(event, this);
    }
}
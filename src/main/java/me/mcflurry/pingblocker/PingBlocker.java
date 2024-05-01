package me.mcflurry.pingblocker;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public final class PingBlocker extends JavaPlugin implements Listener {

    private String[] list_of_addresses = new String[0];

    private void list_reset() {
        if(getConfig().getBoolean("list") && getConfig().getBoolean("list-reset")) {
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    list_of_addresses = new String[0];
                    list_reset();
                }
            }, 60*getConfig().getInt("list-reset-timer")*20L);
        }
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        saveDefaultConfig();

        if(getConfig().getBoolean("list") && getConfig().getBoolean("list-reset")) list_reset();
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        boolean hasIP = false;
        for (String s : list_of_addresses) if (s.equals(event.getAddress().toString())) hasIP = true;

        if(getConfig().getBoolean("list") && !hasIP) {
            String[] clone_buffer = list_of_addresses;
            list_of_addresses = new String[list_of_addresses.length + 1];

            System.arraycopy(clone_buffer, 0, list_of_addresses, 0, clone_buffer.length);
            list_of_addresses[list_of_addresses.length - 1] = String.valueOf(event.getAddress());
        }
    }

    @EventHandler
    public void onPaperServerListPingEvent(PaperServerListPingEvent event) {
        Bukkit.getLogger().info(event.getAddress() + " is attempting to ping the server.");

        boolean hasIP = false;
        for (String s : list_of_addresses) if (s.equals(event.getAddress().toString())) hasIP = true;

        if(!hasIP && Objects.equals(getConfig().getString("mode"), "BLOCK") && !event.isCancelled()) {
            event.setCancelled(true);
        }
        else if(!hasIP && Objects.equals(getConfig().getString("mode"), "CLOAK") && !event.isCancelled()) {
            event.setNumPlayers(0);
            event.setMaxPlayers(20);
            event.setServerIcon(null);
            event.setVersion("null");
            event.setProtocolVersion(0);
            event.setMotd("A Minecraft Server");
        }
    }
}
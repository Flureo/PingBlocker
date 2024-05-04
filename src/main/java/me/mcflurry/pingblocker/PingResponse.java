package me.mcflurry.pingblocker;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Objects;

public class PingResponse {
    public PingResponse(PaperServerListPingEvent event, PingBlocker plugin) {
        String Address = event.getAddress().toString().replace("/", "");

        if(plugin.Debug) Bukkit.getLogger().info(Address + " has pinged the server.");
        if(event.isCancelled() || plugin.ListOfAddresses.contains(Address) || plugin.WhitelistedAddresses.contains(Address)) return;

        if(Objects.equals(plugin.Mode, "BLOCK")) {
            event.setCancelled(true);
        }
        else if(Objects.equals(plugin.Mode, "CLOAK")) {
            event.setNumPlayers(plugin.getConfig().getInt("cloak.player-count"));
            event.setMaxPlayers(plugin.getConfig().getInt("cloak.player-max"));
            event.setVersion(Objects.requireNonNull(plugin.getConfig().getString("cloak.version")));
            event.setProtocolVersion(plugin.getConfig().getInt("cloak.protocol-version"));
            event.setMotd(Objects.requireNonNull(plugin.getConfig().getString("cloak.motd")));
            event.setServerIcon(null);
        }
    }
}

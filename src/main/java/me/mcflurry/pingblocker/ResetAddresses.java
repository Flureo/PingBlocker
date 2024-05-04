package me.mcflurry.pingblocker;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class ResetAddresses {
    public ResetAddresses(PingBlocker plugin) {
        if(plugin.WhitelistAfterPlayerQuits && plugin.ResetAddressesTimer > 0) {
            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.ListOfAddresses.clear();
                    new ResetAddresses(plugin);
                }
            }, 60*plugin.ResetAddressesTimer*20L);
        }
    }
}

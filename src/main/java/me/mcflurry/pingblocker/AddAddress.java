package me.mcflurry.pingblocker;

import java.util.Objects;

public class AddAddress {
    public AddAddress(String Address, PingBlocker plugin) {
        if(plugin.WhitelistAfterPlayerQuits && !plugin.ListOfAddresses.contains(Address) && !plugin.WhitelistedAddresses.contains(Address)) plugin.ListOfAddresses.add(Address);
    }
}

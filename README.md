# PingBlocker

PingBlocker is an open-source Minecraft plugin that hides servers from server scanners!
Server scanners work by first scanning every ip with 25565 open.
Then they scan all of those ips to see if it's a Minecraft server. 
To do this they send a ping request packet to the server that sends them back a json file format status of the server.
It contains the players, player count, version and the motd for the server.
This is how you get the status of the server on the server list in Minecraft.
But if the server doesn't respond to the ping then the server scanner wouldn't know if it's a Minecraft server or not. 
This is how PingBlocker protects your server from server scanners.

## Config

```yml
# Don't change this unless you update this plugin.
version: '1.0'

# If true, the plugin will log debug info.
debug: false

# There are three different modes 'BLOCK', 'CLOAK', AND 'OFF'.
# 'BLOCK' doesn't respond to any pings.
# 'CLOAK' responds with a fake ping.
# 'OFF' turns the plugin OFF.
mode: 'BLOCK'

# If true, whenever a player leaves the server the player's ip is added to a list.
# And all ips added to the list will be able to ping the server.
respond-after-player-quits: true

# Each 1 whole number is a whole minute.
# If 0, the timer will be disabled.
# After this timer is over it will clear all ips added to the respond-after-player-join list.
cached-address-timer: 30

# All ips added to allowed-addresses below will always be able to ping the server.
allowed-addresses:
  - '127.0.0.1'

# When 'CLOAK' mode is selected this will be the version displayed in the fake response ping.
cloak-version: "Null"

# When 'CLOAK' mode is selected this will be the protocol version displayed in the fake response ping.
cloak-protocol-version: 0

# When 'CLOAK' mode is selected this will be the motd displayed in the fake response ping.
cloak-motd: "A Minecraft Server"

# When 'CLOAK' mode is selected this will be the player count displayed in the fake response ping.
cloak-player-count: 0
cloak-player-max: 20
```

## Features

## Installation

### Requirements

- Java Version 17
- Paper 1.20.4 and above
- Internet (Sorry Joe you don't have download speed)

### Download

- After you [Download Latest Release](https://github.com/McFlurrYT/PingBlocker/releases/) drag and drop the plugin into your plugins folder.

## Build

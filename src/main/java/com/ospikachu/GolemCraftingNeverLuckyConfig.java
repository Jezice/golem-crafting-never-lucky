package com.ospikachu;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("golemcraftingneverlucky")
public interface GolemCraftingNeverLuckyConfig extends Config
{
    @ConfigItem(
            keyName = "enabled",
            name = "Enabled",
            description = "Enable the plugin"
    )
    default boolean enabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "message",
            name = "Message",
            description = "Message displayed when the trigger fires"
    )
    default String message()
    {
        return "Never lucky";
    }

    @ConfigItem(
            keyName = "cooldownTicks",
            name = "Cooldown",
            description = "Minimum number of game ticks between trigger messages"
    )
    default int cooldownTicks()
    {
        return 10;
    }
}

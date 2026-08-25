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
            description = "Enable the Never Lucky overlay"
    )
    default boolean enabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "message",
            name = "Message",
            description = "Text shown above your character"
    )
    default String message()
    {
        return "Never lucky";
    }

    @ConfigItem(
            keyName = "displayTicks",
            name = "Display duration",
            description = "How long the message stays above your character"
    )
    default int displayTicks()
    {
        return 30;
    }

    @ConfigItem(
            keyName = "cooldownTicks",
            name = "Cooldown",
            description = "Prevents duplicate messages"
    )
    default int cooldownTicks()
    {
        return 5;
    }
}
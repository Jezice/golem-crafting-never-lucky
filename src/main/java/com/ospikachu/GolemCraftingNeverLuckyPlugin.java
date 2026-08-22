package com.ospikachu;

import com.google.inject.Provides;
import javax.inject.Inject;

import net.runelite.api.GameState;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
        name = "Golem Crafting Never Lucky",
        description = "Shows a configurable Never lucky message for Golem Crafting events.",
        tags = {"golem", "crafting", "minigame", "never lucky"}
)
public class GolemCraftingNeverLuckyPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private GolemCraftingNeverLuckyConfig config;

    private int cooldownTicks;

    @Provides
    GolemCraftingNeverLuckyConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(GolemCraftingNeverLuckyConfig.class);
    }

    @Override
    protected void startUp()
    {
        cooldownTicks = 0;
    }

    @Override
    protected void shutDown()
    {
        cooldownTicks = 0;
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (cooldownTicks > 0)
        {
            cooldownTicks--;
        }

        if (client.getGameState() != GameState.LOGGED_IN || !config.enabled())
        {
            return;
        }

        /*
         * Golem Crafting trigger intentionally left isolated here.
         *
         * We should wire this to the exact current OSRS/RuneLite
         * event/state after verifying the minigame's current IDs/messages.
         * This avoids relying on invented IDs.
         */
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() != GameState.LOGGED_IN)
        {
            cooldownTicks = 0;
        }
    }

    private boolean canTrigger()
    {
        return cooldownTicks <= 0;
    }

    private void trigger()
    {
        cooldownTicks = Math.max(0, config.cooldownTicks());

        /*
         * The final reaction should be implemented here once the
         * exact Golem Crafting event has been verified.
         */
    }
}

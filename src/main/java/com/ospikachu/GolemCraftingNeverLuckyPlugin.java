package com.ospikachu;

import com.google.inject.Provides;
import java.util.regex.Pattern;
import javax.inject.Inject;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Golem Crafting Never Lucky",
        description = "Shows 'Never lucky' above your character when a Golem Crafting golem is completed.",
        tags = {
                "golem",
                "crafting",
                "wyrmscraig",
                "never lucky"
        }
)
public class GolemCraftingNeverLuckyPlugin extends Plugin
{
    private static final Pattern GOLEM_COMPLETE_MESSAGE = Pattern.compile(
            "As you complete the golem it leaves a gift \\((on the ground|in your gem sack|in your gem bag)\\) for you: (\\d+) x (.*)\\."
    );

    @Inject
    private Client client;

    @Inject
    private GolemCraftingNeverLuckyConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private GolemCraftingNeverLuckyOverlay overlay;

    private int ticksRemaining;

    @Provides
    GolemCraftingNeverLuckyConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(GolemCraftingNeverLuckyConfig.class);
    }

    @Override
    protected void startUp()
    {
        ticksRemaining = 0;
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        ticksRemaining = 0;
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (ticksRemaining > 0)
        {
            ticksRemaining--;
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        ticksRemaining = 0;
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (!config.enabled())
        {
            return;
        }

        if (event.getType() != ChatMessageType.GAMEMESSAGE)
        {
            return;
        }

        if (ticksRemaining > 0)
        {
            return;
        }

        String message = event.getMessage();

        if (message == null)
        {
            return;
        }

        message = message.replaceAll("<[^>]*>", "");

        if (!GOLEM_COMPLETE_MESSAGE.matcher(message).matches())
        {
            return;
        }

        Player localPlayer = client.getLocalPlayer();

        if (localPlayer == null)
        {
            return;
        }

        ticksRemaining = Math.max(1, config.cooldownTicks());

        overlay.show(config.message(), config.displayTicks());
    }
}
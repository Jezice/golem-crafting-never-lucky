package com.ospikachu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

public class GolemCraftingNeverLuckyOverlay extends Overlay
{
    private final Client client;

    private String text = "Never lucky";
    private int ticksRemaining = 0;

    @Inject
    public GolemCraftingNeverLuckyOverlay(Client client)
    {
        this.client = client;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    public void show(String text, int ticks)
    {
        this.text = text;
        this.ticksRemaining = ticks;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (ticksRemaining <= 0)
        {
            return null;
        }

        Player player = client.getLocalPlayer();

        if (player == null)
        {
            return null;
        }

        Point point = player.getCanvasTextLocation(graphics, text, player.getLogicalHeight() + 30);

        if (point == null)
        {
            return null;
        }

        graphics.setFont(graphics.getFont().deriveFont(18f));

        graphics.setColor(Color.BLACK);
        graphics.drawString(text, point.x + 1, point.y + 1);

        graphics.setColor(Color.WHITE);
        graphics.drawString(text, point.x, point.y);

        ticksRemaining--;

        return null;
    }
}
package me.dexrn.conversiondebugger.screen.debug.renderers;

import me.dexrn.conversiondebugger.itf.PlayerDuckInterface;
import me.dexrn.conversiondebugger.screen.DebugScreen;
import me.dexrn.conversiondebugger.screen.debug.IDebugRenderer;
import net.minecraft.client.gui.GuiElement;

public class PlayerSpeedDebugRenderer implements IDebugRenderer {
	@Override
	public void render(DebugScreen screen, float tickDelta) {
		if (screen.minecraft.player == null) return;

		PlayerDuckInterface d = (PlayerDuckInterface) screen.minecraft.player;

		GuiElement.drawString(screen.minecraft.textRenderer, "Speed: " + String.format("%.3f", d.conversionDebugger$getSpeed()), 2, 88, 14737632);
	}
}

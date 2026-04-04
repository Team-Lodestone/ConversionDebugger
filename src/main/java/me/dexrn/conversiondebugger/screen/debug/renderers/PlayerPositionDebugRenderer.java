package me.dexrn.conversiondebugger.screen.debug.renderers;

import me.dexrn.conversiondebugger.screen.DebugScreen;
import me.dexrn.conversiondebugger.screen.debug.IDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.util.math.MathHelper;

public class PlayerPositionDebugRenderer implements IDebugRenderer {
	private final String[] directions = {
		"North",
		"West",
		"South",
		"East"
	};

	public void render(DebugScreen screen, float tickDelta) {
		Minecraft mc = screen.minecraft;
		TextRenderer textRenderer = mc.textRenderer;
		GuiElement.drawString(textRenderer, "XYZ: " + String.format("%.3f / %.5f / %.3f", mc.player.x, mc.player.y, mc.player.z), 2, 28, 14737632);
		GuiElement.drawString(textRenderer, "Facing: " + directions[(MathHelper.floor((double)(mc.player.yaw * 4.0F / 360.0F) + 0.5D) & 3)], 2, 38, 14737632);
	}
}

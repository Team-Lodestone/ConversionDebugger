package me.dexrn.conversiondebugger.screen.debug.renderers;

import me.dexrn.conversiondebugger.screen.DebugScreen;
import me.dexrn.conversiondebugger.screen.debug.IDebugRenderer;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.render.TextRenderer;

public class MemoryDebugRenderer implements IDebugRenderer {

	@Override
	public void render(DebugScreen screen, float tickDelta) {
		TextRenderer textRenderer = screen.minecraft.textRenderer;

		long maxMemory = Runtime.getRuntime().maxMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long usedMemory = maxMemory - freeMemory;

		// todo too lazy to get Window, fix this later.
		String freeMemoryLine = "Free memory: " + usedMemory * 100L / maxMemory + "% of " + maxMemory / 1024L / 1024L + "MB";
		GuiElement.drawString(textRenderer, freeMemoryLine, textRenderer.getWidth(freeMemoryLine) - 2, 2, 14737632);

		String allocatedMemoryLine = "Allocated memory: " + totalMemory * 100L / maxMemory + "% (" + totalMemory / 1024L / 1024L + "MB)";
		GuiElement.drawString(textRenderer, allocatedMemoryLine, textRenderer.getWidth(allocatedMemoryLine) - 2, 12, 14737632);
	}
}

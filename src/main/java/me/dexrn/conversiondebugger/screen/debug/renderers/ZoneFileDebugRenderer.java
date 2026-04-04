package me.dexrn.conversiondebugger.screen.debug.renderers;

import me.dexrn.conversiondebugger.screen.DebugScreen;
import me.dexrn.conversiondebugger.screen.debug.IDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.util.math.MathHelper;

public class ZoneFileDebugRenderer implements IDebugRenderer {
	//ported constant
	private static final int CHUNKS_PER_ZONE_BITS = 5;

	@Override
	public void render(DebugScreen screen, float tickDelta) {
		Minecraft mc = screen.minecraft;

		TextRenderer textRenderer = screen.minecraft.textRenderer;

		int cx = MathHelper.floor(mc.player.x) >> 4;
		int cz = MathHelper.floor(mc.player.z) >> 4;

		int zx = cx >> CHUNKS_PER_ZONE_BITS;
		int zz = cz >> CHUNKS_PER_ZONE_BITS;

		GuiElement.drawString(textRenderer, "Zone: " + String.format("%d %d in zone_%s_%s.dat", zx, zz, Integer.toString(zx, 36), Integer.toString(zz, 36)), 2, 58, 14737632);
		GuiElement.drawString(textRenderer, "Chunk: " + String.format("%d %d", cx, cz), 2, 68, 14737632);
	}
}

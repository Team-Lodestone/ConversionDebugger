package me.dexrn.conversiondebugger.screen;

import me.dexrn.conversiondebugger.screen.debug.IDebugRenderer;
import me.dexrn.conversiondebugger.screen.debug.renderers.MemoryDebugRenderer;
import me.dexrn.conversiondebugger.screen.debug.renderers.PlayerPositionDebugRenderer;
import me.dexrn.conversiondebugger.screen.debug.renderers.PlayerSpeedDebugRenderer;
import me.dexrn.conversiondebugger.screen.debug.renderers.ZoneFileDebugRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;

import java.util.ArrayList;
import java.util.List;

public class DebugScreen extends GuiElement {
	public final Minecraft minecraft;

	private final List<IDebugRenderer> debugRenderers = new ArrayList<>();

	public DebugScreen(Minecraft minecraft) {
		this.minecraft = minecraft;

		this.debugRenderers.add(new MemoryDebugRenderer());
		this.debugRenderers.add(new ZoneFileDebugRenderer());
		this.debugRenderers.add(new PlayerPositionDebugRenderer());
		this.debugRenderers.add(new PlayerSpeedDebugRenderer());
	}

	public final void render(float tickDelta) {
		debugRenderers.forEach(r -> r.render(this, tickDelta));
	}
}

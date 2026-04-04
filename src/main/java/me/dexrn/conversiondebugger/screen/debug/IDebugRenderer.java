package me.dexrn.conversiondebugger.screen.debug;

import me.dexrn.conversiondebugger.screen.DebugScreen;

@FunctionalInterface
public interface IDebugRenderer {
	void render(DebugScreen screen, float tickDelta);
}

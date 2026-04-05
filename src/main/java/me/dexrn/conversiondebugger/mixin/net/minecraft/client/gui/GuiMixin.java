package me.dexrn.conversiondebugger.mixin.net.minecraft.client.gui;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.dexrn.conversiondebugger.screen.DebugScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameGui.class)
public class GuiMixin {
	@Shadow
	private Minecraft minecraft;

	@Unique
	private DebugScreen debugScreen;

	@Unique
	private float tickDelta;

	@Inject(method = "render", at = @At("HEAD"))
	private void conversiondebugger$injectRender(float tickDelta, CallbackInfo ci) {
		this.tickDelta = tickDelta;
	}

	@Definition(id = "minecraft", field = "Lnet/minecraft/client/gui/GameGui;minecraft:Lnet/minecraft/client/Minecraft;")
	@Definition(id = "options", field = "Lnet/minecraft/client/Minecraft;options:Lnet/minecraft/client/options/GameOptions;")
	@Definition(id = "showFps", field = "Lnet/minecraft/client/options/GameOptions;showFps:Z")
	@Expression("this.minecraft.options.showFps")
	@ModifyExpressionValue(method = "render", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
	public boolean conversiondebugger$renderDebugScreen(boolean original)  {
		if (original) {
			if (debugScreen == null) {
				debugScreen = new DebugScreen(this.minecraft);
			}

			debugScreen.render(this.tickDelta);
		}

		return false;
	}
}

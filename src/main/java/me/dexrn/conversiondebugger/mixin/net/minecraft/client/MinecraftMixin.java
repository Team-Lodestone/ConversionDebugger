package me.dexrn.conversiondebugger.mixin.net.minecraft.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.dexrn.conversiondebugger.itf.PlayerDuckInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.mob.player.ClientPlayerEntity;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public GameOptions options;

	@Shadow
	public ClientPlayerEntity player;

	// inject where keyboard loop happens
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;next()Z"))
	private boolean conversiondebugger$tickKeyboard(Operation<Boolean> original) {
		Keyboard.enableRepeatEvents(true);
		boolean loop = original.call();
		Keyboard.enableRepeatEvents(false);

		return loop;
	}

	// then we add our keybinds
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 5))
	private void conversiondebugger$tickKeybinds(CallbackInfo ci) {
		if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
			this.options.showFps = !this.options.showFps;
		}
	}

	// and then our mouse shit
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", shift = At.Shift.BEFORE))
	private void conversiondebugger$tickMouse(CallbackInfo ci) {
		if (Mouse.hasWheel()) {
			int wheel = Mouse.getEventDWheel();

			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				if (wheel > 0) {
					// speed up player
					((PlayerDuckInterface) this.player).conversionDebugger$increaseSpeed();
				} else if (wheel < 0) {
					// slow down player
					((PlayerDuckInterface) this.player).conversionDebugger$decreaseSpeed();
				}
			}
		}
	}

}

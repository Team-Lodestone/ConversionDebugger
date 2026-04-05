package me.dexrn.conversiondebugger.mixin.net.minecraft.world.entity.player;

import me.dexrn.conversiondebugger.itf.PlayerDuckInterface;
import net.minecraft.entity.mob.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public class PlayerMixin implements PlayerDuckInterface {
	@Unique
	private static final float MAX_SPEED = 16.0f;
	@Unique
	private static final float MIN_SPEED = 1.0f;
	@Unique
	private static final float DEFAULT_SPEED = MIN_SPEED;

	@Unique
	public float speedModifier = DEFAULT_SPEED;

	@Override
	public void conversionDebugger$increaseSpeed() {
		speedModifier = Math.min(speedModifier + 0.1f, MAX_SPEED);
	}

	@Override
	public void conversionDebugger$decreaseSpeed() {
		speedModifier = Math.max(speedModifier - 0.1f, MIN_SPEED);
	}

	@Override
	public float conversionDebugger$getSpeed() {
		return this.speedModifier;
	}
}

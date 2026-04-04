package me.dexrn.conversiondebugger.mixin;

import me.dexrn.conversiondebugger.itf.PlayerDuckInterface;
import net.minecraft.client.entity.mob.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@ModifyVariable(method = "move(DDD)V", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
	public double moveX(double dx) {
		double d = dx;

		if (((Entity) (Object)this) instanceof ClientPlayerEntity) {
			PlayerDuckInterface playerDuck = (PlayerDuckInterface) this;

			d *= playerDuck.conversionDebugger$getSpeed();
		}

		return d;
	}

	@ModifyVariable(method = "move(DDD)V", at = @At(value = "HEAD"), argsOnly = true, ordinal = 2)
	public double moveZ(double dz) {
		double d = dz;

		if (((Entity) (Object)this) instanceof ClientPlayerEntity) {
			PlayerDuckInterface playerDuck = (PlayerDuckInterface) this;

			d *= playerDuck.conversionDebugger$getSpeed();
		}

		return d;
	}
}

package me.dexrn.conversiondebugger.mixin.net.minecraft.client.player;

import net.minecraft.client.entity.mob.player.ClientPlayerEntity;
import net.minecraft.client.entity.mob.player.Input;
import net.minecraft.entity.mob.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerEntity.class)
public abstract class LocalPlayerMixin extends PlayerEntity {
	@Shadow
	public Input input;

	public LocalPlayerMixin(World world) {
		super(world);
	}
}

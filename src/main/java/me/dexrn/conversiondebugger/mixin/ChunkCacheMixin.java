package me.dexrn.conversiondebugger.mixin;

import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.world.chunk.ChunkCache;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ChunkCache.class)
public abstract class ChunkCacheMixin implements ChunkSource {
	@Unique
	private static final int CHUNK_CACHE_WIDTH = 32;

	@Shadow
	public abstract boolean hasChunk(int chunkX, int chunkZ);

	@Shadow
	private WorldChunk empty;

	@Shadow
	protected abstract WorldChunk loadChunkFromStorage(int chunkX, int chunkZ);

	@Shadow
	private int cachedChunkX;

	@Shadow
	private int cachedChunkZ;

	@Shadow
	private WorldChunk cachedChunk;

	@Shadow
	private WorldChunk[] chunks;

	@Shadow
	protected abstract void saveChunk(WorldChunk chunk);

	@Shadow
	protected abstract void saveEntities(WorldChunk chunk);

	@Shadow
	private ChunkSource generator;

	@Unique
	private boolean shouldGenerate = true;

	@Override
	public WorldChunk getChunk(int chunkX, int chunkZ) {
		// TODO clean this up, maybe optimize it idk
		// it's carbon copy of notch's original with shouldGenerate added
		if (chunkX == this.cachedChunkX && chunkZ == this.cachedChunkZ && this.cachedChunk != null) {
			return this.cachedChunk;
		} else {
			int var3 = chunkX & 31;
			int var4 = chunkZ & 31;

			int idx = var3 + var4 * CHUNK_CACHE_WIDTH;
			if (!this.hasChunk(chunkX, chunkZ)) {
				FallingBlock.fallImmediately = true;
				if (this.chunks[idx] != null) {
					WorldChunk ch = this.chunks[idx];

					ch.loaded = false;
					ch.world.blockEntities.removeAll(ch.blockEntities.values());

					for (List entity : ch.entities) {
						ch.world.unloadEntities(entity);
					}

					if (shouldGenerate) {
						this.saveChunk(this.chunks[idx]);
						this.saveEntities(this.chunks[idx]);
					}
				}

				WorldChunk chunk;
				if ((chunk = this.loadChunkFromStorage(chunkX, chunkZ)) == null) {
					if (!shouldGenerate) {
						chunk = this.empty;
					} else {
						if (this.generator == null) {
							chunk = this.empty;
						}
						else {
							chunk = this.generator.getChunk(chunkX, chunkZ);
						}
					}
				}

				this.chunks[idx] = chunk;
				if (this.chunks[idx] != null) {
					WorldChunk ch = this.chunks[idx];

					ch.loaded = true;
					ch.world.blockEntities.addAll(ch.blockEntities.values());

					for (List entity : ch.entities) {
						ch.world.loadEntities(entity);
					}
				}

				if (!this.chunks[idx].terrainPopulated && this.hasChunk(chunkX + 1, chunkZ + 1) && this.hasChunk(chunkX, chunkZ + 1) && this.hasChunk(chunkX + 1, chunkZ)) {
					this.populateChunk(this, chunkX, chunkZ);
				}

				if (this.hasChunk(chunkX - 1, chunkZ)
					&& !this.getChunk(chunkX - 1, chunkZ).terrainPopulated
					&& this.hasChunk(chunkX - 1, chunkZ + 1)
					&& this.hasChunk(chunkX, chunkZ + 1)
					&& this.hasChunk(chunkX - 1, chunkZ)) {
					this.populateChunk(this, chunkX - 1, chunkZ);
				}

				if (this.hasChunk(chunkX, chunkZ - 1)
					&& !this.getChunk(chunkX, chunkZ - 1).terrainPopulated
					&& this.hasChunk(chunkX + 1, chunkZ - 1)
					&& this.hasChunk(chunkX, chunkZ - 1)
					&& this.hasChunk(chunkX + 1, chunkZ)) {
					this.populateChunk(this, chunkX, chunkZ - 1);
				}

				if (this.hasChunk(chunkX - 1, chunkZ - 1)
					&& !this.getChunk(chunkX - 1, chunkZ - 1).terrainPopulated
					&& this.hasChunk(chunkX - 1, chunkZ - 1)
					&& this.hasChunk(chunkX, chunkZ - 1)
					&& this.hasChunk(chunkX - 1, chunkZ)) {
					this.populateChunk(this, chunkX - 1, chunkZ - 1);
				}

				FallingBlock.fallImmediately = false;
			}

			this.cachedChunkX = chunkX;
			this.cachedChunkZ = chunkZ;
			this.cachedChunk = this.chunks[idx];
			return this.chunks[idx];
		}
	}
}

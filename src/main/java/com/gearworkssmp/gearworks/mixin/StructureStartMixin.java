package com.gearworkssmp.gearworks.mixin;

import com.gearworkssmp.gearworks.Gearworks;
import com.gearworkssmp.gearworks.special.TrailRuinManager;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mixin(StructureStart.class)
public abstract class StructureStartMixin {
	@Unique
	private String customStructureId;

	public String getCustomStructureId() {
		return customStructureId;
	}

	public void setCustomStructureId(String id) {
		this.customStructureId = id;
	}

	@Shadow
	public abstract Structure getStructure();

	@Shadow
	public abstract List<StructurePiece> getChildren();

	@Inject(method = "fromNbt", at = @At("RETURN"))
	private static void onFromNbt(StructureContext context, NbtCompound nbt, long seed, CallbackInfoReturnable<StructureStart> cir) {
		StructureStart instance = cir.getReturnValue();
		if (instance != null) {
			// Set the custom id from the NBT "id" field.
			((StructureStartMixin) (Object) instance).setCustomStructureId(nbt.getString("id"));
		}
	}

	@Unique
	private static final Set<String> ALLOWED_STRUCTURE_IDS = Set.of(
			"minecraft:trail_ruins",
			"betterarcheology:archeologist_camp_grassy",
			"betterarcheology:archeologist_camp_redsand",
			"betterarcheology:archeologist_camp_sand",
			"betterarcheology:buried_ruins_sand",
			"betterarcheology:catacombs",
			"betterarcheology:desert_obelisk",
			"betterarcheology:fossil_chicken",
			"betterarcheology:fossil_chicken_birch",
			"betterarcheology:fossil_creeper",
			"betterarcheology:fossil_jungle_0",
			"betterarcheology:fossil_jungle_1",
			"betterarcheology:fossil_sheep_0",
			"betterarcheology:fossil_wolf",
			"betterarcheology:light_temple",
			"betterarcheology:mesa_ruins",
			"betterarcheology:mott",
			"betterarcheology:ruins_sand",
			"betterarcheology:stonehenge_grassy",
			"betterarcheology:temple_jungle",
			"betterarcheology:tumulus_grassy",
			"betterarcheology:underwater_0",
			"betterarcheology:underwater_1",
			"betterarcheology:underwater_2",
			"betterarcheology:underwater_3",
			"betterarcheology:villager_grave"
	);

	/**
	 * Injects at the end of the place() method.
	 * When a structure is placed, if it is a trail ruin, record its center position.
	 */
	@Inject(method = "place", at = @At("RETURN"))
	private void onPlace(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, CallbackInfo ci) {
		// Check that the structure's registry name matches the trail ruin.
		String customId = getCustomStructureId();
		if (customId != null && ALLOWED_STRUCTURE_IDS.contains(customId)) {
			List<StructurePiece> pieces = getChildren();
			if (!pieces.isEmpty()) {
				// Use the first piece's bounding box to calculate a center.
				StructurePiece firstPiece = pieces.get(0);
				BlockBox pieceBox = firstPiece.getBoundingBox();
				BlockPos center = pieceBox.getCenter();
				// Use the minimum Y value of the bounding box to get a ground-level position.
				BlockPos pos = new BlockPos(center.getX(), pieceBox.getMinY(), center.getZ());
				// Record the trail ruin position in our global manager.
				TrailRuinManager.addTrailRuin(pos);
			}
		}
	}
}

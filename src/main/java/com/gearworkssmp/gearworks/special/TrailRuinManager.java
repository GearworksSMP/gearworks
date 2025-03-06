package com.gearworkssmp.gearworks.special;

import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class TrailRuinManager {
	private static final Set<BlockPos> TRAIL_RUINS = new HashSet<>();

	public static void addTrailRuin(BlockPos pos) {
		TRAIL_RUINS.add(pos);
	}

	public static Set<BlockPos> getTrailRuins() {
		return TRAIL_RUINS;
	}
}

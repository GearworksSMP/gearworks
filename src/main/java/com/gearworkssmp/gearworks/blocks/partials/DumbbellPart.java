package com.gearworkssmp.gearworks.blocks.partials;

import net.minecraft.util.StringIdentifiable;

public enum DumbbellPart implements StringIdentifiable {
	LEFT,
	RIGHT;

	@Override
	public String asString() {
		return name().toLowerCase();
	}
}

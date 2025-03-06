package com.gearworkssmp.gearworks.mixin;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

	@Inject(method = "tickWeather", at = @At("TAIL"))
	private void overrideWeatherTimers(CallbackInfo ci) {
		ServerWorld world = (ServerWorld) (Object) this;

		int customClearDuration = 1800000;
		int customRainDuration = 1200;
		boolean customRaining = false;
		boolean customThundering = false;

		// Call setWeather with our custom values.
		world.setWeather(customClearDuration, customRainDuration, customRaining, customThundering);
	}
}

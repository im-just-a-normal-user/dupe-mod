package com.example.dupemod.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "runServer")
	private void init(CallbackInfo info) {
		// Este método "runServer" es más estable en 1.21.1
		System.out.println("DupeMod: Mixin cargado correctamente.");
	}
}

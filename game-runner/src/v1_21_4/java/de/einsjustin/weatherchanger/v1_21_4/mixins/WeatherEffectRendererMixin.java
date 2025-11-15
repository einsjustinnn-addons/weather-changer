package de.einsjustin.weatherchanger.v1_21_4.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Biome.class)
public abstract class WeatherEffectRendererMixin {

  @Shadow
  public abstract boolean coldEnoughToSnow(BlockPos $$0, int $$1);

  @Redirect(
      method = "getPrecipitationAt",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;I)Z")
  )
  private boolean redirectGetPrecipitationAt(Biome instance, BlockPos blockPos, int i) {

    Weather weather = WeatherChangerAddon.INSTANCE.configuration().weather().get();
    if (weather == null) {
      return this.coldEnoughToSnow(blockPos, i);
    }
    if (weather == Weather.CLEAR) {
      return false;
    }
    if (weather == Weather.SNOW) {
      return true;
    }
    return this.coldEnoughToSnow(blockPos, i);
  }
}

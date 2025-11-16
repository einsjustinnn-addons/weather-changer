package de.einsjustin.weatherchanger.v1_21_5.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Biome.class)
public abstract class BiomeMixin {

  @Shadow
  public abstract boolean coldEnoughToSnow(BlockPos $$0, int $$1);

  @Redirect(
      method = "getPrecipitationAt",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;I)Z")
  )
  private boolean redirectGetPrecipitationAt(Biome instance, BlockPos blockPos, int i) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return this.coldEnoughToSnow(blockPos, i);
    }
    Weather weather = configuration.weather().get();
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

package de.einsjustin.weatherchanger.v1_17_1.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {

  @Inject(
      method = "getPrecipitation",
      at = @At("HEAD"),
      cancellable = true
  )
  private void injectGetPrecipitation(CallbackInfoReturnable<Precipitation> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    Weather weather = configuration.weather().get();
    if (weather == null) {
      return;
    }
    if (weather == Weather.CLEAR) {
      cir.setReturnValue(Precipitation.NONE);
      return;
    }
    if (weather == Weather.SNOW) {
      cir.setReturnValue(Precipitation.SNOW);
    }
  }
}

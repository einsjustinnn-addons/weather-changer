package de.einsjustin.weatherchanger.v1_12_2.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public abstract class BiomeMixin {

  @Inject(
      method = "isSnowyBiome",
      at = @At("HEAD"),
      cancellable = true
  )
  private void injectGetPrecipitation(CallbackInfoReturnable<Boolean> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    Weather weather = configuration.weather().get();
    if (weather == null) {
      return;
    }
    if (weather == Weather.CLEAR) {
      cir.setReturnValue(false);
      return;
    }
    if (weather == Weather.SNOW) {
      cir.setReturnValue(true);
    }
  }
}

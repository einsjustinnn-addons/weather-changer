package de.einsjustin.weatherchanger.v1_21_4.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome.Precipitation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherEffectRenderer.class)
public class WeatherEffectRendererMixin {

  @Inject(
      method = "getPrecipitationAt",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_getPrecipitationAt(Level level, BlockPos blockPos, CallbackInfoReturnable<Precipitation> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    Weather weather = configuration.weather().get();
    if (weather == Weather.SNOW) {
      cir.setReturnValue(Precipitation.SNOW);
    }
  }
}

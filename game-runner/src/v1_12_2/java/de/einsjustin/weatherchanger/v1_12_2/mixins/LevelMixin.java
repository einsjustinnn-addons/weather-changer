package de.einsjustin.weatherchanger.v1_12_2.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class LevelMixin {

  @Inject(
      method = "isRaining",
      at = @At("HEAD"),
      cancellable = true
  )
  private void injectRain(CallbackInfoReturnable<Boolean> cir) {
    Weather weather = weather_changer$getWeather();
    if (weather == null) {
      return;
    }
    if (weather == Weather.CLEAR) {
      cir.setReturnValue(false);
      return;
    }
    if (weather == Weather.RAIN || weather == Weather.THUNDER || weather == Weather.SNOW) {
      cir.setReturnValue(true);
    }
  }

  @Inject(
      method = "getRainStrength",
      at = @At("HEAD"),
      cancellable = true
  )
  private void injectRainLevel(CallbackInfoReturnable<Float> cir) {
    Weather weather = weather_changer$getWeather();
    if (weather == null) {
      return;
    }
    if (weather == Weather.CLEAR) {
      cir.setReturnValue(0.0F);
      return;
    }
    if (weather == Weather.RAIN || weather == Weather.THUNDER || weather == Weather.SNOW) {
      cir.setReturnValue(weatherchanger$getFrequency());
    }
  }

  @Inject(
      method = "isThundering",
      at = @At("HEAD"),
      cancellable = true
  )
  private void injectThundering(CallbackInfoReturnable<Boolean> cir) {
    Weather weather = weather_changer$getWeather();
    if (weather == null) {
      return;
    }
    if (weather == Weather.CLEAR) {
      cir.setReturnValue(false);
      return;
    }
    if (weather == Weather.THUNDER) {
      cir.setReturnValue(true);
    }
  }

  @Inject(
      method = "getThunderStrength",
      at = @At("HEAD"),
      cancellable = true
  )
  private void injectThunderLevel(CallbackInfoReturnable<Float> cir) {
    Weather weather = weather_changer$getWeather();
    if (weather == null) {
      return;
    }
    if (weather == Weather.CLEAR) {
      cir.setReturnValue(0.0F);
      return;
    }
    if (weather == Weather.THUNDER) {
      cir.setReturnValue(weatherchanger$getFrequency());
    }
  }

  @Unique
  private float weatherchanger$getFrequency() {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return 1.0F;
    }
    return configuration.frequency().get();
  }

  @Unique
  private Weather weather_changer$getWeather() {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return null;
    }
    return configuration.weather().get();
  }
}

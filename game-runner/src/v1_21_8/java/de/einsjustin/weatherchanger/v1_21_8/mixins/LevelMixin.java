package de.einsjustin.weatherchanger.v1_21_8.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {

  @Inject(
      method = "getRainLevel",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_getRainLevel(float $$0, CallbackInfoReturnable<Float> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    switch (configuration.weather().get()) {
      case CLEAR -> cir.setReturnValue(0.0F);
      case RAIN, SNOW, THUNDER -> cir.setReturnValue(configuration.frequency().get());
    }
    cir.cancel();
  }

  @Inject(
      method = "getThunderLevel",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_getThunderLevel(float $$0, CallbackInfoReturnable<Float> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    switch (configuration.weather().get()) {
      case CLEAR -> cir.setReturnValue(0.0F);
      case THUNDER -> cir.setReturnValue(configuration.frequency().get());
    }
    cir.cancel();
  }

  @Inject(
      method = "isRaining",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_isRaining(CallbackInfoReturnable<Boolean> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    switch (configuration.weather().get()) {
      case CLEAR -> cir.setReturnValue(false);
      case RAIN, SNOW, THUNDER -> cir.setReturnValue(true);
    }
    cir.cancel();
  }

  @Inject(
      method = "isThundering",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_isThundering(CallbackInfoReturnable<Boolean> cir) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return;
    }
    switch (configuration.weather().get()) {
      case CLEAR -> cir.setReturnValue(false);
      case THUNDER -> cir.setReturnValue(true);
    }
    cir.cancel();
  }
}

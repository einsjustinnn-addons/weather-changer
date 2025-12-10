package de.einsjustin.weatherchanger.v1_8_9.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class LevelMixin {

  @Inject(
      method = "getRainStrength",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_getRainStrength(float $$0, CallbackInfoReturnable<Float> cir) {
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
      method = "getThunderStrength",
      at = @At("HEAD"),
      cancellable = true
  )
  private void inject_getThunderStrength(float $$0, CallbackInfoReturnable<Float> cir) {
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

  @Redirect(
      method = "canSnowAt",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeGenBase;getFloatTemperature(Lnet/minecraft/util/BlockPos;)F")
  )
  private float redirect_canSnowAt(BiomeGenBase instance, BlockPos blockPos) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return instance.getFloatTemperature(blockPos);
    }
    Weather weather = configuration.weather().get();
    if (weather == Weather.SNOW) {
      return 0.1F;
    }
    return instance.getFloatTemperature(blockPos);
  }
}

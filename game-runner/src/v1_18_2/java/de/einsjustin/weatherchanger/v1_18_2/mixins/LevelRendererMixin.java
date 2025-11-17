package de.einsjustin.weatherchanger.v1_18_2.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

  @Redirect(
      method = "renderSnowAndRain",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z")
  )
  private boolean injectShouldSnow(Biome instance, BlockPos blockPos) {

    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return instance.warmEnoughToRain(blockPos);
    }
    Weather weather = configuration.weather().get();
    if (weather == null) {
      return instance.warmEnoughToRain(blockPos);
    }
    if (weather == Weather.CLEAR) {
      return true;
    }
    if (weather == Weather.SNOW) {
      return false;
    }

    return instance.warmEnoughToRain(blockPos);
  }
}

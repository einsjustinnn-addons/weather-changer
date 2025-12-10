package de.einsjustin.weatherchanger.v1_16_5.mixins;

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
public class LevelRendererMixin {

  @Redirect(
      method = "renderSnowAndRain",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getTemperature(Lnet/minecraft/core/BlockPos;)F")
  )
  private float redirect_renderSnowAndRain(Biome instance, BlockPos blockPos) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return instance.getTemperature(blockPos);
    }
    Weather weather = configuration.weather().get();
    if (weather == Weather.SNOW) {
      return 0.1F;
    }
    return instance.getTemperature(blockPos);
  }
}

package de.einsjustin.weatherchanger.v1_21.mixins;

import de.einsjustin.weatherchanger.WeatherChangerAddon;
import de.einsjustin.weatherchanger.WeatherChangerConfiguration;
import de.einsjustin.weatherchanger.api.Weather;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

  @Redirect(
      method = "renderSnowAndRain",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;")
  )
  private Precipitation redirect_renderSnowAndRain(Biome instance, BlockPos $$0) {
    WeatherChangerConfiguration configuration = WeatherChangerAddon.INSTANCE.configuration();
    if (!configuration.enabled().get()) {
      return instance.getPrecipitationAt($$0);
    }
    Weather weather = configuration.weather().get();
    if (weather == Weather.SNOW) {
      return Precipitation.SNOW;
    }
    return null;
  }
}

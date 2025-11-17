package de.einsjustin.weatherchanger;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class WeatherChangerAddon extends LabyAddon<WeatherChangerConfiguration> {

  public static WeatherChangerAddon INSTANCE;

  @Override
  protected void enable() {
    // TODO: fix 1.8, 1.12, 1.16, 1.17
    INSTANCE = this;
    this.registerSettingCategory();
  }

  @Override
  protected Class<WeatherChangerConfiguration> configurationClass() {
    return WeatherChangerConfiguration.class;
  }
}

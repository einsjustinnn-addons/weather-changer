package de.einsjustin.weatherchanger;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class WeatherChangerAddon extends LabyAddon<WeatherChangerConfiguration> {

  public static WeatherChangerAddon INSTANCE;

  @Override
  protected void enable() {
    /* TODO
    1.8, 1.12 - snow the world and no snow effect
    1.16, 1.17 - rain particle while snowing
     */
    INSTANCE = this;
    this.registerSettingCategory();
  }

  @Override
  protected Class<WeatherChangerConfiguration> configurationClass() {
    return WeatherChangerConfiguration.class;
  }
}

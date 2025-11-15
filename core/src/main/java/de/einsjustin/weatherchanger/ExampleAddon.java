package de.einsjustin.weatherchanger;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class ExampleAddon extends LabyAddon<ExampleConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();
  }

  @Override
  protected Class<ExampleConfiguration> configurationClass() {
    return ExampleConfiguration.class;
  }
}

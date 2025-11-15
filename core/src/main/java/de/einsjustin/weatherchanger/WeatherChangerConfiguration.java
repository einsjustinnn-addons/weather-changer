package de.einsjustin.weatherchanger;

import de.einsjustin.weatherchanger.api.Weather;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class WeatherChangerConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @DropdownSetting
  private final ConfigProperty<Weather> weather = new ConfigProperty<>(Weather.CLEAR);

  @SliderSetting(min = 0.1F, max = 1.0F, steps = 0.1F)
  private final ConfigProperty<Float> frequency = new ConfigProperty<>(1.0F);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Weather> weather() {
    return this.weather;
  }

  public ConfigProperty<Float> frequency() {
    return this.frequency;
  }
}

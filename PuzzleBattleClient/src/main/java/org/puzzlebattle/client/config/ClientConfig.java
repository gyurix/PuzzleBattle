package org.puzzlebattle.client.config;

import lombok.Getter;
import lombok.Setter;
import org.puzzlebattle.client.games.bouncer.BouncerGameClientSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowClientSettings;
import org.puzzlebattle.core.entity.AddressInfo;

import java.util.HashMap;

@Getter
public class ClientConfig {
  private HashMap<String, BouncerGameClientSettings> ballBouncerTemplates;
  private HashMap<String, FourInARowClientSettings> fourInARowTemplates;
  private SettingsForScreens screenSettings;
  @Setter
  private String lang = "en";
  private AddressInfo server;
}

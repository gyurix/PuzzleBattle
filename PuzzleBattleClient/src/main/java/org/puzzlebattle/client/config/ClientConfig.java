package org.puzzlebattle.client.config;

import lombok.Getter;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.core.entity.AddressInfo;

import java.util.HashMap;

@Getter
public class ClientConfig {
  private HashMap<String, BouncerGameSettings> ballBouncerGameTemplates;
  private HashMap<String, BouncerGameSettings> fourInARowGameT;
  private String lang = "en";
  private AddressInfo serverAddress;
}

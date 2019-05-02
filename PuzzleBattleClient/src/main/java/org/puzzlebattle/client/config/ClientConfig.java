package org.puzzlebattle.client.config;

import lombok.Getter;
import lombok.Setter;
import org.puzzlebattle.client.games.bouncer.BouncerGameClientSettings;
import org.puzzlebattle.core.entity.AddressInfo;

import java.util.HashMap;

@Getter
public class ClientConfig {
  private HashMap<String, BouncerGameClientSettings> ballBouncerGameTemplates;
  private HashMap<String, BouncerGameClientSettings> fourInARowGameT;
  @Setter
  private String lang = "en";
  private AddressInfo server;
}

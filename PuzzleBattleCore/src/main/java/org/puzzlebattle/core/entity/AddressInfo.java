package org.puzzlebattle.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddressInfo {
  private String host;
  private int port;

  @Override
  public String toString() {
    return host + ":" + port;
  }
}

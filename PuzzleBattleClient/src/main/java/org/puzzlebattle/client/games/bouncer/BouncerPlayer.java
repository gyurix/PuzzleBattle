package org.puzzlebattle.client.games.bouncer;


import lombok.Getter;

@Getter
public class BouncerPlayer {
  private BouncerGame game;
  private Bouncer bouncer;
  private int goals;

  public BouncerPlayer(BouncerGame game, Bouncer bouncer) {
    this.game = game;
    this.bouncer = bouncer;
  }

  public void goal() {
    ++goals;
  }
}

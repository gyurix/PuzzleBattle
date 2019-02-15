package org.puzzlebattle.client.games.ballbouncer;


import javafx.scene.layout.Pane;
import lombok.Getter;

@Getter
public class BallBouncerPlayer extends BallBouncerEntity {
  private Bouncer bouncer;
  private int goals;

  public BallBouncerPlayer(BallBouncerGame game, Bouncer bouncer) {
    super(game);
    this.bouncer = bouncer;
  }

  public void goal() {
    ++goals;
  }

  @Override
  public void render(Pane canvas) {

  }
}

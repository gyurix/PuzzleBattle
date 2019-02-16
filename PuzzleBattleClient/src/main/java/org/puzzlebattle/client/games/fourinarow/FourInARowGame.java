package org.puzzlebattle.client.games.fourinarow;


import javafx.geometry.Point2D;
import javafx.scene.Scene;
import org.puzzlebattle.client.games.Game;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;


public class FourInARowGame extends Game {

  private FourInARowGameSettings settings;
  private FourInARowPlayer you, enemy;


  public FourInARowGame(Object serverConnection, FourInARowGameSettings settings)
 {
    super(serverConnection);
    this.settings= settings;

 }


}

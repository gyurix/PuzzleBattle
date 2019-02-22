package org.puzzlebattle.client.games.fourinarow;

import javafx.animation.Timeline;
import lombok.Setter;


/**
 * Simulation of coin fall, falling coin from certain place -at selected column
 *
 * @author Jakub Perdek, Juraj Barath
 * @version 1.0
 */
public class CoinFall implements Runnable {

  private Coin coin;
  private double speed;
  private double targetY;
  @Setter
  private Timeline timeline;


  /**
   * Rendering task used for animating a coin fall.
   *
   * @param coin    - The fallable coin
   * @param targetY - The coins target Y coordinate
   * @param speed   - The coins falling speed
   */
  public CoinFall(FourInARowScreen screen, Coin coin, double targetY, double speed) {
    this.coin = coin;
    this.targetY = targetY;
    this.speed = speed;
    timeline = screen.scheduleAtFixedRate(16, this);
  }


  /**
   * Applies the next frame of the animation.
   * Automatically cancels the animation after the coin fully fell.
   */
  public void run() {
    double toY = Math.min(targetY, coin.getCenterY() + speed);
    coin.setCenterY(toY);
    if (toY == targetY)
      timeline.stop();
  }
}

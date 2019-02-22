package org.puzzlebattle.client.games.fourinarow;

import java.util.TimerTask;


/**
 * Simulation of coin fall, falling coin from certain place -at selected column
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */

public class CoinFall extends TimerTask {

  private FourInARowScreen screen;
  private Coin newCoin;
  private double i;
  private double to;


  /**
   * Constructor which stores information about coin fall, as initial position from which coin starts fall
   * and final position where coin stops falling. Certain representation of coin and screen on which the simulation will be drawn
   * is prepared here too.
   */

  public CoinFall(double from, double to,FourInARowScreen screen,Coin newCoin) {
    super();
    this.screen= screen;
    this.newCoin= newCoin;
    this.i= from;
    this.to= to;
  }


  /**
   * Repaint method to simulate coin fall. If coin crossed given distance, then it stops falling
   *
   */

  public void run() {
    if(i<to) {
      screen.repaint(newCoin.getInitialPozitionXForFall(), i, newCoin);
      i += 20;
    }
  }
}

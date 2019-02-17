package org.puzzlebattle.client.games.fourinarow;

import java.util.TimerTask;

public class CoinFall extends TimerTask {

  private FourInARowScreen screen;
  private Coin newCoin;
  private double i;
  private double to;

  public CoinFall(double from, double to,FourInARowScreen screen,Coin newCoin) {
    super();
    this.screen= screen;
    this.newCoin= newCoin;
    this.i= from;
    this.to= to;
  }

  public void run()
  {
    if(i<to) {
      screen.repaint(newCoin.getInitialPozitionXForFall(), i, newCoin);
      i += 20;
    }
  }
}

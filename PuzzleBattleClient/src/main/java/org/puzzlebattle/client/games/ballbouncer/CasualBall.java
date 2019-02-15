package org.puzzlebattle.client.games.ballbouncer;

import javafx.scene.shape.Ellipse;

public class CasualBall extends Ellipse {

  private double x;
  private double y;
  private double radius1;
  private double radius2;

  public CasualBall(double x, double y, double radius1, double radius2)
  {
      super(x,y,radius1,radius2);
      this.x=x;
      this.y=y;
      this.radius1=radius1;
      this.radius2=radius2;
  }
}

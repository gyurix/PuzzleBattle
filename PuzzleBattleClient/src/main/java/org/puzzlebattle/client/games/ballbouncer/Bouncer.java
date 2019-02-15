package org.puzzlebattle.client.games.ballbouncer;

import com.sun.javafx.geom.RoundRectangle2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
//import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.games.PaneRenderable;


//@AllArgsConstructor
@Getter
public class Bouncer extends Rectangle implements PaneRenderable {
  private Point2D center, size;
  private Color color;


  private Point2D getVelocity(Point2D ball) {
    return null;
  }


  public Bouncer(Point2D center, Point2D size,Color color, double angle1, double angle2)
  {
    super(center.getX(),center.getY(),size.getX(),size.getY());
    super.setArcHeight(angle2);
    super.setArcWidth(angle1);
    this.color=color;
    this.center = center;
    this.size= size;
  }

  public void addRectangleOnCanvas(Pane canvas,double X, double Y) {
    canvas.getChildren().add(this);
    this.setFill(color);
    this.setStroke(color);
    this.relocate(X,Y);
  }

  public void render(Pane canvas) {

     // canvas.relocate(center.getX(), center.getY());
  }
}

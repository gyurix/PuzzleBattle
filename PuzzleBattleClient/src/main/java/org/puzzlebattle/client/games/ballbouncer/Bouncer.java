package org.puzzlebattle.client.games.ballbouncer;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.games.PaneRenderable;

@AllArgsConstructor
@Getter
public class Bouncer implements PaneRenderable {
  private Point2D center, size;
  private Color color;

  private Point2D getVelocity(Point2D ball) {
    return null;
  }


  public void render(Pane canvas) {

  }
}

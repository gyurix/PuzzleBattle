package org.puzzlebattle.client.games.ballbouncer;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import lombok.Getter;


@Getter
public class Ball extends BallBouncerEntity {
  private double acceleration = 0.01;
  private Point2D loc;
  private double radius = 100;
  private Point2D velocity;

  private CasualBall ball;

  private double oldPointX;
  private double oldPointY;

  public Ball(BallBouncerGame game) {
    super(game);

    loc = game.getMapSize().multiply(0.5);

    //saves initial X and Y coordinates after obtaining map size
    oldPointX = loc.getX();
    oldPointY= loc.getY();
    ball = new CasualBall(oldPointX,oldPointY,50,50);
    velocity = new Point2D(-3, 5);
  }

  public void addBallOnCanvas(Pane canvas){
    canvas.getChildren().add(ball);
  }


  public void render(Pane canvas) {

      //System.out.println("clear: "+loc.getX()+"  "+loc.getY());
      oldPointX= loc.getX();
      oldPointY= loc.getY();
      ball.relocate(oldPointX, oldPointY);

      tick();
  }


  public void tick() {
    loc = loc.add(velocity);
    velocity = velocity.multiply(1 - acceleration);
  }

  /*
  public void render(Canvas canvas) {

    GraphicsContext gc = canvas.getGraphicsContext2D();

    // canvas.setStyle("-fx-background-color: white");

    //clear shape
    gc.setFill(Color.WHITE);
    gc.setStroke(Color.WHITE);
    gc.fillOval(oldPointX,oldPointY,radius,radius);

    System.out.println("clear: "+loc.getX()+"  "+loc.getY());

    canvas.setStyle("-fx-background-color: redd");
    Circle circle = new Circle(50,Color.BLUE);
    circle.relocate(20, 20);
    canvas.add(circle);


    //update of shape
    gc.setFill(Color.BLUE);
    gc.setStroke(Color.BLACK);
    oldPointX= loc.getX();
    oldPointY= loc.getY();
    gc.fillOval(oldPointX,oldPointY,radius,radius);

    System.out.println("update: "+loc.getX()+"  "+loc.getY());
    tick();
  }
*/
}

package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import org.puzzlebattle.client.games.Game;

@Getter
public class BouncerGame extends Game {
  private BouncerBall ball;
  private boolean left1, left2;
  private Point2D mapSize = new Point2D(400, 400);
  private boolean right1, right2;
  private BouncerGameSettings settings;
  private BouncerPlayer you, enemy;

  //simple bouncer
  public BouncerGame(Object serverConnection, BouncerGameSettings settings) {
    super(serverConnection);
    this.settings = settings;
    createBall();
    createEnemy();
    createYou();
  }

  private void createBall() {
    ball = new BouncerBall(this, 20);
  }

  private void createEnemy() {
    enemy = new BouncerPlayer(this,
            new Bouncer(this, mapSize.getX() / 2 - 50, mapSize.getY() / 32 - 7.5, 100, 15, settings.getEnemy().getColor())
    );
  }

  private void createYou() {
    you = new BouncerPlayer(this,
            new Bouncer(this, mapSize.getX() / 2 - 50, (mapSize.getY() - mapSize.getY() / 32) - 7.5, 100, 15, settings.getYou().getColor())
    );
  }

  public boolean isEnemyFailed() {
    return false;
  }

  public boolean isYouFailed() {
    return false;
  }

  public void move(boolean left, boolean right, BouncerPlayer player, double intensity) {
    if (left && right)
      return;
    Bouncer bouncer = player.getBouncer();
    double x = bouncer.getX();
    if (left)
      bouncer.setX(Math.max(0, x - intensity));
    else if (right)
      bouncer.setX(Math.min(mapSize.getX() - bouncer.getWidth(), x + intensity));
  }

  public void onKeyEvent(KeyCode key, boolean pressed) {
    if (key == settings.getYou().getLeft())
      left1 = pressed;
    else if (key == settings.getYou().getRight())
      right1 = pressed;
    if (getServerConnection() == null) {
      if (key == settings.getEnemy().getLeft())
        left2 = pressed;
      else if (key == settings.getEnemy().getRight())
        right2 = pressed;
    }
  }

  public void tick() {
    move(left1, right1, you, settings.getYou().getMovementIntensity());
    if (getServerConnection() == null)
      move(left2, right2, enemy, settings.getEnemy().getMovementIntensity());
    ball.tick();
  }
}
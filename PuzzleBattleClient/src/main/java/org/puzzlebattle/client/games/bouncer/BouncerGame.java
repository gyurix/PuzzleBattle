package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import org.puzzlebattle.client.games.Game;

import java.util.Random;

@Getter
public class BouncerGame extends Game {
  private Point2D mapSize = new Point2D(720, 720);
  private BouncerBall ball;
  private boolean left1, left2;
  private Random random = new Random();
  private boolean right1, right2;
  private BouncerGameSettings settings;
  private BouncerPlayer you, enemy;


  public BouncerGame(Object serverConnection, BouncerGameSettings settings) {
    super(serverConnection);
    this.settings = settings;
    createBall();
    createEnemy();
    createYou();
  }

  private void createBall() {
    ball = new BouncerBall(this, 20);
    resetBall();
  }

  private void createEnemy() {
    enemy = new BouncerPlayer(this,
            new Bouncer(this, mapSize.getX() / 2 - 50, mapSize.getY() / 32 - 7.5, 100, 15, settings.getEnemy().getColor()),
            settings.getEnemy().getGoalColor()
    );
  }

  private void createYou() {
    you = new BouncerPlayer(this,
            new Bouncer(this, mapSize.getX() / 2 - 50, (mapSize.getY() - mapSize.getY() / 32) - 7.5, 100, 15, settings.getYou().getColor()),
            settings.getYou().getGoalColor()
    );
  }

  public boolean isEnemyFailed() {
    return ball.getCenterY() < mapSize.getY() / 32;
  }

  public boolean isYouFailed() {
    return ball.getCenterY() > mapSize.getY() - mapSize.getY() / 32;
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

  public void resetBall() {
    ball.setCenterX(mapSize.getX() / 2);
    ball.setCenterY(mapSize.getY() / 2);
    ball.setRadius(10 + Math.random() * 20);
    ball.setVelocity(new Point2D((random.nextBoolean() ? -1 : 1) * (2 + random.nextDouble() * 2),
            (random.nextBoolean() ? -1 : 1) * (1 + random.nextDouble() * 2)));
  }

  public void tick() {
    move(left1, right1, you, settings.getYou().getMovementIntensity());
    if (getServerConnection() == null)
      move(left2, right2, enemy, settings.getEnemy().getMovementIntensity());
    ball.tick();
    Point2D vel = enemy.getBouncer().getAppliedVelocity(ball, 1);
    if (vel == null)
      vel = you.getBouncer().getAppliedVelocity(ball, -1);
    if (vel == null) {
      if (isEnemyFailed())
        you.goal();
      else if (isYouFailed())
        enemy.goal();
      return;
    }
    ball.setVelocity(vel);
    ball.tick();
  }
}
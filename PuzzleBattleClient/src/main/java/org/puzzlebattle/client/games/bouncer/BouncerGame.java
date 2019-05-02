package org.puzzlebattle.client.games.bouncer;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import org.puzzlebattle.client.games.Game;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutUpdateGame;
import org.puzzlebattle.core.entity.GameType;

import java.util.Random;


/**
 * Ball Bouncer game class. Events, ball and players are controlled here.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
@Getter
public class BouncerGame extends Game {
  private BouncerBall ball;
  private boolean left1, left2;
  private Point2D mapSize = new Point2D(720, 720);
  private Random random = new Random();
  private boolean right1, right2;
  private BouncerGameSettings settings;
  private BouncerPlayer you, enemy;

  /**
   * Constructor for objects of class SkladPonuka
   */
  public BouncerGame(BouncerGameSettings settings, Client client) {
    super(client);
    this.settings = settings;
    createBall();
    createEnemy();
    createYou();
  }


  /**
   * It creates a ball.
   */
  private void createBall() {
    ball = new BouncerBall(this, 20);
    resetBall();
  }


  /**
   * Enemy is created here.
   */
  private void createEnemy() {
    enemy = new BouncerPlayer(this,
            new Bouncer(this, mapSize.getX() / 2 - 50, mapSize.getY() / 32 - 7.5, 100, 15, settings.getEnemy().getColor()),
            settings.getEnemy().getGoalColor()
    );
  }


  /**
   * It creates you.
   */
  private void createYou() {
    you = new BouncerPlayer(this,
            new Bouncer(this, mapSize.getX() / 2 - 50, (mapSize.getY() - mapSize.getY() / 32) - 7.5, 100, 15, settings.getYou().getColor()),
            settings.getYou().getGoalColor()
    );
  }

  public GameType getType() {
    return GameType.BOUNCER;
  }

  /**
   * Resets a ball into its standard position
   */
  public void resetBall() {
    ball.setCenterX(mapSize.getX() / 2);
    ball.setCenterY(mapSize.getY() / 2);
    ball.setRadius(10 + Math.random() * 20);
    ball.setVelocity(new Point2D((random.nextBoolean() ? -1 : 1) * (2 + random.nextDouble() * 2),
            (random.nextBoolean() ? -1 : 1) * (1 + random.nextDouble() * 2)));
  }

  /**
   * Key event, which is triggered by special keys. These keys can manipulate with bouncers.
   *
   * @param key     used key
   * @param pressed if button is pressed
   */
  public void onKeyEvent(KeyCode key, boolean pressed) {
    if (key == settings.getYou().getLeft())
      left1 = pressed;
    else if (key == settings.getYou().getRight())
      right1 = pressed;
    if (key == settings.getEnemy().getLeft())
      left2 = pressed;
    else if (key == settings.getEnemy().getRight())
      right2 = pressed;
    client.sendPacket(new ServerOutUpdateGame(new int[]{(right1 ? 1 : 0) - (left1 ? 1 : 0)}));
  }

  @Override
  public void updateData(int[] data) {
    if (data[0] == 1)
      you.goal();
    if (data[1] == 1)
      enemy.goal();
    you.getBouncer().setX(data[2]);
    you.getBouncer().setY(data[3]);
    enemy.getBouncer().setX(data[4]);
    enemy.getBouncer().setY(data[5]);
    ball.setCenterX(data[6]);
    ball.setCenterY(data[7]);
    ball.setRadius(data[8]);
  }
}
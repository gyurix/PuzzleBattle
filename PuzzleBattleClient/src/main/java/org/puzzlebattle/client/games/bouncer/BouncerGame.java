package org.puzzlebattle.client.games.bouncer;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.puzzlebattle.client.games.Game;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.out.ServerOutUpdateGame;
import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.gamesettings.BallBouncerSettings;


/**
 * Ball Bouncer game class. Events, ball and players are controlled here.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
@Getter
public class BouncerGame extends Game {
  private BouncerBall ball;
  private BouncerGameClientSettings clientSettings;
  private boolean left, right;
  private BallBouncerSettings settings;
  private BouncerPlayer you, enemy;

  /**
   * Constructor for objects of class SkladPonuka
   */
  public BouncerGame(BouncerGameClientSettings clientSettings, BallBouncerSettings settings, Client client) {
    super(client);
    this.settings = settings;
    this.clientSettings = clientSettings;
    createBall();
    createEnemy();
    createYou();
  }


  /**
   * It creates a ball.
   */
  private void createBall() {
    ball = new BouncerBall(this, 20);
  }


  /**
   * Enemy is created here.
   */
  private void createEnemy() {
    enemy = new BouncerPlayer(this,
            new Bouncer(this,
                    (settings.getMapMaxx() - settings.getBouncerWidth()) / 2,
                    settings.getMapMaxy() / 32.0 - 7.5,
                    settings.getBouncerWidth(),
                    settings.getBouncerHeight(),
                    Color.valueOf(clientSettings.getEnemy().getColor())),
            Color.valueOf(clientSettings.getEnemy().getGoalColor())
    );
  }


  /**
   * It creates you.
   */
  private void createYou() {
    you = new BouncerPlayer(this,
            new Bouncer(this,
                    (settings.getMapMaxx() - settings.getBouncerWidth()) / 2,
                    (settings.getMapMaxy() - settings.getMapMaxy() / 32.0) - 7.5,
                    settings.getBouncerWidth(),
                    settings.getBouncerHeight(),
                    Color.valueOf(clientSettings.getYou().getColor())
            ), Color.valueOf(clientSettings.getYou().getGoalColor()));
  }

  public GameType getType() {
    return GameType.BOUNCER;
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

  /**
   * Key event, which is triggered by special keys. These keys can manipulate with bouncers.
   *
   * @param key     used key
   * @param pressed if button is pressed
   */
  public void onKeyEvent(KeyCode key, boolean pressed) {
    if (key == clientSettings.getYou().getLeft())
      left = pressed;
    else if (key == clientSettings.getYou().getRight())
      right = pressed;
    client.sendPacket(new ServerOutUpdateGame(new int[]{(right ? 1 : 0) - (left ? 1 : 0)}));
  }
}
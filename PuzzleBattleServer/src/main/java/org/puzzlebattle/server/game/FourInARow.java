package org.puzzlebattle.server.game;

import org.puzzlebattle.core.entity.GameType;
import org.puzzlebattle.core.gamesettings.FourInARowSettings;
import org.puzzlebattle.core.gamesettings.MainSettings;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.db.entity.GameSettings;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.manager.ConfigManager;
import org.puzzlebattle.server.protocol.packets.out.ClientOutUpdateGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourInARow extends Game {
  private int dropped;
  private List<List<Boolean>> map = new ArrayList<>();
  private Client nextStep;
  private FourInARowSettings settings;

  public FourInARow(Client p1, Client p2, GameSettings gameSettings) {
    super(p1, p2, gameSettings);
  }

  public boolean canDrop(int col) {
    return map.get(col).size() < settings.getMaxy();
  }

  private boolean checkWin(Client client, int column) {
    Boolean checkable = client == p2;
    int row = map.get(column).size() - 1;

    int pointsObtainedInARow = 1;
    int pointsObtainedInAColumn = 1;
    int toLeft, toRight, toDown;

    toLeft = column - 1;
    toRight = column + 1;
    toDown = row - 1;


    while (toLeft >= 0 && checkable.equals(getCoin(toLeft, row))) {
      pointsObtainedInARow += 1;
      toLeft -= 1;
    }

    while (toRight <= settings.getMaxx() && toRight >= 0 && checkable.equals(getCoin(toRight, row))) {
      pointsObtainedInARow += 1;
      toRight += 1;
    }

    while (toDown >= 0 && checkable.equals(getCoin(column, toDown))) {
      pointsObtainedInAColumn += 1;
      toDown -= 1;
    }

    if (pointsObtainedInARow >= settings.getNumberInRowCount()) {
      return true;
    } else return pointsObtainedInAColumn >= settings.getNumberInRowCount();
  }

  public void drop(Client client, int col) {
    map.get(col).add(client == p2);
    ++dropped;
  }

  public Boolean getCoin(int x, int y) {
    if (x >= map.size())
      return null;
    List<Boolean> list = map.get(x);
    if (list.size() <= y)
      return null;
    return list.get(y);
  }

  @Override
  public void loadSettings() {
    settings = ConfigManager.getInstance().getConfig().getGameProfiles().getFourInARow()
            .get(gameSettings.getProfileName());
  }

  @Override
  public MainSettings getSettings() {
    return settings;
  }

  @Override
  public GameType getType() {
    return GameType.FOUR_IN_A_ROW;
  }

  @Override
  protected void start() {
    for (int x = 0; x < settings.getMaxx(); ++x) {
      List<Boolean> list = new ArrayList<>();
      map.add(list);
    }
    nextStep = p1;
  }

  @Override
  public void update(Client client, int[] data) {
    Logging.logInfo("Update FourInARow", "client", client, "data", Arrays.toString(data));
    if (nextStep != client) {
      Logging.logWarning("Unauthorized player tried to move", "moved", client, "expected", nextStep);
      return;
    }
    int col = data[0] - 1;
    if (!canDrop(col)) {
      Logging.logWarning("Can not drop to column, because it's full already", "column", col);
      return;
    }
    drop(client, col);
    nextStep = client == p1 ? p2 : p1;
    nextStep.getHandler().sendPacket(new ClientOutUpdateGame(new int[]{data[0]}));
    if (checkWin(client, col))
      lose(nextStep);
    Logging.logInfo(""+settings.getMaxx() * settings.getMaxy());
    if (dropped == settings.getMaxx() * settings.getMaxy())
      draw();
  }
}

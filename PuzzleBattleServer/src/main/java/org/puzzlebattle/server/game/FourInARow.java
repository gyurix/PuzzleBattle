package org.puzzlebattle.server.game;

        import org.puzzlebattle.core.entity.GameType;
        import org.puzzlebattle.server.db.entity.GameSettings;
        import org.puzzlebattle.server.entity.Client;

        import java.util.ArrayList;
        import java.util.List;

public class FourInARow extends Game {
  private static final int MAXX = 7;
  private static final int MAXY = 6;
  private static final int NUMBER_IN_ROW_COUNT = 4;
  private int dropped;
  private List<List<Boolean>> map = new ArrayList<>();
  private Client nextStep;

  public FourInARow(Client p1, Client p2, GameSettings settings) {
    super(p1, p2, settings);
  }

  public boolean canDrop(int col) {
    return map.get(col).size() < MAXY;
  }

  private boolean checkWin(Client client, int column) {
    boolean checkable = client == p2;
    int row = map.get(column).size() - 1;

    int pointsObtainedInARow = 0;
    int pointsObtainedInAColumn = 0;
    int toLeft, toRight, toDown;

    toLeft = column - 1;
    toRight = column + 1;
    toDown = row - 1;


    while (toLeft >= 0 && getCoin(toLeft, row) == checkable) {
      pointsObtainedInARow += 1;
      toLeft -= 1;
    }

    while (toRight < MAXX && toRight >= 0 && getCoin(toRight, row) == checkable) {
      pointsObtainedInARow += 1;
      toRight += 1;
    }

    while (toDown >= 0 && getCoin(column, toDown) == checkable) {
      pointsObtainedInAColumn += 1;
      toDown -= toDown;
    }

    if (pointsObtainedInARow >= NUMBER_IN_ROW_COUNT) {
      return true;
    } else return pointsObtainedInAColumn >= NUMBER_IN_ROW_COUNT;
  }

  public void drop(Client client, int col) {
    map.get(col).add(client == p2);
    ++dropped;
  }

  public Boolean getCoin(int x, int y) {
    List<Boolean> list = map.get(x);
    if (list.size() <= y)
      return null;
    return list.get(y);
  }

  @Override
  public GameType getType() {
    return GameType.FOUR_IN_A_ROW;
  }

  @Override
  protected void start() {
    for (int x = 0; x < MAXX; ++x) {
      List<Boolean> list = new ArrayList<>();
      map.add(list);
    }
    nextStep = p1;
  }

  @Override
  public void update(Client client, int[] data) {
    if (nextStep != client)
      return;
    int col = data[0];
    if (!canDrop(col))
      return;
    drop(client, col);
    nextStep = client == p1 ? p2 : p1;
    if (checkWin(client, col))
      lose(nextStep);
    if (dropped == MAXX * MAXY)
      draw();
  }
}

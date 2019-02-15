package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class DottedPanel extends Pane {

  private int rows = 6;
  private int columns = 7;
  private double thicknessOfRows = 20;
  private double thicknessOfColumns = 25;
  private Rectangle obdlznik

  public DottedPanel(double width, double height) {
      this(0,50,width, height);
  }

  public DottedPanel(double X, double Y, double  width, double height) {
      super();
      applySettingsOnPane(this);
      this.setFill(Color.BLUE);
      this.setStroke(Color.BLANCHEDALMOND);

      createGrids(this,rows,columns,width, height, Color.YELLOW, Color.GREEN);
  }


  private void createGrids(Pane panel, double X, double Y, double width, double height, Color colorOfRows,Color colorOfColumns) {


  }

  private void create
}

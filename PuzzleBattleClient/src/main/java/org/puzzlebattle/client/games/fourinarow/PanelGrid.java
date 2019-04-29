package org.puzzlebattle.client.games.fourinarow;


import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;


/**
 * Grid panel with rows and column. There will be coins, after they fall after specific order of players.
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class PanelGrid extends Pane {
  private final int fontSize = 20;
  private int columns = 7;
  private int distanceOfColumns;
  private Font font;
  private List<Rectangle> grid = new ArrayList<Rectangle>();
  private double initialSpace = 50;
  private Label label;
  private Rectangle rect;
  private int rows = 6;
  private double spaceFromTop = 10;
  private double thicknessOfColumns = 15;
  private double thicknessOfRows = 10;


  /**
   * Grid panel will be created 50 from top and 0, strictly on the left with specified width and height
   */
  public PanelGrid(double width, double height, FourInARowScreen fourInARowScreen) {
    this(0, 50, width, height, fourInARowScreen);
  }


  /**
   * Specific Grid panel, where additionally position X and Y where panel should be situated can be set
   */
  public PanelGrid(double X, double Y, double width, double height, FourInARowScreen fourInARowScreen) {
    createGrids(this, rows, columns, width, height, Color.YELLOW, Color.GREEN);
    createLabels(this, initialSpace / 2, spaceFromTop, width, height, columns, fourInARowScreen);
  }

  /**
   * This method apply certain settings for a label. Font with specified font size will be set.
   *
   * @param label a sample parameter for a method
   */
  private void applySettingsOnLabel(Label label) {

    font = new Font("TimesNewRoman", fontSize);
    label.setFont(font);
  }

  /**
   * Count column space from width of whole grid. From the whole space is thickness of rectangles given away,
   * and then divided by number of columns.
   *
   * @param numberOfColumns number of columns in grid
   * @param width           width of whole grid
   * @return the sum of x and y
   */
  private int countColumnSpace(double numberOfColumns, double width) {
    double sizeOfRowGrids = numberOfColumns * thicknessOfRows;
    return (int) ((width - sizeOfRowGrids) / numberOfColumns);
  }

  /**
   * Count row space from width of whole grid. From the whole space is thickness of rectangles given away,
   * and then divided by number of rows.
   *
   * @param numberOfRows number of rows in grid
   * @param width        width of whole grid
   * @return row space
   */
  private int countRowSpace(double numberOfRows, double width) {
    double sizeOfRowGrids = numberOfRows * thicknessOfRows;
    return (int) ((width - sizeOfRowGrids) / numberOfRows);
  }

  /**
   * Vertical and horizontal lines as rectangles are created here. Grid consists of this rectangles
   *
   * @param panel          panel where grid will be added
   * @param X              X coordinate position of grid
   * @param Y              Y coordinate position of grid
   * @param width          width of the whole grid
   * @param height         height of the whole grid
   * @param colorOfRows    color for rows, rectangles which run horizontally
   * @param colorOfColumns color for columns, rectangles which run vertically
   */
  private void createGrids(Pane panel, double X, double Y, double width, double height, Color colorOfRows, Color colorOfColumns) {
    int rowSpace = countRowSpace(X, width);
    int columnSpace = countColumnSpace(Y, width);

    for (int i = 0; i < rows + 1; i = i + 1) {
      grid.add(createRectangle(initialSpace, i * rowSpace + initialSpace + columnSpace, height, thicknessOfRows, colorOfRows));
      panel.getChildren().add(grid.get(i));
    }

    for (int i = 0; i < columns + 1; i = i + 1) {
      grid.add(createRectangle(i * rowSpace + initialSpace, initialSpace, thicknessOfColumns, height, colorOfColumns));
      panel.getChildren().add(grid.get(i + rows + 1));
    }
  }

  /**
   * Label with specific text will be created. Label is situated at given coordinates.
   *
   * @param positionX X coordinate, where label should be situated on the scene
   * @param positionY Y coordinate, where label should be situated on the scene
   * @param context   text which will be displayed on the label
   * @return created label
   */
  private Label createLabel(double positionX, double positionY, String context) {
    label = new Label(context);
    applySettingsOnLabel(label);
    label.relocate(positionX, positionY);
    return label;
  }

  /**
   * Method which creates labels, to mark columns.
   *
   * @param panel           panel where labels should be positioned
   * @param positionX       position X where grid starts, X coordinate
   * @param positionY       position Y where grid starts, Y coordinate
   * @param widthOfWindow   width of whole window
   * @param numberOfWindows number of windows in the row
   */
  private void createLabels(Pane panel, double positionX, double positionY, double widthOfWindow, double heightOfWindow, int numberOfWindows, FourInARowScreen fourInARowScreen) {
    this.distanceOfColumns = countRowSpace(numberOfWindows, widthOfWindow) - 1;

    for (int number = 1; number <= numberOfWindows; number = number + 1) {
      Label label = createLabel(positionX + number * distanceOfColumns + number * thicknessOfRows,
              positionY, Integer.toString(number));
      label.setMinHeight(heightOfWindow);
      label.setMinWidth(distanceOfColumns);
      label.setAlignment(Pos.TOP_CENTER);
      label.setOnMouseClicked(e -> handleLabel(e, fourInARowScreen));
      panel.getChildren().add(label);
    }
  }

  /**
   * Creates rectangle, which is a grid, one row or column in pane. Given parameters are applied.
   * Position, size and color is set.
   *
   * @param X      X coordinate where new rectangle should be located
   * @param Y      Y coordinate where new rectangle should be located
   * @param width  width of the rectangle, grid
   * @param height height of the rectangle, grid
   * @param color  color of rectangle, which represents grid
   * @return created specified rectangle located at given coordinates and filled in certain color
   */
  private Rectangle createRectangle(double X, double Y, double width, double height, Color color) {
    rect = new Rectangle(X, Y, width, height);
    rect.setFill(color);
    rect.setStroke(color);
    return rect;
  }

  /**
   * Method which calculates distance, where coin will take
   *
   * @param requestedColumn column where coin should fall
   * @return location of Y coordinate, where coin should fall
   */
  public double getColumnX(int requestedColumn) {
    return initialSpace / 2 + requestedColumn * (distanceOfColumns + thicknessOfRows) + 4;
  }

  /**
   * Method which returns distance between columns
   *
   * @return distance of column, how columns are far away
   */
  public double getDistanceOfColumns() {
    return distanceOfColumns;
  }

  /**
   * Method which returns thickness of rows. How rectangle which represents row is thick.
   *
   * @return thickness of rows
   */
  public double getThicknessOfRows() {
    return thicknessOfRows;
  }

  /**
   * Handling clicking on label in four in a row game
   *
   * @param event             - mouse event
   * @param fourInARowScreen  - screen of four in a row game
   */
  private void handleLabel(MouseEvent event, FourInARowScreen fourInARowScreen) {
    Label label = (Label) event.getSource();
    String labelText = label.getText();
    fourInARowScreen.onKeyEvent(FourInARowGameSettings.getDigit(Integer.parseInt(labelText)));
  }
}

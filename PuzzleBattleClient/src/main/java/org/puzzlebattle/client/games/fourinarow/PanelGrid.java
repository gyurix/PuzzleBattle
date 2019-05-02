package org.puzzlebattle.client.games.fourinarow;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import lombok.Getter;
import org.puzzlebattle.core.gamesettings.FourInARowSettings;

import java.util.List;


/**
 * Grid panel with rows and column. There will be coins, after they fall after specific order of players.
 *
 * @author (Jakub Perdek)
 * @version (1.0)
 */
public class PanelGrid extends Pane {
  private FourInARowClientSettings clientSettings;
  @Getter
  private double colSpace, rowSpace;
  private Font font;
  private Label label;
  private FourInARowScreen screen;
  private FourInARowSettings settings;
  private double width, height;


  /**
   * Specific Grid panel, where additionally position X and Y where panel should be situated can be set
   */
  public PanelGrid(double width, double height, FourInARowScreen screen) {
    this.settings = screen.getGame().getSettings();
    this.clientSettings = screen.getGame().getClientSettings();
    this.width = width;
    this.height = height;
    this.screen = screen;
    createGrid();
    createLabels();
  }

  /**
   * This method apply certain settings for a label. Font with specified font size will be set.
   *
   * @param label a sample parameter for a method
   */
  private void applySettingsOnLabel(Label label) {
    font = new Font("TimesNewRoman", clientSettings.getFontSize());
    label.setFont(font);
  }

  /**
   * Render the gaming map grid
   */
  private void createGrid() {
    rowSpace = (height - clientSettings.getSpaceFromTop()) / settings.getMaxy();
    colSpace = (width - clientSettings.getInitialSpace() * 2) / settings.getMaxx();
    List<Node> nodes = getChildren();

    double rowWidth = width - 2 * clientSettings.getInitialSpace();
    for (int row = 1; row <= settings.getMaxy(); ++row)
      nodes.add(createHorizontalLine(clientSettings.getSpaceFromTop() + rowSpace * row, rowWidth));

    double colHeight = height - clientSettings.getSpaceFromTop();
    for (int col = 0; col <= settings.getMaxx(); ++col) {
      nodes.add(createVerticalLine(clientSettings.getInitialSpace() + colSpace * col, colHeight));
    }
  }

  /**
   * Creates a new horizontal line to the given Y coordinate
   *
   * @param y     - The y coordinate of the line
   * @param width - The width of the line
   * @return The created line
   */
  private Line createHorizontalLine(double y, double width) {
    Line line = new Line(clientSettings.getInitialSpace(), y, clientSettings.getInitialSpace() + width, y);
    line.setStrokeWidth(clientSettings.getThicknessOfRows());
    line.setStroke(Paint.valueOf(clientSettings.getRowColor()));
    return line;
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
   */
  private void createLabels() {
    int positionY = (int) (clientSettings.getSpaceFromTop() * 0.75 - clientSettings.getFontSize());
    List<Node> children = getChildren();
    for (int col = 1; col <= settings.getMaxx(); col = col + 1) {
      Label label = createLabel(clientSettings.getInitialSpace() + colSpace * (col - 1), positionY, Integer.toString(col));
      label.setMinSize(colSpace, height);
      label.setAlignment(Pos.TOP_CENTER);
      label.setOnMouseClicked(this::handleLabel);
      children.add(label);
    }
  }

  /**
   * Creates a new vertical line to the given X coordinate
   *
   * @param x      - The x coordinate of the line
   * @param height - The height of the line
   * @return The created line
   */
  private Line createVerticalLine(double x, double height) {
    Line line = new Line(x, clientSettings.getSpaceFromTop(), x, clientSettings.getSpaceFromTop() + height);
    line.setStrokeWidth(clientSettings.getThicknessOfRows());
    line.setStroke(Paint.valueOf(clientSettings.getColColor()));
    return line;
  }

  /**
   * Method which calculates the center x location to which the coin should land
   *
   * @param col - The column where coin should fall
   * @return The location of X coordinate, where coin should fall
   */
  public double getColumnX(int col) {
    return clientSettings.getInitialSpace() + (col - 0.5) * colSpace;
  }

  /**
   * Handling clicking on label in four in a row game
   *
   * @param event - mouse event
   */
  private void handleLabel(MouseEvent event) {
    Label label = (Label) event.getSource();
    String labelText = label.getText();
    screen.onKeyEvent(FourInARowClientSettings.getDigit(Integer.parseInt(labelText)));
  }
}

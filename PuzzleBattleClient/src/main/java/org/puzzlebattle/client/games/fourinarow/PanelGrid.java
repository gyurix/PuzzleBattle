package org.puzzlebattle.client.games.fourinarow;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class PanelGrid extends Pane {

  private int rows = 6;
  private int columns = 7;
  private double thicknessOfRows = 10;
  private double thicknessOfColumns = 15;
  private double rowSpace;
  private double columnSpace;
  private Rectangle obdlznik;
  private Rectangle rect;
  private Label label;
  private double initialSpace= 50;
  private double spaceFromTop = 10;
  private Font font;
  private final int fontSize = 20;



  public PanelGrid(double width, double height) {
      this(0,50,width, height);
  }


  public PanelGrid(double X, double Y, double  width, double height) {
      super();

      createGrids(this,rows,columns,width, height, Color.YELLOW, Color.GREEN);
      createLabels(this,initialSpace/2,spaceFromTop,width,columns);
  }


  private void createGrids(Pane panel, double X, double Y, double width, double height, Color colorOfRows,Color colorOfColumns) {

       int rowSpace = countRowSpace(X,width);
       int columnSpace = countColumnSpace(Y,width);

       for(int i =0;i<rows+1;i=i+1){
         panel.getChildren().add(createRectangle(initialSpace,i*rowSpace+initialSpace + columnSpace, height,thicknessOfRows,colorOfRows));
         System.out.println("0 "+i*rowSpace+"   "+thicknessOfRows+" "+height);
       }

       for(int i=0;i<columns+1;i=i+1) {
         panel.getChildren().add(createRectangle(i*rowSpace+initialSpace,initialSpace,thicknessOfColumns,height,colorOfColumns));
       }

  }

  private int countRowSpace(double numberOfRows,double width) {

    double sizeOfRowGrids= numberOfRows*thicknessOfRows;
    return (int) ((width-sizeOfRowGrids)/numberOfRows);
  }

  private int countColumnSpace(double numberOfColumns,double width) {

    double sizeOfRowGrids= numberOfColumns*thicknessOfRows;
    return (int) ((width-sizeOfRowGrids)/numberOfColumns);
  }

  private Rectangle createRectangle(double X,double Y,double width, double height,Color color)
  {
     rect= new Rectangle(X,Y,width, height);
     rect.setFill(color);
     rect.setStroke(color);
     return rect;
  }


  private void createLabels(Pane panel,double positionX, double positionY,double widthOfWindow,int numberOfWindows) {

    int distance = countRowSpace(numberOfWindows,widthOfWindow);

    for (int number = 1; number <= numberOfWindows; number = number + 1) {
      panel.getChildren().add(createLabel(positionX+number*distance+ number*thicknessOfRows, positionY, Integer.toString(number)));
    }
  }

  private Label createLabel(double positionX, double positionY,String context) {

    label = new Label(context);
    applySettingsOnLabel(label);
    label.relocate(positionX,positionY);
    return label;
  }

  private void applySettingsOnLabel(Label label)
  {
    font= new Font("TimesNewRoman",fontSize);
    label.setFont(font);
    label.setBackground(new Background(new BackgroundFill(Paint.valueOf("GAINSBORO"),null,null)));
    label.setStyle("-fx-control-inner-background: lightGray");

  }

}

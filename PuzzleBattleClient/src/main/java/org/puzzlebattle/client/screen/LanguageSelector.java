package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.core.utils.LangFile;


public class LanguageSelector extends HBox {
  private ChoiceBox<String> languageChooser;
  private LangFile english;
  private Stage stageOfLoginScreen;
  private Label languageTitle;
  private String[] languages =  {"en","sk"};

  public LanguageSelector(Stage stageOfLoginScreen){
    this(stageOfLoginScreen,150,25);
  }

  public LanguageSelector(Stage stageOfLoginScreen, double width, double height) {
    super(10);
    this.stageOfLoginScreen = stageOfLoginScreen;
    this.setPadding(new Insets(10,5,5,10));
    this.setPrefSize(width,height);
    prepareComponentsForLanguageSelector(width, height);
    this.getChildren().addAll(languageTitle,languageChooser);
  }

  private void prepareComponentsForLanguageSelector(double width, double height){
    prepareDefaultLanguage();
    prepareChoiceBox();
    prepareLanguageTitle(width, height);
  }

  private void prepareLanguageTitle(double width, double height){
    languageTitle = new Label("Language");
    languageTitle.setMinSize(width/1.5,height);
    languageTitle.setFont(Font.font("Courier",height-5));
  }

  private void prepareDefaultLanguage(){
    english = new LangFile("en");
    LangFile.lang = new LangFile(english, "en");
  }

  private void prepareChoiceBox() {
    languageChooser = new ChoiceBox<String>();
    addComponentsToChoiceBox(languageChooser);
  }

  private void addComponentsToChoiceBox(ChoiceBox languageChooser) {
    languageChooser.getItems().addAll(languages);
    languageChooser.getSelectionModel().selectFirst();
    languageChooser.setOnAction(e->applyLanguageChoice());
  }

  private void applyLanguageChoice() {
    LangFile.lang = new LangFile(english, languageChooser.getSelectionModel().getSelectedItem());
    stageOfLoginScreen.close();
    new LoginScreen(stageOfLoginScreen,this).show();
  }
}

package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.puzzlebattle.core.utils.LangFile;

/**
 * Language selector for selecting requested language
 *
 * @author Jakub Perdek
 * @version 1.0
 */
public class LanguageSelector extends HBox {
  private ChoiceBox<String> languageChooser;
  private LangFile english;
  private Stage stageOfLoginScreen;
  private Label languageTitle;
  private String[] languages =  {"en","sk"};

  /**
   * Creation of default language selector instance with default width and height
   *
   * @param stageOfLoginScreen - stage where login screen will be reloaded after selection of requested language
   */
  public LanguageSelector(Stage stageOfLoginScreen){
    this(stageOfLoginScreen,150,25);
  }

  /**
   * Creation of language selector with specified width and height
   *
   * @param stageOfLoginScreen - stage where login screen will be reloaded after selection of requested language
   * @param width - width of language selector - prefer
   * @param height - height of language selector - prefer
   */
  public LanguageSelector(Stage stageOfLoginScreen, double width, double height) {
    super(10);
    this.stageOfLoginScreen = stageOfLoginScreen;
    this.setPadding(new Insets(10,5,5,10));
    this.setPrefSize(width,height);
    prepareComponentsForLanguageSelector(width, height);
    this.getChildren().addAll(languageTitle,languageChooser);
  }

  /**
   * Prepares components for language selector
   *
   * @param width - width of language layout
   * @param height - height of language layout
   */
  private void prepareComponentsForLanguageSelector(double width, double height){
    prepareDefaultLanguage();
    prepareChoiceBox();
    prepareLanguageTitle(width, height);
  }

  /**
   * Prepares language title label
   *
   * @param width - width of language layout
   * @param height - height of language layout
   */
  private void prepareLanguageTitle(double width, double height){
    languageTitle = new Label("Language");
    languageTitle.setMinSize(width/1.5,height);
    languageTitle.setFont(Font.font("Courier",height-5));
  }

  /**
   *  Creates instance of default language
   */
  private void prepareDefaultLanguage(){
    english = new LangFile("en");
    LangFile.lang = new LangFile(english, "en");
  }

  /**
   * Prepares choice box
   */
  private void prepareChoiceBox() {
    languageChooser = new ChoiceBox<String>();
    addComponentsToChoiceBox(languageChooser);
  }

  /**
   * Languages will be added to choice box
   *
   * @param languageChooser - instance of choice box for language selection
   */
  private void addComponentsToChoiceBox(ChoiceBox languageChooser) {
    languageChooser.getItems().addAll(languages);
    languageChooser.getSelectionModel().selectFirst();
    languageChooser.setOnAction(e->applyLanguageChoice());
  }

  /**
   * Action after changed state of choice box, language selector, is performed here
   */
  private void applyLanguageChoice() {
    LangFile.lang = new LangFile(english, languageChooser.getSelectionModel().getSelectedItem());
    stageOfLoginScreen.close();
    new LoginScreen(stageOfLoginScreen,this).show();
  }
}

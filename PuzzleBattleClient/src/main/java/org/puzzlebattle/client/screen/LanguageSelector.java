package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.config.ClientConfig;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.core.utils.LangFile;

import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

import static org.puzzlebattle.core.utils.LangFile.lang;

/**
 * Language selector for selecting requested language
 *
 * @author Jakub Perdek
 * @version 1.0
 */
public class LanguageSelector extends HBox {
  private LangFile english;
  private HashMap<String, LangFile> langFiles = new HashMap<>();
  private ChoiceBox<LanguageOption> languageChooser;
  private Label languageTitle;
  private HashMap<String, LanguageOption> optionMap = new HashMap<>();
  private TreeSet<LanguageOption> options = new TreeSet<>();
  private Stage stageOfLoginScreen;

  /**
   * Creation of default language selector instance with default width and height
   *
   * @param stageOfLoginScreen - stage where login screen will be reloaded after selection of requested language
   */
  public LanguageSelector(Stage stageOfLoginScreen) {
    this(stageOfLoginScreen, 150, 25);
  }

  /**
   * Creation of language selector with specified width and height
   *
   * @param stageOfLoginScreen - stage where login screen will be reloaded after selection of requested language
   * @param width              - width of language selector - prefer
   * @param height             - height of language selector - prefer
   */
  public LanguageSelector(Stage stageOfLoginScreen, double width, double height) {
    super(10);
    this.stageOfLoginScreen = stageOfLoginScreen;
    this.setPadding(new Insets(10, 5, 5, 10));
    this.setPrefSize(width, height);
    loadLanguages();
    prepareComponentsForLanguageSelector(width, height);
    this.getChildren().addAll(languageTitle, languageChooser);
  }

  /**
   * Languages will be added to choice box
   *
   * @param languageChooser - instance of choice box for language selection
   */
  private void addComponentsToChoiceBox(ChoiceBox<LanguageOption> languageChooser) {
    languageChooser.getItems().addAll(options);
    LanguageOption selected = optionMap.getOrDefault(ClientConfig.lang, optionMap.get("en"));
    ClientConfig.lang = selected.getCode();
    languageChooser.getSelectionModel().select(selected);
    languageChooser.setOnAction(e -> applyLanguageChoice());
  }

  /**
   * Action after changed state of choice box, language selector, is performed here
   */
  private void applyLanguageChoice() {
    lang = langFiles.get(languageChooser.getSelectionModel().getSelectedItem().getCode());
    stageOfLoginScreen.close();
    languageTitle.setText(lang.get("login.language"));
    new LoginScreen(stageOfLoginScreen, this).show();
  }

  private void loadLanguages() {
    IOUtils.saveResources("lang/en.json", "lang/de.json", "lang/sk.json");
    registerLanguage("en");
    for (String name : new File("lang").list((dir, name) -> name.endsWith(".json"))) {
      String langCode = name.replace(".json", "");
      if (!langCode.equals("en"))
        registerLanguage(langCode);
    }
  }

  /**
   * Prepares choice box
   */
  private void prepareChoiceBox() {
    languageChooser = new ChoiceBox<LanguageOption>();
    addComponentsToChoiceBox(languageChooser);
  }

  /**
   * Prepares components for language selector
   *
   * @param width  - width of language layout
   * @param height - height of language layout
   */
  private void prepareComponentsForLanguageSelector(double width, double height) {
    prepareDefaultLanguage();
    prepareChoiceBox();
    prepareLanguageTitle(width, height);
  }

  /**
   * Creates instance of default language
   */
  private void prepareDefaultLanguage() {
    english = new LangFile("en");
    lang = new LangFile(english, "en");
  }

  /**
   * Prepares language title label
   *
   * @param width  - width of language layout
   * @param height - height of language layout
   */
  private void prepareLanguageTitle(double width, double height) {
    languageTitle = new Label(lang.get("login.language"));
    languageTitle.setMinSize(width / 1.5, height);
    languageTitle.setFont(Font.font("Courier", height - 5));
  }

  private LangFile registerLanguage(String name) {
    LangFile lf = new LangFile(langFiles.get("en"), name);
    langFiles.put(name, lf);
    LanguageOption option = new LanguageOption(name, lf.get("login.languageTitle"));
    options.add(option);
    optionMap.put(name, option);
    return lf;
  }

  @AllArgsConstructor
  @Getter
  public class LanguageOption implements Comparable<LanguageOption> {
    private String code;
    private String name;

    @Override
    public int compareTo(LanguageOption o) {
      int nameComparationResult = name.compareTo(o.getName());
      return nameComparationResult == 0 ? code.compareTo(o.getCode()) : name.compareTo(o.getName());
    }

    @Override
    public String toString() {
      return getName();
    }
  }
}

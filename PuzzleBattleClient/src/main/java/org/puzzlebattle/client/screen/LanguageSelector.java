package org.puzzlebattle.client.screen;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.config.ClientConfig;
import org.puzzlebattle.client.config.ConfigManager;
import org.puzzlebattle.core.utils.IOUtils;
import org.puzzlebattle.core.utils.LangFile;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
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
  private LoginScreen loginScreen;
  private HashMap<String, LanguageOption> optionMap = new HashMap<>();
  private TreeSet<LanguageOption> options = new TreeSet<>();

  /**
   * Creation of default language selector instance with default width and height
   *
   * @param loginScreen - the open login screen
   */
  public LanguageSelector(LoginScreen loginScreen) {
    this(loginScreen, 150, 25);
  }

  /**
   * Creation of language selector with specified width and height
   *
   * @param loginScreen - the current login screen
   * @param width       - width of language selector - prefer
   * @param height      - height of language selector - prefer
   */
  public LanguageSelector(LoginScreen loginScreen, double width, double height) {
    super(10);
    this.loginScreen = loginScreen;
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
    ConfigManager configManager = ConfigManager.getInstance();
    ClientConfig config = configManager.getConfig();

    LanguageOption selected = optionMap.getOrDefault(config.getLang(), optionMap.get("en"));
    config.setLang(selected.getCode());
    configManager.save();

    languageChooser.getSelectionModel().select(selected);
    lang = langFiles.get(selected.getCode());
    languageChooser.setOnAction(e -> applyLanguageChoice());
  }

  /**
   * Action after changed state of choice box, language selector, is performed here
   */
  private void applyLanguageChoice() {
    String selectedLang = languageChooser.getSelectionModel().getSelectedItem().getCode();
    ConfigManager configManager = ConfigManager.getInstance();
    ClientConfig config = configManager.getConfig();
    config.setLang(selectedLang);
    configManager.save();
    lang = langFiles.get(selectedLang);

    languageTitle.setText(lang.get("login.language"));
    loginScreen.refresh();
  }

  private void loadLanguages() {
    IOUtils.saveResources("lang/en.json", "lang/de.json", "lang/sk.json");
    registerLanguage("en");
    for (String name : Objects.requireNonNull(new File("lang").list((dir, name) -> name.endsWith(".json")))) {
      String langCode = name.replace(".json", "");
      if (!langCode.equals("en"))
        registerLanguage(langCode);
    }
  }

  /**
   * Prepares choice box
   */
  private void prepareChoiceBox() {
    languageChooser = new ChoiceBox<>();
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

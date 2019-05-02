package org.puzzlebattle.client.screen;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.utils.ThreadUtils;
import org.puzzlebattle.core.utils.Logging;

import java.io.File;

/**
 * Contains file chooser for choosing file and special filters, which can be easily applied
 * before search
 */
public class FileScreen extends AbstractScreen {

  private FileChooser chooseFile;
  private File chosenFile;

  /**
   * Constructor for file screen, initialization is made here for file chooser
   *
   * @param title title for file chooser
   */
  public FileScreen(Stage stage, String title, Client client) {
    super(stage, client);
    chooseFile = new FileChooser();
    chooseFile.setInitialDirectory(new File(System.getProperty("user.home")));
    chooseFile.setTitle(title);
    Logging.logInfo("File screen for choosing avater has been created.");
  }

  /**
   * Initial directory, from which is search applied can be changed with this method
   *
   * @param path path of initial directory, from which searching will be applied
   */
  public void changeInitialDirectory(String path) {
    chooseFile.setInitialDirectory(new File(path));
  }

  /**
   * Method which can set one or few filters for searching files
   *
   * @param args filters for searching
   */
  public void setFilter(FileChooser.ExtensionFilter... args) {
    chooseFile.getExtensionFilters().addAll(args);
  }

  /**
   * Filters applied for searching pictures can be manually set with this method
   */
  public void setPictureFilter() {
    chooseFile.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp"),
            new FileChooser.ExtensionFilter("GIF", "*.gif"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
    );
  }

  @Override
  public void onClose() {
    ThreadUtils.ui(() -> new MainScreen(stage, client).show());
  }

  /**
   * After choosing appropriate file with specific dialog, this file is returned
   *
   * @return chosen file using fileDialog
   */
  public File showDialog() {
    chosenFile = chooseFile.showOpenDialog(stage);
    return chosenFile;
  }
}

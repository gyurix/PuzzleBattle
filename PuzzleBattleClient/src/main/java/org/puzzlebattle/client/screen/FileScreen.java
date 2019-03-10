package org.puzzlebattle.client.screen;

import  javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class FileScreen extends Stage {

  FileChooser chooseFile;
  File chosenFile;

  public FileScreen(String title){
    super();
    chooseFile = new FileChooser();
    chooseFile.setInitialDirectory(new File(System.getProperty("user.home")));
    chooseFile.setTitle(title);
  }

  public void setPictureFilter() {
    chooseFile.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("BMP", "*.bmp"),
            new FileChooser.ExtensionFilter("GIF", "*.gif"),
            new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
    );
  }

  public void setFilter(FileChooser.ExtensionFilter ... args) {
    chooseFile.getExtensionFilters().addAll(args);
  }

  public void changeInitialDirectory(String path) {
    chooseFile.setInitialDirectory(new File(path));
  }

  public File showDialog() {
    chosenFile = chooseFile.showOpenDialog(this);
    return chosenFile;
  }
}

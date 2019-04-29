package org.puzzlebattle.client.games;

import javafx.scene.layout.Pane;


/**
 * Interface where methods for rendering and repainting are specified.
 *
 * @author (Juraj Barath, Jakub Perdek)
 * @version (1.0)
 */
public interface PaneRenderable {


  /**
   * Method for rendering pane.
   *
   * @param canvas pane which should be renderes
   */
  void render(Pane canvas);
}

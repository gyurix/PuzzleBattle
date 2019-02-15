package org.puzzlebattle.client.games.ballbouncer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.games.PaneRenderable;

@AllArgsConstructor
@Getter
public abstract class BallBouncerEntity implements PaneRenderable {
  private BallBouncerGame game;
}

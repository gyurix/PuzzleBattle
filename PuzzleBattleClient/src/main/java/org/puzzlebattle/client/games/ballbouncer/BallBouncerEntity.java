package org.puzzlebattle.client.games.ballbouncer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.puzzlebattle.client.games.CanvasRenderable;

@AllArgsConstructor
@Getter
public abstract class BallBouncerEntity implements CanvasRenderable {
  private BallBouncerGame game;
}

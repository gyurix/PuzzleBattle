package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.games.EndDialog;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.BouncerGame;
import org.puzzlebattle.client.games.bouncer.BouncerGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowGame;
import org.puzzlebattle.client.games.fourinarow.FourInARowGameSettings;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.*;
import org.puzzlebattle.client.screen.BestPlayersScreen;
import org.puzzlebattle.client.screen.LoginScreen;
import org.puzzlebattle.client.utils.ThreadUtils;

public class ServerConnectedHandler extends ServerHandler {
  public ServerConnectedHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInChangeProfile packet) {
    client.setUser(packet.getProfile());
  }


  @Override
  public void handle(ServerInBestPlayers packet) {
    ThreadUtils.ui(() -> BestPlayersScreen.getInstance().getFourInARowGameTable()
            .setItems(packet.getUserGameAttributes()));
  }

  @Override
  public void handle(ServerInEndGame packet) {
    ThreadUtils.ui(() -> {
      new EndDialog(LoginScreen.getInstance().getStage(), client, client.getGame().getType(), packet.getWinner()).show();
      client.setGame(null);
    });
  }

  @Override
  public void handle(ServerInUpdateGame packet) {
    ThreadUtils.ui(() -> client.getGame().updateData(packet.getData()));
  }

  @Override
  public void handle(ServerInStartGame packet) {
    ThreadUtils.ui(() -> {
      switch (packet.getType()) {
        case FOUR_IN_A_ROW:
          new FourInARowScreen(LoginScreen.getInstance().getStage(), new FourInARowGame(packet.isInitializer(),
                  new FourInARowGameSettings(), client), client).show();
          break;
        case BOUNCER:
          new BallBouncerScreen(LoginScreen.getInstance().getStage(),
                  new BouncerGame(new BouncerGameSettings(), client), client).show();
          break;
      }
    });
  }

}

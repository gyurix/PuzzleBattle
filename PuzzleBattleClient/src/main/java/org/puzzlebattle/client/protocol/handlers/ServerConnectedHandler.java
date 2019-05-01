package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.games.bouncer.BallBouncerScreen;
import org.puzzlebattle.client.games.bouncer.EndDialogBouncer;
import org.puzzlebattle.client.games.fourinarow.EndDialog;
import org.puzzlebattle.client.games.fourinarow.FourInARowScreen;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInBestPlayers;
import org.puzzlebattle.client.protocol.packets.in.ServerInChangeProfile;
import org.puzzlebattle.client.protocol.packets.in.ServerInEndGame;
import org.puzzlebattle.client.screen.BestPlayersScreen;
import org.puzzlebattle.client.utils.ThreadUtils;
import org.puzzlebattle.core.entity.GameWinner;

import static org.puzzlebattle.core.entity.GameType.BOUNCER;
import static org.puzzlebattle.core.entity.GameType.FOUR_IN_A_ROW;

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
    ThreadUtils.ui(() -> {
      BestPlayersScreen.getInstance().getFourInARowGameTable().setItems(packet.getUserGameAttributes());
    });
  }

  @Override
  public void handle(ServerInEndGame packet) {
    ThreadUtils.ui(() -> {
      if( client.getGame().getType()==BOUNCER) {
        if (packet.getWinner() == GameWinner.DRAW) {
          new EndDialogBouncer(BallBouncerScreen.getInstance(), BallBouncerScreen.getInstance().getGame().getYou(),
                  BallBouncerScreen.getInstance().getStage(), client, "draw");
        } else if (packet.getWinner() == GameWinner.P1) {
          new EndDialogBouncer(BallBouncerScreen.getInstance(), BallBouncerScreen.getInstance().getGame().getYou(),
                  BallBouncerScreen.getInstance().getStage(), client, "winner");
        } else if (packet.getWinner() == GameWinner.P2) {
          new EndDialogBouncer(BallBouncerScreen.getInstance(), BallBouncerScreen.getInstance().getGame().getYou(),
                  BallBouncerScreen.getInstance().getStage(), client, "loser");
        }
      }
      else if(client.getGame().getType()==FOUR_IN_A_ROW){
        if (packet.getWinner() == GameWinner.DRAW) {
          new EndDialog(FourInARowScreen.getInstance(), FourInARowScreen.getInstance().getGame().getYou(),
                  FourInARowScreen.getInstance().getStage(), client, "draw");
        } else if (packet.getWinner() == GameWinner.P1) {
          new EndDialog(FourInARowScreen.getInstance(), FourInARowScreen.getInstance().getGame().getYou(),
                  FourInARowScreen.getInstance().getStage(), client, "winner");
        } else if (packet.getWinner() == GameWinner.P2) {
          new EndDialog(FourInARowScreen.getInstance(), FourInARowScreen.getInstance().getGame().getYou(),
                  FourInARowScreen.getInstance().getStage(), client, "loser");
        }
      }
  });
  }
}

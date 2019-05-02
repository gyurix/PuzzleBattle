package org.puzzlebattle.client.protocol.packets.in;

import io.netty.buffer.ByteBuf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import org.puzzlebattle.client.screen.FriendshipMenu;
import org.puzzlebattle.core.protocol.ByteBufUtils;

@Data
public class ServerInBestPlayers extends ServerInPacket {
  private ObservableList<FriendshipMenu.UserGameAttributes> userGameAttributes;

  @Override
  public void handle(ServerInPacketHandler handler) {
    handler.handle(this);
  }

  @Override
  public void read(ByteBuf buf) {
    int numberOfBestPlayersReceived = buf.readInt();
    String username;
    int score;
    userGameAttributes = FXCollections.observableArrayList();
    for (int i = 0; i < numberOfBestPlayersReceived; i = i + 1) {
      username = ByteBufUtils.readString(buf);
      score = buf.readInt();
      userGameAttributes.add(new FriendshipMenu.UserGameAttributes(username, score));
    }
  }
}

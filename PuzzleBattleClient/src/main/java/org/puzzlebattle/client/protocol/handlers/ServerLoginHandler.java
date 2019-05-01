package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import javafx.stage.Stage;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInBestPlayers;
import org.puzzlebattle.client.protocol.packets.in.ServerInChangeProfile;
import org.puzzlebattle.client.protocol.packets.in.ServerInLoginResult;
import org.puzzlebattle.client.protocol.packets.in.ServerInRegisterSuccessful;
import org.puzzlebattle.client.screen.*;
import org.puzzlebattle.client.utils.ThreadUtils;
import org.puzzlebattle.core.utils.Logging;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public class ServerLoginHandler extends ServerHandler {
  public ServerLoginHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInLoginResult packet) {
    if (packet.isResult()) {
      ThreadUtils.ui(() -> LoginScreen.getInstance().showAlert(ERROR, "login.incorrect"));
    } else {
      new MainScreen(LoginScreen.getInstance().getStage(), new SettingsForScreens(),
              LoginScreen.getInstance().getUser(), client).show();
      LoginScreen.getInstance().showAlert(INFORMATION, "login.loggingIn");
      ServerConnectedHandler handler = new ServerConnectedHandler(channel, client);
      client.getConnection().setHandler(handler);
      channel.pipeline().replace("handler", "handler", handler);
    }
  }

  public void handle(ServerInChangeProfile packet) {

  }

  public void handle(ServerInBestPlayers packet) {
    BestPlayersScreen.getInstance().getFourInARowGameTable().setItems(packet.getUserGameAttributes());
  }

  public void handle(ServerInRegisterSuccessful packet) {
    if (!packet.isSuccessfulRegistration()) {
      Logging.logSevere("Registration failed");
      RegisterScreen.getInstance().registrationFailedAlert();
    }
    new AdditionalInformationScreen(new Stage(), RegisterScreen.getInstance().getUserPuzzleBattle().getUserName(),
            RegisterScreen.getInstance().getUserPuzzleBattle().getEmail(), client).show();
    new LoginScreen(RegisterScreen.getInstance().getStage(),
            new LanguageSelector(RegisterScreen.getInstance().getStage(), 100, 25), client).show();
  }

  //public void handle(ServerIn)
}

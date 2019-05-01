package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInEncryption;
import org.puzzlebattle.core.protocol.processor.PacketCrypterAES;
import org.puzzlebattle.core.utils.EncryptionUtils;

import static org.puzzlebattle.core.utils.EncryptionUtils.toAESKey;
import static org.puzzlebattle.core.utils.EncryptionUtils.toIV;

public class ServerEncryptionHandler extends ServerHandler {
  public ServerEncryptionHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void handle(ServerInEncryption packet) {
    ServerLoginHandler loginHandler = new ServerLoginHandler(channel, client);
    channel.pipeline().replace("handler", "handler", loginHandler);
    client.getConnection().setHandler(loginHandler);

    EncryptionUtils utils = client.getEncryptionUtils();
    utils.setAesKey(toAESKey(packet.getAesKey()));
    utils.setAesDecryptIV(toIV(packet.getDecryptIv()));
    utils.setAesEncryptIV(toIV(packet.getEncryptIv()));
    utils.setRsaEncryptKey(null);
    utils.setRsaDecryptKey(null);

    channel.pipeline().replace("encryption", "encryption", new PacketCrypterAES(utils));
  }
}

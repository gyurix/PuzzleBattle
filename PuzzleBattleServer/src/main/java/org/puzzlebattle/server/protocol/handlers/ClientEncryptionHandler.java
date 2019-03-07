package org.puzzlebattle.server.protocol.handlers;

import io.netty.channel.Channel;
import org.puzzlebattle.core.protocol.processor.PacketCrypterAES;
import org.puzzlebattle.core.protocol.processor.PacketCrypterRSA;
import org.puzzlebattle.core.utils.EncryptionUtils;
import org.puzzlebattle.server.entity.Client;
import org.puzzlebattle.server.entity.Server;
import org.puzzlebattle.server.protocol.packets.in.ClientInEncryption;
import org.puzzlebattle.server.protocol.packets.out.ClientOutEncryption;

import static org.puzzlebattle.core.utils.EncryptionUtils.toRSAPublicKey;

public class ClientEncryptionHandler extends ClientHandler {
  public ClientEncryptionHandler(Channel channel, Server server) {
    super(channel, new Client(server));
  }

  @Override
  public void handle(ClientInEncryption packet) {
    EncryptionUtils keyUtils = client.getKeyUtils();
    keyUtils.setRsaEncryptKey(toRSAPublicKey(packet.getKey()));
    keyUtils.setAesKey(EncryptionUtils.generateAES());
    keyUtils.setAesDecryptIV(EncryptionUtils.generateIV());
    keyUtils.setAesEncryptIV(EncryptionUtils.generateIV());

    ClientLoginHandler newHandler = new ClientLoginHandler(channel, client);
    channel.pipeline().replace("handler", "handler", newHandler);
    client.setHandler(newHandler);

    channel.pipeline().addBefore("type", "encryption", new PacketCrypterRSA(keyUtils));
    sendPacket(new ClientOutEncryption(keyUtils.getAesKey().getEncoded(), keyUtils.getAesEncryptIV().getIV(), keyUtils.getAesDecryptIV().getIV()));

    channel.pipeline().replace("encryption", "encryption", new PacketCrypterAES(keyUtils));
  }
}

package org.puzzlebattle.client.protocol.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.puzzlebattle.client.protocol.Client;
import org.puzzlebattle.client.protocol.packets.in.ServerInEncryption;
import org.puzzlebattle.client.protocol.packets.out.ServerOutEncryption;
import org.puzzlebattle.core.protocol.processor.PacketCrypterAES;
import org.puzzlebattle.core.protocol.processor.PacketCrypterRSA;
import org.puzzlebattle.core.utils.EncryptionUtils;

import java.security.KeyPair;

import static org.puzzlebattle.core.utils.EncryptionUtils.toAESKey;
import static org.puzzlebattle.core.utils.EncryptionUtils.toIV;

public class ServerEncryptionHandler extends ServerHandler {
  public ServerEncryptionHandler(Channel channel, Client client) {
    super(channel, client);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    EncryptionUtils utils = client.getEncryptionUtils();

    KeyPair pair = EncryptionUtils.generateRSA();
    utils.setRsaDecryptKey(pair.getPrivate());
    sendPacket(new ServerOutEncryption(pair.getPublic().getEncoded()));
    channel.pipeline().addAfter("length", "encryption", new PacketCrypterRSA(utils));
    ctx.fireChannelActive();
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

package org.puzzlebattle.client.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import org.puzzlebattle.client.protocol.handlers.ServerEncryptionHandler;
import org.puzzlebattle.client.protocol.handlers.ServerHandler;
import org.puzzlebattle.client.protocol.packets.out.ServerOutEncryption;
import org.puzzlebattle.client.protocol.packets.out.ServerOutPacket;
import org.puzzlebattle.core.protocol.processor.PacketLengthProcessor;
import org.puzzlebattle.core.utils.EncryptionUtils;

import java.net.InetSocketAddress;
import java.security.KeyPair;

@Getter
public class ServerConnection {
  private Thread connectionThread;
  private Client client;
  @Setter
  private ServerHandler handler;

  public ServerConnection(Client client) {
    connectionThread = new Thread(this::start);
    this.client = client;
  }

  public void sendPacket(ServerOutPacket packet) {
    handler.sendPacket(packet);
  }

  public void start() {
    NioEventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap clientBootstrap = new Bootstrap();

      clientBootstrap.group(group);
      clientBootstrap.channel(NioSocketChannel.class);
      clientBootstrap.remoteAddress(new InetSocketAddress(client.getAddress().getHost(),
              client.getAddress().getPort()));
      clientBootstrap.handler(new ChannelInitializer<Channel>() {
        @Override
        protected void initChannel(Channel ch) {
          ChannelPipeline pipeline = ch.pipeline();
          PacketLengthProcessor lengthProcessor = new PacketLengthProcessor();
          ServerPacketTypeProcessor typeProcessor = new ServerPacketTypeProcessor();
          handler = new ServerEncryptionHandler(ch, client);
          pipeline.addLast("length", lengthProcessor);
          pipeline.addLast("type", typeProcessor);
          pipeline.addLast("handler", handler);
          System.out.println("Established TCP connection.");
          EncryptionUtils encryptionUtils = client.getEncryptionUtils();
          KeyPair rsa = EncryptionUtils.generateRSA();
          encryptionUtils.setRsaDecryptKey(rsa.getPrivate());
          encryptionUtils.setRsaEncryptKey(rsa.getPublic());
          sendPacket(new ServerOutEncryption(rsa.getPublic().getEncoded()));
        }
      });
      ChannelFuture channelFuture = clientBootstrap.connect().sync();
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException ignored) {

    } finally {
      try {
        group.shutdownGracefully().sync();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println("Stopped TCP connection.");
  }

  public void stop() {
    connectionThread.interrupt();
  }
}

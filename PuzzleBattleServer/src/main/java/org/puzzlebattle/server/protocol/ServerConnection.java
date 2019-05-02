package org.puzzlebattle.server.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.puzzlebattle.core.entity.AddressInfo;
import org.puzzlebattle.core.protocol.processor.PacketLengthProcessor;
import org.puzzlebattle.core.utils.Logging;
import org.puzzlebattle.server.entity.Server;
import org.puzzlebattle.server.protocol.handlers.ClientEncryptionHandler;
import org.puzzlebattle.server.protocol.handlers.ClientHandler;
import org.puzzlebattle.server.protocol.packets.ClientPacketTypeProcessor;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.puzzlebattle.core.utils.Logging.logInfo;
import static org.puzzlebattle.core.utils.Logging.logSevere;

public class ServerConnection extends ChannelInitializer<Channel> {
  private final Map<Channel, ClientHandler> clientHandlers = Collections.synchronizedMap(new HashMap<>());
  private final Server server;
  private final Thread connectionThread = new Thread(this::start);

  public ServerConnection(Server server) {
    this.server = server;
    connectionThread.start();
  }

  public void cleanup() {
    clientHandlers.keySet().removeIf((c) -> !c.isOpen());
  }

  @Override
  protected void initChannel(Channel ch) {
    ChannelPipeline pipeline = ch.pipeline();
    PacketLengthProcessor lengthProcessor = new PacketLengthProcessor();
    ClientPacketTypeProcessor typeProcessor = new ClientPacketTypeProcessor();
    ClientHandler handler = new ClientEncryptionHandler(ch, server);
    pipeline.addLast("length", lengthProcessor);
    pipeline.addLast("type", typeProcessor);
    pipeline.addLast("handler", handler);
    clientHandlers.put(ch, handler);
    Logging.logInfo("Connected Client", "client", ch.remoteAddress());
  }

  public void start() {
    NioEventLoopGroup group = new NioEventLoopGroup();
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    serverBootstrap.group(group);
    serverBootstrap.channel(NioServerSocketChannel.class);
    AddressInfo adr = server.getAddress();
    try {
      serverBootstrap.localAddress(new InetSocketAddress(adr.getHost(), adr.getPort()));
      serverBootstrap.childHandler(this);
      ChannelFuture channelFuture = serverBootstrap.bind().sync();
      logInfo("Started server", "address", adr);
      channelFuture.channel().closeFuture().sync();
    } catch (Throwable e) {
      logSevere("Failed to start server", "address", adr, "error", e);
    } finally {
      try {
        group.shutdownGracefully().sync();
      } catch (InterruptedException e) {
        logSevere("Shutdown interrupted", "error", e);
      }
    }
  }
}

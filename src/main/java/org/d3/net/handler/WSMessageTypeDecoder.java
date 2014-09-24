package org.d3.net.handler;

import org.d3.D3Context;
import org.d3.core.transfer.Charactor;
import org.d3.net.protocol.ProtobufDecoder;
import org.d3.net.protocol.ProtobufEncoder;
import org.d3.net.protocol.TextWebsocketDecoder;
import org.d3.net.protocol.TextWebsocketEncoder;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleStateHandler;

@Component
@Sharable
public class WSMessageTypeDecoder extends SimpleChannelInboundHandler<WebSocketFrame> {
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
			WebSocketFrame msg) throws Exception {
		ChannelPipeline pipeline = ctx.pipeline();
		if(msg instanceof BinaryWebSocketFrame){
			
			ProtobufDecoder decoder = (ProtobufDecoder) D3Context.getBean("protobufDecoder");
			ProtobufEncoder encoder = (ProtobufEncoder) D3Context.getBean("protobufEncoder");
			pipeline.addLast(decoder);
			pipeline.addLast(encoder);
			
		}
		else if(msg instanceof TextWebSocketFrame){
			
			TextWebsocketDecoder decoder = (TextWebsocketDecoder) D3Context.getBean("textWebsocketDecoder");
			TextWebsocketEncoder encoder = (TextWebsocketEncoder) D3Context.getBean("textWebsocketEncoder");
			pipeline.addLast(decoder);
			pipeline.addLast(encoder);
			
		}
		PacketHandler packetHandler = (PacketHandler) D3Context.getBean("packetHandler");
		
		pipeline.addLast("packetHandler", packetHandler);
		pipeline.remove(this);
	}
	
	private void applyProtocol(ChannelHandlerContext ctx, Charactor c){
		
		ChannelPipeline pipeline = ctx.pipeline();
		TextWebsocketDecoder decoder = (TextWebsocketDecoder) D3Context.getBean("textWebsocketDecoder");
		TextWebsocketEncoder encoder = (TextWebsocketEncoder) D3Context.getBean("textWebsocketEncoder");
		pipeline.addLast(decoder);
		pipeline.addLast(encoder);
		pipeline.addLast("idleStateCheck", new IdleStateHandler(
				300, 300, 300));
//		pipeline.addLast(new PacketHandler(c));
		
		pipeline.remove(this);
	}

}
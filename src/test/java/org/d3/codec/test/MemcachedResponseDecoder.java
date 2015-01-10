package org.d3.codec.test;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

public class MemcachedResponseDecoder extends ByteToMessageDecoder {
	
	private enum State {
		Header,
		Body
	}
	
	private State state = State.Header;
	private int totalBodySize;
	private byte magic;
	private byte opCode;
	private short keyLength;
	private byte extraLength;
	private byte dataType;
	private short status;
	private int id;
	private long cas;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		switch (state) {
			case Header:
				if (in.readableBytes() < 24) {
					return;//response header is 24 bytes #4
				}
				// read header #5
				magic = in.readByte();
				opCode = in.readByte();
				keyLength = in.readShort();
				extraLength = in.readByte();
				dataType = in.readByte();
				status = in.readShort();
				totalBodySize = in.readInt();
				//referred to in the protocol spec as opaque
				id = in.readInt();
				cas = in.readLong();
				state = State.Body;
			case Body:
				if (in.readableBytes() < totalBodySize) {
					return; //until we have the entire payload return #6
				}
				int flags = 0, expires = 0;
				int actualBodySize = totalBodySize;
				if (extraLength > 0) {
					flags = in.readInt();
					actualBodySize -= 4;
				}
				if (extraLength > 4) {
					expires = in.readInt();
					actualBodySize -= 4;
				}
				String key = "";
				if (keyLength > 0) {
					ByteBuf keyBytes = in.readBytes(keyLength);
					key = keyBytes.toString(CharsetUtil.UTF_8);
					actualBodySize -= keyLength;
				}
				ByteBuf body = in.readBytes(actualBodySize);
				String data = body.toString(CharsetUtil.UTF_8);
				out.add(new MemcachedResponse(
					magic,
					opCode,
					dataType,
					status,
					id,
					cas,
					flags,
					expires,
					key,
					data
				));
				state = State.Header;
		}
	}

}
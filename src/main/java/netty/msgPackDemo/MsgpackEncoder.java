package netty.msgPackDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Created by wangdecheng on 09/06/2018.
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx,Object msg,ByteBuf bytebuf) throws Exception{

        MessagePack messagePack = new MessagePack();

    }
}

package com.example.rpcframework.protocol.server;

import com.example.rpcframework.dto.Invocation;
import com.example.rpcframework.dto.Response;
import com.example.rpcframework.register.RegisterService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Shan Cheng
 * @since 2022/8/15
 */

@Slf4j
@AllArgsConstructor
@Component
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final  RpcRequestHandler requestHandler;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof Invocation) {
                Invocation invocation = (Invocation) msg;

                // Execute the target method (the method the client needs to execute) and return the method result
                Object result = requestHandler.handle(invocation);

                Response<Object> response = new Response<>();
                response.setRequestId(invocation.getRequestId());
                if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                    response.setData(result);
                    response.setCode(200);
                } else {
                    response.setCode(500);
                    log.error("not writable now, message dropped");
                }
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            //Ensure that ByteBuf is released, otherwise there may be memory leaks
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

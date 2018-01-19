package concurrent.highjava.chapter5;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangdecheng on 2018/1/1.
 */
public class MultiThreadEchoServer {

    private Selector selector;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    public static Map<Socket,Long> time_stat = new HashMap<>(10240);

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

       // InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(),8000);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8000);

        serverSocketChannel.socket().bind(inetSocketAddress);
        SelectionKey acceptKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        for(;;){
            selector.select();//阻塞
            Set readyKeys = selector.selectedKeys();
            Iterator i = readyKeys.iterator();
            long e=0;
            while(i.hasNext()){
                SelectionKey sk = (SelectionKey)i.next();
                i.remove();

                if(sk.isAcceptable()){
                    doAccept(sk);
                }else if(sk.isValid() && sk.isReadable()){
                    if(!time_stat.containsKey(((SocketChannel) sk.channel()).socket())){
                        time_stat.put(((SocketChannel) sk.channel()).socket(),System.currentTimeMillis());
                    }
                    doRead(sk);
                }else if(sk.isValid()&& sk.isWritable()){
                    doWrite(sk);
                    e=System.currentTimeMillis();
                    long b=time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:"+(e-b)+"ms");
                }
            }
        }
    }

    private void doWrite(SelectionKey sk) {
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)sk.channel();
        SocketChannel clientChannel;
        try{
            clientChannel = serverSocketChannel.accept();
            clientChannel.configureBlocking(false);
            SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);
            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress()+".");
        }catch (Exception e){
            System.out.println("Failed toaccept new client");
            e.printStackTrace();
        }
    }
    private void doRead(SelectionKey sk){
        SocketChannel channel = (SocketChannel)sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try{
            len = channel.read(bb);
            if(len<0){
                disconnect(sk);
                return;
            }
        }catch (Exception e){
            System.out.println("Failed to read from client");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        bb.flip();
        executorService.execute(new HandleMsg(sk,bb));
    }

    private void disconnect(SelectionKey sk) {
    }

    class EchoClient{
        private LinkedList<ByteBuffer> outq;
        EchoClient(){
            outq = new LinkedList<>();
        }

        public LinkedList<ByteBuffer> getOutq() {
            return outq;
        }
        public void enqueue(ByteBuffer bb){
            outq.addFirst(bb);
        }
    }
}

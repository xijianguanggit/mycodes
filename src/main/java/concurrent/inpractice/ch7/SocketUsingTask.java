package concurrent.inpractice.ch7;

import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public abstract class SocketUsingTask<T> implements CallableTask<T> {

    private Socket socket;
    protected synchronized void setSocket(Socket socket){
        this.socket = socket;
    }

    @Override
    public RunnableFuture<T> newTask(){

        return new FutureTask<T>(this){
            public boolean cancel(boolean mayInterruptIfRunning){
                try{
                    SocketUsingTask.this.cancel();
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }

        };
    }

}

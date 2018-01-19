package concurrent.inpractice.ch7;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.*;

@ThreadSafe
public class CancellingExecutor extends ThreadPoolExecutor {


    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable){
        if(callable instanceof CallableTask){
            return ((CallableTask) callable).newTask();
        }else{
            return super.newTaskFor(callable);
        }
    }

    public static void main(String[] args){
        Executor executor = Executors.newSingleThreadExecutor();
        CancellingExecutor cancellingExecutor = new CancellingExecutor(1,1,0L,
                TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(1));
        CallableTask callable = new SocketUsingTask() {
            @Override
            public void cancel() {

            }

            @Override
            public Object call() throws Exception {
                return null;
            }
        };
        RunnableFuture<Integer> ftask = cancellingExecutor.newTaskFor(callable);
        cancellingExecutor.execute(ftask);
        try {
            ftask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        cancellingExecutor.submit(callable);
    }

}

package concurrent.inpractice.ch7;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * Created by wangdecheng on 2017/12/25.
 */
public interface CallableTask<T> extends Callable<T>{

    void cancel();
    RunnableFuture<T> newTask();
}


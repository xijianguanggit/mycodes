import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangdecheng on 27/05/2018.
 */
public class Test2 {
    private static Test2 ourInstance = new Test2();

    private ReentrantLock lock = new ReentrantLock();
    public static Test2 getInstance() {

        //lock.lock();
        return ourInstance;
       // lock.unlock();
    }

    private Test2() {
    }
}

package concurrent.highjava.chapter3;

/**
 * Created by wangdecheng on 2017/12/30.
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * Created by 13 on 2017/5/5.
 */
public class SemapDemo implements Runnable {
    final Semaphore semp = new Semaphore(5);

    @Override
    public void run() {
        try {
            semp.acquire();
            System.out.println(Thread.currentThread().getId()+" begin sleep");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ":done!");

        } catch (InterruptedException e) {
            e.printStackTrace();

        }finally {
            semp.release();
        }
    }

    /**
     * 总共20个线程,系统会以5个线程一组为单位,依次执行并输出
     *
     * @param args
     */
    public static void main(String args[]) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemapDemo demo = new SemapDemo();
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(demo);
            thread.start();
            if(i<5) {
                Thread.sleep(1000);
                thread.interrupt();
            }

        }
    }
}

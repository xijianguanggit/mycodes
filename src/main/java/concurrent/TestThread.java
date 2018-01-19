package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangdecheng on 2017/12/29.
 */
public class TestThread extends  Thread{

    public static void main(String[] args){
        TestThread testThread = new TestThread();
        testThread.start();
        testThread.interrupt();
        try {
            testThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void run(){
        try {
            TimeUnit.SECONDS.sleep(1);
            wait();

        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        if(Thread.currentThread().isInterrupted()){
            System.out.println("exit");
            return;
        }else{
            System.out.println("continue");
        }
    }
}

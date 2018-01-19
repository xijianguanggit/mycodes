package concurrent.highjava;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangdecheng on 2017/12/29.
 */
public class GoodSuspend {

    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread{
        volatile boolean suspend = false;
        String name;
        public ChangeObjectThread(String name){
            this.name = name;
        }

        public void suspendMe(){
            suspend = true;
        }

        public void resumeMe(){
            suspend = false;
            synchronized (this){
                this.notify();
            }
        }

        public void run(){
            System.out.println(name+" begin to run");
            while(true){
                try {
                    synchronized (this){
                        while(suspend) {
                            System.out.println("====suspend");
                            this.wait();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (u){
                    System.out.println("in change object thread");
                }
                Thread.yield();
            }

        }
    }

    public static class ReadObjectThread extends Thread{

        public void run(){
            while (true){
                synchronized (u){
                    System.out.println("in read object thread");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread("t1");
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
       // t2.start();
        TimeUnit.SECONDS.sleep(1);
        t1.suspendMe();
        System.out.println("t1 suspend 2 sec");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("resume t1");
        t1.resumeMe();
        //t1.join();
        //t2.join();
        TimeUnit.SECONDS.sleep(2);
        t1.suspendMe();
    }
}

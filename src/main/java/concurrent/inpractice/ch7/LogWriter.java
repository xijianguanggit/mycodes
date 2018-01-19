package concurrent.inpractice.ch7;

import java.io.PrintStream;
import java.util.concurrent.*;

/**
 * Created by wangdecheng on 2017/12/25.
 */
public class LogWriter {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private PrintStream writer = System.out;
    private LogThread logThread = new LogThread(writer);
    private BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(5);
    int reservations=0;
    boolean isShutDown = false;


    public void start(){
        executorService.execute(logThread);
        //logThread.start();
    }
    public void stop(){

        //the approach with executorService
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }

        //the approach of myself
//        synchronized (this){
//            isShutDown = true;
//        }
//        logThread.interrupt();
    }

    private void info(String message){
        //1、


        //2、
        try {
//            synchronized (this){
//                if(isShutDown){
//                    throw new RuntimeException(" shutdown====");
//                }
//                reservations++;
//            }
            blockingQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class LogThread extends  Thread {
        private PrintStream writer;
        public LogThread(PrintStream writer){
            this.writer=writer;
        }
        public void run(){
            try {
                while (true) {
                    try {
                        synchronized (LogWriter.this) {
                            if (isShutDown && reservations == 0) {
                                break;
                            }
                            reservations--;
                        }
                        writer.println(blockingQueue.take());
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                        e.printStackTrace();
                    }
                }
            }finally {
                writer.close();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        LogWriter logWriter = new LogWriter();
        logWriter.start();
        logWriter.info("11111");
        logWriter.info("22222");
        logWriter.info("333333");
        logWriter.info("44444");
        logWriter.info("5555555");
        logWriter.stop();

        for(int i=0;i<100;i++){
            logWriter.info(String.valueOf(i));
        }
        //Thread.sleep(1000);
       // Runnable thread = ()->logWriter.stop();
    }
}

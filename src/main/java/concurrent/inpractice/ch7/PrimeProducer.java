package concurrent.inpractice.ch7;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangdecheng on 2017/12/25.
 */
public class PrimeProducer extends  Thread{

    private  final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {

        this.queue = queue;
    }

    @Override
    public void run(){
        BigInteger bigInteger = BigInteger.ONE;
        while(!Thread.currentThread().isInterrupted()){

            bigInteger = bigInteger.add(BigInteger.ONE);
            System.out.println(Thread.currentThread().getName()+"===execute==="+bigInteger);
            try {
                queue.put(bigInteger);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            System.out.println(Thread.currentThread().getName()+"===execute success==="+bigInteger);
        }
    }

    public void cancel(){
        interrupt();
    }

    public static void main(String[] args){
        BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<>(2);
        PrimeProducer primeProducer = new PrimeProducer(queue);
        primeProducer.start();
        new PrimeProducer(queue).start();
        new PrimeProducer(queue).start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        primeProducer.cancel();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            queue.take();
            queue.take();
            queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

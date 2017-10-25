package concurrent;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 当在一个线程池并发多个任务时，只要一个处理完就可以返回，对其他任务进行中断（ExecutorService.invokeAny）
 * 或所有任务处理完再返回(ExecutorService.invokeAll)
 * Created by wangdecheng on 10/25/17.
 */
public class TestCallable {
    static class MyCallable implements Callable<Integer> {
        private String name;

        public MyCallable(String name) {
            this.name = name;
        }

        @Override
        public Integer call() throws Exception {
            try{
                Random random = new Random();

                while(true){
                    int r = random.nextInt(10);
                    if(r==0){
                        System.out.println(name+" result:"+r);
                        return 0;
                    }
                    System.out.println(name+" =============="+r);
                    TimeUnit.SECONDS.sleep(1);
                }
            }catch (InterruptedException e){
                System.out.println(name+" interrupt");
                return -1;
            }

        }
    }


    public static void main(String[] args) throws UnsupportedEncodingException {


        ExecutorService service = Executors.newFixedThreadPool(3);


        List<Callable<Integer>> list = new ArrayList<>();
        list.add(new MyCallable("first"));
        list.add(new MyCallable("second"));


        try {
            int result = service.invokeAny(list);
            // List<Future<Integer>> result = service.invokeAll(list);
            System.out.println(" get result "+result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


}

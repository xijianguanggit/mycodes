package concurrent.inpractice.ch8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by wangdecheng on 2017/12/27.
 */
public class ThreadDeadLock {

    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static class RenderPageTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            Future<String> header,foot;
            header = executorService.submit(new FileLoadTask("header.html"));
            //foot = executorService.submit(new FileLoadTask("foot.html"));

            System.out.println("render:"+header.get());
           // System.out.println("render:"+foot.get());
            return null;
        }
    }
    static class FileLoadTask implements Callable<String>{
        private String fileName;

        public FileLoadTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String call() throws Exception {
            System.out.println(this.fileName);
            return this.fileName;
        }
    }
    public static void main(String[] args) throws Exception {
        new RenderPageTask().call();
        //executorService.submit(new RenderPageTask());
    }
}

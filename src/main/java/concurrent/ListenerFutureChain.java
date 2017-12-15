package concurrent;

/**
 * Created by wangdecheng on 11/23/17.
 */
public class ListenerFutureChain {

    /*
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

    private void executeTask(){
        AsyncFunction<String,String> asyncFunction1 = new AsyncFunction<String, String>() {

            @Override
            public ListenableFuture<String> apply(String input) throws Exception {
                ListenableFuture<String> listenableFuture = listeningExecutorService.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        System.out.println("===step1===="+Thread.currentThread().getName());
                        return input +" listenableFuture 1  call return\n";
                    }
                });
                return listenableFuture;
            }
        };

        AsyncFunction<String,String> asyncFunction2 = new AsyncFunction<String, String>() {
            @Override
            public ListenableFuture<String> apply(String input) throws Exception {
                ListenableFuture<String> listenableFuture = listeningExecutorService.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        System.out.println("===step2===="+Thread.currentThread().getName());
                        return input +" listenableFuture 2  call return\n";
                    }
                });

                return listenableFuture;
            };
        };

        ListenableFuture<String> start =  listeningExecutorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("====start====="+Thread.currentThread().getName());
                return " ====begin====\n";
            }
        });

        ListenableFuture future1 = Futures.transform(start,asyncFunction1,executorService);
        ListenableFuture future2 = Futures.transform(future1,asyncFunction2,executorService);
        Futures.addCallback(future2, new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Success  result:"+result+" "+Thread.currentThread().getName());
                System.out.println("=====Success\n");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("=====Fail");
                t.printStackTrace();
            }
        });
        System.out.println("--blocked?===");
    }

    public static void main(String[] args){
        ListenerFutureChain listenerFutureChain = new ListenerFutureChain();
        listenerFutureChain.executeTask();

        System.out.println("+++Main===END");
    }
    */
}

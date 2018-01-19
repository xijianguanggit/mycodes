import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import redis.clients.jedis.Jedis;

import java.util.concurrent.*;

/**
 * Created by wangdecheng on 9/23/17.
 */
public class Test {

    private final static ExecutorService saveToKafkaThread = new ThreadPoolExecutor(1,1,0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(200));

    private void  exec(final int num){
       Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(num);
            }
        };
       Thread t = new Thread(r);
       t.setDaemon(true);
       t.start();
    }

//    public static void main(String[] args) throws Exception {
//
//        List<Integer> list = new ArrayList<>();
//        list.add(5);
//        list.add(3);
//        list.add(6);
//        list.add(2);
//        list.add(1);
//        list.add(0);
//        list.add(4);
//        Collections.sort(list);
//    }

    static  void connectRedis(){
        Jedis jedis = new Jedis("10.170.191.64", 6379);
        jedis.auth("wow!nemo");
        //jedis.set()

        // 获取数据并输出
//        Set<String> set = jedis.keys("CHARGE:*");
//        System.out.println("success");
//        for(String key:set) {
//            System.out.println("List of stored keys:: "+key);
//        }
        String key = "charge:test";
        jedis.sadd("charge:test","ddd");
        for(String s:jedis.smembers("charge:test")){
            System.out.println(s);
        }
        jedis.del(key);

        for(String s:jedis.smembers("charge:test")){
            System.out.println(s);
        }

    }
    public static void testGuavaCache(){

        LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
        new CacheLoader<String, String>() {
            public String load(String key) throws Exception {
                return createExpensiveGraph(key);
            }
        });

        try {
            System.out.println(graphs.get("1"));
           String b = graphs.get("2");
            String c =graphs.get("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static String createExpensiveGraph(String key) {
        System.out.println("=========");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "test";
    };

    private static void testListenerFuture(){
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    }

    public static void main(String[] args) throws Exception {
        //testGuavaCache();
        connectRedis();
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }


}

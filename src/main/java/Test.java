import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    /*
    static  void connectRedis(){
        Jedis jedis = new Jedis("10.170.191.64", 6379);
        jedis.auth("wow!nemo");

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

    public static class HelloWorldProducer implements Runnable {
        public void run() {
            try {
                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("TEST.FOO");

                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages
                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message
                System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);

                // Clean up
                session.close();
                connection.close();
            }
            catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }

    public static class HelloWorldConsumer implements Runnable, ExceptionListener {
        public void run() {
            try {

                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                connection.setExceptionListener(this);

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("TEST.FOO");

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                // Wait for a message
                Message message = consumer.receive(1000);

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                } else {
                    System.out.println("Received: " + message);
                }

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }*/
}

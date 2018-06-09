package redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by wangdecheng on 2017/12/22.
 */
public class TestRedis {

    private final static String REDIS_KEY_DEVICE_PRESENCE = "device_presence";
    public static void main(String[] args){
        Jedis jedis = new Jedis("10.170.191.64", 6379);
        jedis.auth("wow!nemo");

        new Thread(()->{System.out.print("d");}).start();
        long high = System.currentTimeMillis() / 1000;
        long low = high - 5 * 60;
        Set<String> onLineSet =  jedis.zrangeByScore(REDIS_KEY_DEVICE_PRESENCE,low,high);
        System.out.println(jedis.zrank(REDIS_KEY_DEVICE_PRESENCE,"d:10012106"));
        System.out.println(onLineSet);
      /*
        // 获取数据并输出
        Set<String> set = jedis.keys("CHARGE:*");
       System.out.println("success");

        String key = "charge:test";
        jedis.sadd("charge:test","ddd");
        for(String s:jedis.smembers("charge:test")){
            System.out.println(s);
        }
        jedis.del(key);
        //jedis.get
        for(String s:jedis.smembers("charge:test")){
            System.out.println(s);
        }
*/
    }
}

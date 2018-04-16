package redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by wangdecheng on 03/02/2018.
 */
public class PrivateRedis {

    private final static String REDIS_KEY_DEVICE_PRESENCE = "device_presence";
    public static void main(String[] args){
        Jedis jedis = new Jedis("172.17.100.21", 6379);
        jedis.auth("79kx!IGY");


        long high = System.currentTimeMillis() / 1000;
        long low = high - 5 * 60*60*24;
        Set<String> onLineSet =  jedis.zrangeByScore(REDIS_KEY_DEVICE_PRESENCE,0,high);
        //System.out.println(jedis.zrank(REDIS_KEY_DEVICE_PRESENCE,"d:10012106"));
        System.out.println(onLineSet);

    }

}

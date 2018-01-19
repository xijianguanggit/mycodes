package redis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by wangdecheng on 2017/12/22.
 */
public class TestRedis {

    public static void main(String[] args){
        Jedis jedis = new Jedis("10.170.191.64", 6379);
        jedis.auth("wow!nemo");
      //
        // 获取数据并输出
        Set<String> set = jedis.keys("CHARGE:*");
       System.out.println("success");
//        for(String key:set) {
//            System.out.println("List of stored keys:: "+key);
//        }
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

    }
}

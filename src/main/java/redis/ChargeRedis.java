package redis;

import redis.clients.jedis.Jedis;

/**
 * Created by wangdecheng on 24/05/2018.
 */
public class ChargeRedis {
    public static void main(String[] args){
        Jedis jedis = new Jedis("10.173.48.65", 6379);//pre
        jedis.auth("wow!nemo");


        for(String meetingId : jedis.smembers(getMeetingAllKey())){
            jedis.del(getMeetingIdKeyPrefix()+meetingId);
        }
        jedis.close();
    }

    public static String getChargeIdKeyPrefix(){
        return "CHARGE:CHARGE_ID:";
    }

    public static String getMeetingIdKeyPrefix(){
        return "CHARGE:MEETING_ID:";
    }

    public static String getChargeAllKey(){
        return "CHARGE:CHARGE";
    }

    public static String getMeetingAllKey(){
        return "CHARGE:MEETING";
    }
}

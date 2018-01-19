package xylink.charge;

import redis.clients.jedis.Jedis;

/**
 * Created by wangdecheng on 16/01/2018.
 */
public class MeetingPropertiesUtil {

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

    private static  void meetingInfo(Jedis jedis){
       System.out.println( jedis.hgetAll(getMeetingIdKeyPrefix()+""));

    }

    public static void main(String[] args){

        Jedis jedis = null;
        try{
            jedis = new Jedis("10.170.191.64", 6379);
            jedis.auth("wow!nemo");
            meetingInfo(jedis);

        }finally {
            jedis.close();
        }

    }
}

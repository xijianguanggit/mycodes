package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by wangdecheng on 18/01/2018.
 */
public class Encode {

    public static void main(String[] args){
        try {
            System.out.println(URLEncoder.encode("192.123.123.110","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

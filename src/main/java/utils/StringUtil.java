package utils;

/**
 * Created by wangdecheng on 2018/1/4.
 */
public class StringUtil {

    public static void main(String[] args){
        String s = "360P/1";
        String[] ss = s.split("/");
        System.out.print(ss[0]);
        int i = Integer.parseInt(ss[1]);
        System.out.println(i);
    }
}

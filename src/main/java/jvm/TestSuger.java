package jvm;

/**
 * Created by wangdecheng on 22/01/2018.
 */
public class TestSuger {

    public static void main(String[] args){
        Integer a=1;
        Integer b=2;
        Integer c=3;
        Integer d=3;
        Integer e=321;
        Integer f=321;
        Long g=3L;
        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(e == (f+0));
        System.out.println(c == (a+b));
        System.out.println(c .equals (a +b));
        System.out.println(g == (a+b));
        System.out.println(g.equals(a+b));
    }
}

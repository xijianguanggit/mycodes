/**
 * Created by wangdecheng on 14/04/2018.
 */
public class TestVolitile {
    int a=1;
    static  int b=2;
     int sum(int c){
        return a+b+c;
    }
    public static void main(String[] args){
         new TestVolitile().sum(3);
    }
}

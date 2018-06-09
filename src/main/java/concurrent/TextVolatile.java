package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangdecheng on 22/05/2018.
 */
public class TextVolatile {
    int a = 0;

    private static  TextVolatile textVolatile;

    private TextVolatile(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        a = 10;
    }
    public static TextVolatile getInstance(){
        if(textVolatile == null){
            synchronized (TextVolatile.class){
                if(textVolatile == null){
                    textVolatile = new TextVolatile();
                }
            }
        }
        return textVolatile;
    }

    public  void getA(){
        System.out.println(a);
    }

    public static void main(String[] args){
        new Thread(()->{
           TextVolatile.getInstance().getA();
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            TextVolatile.getInstance().getA();
        }).start();
    }
}

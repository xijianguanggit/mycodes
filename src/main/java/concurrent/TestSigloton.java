package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangdecheng on 14/04/2018.
 */
public class TestSigloton {
    private  static class TestSiglotonInner {
        static TestSigloton sigloton = new TestSigloton();
    }

    private TestSigloton(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("++++init++++");
    }

    public static TestSigloton getInstance(){

        return TestSiglotonInner.sigloton;
    }
    public static void main(String[] args){
        new Thread(()->{
            TestSigloton.getInstance();
        }).start();
        new Thread(()->{
            TestSigloton.getInstance();
        }).start();
    }


}

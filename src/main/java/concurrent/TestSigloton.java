package concurrent;

/**
 * Created by wangdecheng on 14/04/2018.
 */
public class TestSigloton {
    private  static TestSigloton instance = null;

    public static TestSigloton getInstance(){
        if(instance==null){
            synchronized (TestSigloton.class){
                if(instance == null) {
                    instance = new TestSigloton();
                }
            }
        }
        return instance;
    }


}

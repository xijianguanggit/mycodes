/**
 * Created by wangdecheng on 27/05/2018.
 */
public class Testsinga {

    private static class Innter{
         static Testsinga innterInstance  = new Testsinga();;

    }

    public static Testsinga getInstance() {

        return Innter.innterInstance;
    }

    private Testsinga() {
    }
}

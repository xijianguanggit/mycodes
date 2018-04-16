package concurrent.highjava.chapter5;

/**
 * Created by 13 on 2017/5/base.
 */
public class StaticSingleton {
    private StaticSingleton() {
        System.out.println("StaticSingle is create");
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }
}

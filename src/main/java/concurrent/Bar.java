package concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangdecheng on 14/04/2018.
 */
public class Bar {
    static volatile Object a;
    synchronized void sum(){
        a = new Object();
    }
    public static void main(String[] args){
        new Bar().sum();
        ReentrantLock lock =  new ReentrantLock();
        lock.lock();
    }



}

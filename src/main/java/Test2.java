import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangdecheng on 27/05/2018.
 */
public class Test2 {
    private static Test2 ourInstance = new Test2();

    private ReentrantLock lock = new ReentrantLock();
    public static Test2 getInstance() {

        //lock.lock();
        return ourInstance;
       // lock.unlock();
    }

    private Test2() {
    }

    public static void main(String[] args){
        for(int i=0;i<99;i++){
            String index=String.valueOf(i);
            if(i<10){
                index = "0"+i;
            }
            System.out.println("INSERT INTO `libra_device_access_permission_resource` (`id`, `device_sn`, `source_type`, `begin_time`, `expire_time`)\n" +
                    "VALUES " +
                    " ('"+index+"', '3456"+index+"', 'PRESENT', 1481798120899, 9223372036854775807);");
        }
    }
}

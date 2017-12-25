package log;

/**
 * Created by wangdecheng on 2017/12/19.
 */
import org.apache.log4j.Logger;

/**
 * Created by zhengcanrui on 16/7/27.
 */
public class LoggerUtil {

    private static final Logger file = Logger.getLogger("com.file");
    private static final Logger register = Logger.getLogger("com.second.register");
    private static final Logger login = Logger.getLogger("login");
    private static final Logger goldcoin = Logger.getLogger("goldcoin");
    private static final Logger recharge = Logger.getLogger("recharge");
    private static final Logger jjj = Logger.getLogger(LoggerUtil.class.getName());
    private static final Logger FILE = Logger.getLogger("");
    private static org.apache.log4j.Logger log = Logger.getLogger(LoggerUtil.class);
    public static void logInfo(String log) {
        file.info(log);
    }

    public static void registerInfo() {
        register.info("[register] ddd " );
    }


    public static void loginInfo() {
        login.info("[login] 222" );
    }

    public static void main(String[] args) {
        /*logInfo("11");
        registerInfo();
        loginInfo();*/
        /*login.info("[login] 大大大大大大大大");
        register.debug("2222");
        register.info("[register] 人人人人人人人人人人");*/

        //jjj.info("test");
        //log.info(222);
        file.info("334343");
        register.info("2222");
    }
}

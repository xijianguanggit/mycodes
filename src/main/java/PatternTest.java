import java.util.regex.Pattern;

/**
 * Created by wangdecheng on 21/03/2018.
 */
public class PatternTest {

    public static void main(String[] args){
        Pattern phonePattern = Pattern.compile("1\\d{10}$");
        if(phonePattern.matcher("1234567890").matches()){
            System.out.println("======");
        }
    }
}

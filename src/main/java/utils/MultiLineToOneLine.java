package utils;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by wangdecheng on 2017/12/20.
 */
public class MultiLineToOneLine {

    public static void main(String[] args){
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("data/MultiLineToOneLine"));
          String line;
           while( (line=fileReader.readLine())!=null) {
               System.out.print("'"+line.trim().replace("'","")+"',");
           }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

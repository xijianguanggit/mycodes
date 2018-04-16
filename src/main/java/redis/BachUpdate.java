package redis;

import nio.NioWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by wangdecheng on 20/03/2018.
 */
public class BachUpdate {
    static void writeTo() throws IOException{
        BufferedReader reader = null;
        NioWriter writer = null;
        try {
            int count = 0;
            reader = new BufferedReader(new FileReader("data/command_update.txt"));
            StringBuilder sb = new StringBuilder();
            String line;
            writer  = new NioWriter("data/command_out.txt");

            while ((line = reader.readLine()) != null) {
                count++;
                writer.putln(line);
            }
            System.out.println(count);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reader.close();
            writer.close();
        }
    }

    static void check()throws IOException {
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("data/command_out.txt"));
            int length = "sadd CHARGE:NEMO:BEFORE2017 2D15220105CBA44F".length();
            String line;

            System.out.println(length);
            while ((line = reader.readLine()) != null) {
                if(line.length()!=length){
                    System.out.println(line);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            reader.close();
        }
    }
    public static void main(String[] args) throws IOException {

       // writeTo();
        check();
    }
}

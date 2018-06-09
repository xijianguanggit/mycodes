/**
 * Created by wangdecheng on 27/02/2018.
 */
public class Simpletest {

 private static  Simpletest simpletest;
 static int a;
 public static void coutnt(){
    a++;
    System.out.print(a);
 }

 public static void main(String[] args){
   new Thread(()->coutnt()).start();
  new Thread(()->coutnt()).start();
 }
}

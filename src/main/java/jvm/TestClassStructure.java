package jvm;

/**
 * Created by wangdecheng on 11/01/2018.
 */
public class TestClassStructure {

    public int inc(){
        int x;
        try{
            x=100;
            return x;
        }catch (Exception e){
            x=200;
            return x;
        }finally {
            x=300;
        }
    }
    public static void main(String[] args){
       System.out.println(new  TestClassStructure().inc());
    }
}

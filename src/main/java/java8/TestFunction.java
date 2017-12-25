package java8;

import java.util.function.Consumer;

public class TestFunction {

    public static void main(String[] args){
       // DemoFunction demoFunction = TestFunction::test;
       // System.out.println(demoFunction.test("1111"));
        Runnable runnable = ()->System.out.print("runnable");
        Thread thread = new Thread(runnable);
        //Function<Integer,String> substring = runnable::run;

        Consumer<String> consumer = TestFunction::test;

        //consumer.accept("111dd");
      //  Supplier<Void> supplier = TestFunction::test;
        //System.out.println(supplier.get());
    }

    static void test(String s){

    }
}

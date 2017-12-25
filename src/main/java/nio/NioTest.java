package nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * Created by wangdecheng on 2017/12/15.
 */
public class NioTest {

    public static void main(String[] args){

    }

    private static void testBuffer(){
        IntBuffer intBuffer = IntBuffer.allocate(8);
        print(intBuffer);
        intBuffer.put(new int[]{1,3,4,5});
        print(intBuffer);

        intBuffer.flip();

        System.out.println( intBuffer.get());
        System.out.println( intBuffer.get());
        print(intBuffer);
        intBuffer.clear();
        print(intBuffer);

        intBuffer.put(new int[]{6,7,9});
        print(intBuffer);
        intBuffer.rewind();
        print(intBuffer);
        System.out.println( intBuffer.get(2));
    }
    private static void print(Buffer buffer){
        System.out.println("position:"+buffer.position()+",limit:"+buffer.limit()+",capacity:"+buffer.capacity());
    }
}

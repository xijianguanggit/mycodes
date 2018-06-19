package algorithm.ch1;

import java.util.Arrays;

/**
 * Created by wangdecheng on 19/06/2018.
 */
public class BinarySearch {

    public static  void   binarySearch(int target,int[] array){

        if(array.length ==0){
            System.out.println("no target");
            return;
        }
        int middleIndex = array.length/2;
        int middle = array[middleIndex];
        if(target == middle){
            System.out.println(" get result:"+middleIndex);
        }else if(target > middle){
            binarySearch(target, Arrays.copyOfRange(array,middleIndex+1,array.length));
        }else {
            binarySearch(target, Arrays.copyOfRange(array,0,middleIndex));
        }
    }

    public  static void main(String[] args){
        int[] array = {1,3,5,7,9};
        binarySearch(1,array);
        binarySearch(5,array);
        binarySearch(9,array);
        binarySearch(0,array);
        binarySearch(6,array);
        binarySearch(10,array);
    }
}

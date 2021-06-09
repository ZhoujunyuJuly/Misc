package com.example.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujunyu on 2021-06-06.
 */
public class Simple {
    public static void main(String[] args) {
        System.out.println(reverseAnswer(1534236469));
    }

    /**
     * 6.Z 字形排列
     */
    public static String convert(String s,int numRows){
        List<StringBuilder> builderArray = new ArrayList<>();
        char[] c = s.toCharArray();
        for (int i = 0 ;i < numRows;i++){
            builderArray.add(new StringBuilder());
        }

        boolean isDown = false;
        int index = 0;
        for(char a : c){
          builderArray.get(index).append(a);
          if( index == numRows - 1 || index == 0 ){
              isDown = !isDown;
          }
          index += isDown ? 1 : -1;
        }

        StringBuilder sb = new StringBuilder();
        for( int i = 0;i < numRows;i++){
            sb.append(builderArray.get(i));
        }

        return sb.toString();
    }

    /**
     * 7.反转,最复杂也是最笨的解法
     */
    public static int reverseAnswer(int x){
        int result = 0;
        int next = 0;
        while ( x != 0){
            next = result + (x / 10)%10;
            if( next < Integer.MIN_VALUE / 10 || next > Integer.MAX_VALUE / 10){
                return 0;
            }
            result = result * 10 + x % 10;
            x /= 10;
        }
        return result;
    }

    /**
     * 7.反转，高级解法
     */
    public static int reverse(int x){

        int result = 0;
        int rec = 0;
        while (x != 0){
            result = result * 10 + x % 10;
            if( result / 10 != rec){
                return 0;
            }
            rec = result;
            x = x /10;
        }
        return result;
    }


}

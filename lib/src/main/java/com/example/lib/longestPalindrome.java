package com.example.lib;

import java.util.ArrayList;
import java.util.List;

public class longestPalindrome {

    public static void main(String[] args) {
        System.out.println(dynamic("cccc"));
    }

    /**
     * 方法一：暴力解法-------------------------------------------------------
     * 时间复杂度O(n^3)，空间复杂度O(1)
     */
    public static String violent(String s) {
        char[] c = s.toCharArray();
        int maxCount = 0;
        int[] location = new int[]{0,1};
        if( s.length() < 2 ){
            return s;
        }
        for( int i = 0 ; i< s.length() - 1 ; i++){
            for( int j = i + 1;j < s.length();j++){
                if(maxCount < j - i + 1 && isRepeatString(c,i,j)){
                    maxCount = j - i + 1;
                    location[0] = i;
                    location[1] = j + 1;

                }
            }
        }
        return s.substring(location[0],location[1]);
    }

    public static boolean isRepeatString(char[] c,int start,int end){
        while (start < end ){
            if(c[start] == c[end]){
                start++;
                end--;
            }else {
                return false;
            }
        }
        return true;
    }



    /**
     * 方法二：中心对称算法-------------------------------------------------------
     * 【【这是最快的，时间超过94%，内存超过99%】】
     * 时间复杂度O(n^2),空间复杂度O(1)
     */
    public static String centerSymmetry(String s){
        if( s.length() < 2 ){
            return s;
        }
        char[] c = s.toCharArray();
        int maxCount = 1;
        int currentCount = 0;
        int startIndex = 0;
        for( int i = 0;i < s.length() ; i ++ ){
            currentCount = Math.max(palindrome(c,i,i+1),palindrome(c,i,i));
            if(currentCount > maxCount){
                maxCount = currentCount;
                startIndex = maxCount % 2 == 0 ? i - maxCount/2 + 1: i - (maxCount + 1) / 2 + 1;
            }
        }
        return s.substring(startIndex , startIndex + maxCount);
    }

    public static int palindrome(char[] c , int left,int right){
        int count = 1;
        while(left > -1 && right < c.length){
            if( c[left] != c[right]){
                return count;
            }else {
                count = right - left + 1;
                left--;
                right++;
            }
        }
        return count;
    }



    /**
     * 方法三：马拉车算法-------------------------------------------------------
     * 时间复杂度O(n^2),空间复杂度O(1)
     */
    public static String manacher(String s){
        char[] c = s.toCharArray();
        int maxCount = 1;
        StringBuilder sb = new StringBuilder("#");
        for( int i = 0; i < s.length();i++){
            sb.append(c[i]);
            sb.append("#");
        }
        int[] distance = new int[sb.length()];
        int right = 0;
        int maxRight = 0;
        int center = 0;
        for(int i = 0;i < sb.length();i++){
            if( right < i){
                distance[i] = violentSearch(sb.toString().toCharArray(),i,i);
            }else {
                distance[i] = center > 0 ? distance[2 * center - i] : 1 ;
                if( i + (distance[i] - 1) / 2 > maxRight){
                    distance[i] = (maxRight - i)*2;
                }else {
                    distance[i] = violentSearch(sb.toString().toCharArray(),i - (distance[i] - 1) / 2,i + (distance[i] - 1) / 2);
                }
            }
            right = i + (distance[i] - 1)/2;
            if( right > maxRight ){
                maxRight = right;
                center = i;
            }
        }

        maxCount = 0;
        right = 0;
        for( int i = 0; i < distance.length;i++){
            if(distance[i] > maxCount){
                maxCount = distance[i];
                right = i;
            }
        }

        return sb.substring( right - (maxCount - 1)/2 , right + (maxCount - 1)/2).replace("#","");


    }

    public static int violentSearch(char[] c,int left,int right){
        int count = 1;
        while ( left > -1 && right < c.length){
            if( c[left] == c[right]){
                count = right - left + 1;
                left--;
                right++;
            }else {
                return count;
            }
        }
        return count;
    }



    /**
     * 方法四：动态规划算法-------------------------------------------------------
     * 时间复杂度O(n^2),空间复杂度O(1)
     */
    public static String dynamic(String s){
        char[] c = s.toCharArray();
        boolean[][] distance = new boolean[s.length()][s.length()];
        int maxCount = 1;
        int[] result = new int[2];

        for(int i = 1;i < s.length();i++){
            for(int j = 0;j < i;j++){
                if(c[i] == c[j]){
                    distance[j][i] = i - j < 3 || distance[j+1][i-1];
                    if(maxCount < i - j + 1 && distance[j][i]){
                        result[0] = j;
                        result[1] = i;
                        maxCount = i - j + 1;
                    }
                }else {
                    distance[i][j] = false;
                }
            }
        }

        return s.substring(result[0],result[1]+1);

    }


    /**
     * 方法五：答案马拉车-------------------------------------------------------
     * 时间复杂度O(n^2),空间复杂度O(1)
     */
    public static String standardManacher(String s) {
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < s.length(); ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        t.append('#');
        s = t.toString();

        List<Integer> arm_len = new ArrayList<Integer>();
        int right = -1, j = -1;
        for (int i = 0; i < s.length(); ++i) {
            int cur_arm_len;
            if (right >= i) {
                int i_sym = j * 2 - i;
                int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                cur_arm_len = expand(s, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(s, i, i);
            }
            arm_len.add(cur_arm_len);
            if (i + cur_arm_len > right) {
                j = i;
                right = i + cur_arm_len;
            }
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len;
                end = i + cur_arm_len;
            }
        }

        StringBuffer ans = new StringBuffer();
        for (int i = start; i <= end; ++i) {
            if (s.charAt(i) != '#') {
                ans.append(s.charAt(i));
            }
        }
        return ans.toString();
    }

    public static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }


}

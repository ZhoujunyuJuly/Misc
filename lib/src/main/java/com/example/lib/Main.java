package com.example.lib;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by zhoujunyu on 2021-06-17.
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int code = sc.nextInt();
        int num[] = new int[len];
        for (int i = 0; i < num.length; i++) {
            num[i] = sc.nextInt();
        }

        List<Person> result = result2(num);
        for (int i = 0; i < num.length; i++) {
            System.out.print(result.get(i).index);
            System.out.print(" ");
        }

        System.out.println("");
        System.out.print(result.get(code+1).index);

    }

    public static List<Person> result2(int[] num) {
        List<Person> personList = new ArrayList<>();
        for(int i = 0;i < num.length;i++ ){
            Person p = new Person(i+1,num[i]);
            personList.add(p);
        }

        MyComparator comparator = new MyComparator();
        Collections.sort(personList,comparator);

        return personList;

    }

    static class Person {
        int index;
        int priority;

        public Person(int index, int priority) {
            this.index = index;
            this.priority = priority;
        }
    }

    static class MyComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            // 如果o1小于o2，我们就返回正值，如果n1大于n2我们就返回负值，
            if (o1.priority < o2.priority) {
                return 1;
            } else if (o1.priority > o2.priority) {
                return -1;
            } else{
                if (o1.index < o2.index) {
                    return -1;
                } else if(o1.index > o2.index ){
                    return 1;
                }else {
                    return 0;
                }
            }
        }

    }

    public static void answer(){
        Scanner scanner = new Scanner(System.in);
        int n,a;
        String[] s = scanner.nextLine().split(" ");
        n = Integer.valueOf(s[0]);
        a = Integer.valueOf(s[1]);
        String[] arr = scanner.nextLine().split(" ");
        int num[] = new int[n];
        for(int i=0;i<arr.length;i++){
            num[i] = Integer.valueOf(arr[i]);
        }
        int flag[] = new int[n];
        for(int j=0;j<n;j++){
            int max = 0,min = 0;
            for(int i=0;i<n;i++){
                if(num[i] >num[max]){
                    max = i;
                }
                if(num[i]<num[min]){
                    min = i;
                }
            }
            flag[j] = max;
            num[max] = num[min] -n;
            System.out.print((flag[j]+1)+" ");
        }
    }
}
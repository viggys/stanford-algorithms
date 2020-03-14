package com.viggys.algorithms.part2.week1;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        System.out.println("Bubble Sort");
        int[] array = new int[] {10,3,5,1,6,9};
        System.out.println("Array = " + Arrays.toString(array));
        sort(array, 3);

    }

    public static void sort(int[] array, int iterations) {
        for(int i = array.length; i > (array.length - iterations); i--) {
            for(int j = 0; j < (i - 1); j++) {
                if(array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            System.out.println("Rank " + (array.length - i + 1) +" Count = " + array[i - 1]);
        }
    }
}

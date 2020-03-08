package com.viggys.algorithms.part1.week3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		String path = "src/main/resources/QuickSort.txt";
		int length = 10000;
		int[] inputArr = readFromFile(path, length);
//		int[] inputArr = new int[] {9,8,7,6,5,4,3,2,1};
		
		BigInteger comparisons = quickSort(inputArr, 0, inputArr.length);
		System.out.println("Sorted array = " + Arrays.toString(inputArr));
		System.out.println("Comparisons = " + comparisons);
	}
	
	public static int[] readFromFile(String path, int length) throws NumberFormatException, IOException {
		File inputFile = new File(path);
		int[] inputArr = new int[length];
		
		System.out.println(inputFile.getAbsolutePath());
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		for(int i = 0; i < inputArr.length; i++) {
			inputArr[i] = Integer.parseInt(reader.readLine());
			System.out.println(i + "-" + inputArr[i]);
		}
		return inputArr;
	}
	
	public static BigInteger quickSort(int[] array, int start, int end) {
		BigInteger comparisons = BigInteger.ZERO;
		int length = end - start;
		if(length <= 1) {
			return comparisons;
		}
		else {
			comparisons = new BigInteger(Integer.toString(length - 1));
			int pivot = choosePivotIndex(array, start, end);
			System.out.println("Pivot is array[" + pivot + "]=" + array[pivot]);
			swap(array, start, pivot);
			
			int i = start + 1;
			int j = start + 1;
			while(j < end) {
				if(array[j] < array[start]) {
					swap(array, i, j);
					i++;
				}
				j++;
			}
			swap(array, start, i - 1);
//			System.out.println("Current array = " + Arrays.toString(array));
			
			
			System.out.println("Partition sub-array start=" + (start) + " end=" + (i - 1));
			comparisons = comparisons.add(quickSort(array, start, i - 1));
			
//			System.out.println("Current array = " + Arrays.toString(array));
			System.out.println("Partition sub-array start=" + (i) + " end=" + j);
			comparisons = comparisons.add(quickSort(array, i , j));
		}
		System.out.println("Comparison count = " + comparisons);
		return comparisons;
	}
	
	private static void swap(int[] array, int i, int j) {
		int iValue = array[i];
		int jValue = array[j];
//		System.out.println("Current array = " + Arrays.toString(array));
//		System.out.println("Swapping array[" + i + "]=" + iValue + " with array[" + j + "]=" + jValue);
		array[i] = jValue;
		array[j] = iValue;
	}

	private static int choosePivotIndex(int[] array, int start, int end) {
//		int pivot = chooseFirst(array, start, end);
//		int pivot = chooseLast(array, start, end);
		int pivot = chooseMedian(array, start, end);
		
		return pivot;
	}
	
	private static int chooseFirst(int[] array, int start, int end) {
		return start;
	}
	
	private static int chooseLast(int[] array, int start, int end) {
		return end - 1;
	}
	
	private static int chooseMedian(int[] array, int start, int end) {
		int firstValue = array[start];
		int lastValue = array[end - 1];
		int mid = (int) (Math.ceil((double)(end - start) / 2) - 1) + start;
		int midValue = array[mid];
		
		System.out.println("First value = " + firstValue);
		System.out.println("Mid value = " + midValue);
		System.out.println("Last value = " + lastValue);
		
		int min = Integer.min(midValue, Integer.min(firstValue, lastValue));
		int max = Integer.max(midValue, Integer.max(firstValue, lastValue));
		
		if(lastValue != min && lastValue != max) {
			System.out.println("Median value = " + lastValue);
			return end - 1;
		}
		else if(midValue != min && midValue != max) {
			System.out.println("Median value = " + midValue);
			return mid;
		}
		else {
			System.out.println("Median value = " + firstValue);
			return start;
		}
	}
}

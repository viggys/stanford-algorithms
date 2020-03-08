package com.viggys.algorithms.part1.week2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class ArrayInversionsCount {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		String path = "src/main/resources/IntegerArray.txt";
		
		int[] inputArr = readFromFile(path, 100000);
//		int[] inputArr = new int[] {1,3,5,2,4,6};
		
		MergeSortArray sorted = mergeSort(new MergeSortArray(inputArr, BigInteger.ZERO));
		System.out.println(Arrays.toString(sorted.array));
		System.out.println("Inversions     = " + sorted.count);
		System.out.println("Max Inversions = " + BigInteger.valueOf(inputArr.length).multiply(BigInteger.valueOf(inputArr.length - 1)).divide(BigInteger.valueOf(2)));
	}
	
	public static int[] readFromFile(String path, int length) throws NumberFormatException, IOException {
		File inputFile = new File(path);
		int[] inputArr = new int[length];
		
		System.out.println(inputFile.getAbsolutePath());
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		for(int i = 0; i < inputArr.length; i++) {
			inputArr[i] = Integer.parseInt(reader.readLine());
//			System.out.println(i + "-" + inputArr[i]);
		}
		return inputArr;
	}
	
	public static MergeSortArray mergeSort(MergeSortArray input) {
		int[] array = input.array;
		int length = array.length;
		int mid = length / 2;
		
		System.out.println("Length = " + length + ", Mid = " + mid);
		
		if(length == 1) {
			return input;
		}
		else {
			int[] leftArray = Arrays.copyOfRange(array, 0, mid);
			int[] rightArray = Arrays.copyOfRange(array, mid, length);
			MergeSortArray leftSorted = mergeSort(new MergeSortArray(leftArray, BigInteger.ZERO));
			MergeSortArray rightSorted = mergeSort(new MergeSortArray(rightArray, BigInteger.ZERO));
			
			return mergeAndCount(leftSorted, rightSorted);
		}
	}
	
	public static MergeSortArray mergeAndCount(MergeSortArray left, MergeSortArray right) {
		int[] leftArray = left.array;
		int[] rightArray = right.array;
		int mergeLength = leftArray.length + rightArray.length;
		int[] mergeArray = new int[mergeLength];
		int l = 0;
		int r = 0;
		BigInteger count = BigInteger.ZERO;
		
		for(int i = 0; i < mergeLength; i++) {
			if(l == leftArray.length){
				mergeArray[i] = rightArray[r];
				r++;
			}
			else if(r == rightArray.length) {
				mergeArray[i] = leftArray[l];
				l++;
			}
			else if(leftArray[l] < rightArray[r]) {
				mergeArray[i] = leftArray[l];
				l++;
			}
			else {
				mergeArray[i] = rightArray[r];
				count = count.add(BigInteger.valueOf(leftArray.length - l));
				r++;
			}
		}
		BigInteger totalInversions = count.add(left.count).add(right.count);
		System.out.println("Merged with inversion count = " + totalInversions);
		return new MergeSortArray(mergeArray, totalInversions);
	}
	
}

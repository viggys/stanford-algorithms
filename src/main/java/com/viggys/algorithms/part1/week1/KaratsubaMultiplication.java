package com.viggys.algorithms.part1.week1;

import java.math.BigInteger;

public class KaratsubaMultiplication {

	public static void main(String[] args) {

		/*
		 * String inputA =
		 * "3141592653589793238462643383279502884197169399375105820974944592"; String
		 * inputB = "2718281828459045235360287471352662497757247093699959574966967627";
		 */

		
		String inputA = "99";
		String inputB = "99";
		 
		add(inputA, inputB);
//		System.out.println(multiply(inputA, inputB) + " -- Actual result");
//		verify(inputA, inputB);

	}

	public static String multiply(String num1, String num2) {
		String result = null;
		int reqNumLength = getRequiredNumberLength(num1, num2);
		if (reqNumLength == 1) {
			int value1 = Integer.parseInt(num1);
			int value2 = Integer.parseInt(num2);
			Integer value = value1 * value2;
			result = value.toString();
		} else {
			String prefixedNum1 = prefixZeros(num1, reqNumLength);
			String prefixedNum2 = prefixZeros(num2, reqNumLength);
			int semiLength = reqNumLength / 2;

			String a = prefixedNum1.substring(0, semiLength);
			String b = prefixedNum1.substring(semiLength);
			String c = prefixedNum2.substring(0, semiLength);
			String d = prefixedNum2.substring(semiLength);

			String ac = multiply(a, c);
			String bd = multiply(b, d);
			String a_b = add(a, b);
			String c_d = add(c, d);
			String a_b_into_c_d = multiply(a_b, c_d);
			String ad_bc = add(a_b_into_c_d, "-" + ac, "-" + bd);

			String part1 = suffixZeros(ac, reqNumLength);
			String part2 = suffixZeros(ad_bc, semiLength);
			String part3 = bd;

			result = add(part1, part2, part3);

		}
		System.out.println(num1 + " * " + num2 + " = " + result);
		return result;
	}

	public static String add(String... num) {
		BigInteger result = BigInteger.ZERO;
		System.out.print(result);
		for (String valueString : num) {
			BigInteger value = new BigInteger(valueString);
			System.out.print(" + " + valueString);
			result = result.add(value);
		}
		System.out.println(" = " + result);

		return result.toString();
	}
	
	public static String add(String num1, String num2) {
		String result = null;
		int digits = Integer.max(num1.length(), num2.length()) + 1;
		char value1[] = prefixZeros(num1, digits).toCharArray();
		char value2[] = prefixZeros(num2, digits).toCharArray();
		char carry[] = new char[digits];
		char value[] = new char[digits];
		
		carry[digits - 1] = '0';
		for(int i = digits - 1; i >= 0 ; i--) {
			int a = Integer.parseInt(new String(value1, i, 1));
			int b = Integer.parseInt(new String(value2, i, 1));
			int c = Integer.parseInt(new String(carry, i, 1));
			Integer sum = a + b + c;
			String sumValue = sum.toString();
			int sumValueLength = sumValue.length();
			
			value[i] = sumValue.charAt(sumValueLength - 1);
			if(i > 0)
				carry[i - 1] = sumValueLength > 1 ? sumValue.charAt(0) : '0';
		}
		result = new String(value);
		if(result.charAt(0) == '0')
			result = result.substring(1);
		System.out.println(num1 + " + " + num2 + " = " + result);
		return result;
	}

	public static int getRequiredNumberLength(String num1, String num2) {
		int maxLength = Integer.max(num1.length(), num2.length());
		int requiredLength = maxLength;
		if (maxLength > 1 && maxLength % 2 != 0) {
			requiredLength++;
		}
		return requiredLength;
	}

	public static String prefixZeros(String num, int length) {
		int zerosToAppend = length - num.length();
		StringBuilder appender = new StringBuilder();
		for (int i = 0; i < zerosToAppend; i++) {
			appender.append("0");
		}
		appender.append(num);
		String appended = appender.toString();
		System.out.println(num + " ---> " + appended);
		return appended;
	}

	public static String suffixZeros(String num, int noOfZeros) {
		StringBuilder appender = new StringBuilder();
		appender.append(num);
		for (int i = 0; i < noOfZeros; i++) {
			appender.append("0");
		}
		String appended = appender.toString();
		System.out.println(num + " ---> " + appended);
		return appended;
	}

	private static void verify(String num1, String num2) {
		BigInteger value1 = new BigInteger(num1);
		BigInteger value2 = new BigInteger(num2);
		BigInteger result = value1.multiply(value2);

		System.out.println(result.toString() + " -- Expected result");
	}

}

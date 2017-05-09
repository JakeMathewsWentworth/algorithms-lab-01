package edu.wit.cs.comp3370;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/* Sorts integers from command line using various algorithms 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 1
 * 
 */

public class LAB1 {

	// TODO: document this method
	public static int[] countingSort(int[] a) {
		int[] counts = new int[MAX_INPUT];
		for (int i = 0; i < a.length; i++) {
			counts[a[i]]++;
		}
		int[] result = new int[a.length];
		int currentIndex = 0;
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				for (int x = 0; x < counts[i]; x++) {
					result[currentIndex + x] = i;
				}
				currentIndex += counts[i];
			}
		}
		return result;
	}

	// TODO: document this method
	public static int[] radixSort(int[] a) {
		final int RADIX = 10;
		int position = 1;
		int maxSize = 0;
		for(Integer value : a) {
			int length = String.valueOf(value).length();
			maxSize = Math.max(length, maxSize);
		}
		return radixSortHelper(RADIX, a, position, maxSize);
	}
	
	private static int[] radixSortHelper(int radix, int[] a, int position, int maxSize) {
		List<Integer>[] buckets = new ArrayList[radix];
		for(int i = 0; i < buckets.length; i++) {
			buckets[i] = new ArrayList<Integer>();
		}
		for(Integer i = 0; i < a.length; i++) {
			int currentDigit = (int) (a[i] % (Math.pow(radix, position)));
			currentDigit /= Math.pow(10, position-1);
			buckets[currentDigit].add(i);
		}
		int[] result = new int[a.length];
		int aIndex = 0;
		for(int i = 0; i < buckets.length; i++) {
			for(int j = 0; j < buckets[i].size(); j++) {
				result[aIndex++] = a[buckets[i].get(j)];
			}
		}
		if(position <= maxSize) {
			return radixSortHelper(radix, result, position + 1, maxSize);
		}
		return result;
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public final static int MAX_INPUT = 524287;
	public final static int MIN_INPUT = 0;

	// example sorting algorithm
	public static int[] insertionSort(int[] a) {

		for (int i = 1; i < a.length; i++) {
			int tmp = a[i];
			int j;
			for (j = i - 1; j >= 0 && tmp < a[j]; j--)
				a[j + 1] = a[j];
			a[j + 1] = tmp;
		}

		return a;
	}

	/*
	 * Implementation note: The sorting algorithm is a Dual-Pivot Quicksort by
	 * Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch. This algorithm
	 * offers O(n log(n)) performance on many data sets that cause other
	 * quicksorts to degrade to quadratic performance, and is typically faster
	 * than traditional (one-pivot) Quicksort implementations.
	 */
	public static int[] systemSort(int[] a) {
		Arrays.sort(a);
		return a;
	}

	// read ints from a Scanner
	// returns an array of the ints read
	private static int[] getInts(Scanner s) {
		ArrayList<Integer> a = new ArrayList<Integer>();

		while (s.hasNextInt()) {
			int i = s.nextInt();
			if ((i <= MAX_INPUT) && (i >= MIN_INPUT))
				a.add(i);
		}

		return toIntArray(a);
	}

	// copies an ArrayList of Integer to an array of int
	private static int[] toIntArray(ArrayList<Integer> a) {
		int[] ret = new int[a.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = a.get(i);
		return ret;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.printf("Enter the sorting algorithm to use ([c]ounting, [r]adix, [i]nsertion, or [s]ystem): ");
		char algo = s.next().charAt(0);

		System.out.printf("Enter the integers that you would like sorted, followed by a non-integer character: ");
		int[] unsorted_values = getInts(s);
		int[] sorted_values = {};

		s.close();

		switch (algo) {
		case 'c':
			sorted_values = countingSort(unsorted_values);
			break;
		case 'r':
			sorted_values = radixSort(unsorted_values);
			break;
		case 'i':
			sorted_values = insertionSort(unsorted_values);
			break;
		case 's':
			sorted_values = systemSort(unsorted_values);
			break;
		default:
			System.out.println("Invalid sorting algorithm");
			System.exit(0);
			break;
		}

		System.out.println(Arrays.toString(sorted_values));
	}

}

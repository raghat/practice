package com.dw;

import java.util.Arrays;

public class SortArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] origArray  = {1, 2, 3, 6, 5, 4 , 9, 8};
		checkIfArrayCanBeSorted(origArray);
	}
	
	public static boolean checkIfArrayCanBeSorted(int[] array){

		if(array.length == 0){
			System.out.println("No elements passed");
			return false;
		}

		int[] originalArray = (int[])Arrays.copyOf(array, array.length);
		int[] sortedArray = (int[])Arrays.copyOf(originalArray, originalArray.length);
			
		Arrays.sort(sortedArray);
		System.out.println(Arrays.toString(sortedArray));
		
		if(Arrays.equals(sortedArray, originalArray)){
			System.out.println("Array is already sorted. No need to sort.");
			return true;
		}
		
		for(int i=0;i<array.length;i++){
			System.out.println("First iteration");
			for(int j=0;j<array.length;j++){
				//swap the elements and check if its equal to sorted array.
				int temp = originalArray[i];
				originalArray[i] = originalArray[j];
				originalArray[j] = temp;
				
				if(Arrays.equals(originalArray, sortedArray)){
					System.out.println("Array can be sorted just by one swap");
					return true;
				}else{
					//Keep the original array as it was before swapping for next iteration.
					temp = originalArray[i];
					originalArray[i] = originalArray[j];
					originalArray[j] = temp;
					//loop further. do not exit.
				}
			}
		}
		
		System.out.println("Array cannot be sorted.");
		return false;
	}
}
package com.dw;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class BillCalculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//PhoneNumber HH:MM:SS
		String[] data = {"7594957403 01:02:01", "7594957401 00:06:01", "7594957403 00:02:01",  
				"7594957401 00:02:01", "7594957401 00:02:01", "7594957408 00:02:01"};
				
		if(data.length == 0)
			return;
				
				
		List<String> phoneList = Arrays.asList(data);
		// 0 -3 minutes 3p per min
		// 3 -5 minutes 5p per min
		// more than 5minutes 10p per min
		// maximum duration. that cost is deducted from total cost.
		computeBill(phoneList);
	}
	
	
	public static int computeBill(List<String> phoneList){
		
		// map to maintain total duration for each phone number.
		HashMap<String, Integer> durationMap = new HashMap<String, Integer>();
		// map to maintain total cost for each phone number.
		HashMap<String, Integer> costMap = new HashMap<String, Integer>();
		int totalPrice = 0;
		
		for(String phoneRow: phoneList){
			String[] row = phoneRow.split("\\s+");
			System.out.println(Arrays.toString(row));
			
			String[] duration = row[1].split(":");

			// populate duration map
			int durationInSeconds = Integer.valueOf(duration[0])*60*60 + 
					Integer.valueOf(duration[1])*60 + Integer.valueOf(duration[2]);
			if(!durationMap.containsKey(row[0]))
				durationMap.put(row[0], durationInSeconds);
			else{
				int addedTime = durationMap.get(row[0]) + durationInSeconds;
				durationMap.replace(row[0], addedTime);
			}

			// populate price map
			int computedPrice = computePrice(durationInSeconds);
			if(!costMap.containsKey(row[0])){
				costMap.put(row[0], computedPrice);
				totalPrice+=computedPrice;
			}else{
				int addedPrice = costMap.get(row[0]) + computedPrice;
				costMap.replace(row[0], addedPrice);
				totalPrice+=addedPrice;
			}
		}
		
		//find the phone number which spoke for maximum time
		String phoneKey = "";
		int maxDuration = Collections.max(durationMap.values());
        for (Entry<String, Integer> entry : durationMap.entrySet()){ 
            if (entry.getValue() == maxDuration) {
            	phoneKey = entry.getKey();
            }
        }
        
        // deduct the total cost associated with this phone number from the total price.
        totalPrice = totalPrice - costMap.get(phoneKey);
		System.out.println(costMap.size());
		System.out.println(durationMap.size());

        System.out.println("total price is "+totalPrice);
		return totalPrice;
	}
	
	// returns price for the duration called.
	private static int computePrice(int seconds){
		int minutes = seconds/60;
		int remainingSeconds = seconds%60;
		int priceMultiplyFactor = 0;
		if(minutes < 3 || (minutes==3 && remainingSeconds==0)){
			priceMultiplyFactor = 3;
		}else if((minutes > 3 && minutes < 5) || (minutes == 3 && remainingSeconds!=0)
				|| (minutes == 5 && remainingSeconds==0)){
			priceMultiplyFactor = 5;
		}else if((minutes == 5 && remainingSeconds!=0) || minutes > 5){
			priceMultiplyFactor = 10;
		}
		
		if(remainingSeconds!=0)
			return ((minutes+1)*priceMultiplyFactor);
		else
			return ((minutes)*priceMultiplyFactor);
	}
}
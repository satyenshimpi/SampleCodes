package me.satyen.code;

import java.util.HashMap;
import java.util.Hashtable;

public class LookNSay{
	public static void main(String[] args){
		int input = 1100;
//		lookNSay(input);
//		lookNSay4(100);
//		System.out.println(lookandsayAtlassian(lookandsayAtlassian("11")));
		System.out.println(lookAndSayLong(21));
	}
	
	static String[] digits = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
	
	//this method wont work for -ve and any 0s in the input. like 100 will not work.
	//for that we need to convert input to String and then count each character
	private static void lookNSay(int input){
		if(input <= 0){
			//log it;
		}
		int prev = input % 10;	//2
		int cnt = 0;
		int a = prev;		//2
		StringBuilder sb = new StringBuilder();
		
		//start from back. Iterate as we are reversing integer
		while(input % 10 != input){
			//take last digit
			a = input % 10;	//2 /1
			//check if is same as prev digit. 1st time it will be same
			if(a == prev){
				cnt++;
			}else{			//if it is not same then we need to create string of previous digit
				sb.insert(0, " " + digits[cnt] + " " + prev);
				cnt = 1;			// and set count as 1 for new digit
			}
			//assign current digit as previous digit for next iteration
			prev = a;	//2 /1
			//remove last digit by dividing 10
			input = input / 10;	//11
		}
		//after last iteration we need to create string as it will not be created in while loop
		//we can put this in while loop and check if its a last iteration then break while
		if(input % 10 == input){
			cnt++;
			sb.insert(0, digits[cnt] + " " + a);
		}
		
		System.out.println(sb.toString());
	}
	
	private static void lookNSay4(int input){
		if(input <= 0){
			//log it;
		}
		int prev = input % 10;	//2
		int cnt = 0;
		int a = prev;		//2
		StringBuilder sb = new StringBuilder();
		
		//start from back. Iterate as we are reversing integer
		while(input / 10 != 0){
			//take last digit
			a = input % 10;	//2 /1
			//check if is same as prev digit. 1st time it will be same
			if(a == prev){
				cnt++;
			}else{			//if it is not same then we need to create string of previous digit
				sb.insert(0, " " + digits[cnt] + " " + prev);
				cnt = 1;			// and set count as 1 for new digit
			}
			//assign current digit as previous digit for next iteration
			prev = a;	//2 /1
			//remove last digit by dividing 10
			input = input / 10;	//11
		}
		//after last iteration we need to create string as it will not be created in while loop
		//we can put this in while loop and check if its a last iteration then break while
		if(input % 10 == input){
			cnt++;
			sb.insert(0, digits[cnt] + " " + a);
		}
		
		System.out.println(sb.toString());
	}
	
	private static void lookNSay2(int input){
		int a = input % 10;		// get the last digit
		int prev = a;
		int cnt = 0;		//its count is 1
		StringBuilder sb = new StringBuilder();
		
		//check if we have any more digits
		do{
			a = input % 10;
			if(a==prev && (input % 10) != input) cnt++;
			else{
				sb.insert(0, " " + digits[cnt] + " " + prev);
				cnt = 1;			// and set count as 1 for new digit
			}
			input = input / 10;
		}while(input / 10 != 0);
		
		//iterate from back
		//each digit at a time. increment count
		//if the digit changes then print the prev digit and count. reset count to 0.
	}
	//this method wont work for -ve
	//for that we need to convert input to String and then count each character
	private static void lookNSay3(int input){
		if(input <= 0){
			//log it;
		}
		int prev = input % 10;	//2
		int cnt = 0;
		int a = prev;		//2
		StringBuilder sb = new StringBuilder();
		
		//start from back. Iterate as we are reversing integer
		do{
			//take last digit
			a = input % 10;	//2 /1
			//check if is same as prev digit. 1st time it will be same
			if(a == prev){
				cnt++;
			}else{			//if it is not same then we need to create string of previous digit
				sb.insert(0, " " + digits[cnt] + " " + prev);
				cnt = 1;			// and set count as 1 for new digit
			}
			//assign current digit as previous digit for next iteration
			prev = a;	//2 /1
			//remove last digit by dividing 10
			input = input / 10;	//11
		}while(input % 10 != input);
		
		//after last iteration we need to create string as it will not be created in while loop
		//we can put this in while loop and check if its a last iteration then break while
		if(input % 10 == input){
			sb.insert(0, digits[cnt] + " " + a);
			cnt++;
			if(a==prev){
				cnt = 1;
				sb.insert(0, digits[cnt] + " " + input % 10);
			}
		}
		
		System.out.println(sb.toString());
	}
	
	
	//atlassian string version
	public static String lookandsayAtlassian(String number){
		StringBuilder result= new StringBuilder();
		char repeat= number.charAt(0);
		number= number.substring(1) + "";
		int times= 1;
	
		for(char actual: number.toCharArray()){
			if(actual != repeat){
				result.append(times + "" + repeat);
				times= 1;
				repeat= actual;
			}else{
				times+= 1;
			}
		}
		result.append(times + "" + repeat);
		return result.toString();
	}
	
	//11  21 1211
	//atlassian long version
	  static long lookAndSayLong(long num){
	    long ans = 0;
	    long prev = num % 10;  //last digit as first char
	    int cnt = 1;	//initial count as 1
	    num /= 10;	//remove the last digit
	    while(num > 0){
	      long last = num % 10;
	      if(last == prev){
	        cnt++;
	      }else{
	        ans =( ans * 100 ) + ((cnt*10) + prev);	//multiply by 100
	        cnt = 1;
	        prev = last;
	      }
	      num /= 10;
	    }
	    //for last result we need to add separately
	    ans =( ans * 100 ) + ((cnt*10) + prev); //multiply by 100 for
	    return ans;
	  }
}
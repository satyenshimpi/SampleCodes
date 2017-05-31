package me.satyen.code;

import java.util.Arrays;

public class Strings {
	public static void main(String[] args) {
		System.out.println(findSubString("aabaadaaaaf"));
		System.out.println(findSubString("aabadefghaabbaagad"));
		System.out.println(findSubString("abcbbbbcccbdddadacb"));
	}
	
	static boolean isAnagram(String a, String b){
	    char[] arrA = a.toUpperCase().toCharArray();
	    char[] arrB = b.toUpperCase().toCharArray();
	    
	    Arrays.sort(arrA);
	    Arrays.sort(arrB);
	    
	    return new String(arrA).equals(new String(arrB));
	  }
	  
	  static boolean isPalindromeFor(String a){
	    char[] arrA = a.toUpperCase().toCharArray();
	    for(int i=0; i< arrA.length/2; i++){
	      if(arrA[i] != arrA[arrA.length -1 - i]) return false;
	    }
	    return true;
	  }
	  
	  //https://stackoverflow.com/questions/14997262/how-to-find-the-longest-substring-containing-two-unique-repeating-characters
	static String findSubString(String str) {
		System.out.println("Input str = " + str);
		// Find the first letter that is not equal to the first one,
		// or return the entire string if it consists of one type of characters
		char[] cArr = str.toCharArray();
		int start = 0;
		int i = 1;
		while (i < cArr.length && cArr[i] == cArr[start])
			i++;
		if (i == cArr.length)
			return str;

		// The main algorithm
		char[] chars = { cArr[start], cArr[i] };
		int lastGroupStart = 0;
		int maxStart = 0;
		int maxEnd = i;
		int maxLen = maxEnd - maxStart;

		while (i < cArr.length) {
			if (cArr[i] == chars[0] || cArr[i] == chars[1]) {
				//lastGroupStart is a key logic ******
				if (cArr[i] != cArr[i - 1]) //when char changes then we need to identify the last group start position
					lastGroupStart = i;
			} else {
				if ((i - start) > maxLen) {
					maxLen = i - start;
					maxStart = start;
					maxEnd = i;
				}
				start = lastGroupStart;   //put the last group start position as new start position
				lastGroupStart = i;		//and i as last group start
				//now the two chars are different set
				chars[0] = cArr[start];
				chars[1] = cArr[lastGroupStart];
			}
			i++;
		}
		return str.substring(maxStart, maxEnd);
	}
  
	@Deprecated  //don't use this
	static String findSubStrin(String str) {
		System.out.println("Input str = " + str);
		char[] carr = str.toCharArray();
		char fst = carr[0];
		int fstStart = 0;
		int i = 1;
		while (carr[i] == fst) {
			i++;
		}

		char sec = carr[i];
		int secStart = i;
		int maxStart = fstStart;
		int maxEnd = secStart;
		int maxLen = secStart - fstStart;

		for (; i < carr.length; i++) {
			if (carr[i] != fst && carr[i] != sec) {
				System.out.println("fst=" + fst + " sec=" + sec + " changed " + carr[i]);
				if ((i - fstStart) > maxLen) {
					maxLen = i - fstStart;
					maxStart = fstStart;
					maxEnd = i;
					System.out.println();
				}
				fstStart = secStart;
				secStart = i;
				fst = sec;
				sec = carr[i - 1];
			}
		}
		return str.substring(maxStart, maxEnd);
	}

}

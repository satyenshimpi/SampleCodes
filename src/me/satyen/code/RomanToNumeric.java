package me.satyen.code;

import java.io.*;
import java.util.*;

public class RomanToNumeric {


/**
Write a Roman Numeral converter. 

Values
I = 1
V = 5
X = 10
L = 50
C = 100
D = 500
M = 1000

Rules
Repeating a numeral up to three times represents addition of the number. 
Only I, X, C, and M can be repeated
V, L, and D cannot be, and there is no need to do so.
For example, III represents 1 + 1 + 1 = 3. 

Writing numerals that decrease from left to right represents addition of the numbers. 
For example, LX represents 50 + 10 = 60 and XVI represents 10 + 5 + 1 = 16.
To write a number that otherwise would take repeating of a numeral four or more times, there is a subtraction rule. 

Writing a smaller numeral to the left of a larger numeral represents subtraction. 
For example, IV represents 5 - 1 = 4 and IX represents 10 - 1 = 9. 
To avoid ambiguity, the only pairs of numerals that use this subtraction rule are
IV = 4
IX = 9
XL = 40
XC = 90
CD = 400
CM = 900


Problems to solve (Note: we will ignore bar of numeral for this problem):
Given a roman numeral string, return its numeric value. For example: “XX” evaluates to 20.Given a numeric value, return its roman numeral representation

Test case: XCIV = 94, CMXCIV = 994
//to number
"XCIV" = 94
"III" = 3
"IV" = 4
"CMXCV" = 995
"CMXCIV" = 994
"MMMCMXCIX" = 3999

//is valid
CMXCIV = valid
MMMCMXCIX = valid
VIII = valid
IIIV = invalid
IIV = invalid

//to roman numeral
15 = XV
3 = III
4 = IV
8 = VIII
94 = XCIV
994 = CMXCIV
995 = CMXCV
3999 = MMMCMXCIX

**/

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

  public static void main(String[] args) {
    values.put('I',1);
    values.put('V',5);
    values.put('X',10);
    values.put('L',50);
    values.put('C',100);
    values.put('D',500);
    values.put('M',1000);
//     values.put('IV',4);
//    
//    'CMXCV'
//    900
//    CM
//    'XCV'
//    90 + 900
//    'V'   
      
    cAL.add('I');cAL.add('V');cAL.add('X');cAL.add('L');cAL.add('C');cAL.add('D');cAL.add('M');cAL.add(' ');

    //roman to numeral
    String roman = "III";
    int result = romanToNumeral2(roman);
    System.out.println(result);
  }
  
 static LinkedHashMap<Character,Integer> values = new LinkedHashMap<Character,Integer>();
static ArrayList<Character> cAL = new ArrayList<Character>();  
  
  /**
  I = 1
V = 5
X = 10
L = 50
C = 100
D = 500
M = 1000
**/
  
  static int romanToNumeral(String roman){
    char[] carr = roman.toCharArray();
    int result = 0;
    char prev = ' ';
    for(int i= carr.length-1 ; i > -1 ; i--){
      //hello there. call got disconnected
      char curr = carr[i];
      if(prev==' ' || prev == curr){
        //TODO
        result += values.get(curr);
      }else{
        if(cAL.indexOf(curr) < cAL.indexOf(prev)){
          result = result - values.get(curr);
        }else{
          result = result + values.get(curr);
        }
      }
      prev = curr;
    }
    return result;
  }
  
  static int romanToNumeral2(String roman){
	    char[] carr = roman.toCharArray();
	    char prev = carr[carr.length-1];
	    int result = values.get(prev);
	    for(int i= carr.length-2 ; i >= 0 ; i--){
	      char curr = carr[i];
	        if(cAL.indexOf(curr) < cAL.indexOf(prev)){
	          result = result - values.get(curr);
	        }else{
	          result = result + values.get(curr);
	        }
	      prev = curr;
	    }
	    return result;
	  }
  
}


/* 
Your previous Plain Text content is preserved below:



This is just a simple shared plaintext pad, with no execution capabilities.

When you know what language you'd like to use for your interview,
simply choose it from the dropdown in the top bar.

You can also change the default language your pads are created with
in your account settings: https://coderpad.io/settings

Enjoy your interview!

// john here
//satyen here
 */
package me.satyen.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Integers {
//	<br>1. rearrange the array such that all non-zero members appear on the left of the array 
//	(order is not important)
//	<br>2. return the number of non-zero members
//	<br>
//	<br>e.g. [1,2,0,5,3,0,4,0] =&gt; [1,2,5,3,4,0,0,0] and return 5. 
//	The non-zero array members can be in any order.</p></a>
	public static int rearrangeInts(int[] arr){
		int cnt = -1;
		int indxZ = -1;
		int indxNZ = -1;
		boolean foundZero = false;
		for(int i=0; i< arr.length; i++){
			//find first zero index first time only.
			if(!foundZero && arr[i] == 0){
				indxZ = i;
				foundZero = true;
			}else if (arr[i] != 0){		//find the non zero index
				indxNZ = i;
				//if we already found zero and we got non zero index then swap
				//and increment zero index
				if(foundZero){
					swap(arr, indxZ++, indxNZ);
				}
			}
		}
		return cnt;
	}
	public static void swap(int[] arr, int indx1, int indx2){
		int tmp = arr[indx1];
		arr[indx1] = arr[indx2];
		arr[indx2] = tmp;
	}
	
	//facebook question
	public static int rearrangeInts1(int[] arr){
		printArr(arr);
		if(arr.length < 2) return arr[0]==0 ? 0 : 1;
		int i=0, j=0;
		for(i=0, j=arr.length-1;i!=j && i< j;){
			while(arr[i]!=0 && i<j) i++;
			while(arr[j]==0 && j > i) j--;
			swap(arr, i, j);
		}
		printArr(arr);
		return i;
	}
	
	public static void main(String[] args){
		int[] arr = new int[]{0,1,0,5,0,0,4,0,0};
//		arr = new int[]{2,1,5,3,7,4,6};
		
//		System.out.println(rearrangeInts1(arr));
//		sortAlternate(arr, 0, arr.length);
//		printArr(arr);
//		System.out.println(convertToColumn(676));
//		List<Integer> lst = new ArrayList<Integer>();
//		lst.add(97);lst.add(66);lst.add(37);lst.add(46);lst.add(56);lst.add(49);lst.add(65);lst.add(62);lst.add(21);lst.add(7);lst.add(70);lst.add(13);lst.add(71);lst.add(93);lst.add(26);lst.add(18);lst.add(84);lst.add(96);lst.add(65);lst.add(92);lst.add(69);lst.add(97);lst.add(47);lst.add(6);lst.add(18);lst.add(17);lst.add(47);lst.add(28);lst.add(71);lst.add(70);lst.add(24);lst.add(46);lst.add(58);lst.add(71);lst.add(21);lst.add(30);lst.add(44);lst.add(78);lst.add(31);lst.add(45);lst.add(65);lst.add(16);lst.add(3);lst.add(22);lst.add(54);lst.add(51);lst.add(68);lst.add(19);lst.add(86);lst.add(44);lst.add(99);lst.add(53);lst.add(24);lst.add(40);lst.add(92);lst.add(38);lst.add(81);lst.add(4);lst.add(96);lst.add(1);lst.add(13);lst.add(45);lst.add(76);lst.add(77);lst.add(8);lst.add(88);lst.add(50);lst.add(89);lst.add(38);lst.add(60);lst.add(61);lst.add(49);lst.add(25);lst.add(10);lst.add(80);lst.add(49);lst.add(63);lst.add(95);lst.add(74);lst.add(29);lst.add(27);lst.add(52);lst.add(27);lst.add(40);lst.add(66);lst.add(38);lst.add(22);lst.add(85);lst.add(22);lst.add(91);lst.add(98);lst.add(19);lst.add(20);lst.add(78);lst.add(77);lst.add(48);lst.add(63);lst.add(27);
//		System.out.println(titleToNumber("AAA"));
//		System.out.println(sieve(10));
//		System.out.println(reverse(-1146467285));
//		System.out.println(getSum(3, 7));
//		System.out.println(summaryRanges(new int[]{0,1,2,5,6}));
		ArrayList<Integer> lst = new ArrayList<Integer>();
//		lst.add(0);lst.add(1);//lst.add(-1);lst.add(0);lst.add(0);lst.add(0);lst.add(0);//lst.add(62);lst.add(21);lst.add(7);lst.add(70);lst.add(13);lst.add(71);lst.add(93);lst.add(26);lst.add(18);lst.add(84);lst.add(96);lst.add(65);lst.add(92);lst.add(69);lst.add(97);lst.add(47);lst.add(6);lst.add(18);lst.add(17);lst.add(47);lst.add(28);lst.add(71);lst.add(70);lst.add(24);lst.add(46);lst.add(58);lst.add(71);lst.add(21);lst.add(30);lst.add(44);lst.add(78);lst.add(31);lst.add(45);lst.add(65);lst.add(16);lst.add(3);lst.add(22);lst.add(54);lst.add(51);lst.add(68);lst.add(19);lst.add(86);lst.add(44);lst.add(99);lst.add(53);lst.add(24);lst.add(40);lst.add(92);lst.add(38);lst.add(81);lst.add(4);lst.add(96);lst.add(1);lst.add(13);lst.add(45);lst.add(76);lst.add(77);lst.add(8);lst.add(88);lst.add(50);lst.add(89);lst.add(38);lst.add(60);lst.add(61);lst.add(49);lst.add(25);lst.add(10);lst.add(80);lst.add(49);lst.add(63);lst.add(95);lst.add(74);lst.add(29);lst.add(27);lst.add(52);lst.add(27);lst.add(40);lst.add(66);lst.add(38);lst.add(22);lst.add(85);lst.add(22);lst.add(91);lst.add(98);lst.add(19);lst.add(20);lst.add(78);lst.add(77);lst.add(48);lst.add(63);lst.add(27);
//		System.out.println(maxset(lst));
		
//		System.out.println(rotateArray(lst, 3));
		lst.add(4);lst.add(0);lst.add(2);lst.add(1);lst.add(3);//lst.add(0);lst.add(0);//lst.add(62);lst.add(21);lst.add(7);lst.add(70);lst.add(13);lst.add(71);lst.add(93);lst.add(26);lst.add(18);lst.add(84);lst.add(96);lst.add(65);lst.add(92);lst.add(69);lst.add(97);lst.add(47);lst.add(6);lst.add(18);lst.add(17);lst.add(47);lst.add(28);lst.add(71);lst.add(70);lst.add(24);lst.add(46);lst.add(58);lst.add(71);lst.add(21);lst.add(30);lst.add(44);lst.add(78);lst.add(31);lst.add(45);lst.add(65);lst.add(16);lst.add(3);lst.add(22);lst.add(54);lst.add(51);lst.add(68);lst.add(19);lst.add(86);lst.add(44);lst.add(99);lst.add(53);lst.add(24);lst.add(40);lst.add(92);lst.add(38);lst.add(81);lst.add(4);lst.add(96);lst.add(1);lst.add(13);lst.add(45);lst.add(76);lst.add(77);lst.add(8);lst.add(88);lst.add(50);lst.add(89);lst.add(38);lst.add(60);lst.add(61);lst.add(49);lst.add(25);lst.add(10);lst.add(80);lst.add(49);lst.add(63);lst.add(95);lst.add(74);lst.add(29);lst.add(27);lst.add(52);lst.add(27);lst.add(40);lst.add(66);lst.add(38);lst.add(22);lst.add(85);lst.add(22);lst.add(91);lst.add(98);lst.add(19);lst.add(20);lst.add(78);lst.add(77);lst.add(48);lst.add(63);lst.add(27);
//		arrange(lst);
		
		System.out.println(changeBaseToHexa(123456));
	}
	private static void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+",");
		}
		System.out.println();
	}
	
	public static void sortAlternate(int[] nums, int startIndx, int endIndx){
		if(startIndx >= endIndx) return;
		greatestFirst(nums, startIndx, endIndx);
		smallestFirst(nums, ++startIndx, endIndx);
		sortAlternate(nums, ++startIndx, endIndx);
	}
	
	public static void greatestFirst(int[] nums, int startIndx, int endIndx){
		for(int i=startIndx; i< endIndx; i++){
			if(nums[i] > nums[startIndx])
				swap(nums, i, startIndx);
		}
	}
	
	public static void smallestFirst(int[] arr, int startIndx, int endIndx){
		for(int i=startIndx; i< endIndx; i++){
			if(arr[i] < arr[startIndx])
				swap(arr, i, startIndx);
		}
	}
	
	//excel find of column indexes. eg 2->B, 28-> AB, 704 -> AAB
	//keep index 0 as Z cause 26 % 26 is 0 so we need Z at index 0*****************************
	static String[] alphabets = {"Z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
	public static String convertToColumn(int num){
		int mod = num % 26;
		int div = num / 26;
		if(num > 26)
			return convertToColumn(div) + alphabets[mod];
		else
			return alphabets[mod];
	}
	
	
	
	public static int titleToNumber(String a) {
		a = a.toLowerCase();
	    int ans = (int)a.charAt(a.length()-1) - 'a'+1 ;
	    for(int i=a.length()-2; i>=0; i--){
	        ans += (pow(26,a.length()-1-i) * ((int)a.charAt(i) - 'a' + 1));
	    }
	    return ans;
	}
	
	public static int pow(int a, int b){
	    int ans = 1;
	    for(int i=1; i<=b; i++){
	        ans *= a;
	    }
	    return ans;
	}
	
	
	
	public static ArrayList<Integer> sieve(int a) {
	    ArrayList<Integer> ret = new ArrayList();
	    int sqrt = (int) Math.sqrt(a);
	    for(int i = 2; i < a; i++){
	        ret.add(i);
	    }
	    for(int i=2; i<= sqrt; i++){
	        if(ret.contains(i)){
	            for(int j=2; i*j<a; j++){
	                ret.remove(new Integer(i*j));
	            }
	        }
	    }
	    return ret;
	}
	
	
	public static int reverse(int a) {
	    boolean isNeg = false;
	    if(a<0) isNeg = true;
	    long ans = 0;
	    while(a != 0){
	    	ans *= 10;
	    	if(ans > Integer.MAX_VALUE || ans < Integer.MIN_VALUE)
	    		return 0;
	        int mod = a %10;
	        a = a/10;
	        ans += mod;
	    }
	    return (int)ans;
	}
	
	//sum using binary bitwise operator
	//http://stackoverflow.com/a/15450315/3661654
	public static int getSum(int p, int q)
	{
	    int result = p ^ q; // + without carry 0+0=0, 0+1=1+0=1, 1+1=0
	    int carry = (p & q) << 1; // 1+1=2
	    if (carry != 0) {
	        return getSum(result, carry);
	    }
	    return result;
	}
	
    
    public static List<String> summaryRanges(int[] nums) {
        List<String> ret = new ArrayList();
        if(nums.length==0) return ret;
        String range = ""+nums[0];
        int rangeStart = 0;
        for(int i=1; i< nums.length; i++){
            if(nums[i+1] - nums[i] > 1 ){
                if(rangeStart == i+1)
                    ret.add(range);
                else
                	ret.add(range + "->" + nums[i-1]);
                
                if(i==nums.length-1)
                	ret.add(""+nums[i]);
                range = ""+nums[i];
                rangeStart = i+1;
            }
            if(i == nums.length-1){
            	if(rangeStart == i)
                    ret.add(range);
                else
                	ret.add(range + "->" + nums[i-1]);
            }
        }
        if(rangeStart == 0)
            ret.add(range);
        return ret;
    }
    
    
    public static ArrayList<Integer> maxset(ArrayList<Integer> a) {
	    int i=0;
	    int startIndex = -1, endIndex = -1;
	    int prevStartIndex = -1, prevEndIndex = -1;
	    int sum =0, largestSum =0;
	    int length = 0, prevLen=0;
	    for(i=0; i< a.size(); i++){
	        if(a.get(i)>-1 && i < a.size()-1){
	            if (startIndex == -1) startIndex = i;
	            sum += a.get(i);
	            if(sum > largestSum){
	                largestSum = sum;
	                prevLen = length;
	                prevStartIndex = startIndex;
	               prevEndIndex = endIndex;
	            }else if(largestSum == sum){
	                if(prevLen < length){
	                    prevStartIndex = startIndex;
	                    prevEndIndex = endIndex;
	                }else if(prevLen == length){
	                    if(prevStartIndex > startIndex){
	                        prevStartIndex = startIndex;
	                        prevEndIndex = endIndex;
	                    }
	                }
	            }
	        }else if(startIndex > -1){ 
	            endIndex = i;
	            length = endIndex - startIndex;
	            
	            sum = 0;
	            startIndex = -1;
	        }
	    }
	    if (startIndex > endIndex) endIndex = i;
	    if(prevStartIndex > -1 && prevEndIndex > -1)
    	    return new ArrayList<Integer>(a.subList(prevStartIndex, prevEndIndex));
	    else
	        return new ArrayList<Integer>();
	}
    
    public static ArrayList<Integer> rotateArray(ArrayList<Integer> A, int B) {
    	B = B % A.size();
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < A.size()-B; i++) {
			ret.add(A.get(i + B));
		}
		for(int i=0; i < B; i++){
		    ret.add(A.get(i));
		}
		return ret;
	}
    
    public static void arrange(ArrayList<Integer> a) {
	    int index = 1;
	    for(int i=0; i< a.size()-1; i++){
	        if(i==0) index = 0;else index =1;
	        int val = a.get(a.get(i+index)+index);
	        a.remove(Integer.valueOf(val));
	        a.add(i, val);
	    }
	    
	    for(int i=0; i< a.size()-1; i++){
	    	System.out.println(a.get(i));
	    }
	}
    
  //For Hexadecimal number conversion
  //We can use same function for any base too. Just change base var value
  static String changeBaseToHexa(int in){
  	char[] digits = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
              StringBuilder sb = new StringBuilder();
              int base = 7;
              while(in > 0){
                  int mod = in % base;
                  in = in / base;
                  sb.insert(0,digits[mod]);
              }
  	return sb.toString();
  }
}

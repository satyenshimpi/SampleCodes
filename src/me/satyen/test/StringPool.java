package me.satyen.test;

public class StringPool {
	public static void main(String[] args) {
		//only string literals are stored in pool
		String str1 = "Sat" + "yen";
		String str2 = "Satye" + "n";
		
		//objects go to Heap
		String str3 = new String("Satyen1");
		String str4 = new String("Satyen1");
		
		System.out.println("1==2 " + (str1==str2));
		System.out.println("1==3 " + (str1==str3));
		System.out.println("3==4 " + (str4==str3));
	}
}

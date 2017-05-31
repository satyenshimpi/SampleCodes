package me.satyen.tests.ds;

import java.util.*;

public class SetTest{
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1); set.add(2); set.add(3); set.add(4);

		//iterate for loop
		for(int i: set){
			System.out.println(i);
		}

		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}

		System.out.println("set.contains 3 " + set.contains(3));

		System.out.println("remove 3 "+set.remove(3));
		System.out.println("set.contains 3 " + set.contains(3));
	}
}
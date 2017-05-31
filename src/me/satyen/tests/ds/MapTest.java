package me.satyen.tests.ds;

import java.util.*;

public class MapTest{
	public static void main(String[] args){
		hashMapTest();
	}

	static void hashMapTest(){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("1",1);
		map.put("2",2);
		map.put("3",3);
		map.put("4",4);

		System.out.println(map.get("1"));
		if(map.containsKey("2")){
			System.out.println("Contains 2");
		}

		if(map.containsValue(3)){
			System.out.println("Contains 3");
		}

		//iterate
		Set<String> keySet = map.keySet();
		Collection<Integer> colllection = map.values();
		Set<Map.Entry<String, Integer>> eSet = map.entrySet();

		//iterate by entries
		for(Map.Entry<String, Integer> e : eSet){
			System.out.println(e.getKey() + "  " + e.getValue());
		}

		//
	}
}
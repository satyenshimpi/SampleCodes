package me.satyen.test;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * LRU cache implementation
 */
public class Cache{
	static HashMap<String, String> map = new HashMap<String, String>();
	static LinkedList<String> ll = new LinkedList<String>();
	static int max_cap = 2;

	public static String getValue(String key){
		if(map.containsKey(key)){
			System.out.println("Found key in cache");
			moveToTop(key);
			return map.get(key);
		}else{
			System.out.println("Adding key to cache");
			ensureCapacity();
			addNewKeyToList(key);
			map.put(key, key);
			return key;
		}
	}

	private static void moveToTop(String key){
		int index = ll.indexOf(key);
		ll.remove(index);
		addNewKeyToList(key);
	}

	private static void ensureCapacity(){
		if(ll.size() == max_cap){
			String removed = ll.remove(ll.size()-1);
			map.remove(removed);
		}
	}

	private static void addNewKeyToList(String key){
		ll.add(0, key);
	}
	
	public static void main(String[] args) {
		getValue("s");
		getValue("a");
		getValue("s");
		getValue("y");
		getValue("s");
		
	}
}
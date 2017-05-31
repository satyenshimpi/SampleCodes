package me.satyen.test;

import java.util.Comparator;

public class Comparing implements Comparable<Comparing>{

	@Override
	public int compareTo(Comparing o) {
		return 0;
	}

}

class MyCompare implements Comparator<MyCompare>{
	@Override
	public int compare(MyCompare a, MyCompare b){
		return 0;
	}
}

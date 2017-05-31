package me.satyen.tests.ds;

import java.util.*;

public class ListTest{
	public static void main(String[] args){
		//create various list types
		ArrayList<Integer> lstInt = new ArrayList<Integer>();
		LinkedList<Integer> lstLL = new LinkedList<Integer>();
		Vector<Integer> vecInt = new Vector<Integer>();

		alAPIs(lstInt);
		
	}

	private static void alAPIs(ArrayList<Integer> al){
		printList(al);
		al.add(1);
		al.add(2);
		al.add(4);
		al.add(3);
//		iterateLst(al);
		lstListIterator(al);
		printList(al);
		if(true) return;
		Collections.sort(al);

		printList(al);
		//in ArrayList there is a get by Index only. NO getByObject api ******************
		System.out.println("Element at index 0 is " + al.get(0));   //get by index
		System.out.println("Index of element 1 is " + al.indexOf(Integer.valueOf(1)));
		int x;
		if( (x = al.indexOf(2)) > 0){
			System.out.println("Found x=" + x);
		}
		if(al.contains(2)){
			//for remove there are remove by index and remove by object both
			al.remove(3);					//remove by INDEX
			al.remove(Integer.valueOf(3));	//remove by OBJECT
		}
		printList(al);
	}

	private static void printList(List<Integer> lst){
		if(lst.size() == 0){
			System.out.println("List size is ZERO 0.");
			return;
		}
		System.out.println("------Printing list elements");
		for(Integer in : lst){
			System.out.print(in + ", ");
		}
		System.out.println("\n------------------\n");
	}

	private static void iterateLst(List<Integer> lst){
		Iterator it = lst.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
			//lst.add(7);    //throes ConcurrentModificationException
			it.remove();
		}
	}
	
	private static void lstListIterator(List<Integer> lst){
		ListIterator<Integer> iterator = lst.listIterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			iterator.add(7);
		}
	}
}
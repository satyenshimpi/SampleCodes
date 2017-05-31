package me.satyen.test;

import java.util.*;

//set of subsets. Credit Karma
public class PowerSet {
	public static void main(String[] args) {
		Set<Character> set = new HashSet<Character>();
		set.add('a');set.add('b');set.add('c');
		System.out.println("Input set");
		printSet(set);
		System.out.println("\nsub sets");
		for(Set<Character> e : findPowerSet(set)){
			printSet(e);
		}
	}

	public static <E> Set<Set<E>> findPowerSet(Set<E> set){
		Set<Set<E>> ret = new HashSet<Set<E>>();
		ret.add(set);
		if(set.isEmpty()){
			return ret;
		}
		Iterator<E> it = set.iterator();
		while(it.hasNext()){
			Set<E> tmp = new HashSet<E>(set);	//create a copy of current set
			tmp.remove(it.next());				//remove current element from copy set
			ret.add(tmp);						//add the remaining set to result
			ret.addAll(findPowerSet(tmp));		//recursively find subsets of copy set
		}
		return ret;
	}

	public static <E> void printSet(Set<E> set){
		System.out.print("{");
		for(E e : set){
			System.out.print(e + " ");
		}
		System.out.print("}\n");
	}
}

/*
Atlassian
Cashiers Problem (~40 min)
  
Given a list of transactions, How can we calculate the frequency
counts of all possible item-sets?
  
For example,
  
[INPUT] list of transactions
| -- | -----------------------------|
| ID | Purchased items              |
| -- | -----------------------------|
| 1  | apple, banana, lemon         |
| 2  | banana, berry, lemon, orange |
| 3  | banana, berry, lemon         |
| -- | -----------------------------|
  
  
[OUTPUT] frequency counts of all possible item-sets. Note: some
outputs are omitted for brevity.
| ---------------------------- | --------- |
| Itemset                      | Frequency |
| ---------------------------- | --------- |
| apple, banana                | 1         |
| apple, lemon                 | 1         |
| banana, berry                | 2         |
| banana, lemon                | 3         |
| ...                                      |
| apple, banana, lemon         | 1         |
| banana, berry, lemon         | 2         |
| ...                                      |
| banana, berry, lemon, orange | 1         |
| ...                                      |
| ---------------------------- | --------- |
  
*/
  
class Cashier {
    public static void main(String[] args){
        Set<String> st1 = new HashSet<String>();
        st1.add("apple");st1.add("banana");st1.add("lemon");
        Set<Set<String>> sbs1 = getSubsets(st1);
        
        Set<String> st2 = new HashSet<String>();
        st2.add("banana");st2.add("berry");st2.add("lemon");st2.add("orrange");
        Set<Set<String>> sbs2 = getSubsets(st2);
        
        Set<String> st3 = new HashSet<String>();
        st3.add("banana");st3.add("berry");st3.add("lemon");
        Set<Set<String>> sbs3 = getSubsets(st3);
        
        Iterator<Set<String>> itPar = sbs2.iterator();
        while(itPar.hasNext()){
            Iterator<String> it = itPar.next().iterator();
            while(it.hasNext())
                System.out.print(it.next() + ", ");
            System.out.println();
        }
    } 
    
    static Set<Set<String>> getSubsets(Set<String> st){
        if(st.size()<3) return null;
        Set<Set<String>> ret = new HashSet<Set<String>>();
        ret.add(st);
        Iterator<String> it = st.iterator();
        while(it.hasNext()){
            String s1 = it.next();
            Set<String> tmp = new HashSet<String>(st);
            tmp.remove(s1);
            ret.add(tmp);
            Set<Set<String>> sb1 = getSubsets(tmp);
            if( sb1 != null){
                ret.addAll(sb1);
            }
        }
        return ret;
    }
}

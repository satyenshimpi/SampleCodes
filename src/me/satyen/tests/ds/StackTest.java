package me.satyen.tests.ds;

import java.util.*;

public class StackTest{
	public static void main (String[] args){
//		legacyStackClass();
		System.out.println(isParenBalanced("{[()]}"));
	}

	static void dequeueAsStack(){
		Deque<Integer> stack = new ArrayDeque<Integer>();
	}

	static void legacyStackClass(){
		Stack<Integer> st = new Stack<Integer>();
		if(st.isEmpty()){
			System.out.println("Stack is empty");
		}
		st.push(1);
		st.push(2);
		st.push(3);
		st.push(4);

		while(!st.isEmpty()){
			if(st.peek() != null){
				System.out.println(st.pop());
			}
		}
		st.clear();
	}
	
	//parenthesis
	public static boolean isParenBalanced(String str){
		Stack<Character> st = new Stack<Character>();
		HashMap<Character, Character> map = new HashMap<Character, Character>();
		map.put('{', '}');
		map.put('[', ']');
		map.put('(', ')');
		
		for(char c: str.toCharArray()){
			if(st.isEmpty()){			//1st time put the char in stack
				if(map.containsKey(c))
					st.push(c);
				else			//if 1st paren is ending paran
					return false;
			}else{
				if(map.containsKey(c)){	//check if it is a starting paren
					st.push(c);
				}else{					//if it is ending param then 
					if( map.get(st.peek()) != c)	//get the value for top char in stack
						return false;				//if value of top char is not maching current char then its imbalanced
					else
						st.pop();		//if macthes then remove from stack
				}
			}
		}
		return st.isEmpty();
	}
}
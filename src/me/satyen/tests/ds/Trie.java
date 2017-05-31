package me.satyen.tests.ds;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Trie {
	Node root;
	public Trie(char c){
		root = new Node(c);
	}
	
	class Node{
		char prefix;
		HashMap<Character, Node> children;
		boolean isCompleteWord;
		public Node(char c){
			prefix = c;
			children = new HashMap<Character, Trie.Node>();
			isCompleteWord = false;
		}
	}
	
	public void insertWord(String str, Node n){
		str = str.toLowerCase();		// make all lowercase
		for(int i=0; i< str.length(); i++){
			char charAt = str.charAt(i);
			if(!n.children.containsKey(charAt)){
				n.children.put(str.charAt(i), new Node(str.charAt(i)));
			}
			n = n.children.get(charAt);
		}
		n.isCompleteWord = true;
	}
	
	public List<String> getWords(String prefix, Node n){
 		for(int i=0; i< prefix.length(); i++){
			char charAt = prefix.charAt(i);
			if(!n.children.containsKey(charAt)){
				return Collections.EMPTY_LIST;
			}
			n = n.children.get(charAt);
		}
		return findWordsFrom(prefix, n);
	}
	
	public List<String> findWordsFrom(String prefix, Node n){
		List<String> ret = new LinkedList<String>();
		if(n.isCompleteWord){
			ret.add(prefix);
		}		
		for(Node x : n.children.values()){
			String str = prefix + x.prefix;
			List<String> tmp = findWordsFrom(str, x);
			ret.addAll(tmp);
		}
		return ret;
	}
	
	public static void main(String[] args) {
		Trie t = new Trie('*');
		t.insertWord("ab", t.root);
		t.insertWord("abc", t.root);
		t.insertWord("aef", t.root);
		t.insertWord("de", t.root);
		t.insertWord("def", t.root);
		print(t.root);
		for(String s: t.getWords("a", t.root)){
			System.out.println(s);
		}
	}
	
	public static void print(Node n){
		System.out.println("char " + n.prefix);
		System.out.println("childs " + n.children);
		System.out.println();
		for(Node x : n.children.values()){
			print(x);
		}
	}
}

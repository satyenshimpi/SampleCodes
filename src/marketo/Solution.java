package marketo;

public class Solution {
	private static Solution instance = null;
	
	private Solution(){
		//constructor
	}
	
	public static Solution getInstance(){
		synchronized(Solution.class){
			if(instance == null){
				instance = new Solution();
			}
		}
		return instance;
	}
	
	static class Node{
		int val;
		Node next;
		public Node(int n){
			val = n;
			next = null;
		}
	}
	
	//1-2-3 -> 3-2-1
	public static void reverseList(Node head){
		Node n = head;
		Node prev = null;
		Node curr = null;
		while(n != null){
			curr = n;
			curr.next = prev;
			
			prev = n;
			n = n.next;
		}
	}
	
	public static Node reverseListRec(Node head){
		if(head == null) return head;
		if(head.next == null) return head;

		Node sec = head.next;
		head.next = null;
		Node tmp = reverseListRec(sec);
		sec.next = head;
				
		return tmp;
	}
	
	public static void invert(Node head) {
		Node prev = null;
		Node next = null;
	 if (head == null) return;
     while (true) {
         next = head.next;
         head.next = prev;
         prev = head;
         if (next == null) return;
         head = next;
     }
}
	public static void main(String[] args) { 
		Node n = new Node(1);
		n.next = new Node(2);
		n.next.next = new Node(3);
		printList(n);
//		invert(n);
//		printList(n);
		printList(reverseListRec(n));
	}
	
	static void printList(Node head){
		Node n = head;
		while(n != null){
			System.out.print(n.val + "->");
			n = n.next;
		}
		System.out.println();
	}
}

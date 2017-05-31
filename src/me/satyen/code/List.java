package me.satyen.code;

import java.util.LinkedList;

public class List {
    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
        ll.add(6);
        ll.add(7);
        ll.add(8);
        ll.add(9);
        ll.add(10);
        System.out.println(ll);
        //        ll.addNode(9);
        //        ll.deleteDuplicates2();
//        System.out.println(ll.findNthLastElement(3).data);
        
        LinkedList lst1 = new LinkedList();
        LinkedList lst2 = new LinkedList();
        lst1.add(1);
        lst1.add(2);
        lst1.add(3);
        
        lst1.add(4);
        lst2.add(2);
        lst2.add(3);
        
//        LinkedList l3=ll.addLists(lst1, lst2);
//        System.out.println("sum: " + l3);
        System.out.println(findSubList(lst1, lst2));
    }

	static Node deleteNode(Node head, int val){
		if(head == null) return head;
		while(head.data == val) {
			Node tmp = head;
			head = head.next;
			tmp.next = null;
		}
		if(head == null) return head;
		
		Node n = head;
		while(n != null || n.next != null){
			if(n.next.data == val){
				Node tmp = n.next;
				n.next = n.next.next;
				tmp.next = null;
			}
			n = n.next;
		}
		return head;
	}
	
    class Node {
        Node next;
        Integer data;
        Node(Integer data) {
            this.data = data;
            next = null;
        }
    }

	static boolean findSubList(java.util.List<Integer> l1, java.util.List<Integer> l2) {
		int startOfSub = -1;
		boolean flag = false;
		for (int i = 0; i < l1.size(); i++) {
			if (!flag && l1.get(i) == l2.get(0)) {
				if (startOfSub < 0 && i != l1.size() - 1 && l1.get(i + 1) == l2.get(1)) {
					startOfSub = i;
					flag = true;
				}
			} else if (startOfSub >= 0 && i - startOfSub < l2.size() && l1.get(i) != l2.get(i - startOfSub)) {
				flag = false;
				break;
			}
		}
		return flag;
	}
}

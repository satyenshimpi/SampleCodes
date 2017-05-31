package me.satyen.code;

import java.util.HashMap;

public class LinkedList {
	Node head = null;
Node tail = null;
    public LinkedList() {
        head = null;
    }
    //add at end
    public void addNode(Integer data) {
        Node toAdd = new Node(data);
        if (head == null && tail == null) {
            head = toAdd;
            tail = toAdd;
        } else {
            tail.next = toAdd;
            tail = toAdd;
        }
    }
    public void deleteNode(Object data) {
        if (head == null) {
            return;
        } else {
            Node n = new Node(null);
            n.next = head;
            while (n.next != null) {
                if (n.next.data.equals(data)) {
                    //delete this node
                    Node tmp = n.next;
                    n.next = tmp.next;
                    tmp.next = null;     //eligible for GC
                    break;
                } else {
                    n = n.next;
                }
            }
            return;
        }
    }
    public void deleteDuplicates() {
        HashMap<Integer, Boolean> hm = new HashMap();
        Node n = head;
        Node prev = null;
        
        while (n != null) {
            if (hm.containsKey(n.data)) {
                prev.next=n.next;
                n.next=null;
            } else {
                hm.put(n.data, true);
                prev = n;
            }
            n = n.next;
        }
    }
    /**
     * remove dup without extra buffer
    *
     */
    public void deleteDuplicates2() {
        //runner reference to iterate
        Node runner = head;
        //current no which should be compared to 
        Node current = head.next;
        //reference to previous node of current node. SO that it will be easy to delete current node
        Node previous = head;

        //while current points to last node
        while (current != null) {
            //while runner does not reach till current
            while (runner != current) {
                if (runner.data.equals(current.data)) {
                    //delete current
                    Node tmp = current.next;
                    previous.next = tmp;
                    current.next = null;     //eligible for GC
                    current = tmp;  //update current to next node
                    break;   //cause all other duplicates are already removed since we are moving from start to end. So whenever first duplicate current matches duplicate runner it removes
                    //so there is no possibility of extra duplicates. cause extra will be removed when current reaches to them.
                }
                runner = runner.next;
            }
            if (runner == current) {
                previous = current;
                current = current.next;
            }
        }
    }
    /**
     * Nth last means eg 2nd last, 3rd last.
    *
     */
    public Node findNthLastElement(int n) {
        //we will iterate though while keeping the count of size
        Node runner = head;
        Node expected = head;  //initially assign expected as head
        Node ret = null;
        int cnt = 0;
        while (runner != null) {
            cnt++;
            if (cnt - n > -1) {
                ret = runner;
            }
            runner = runner.next;
        }
        return ret;
    }


    class Node {
        Node next;
        Integer data;
        Node(Integer data) {
            this.data = data;
            next = null;
        }
    }
    
    public LinkedList addLists(LinkedList list1, LinkedList list2){
        LinkedList ret = new LinkedList();
        Node n1 = list1.head;
        Node n2 = list2.head;
        
        Node n=n1;
        Node m = n2;
        int carry = 0;
        
        while(n!=null){
            Node x;
            if(m!=null){
                int sum = n.data + m.data + carry;
                ret.addNode(sum>9?(sum%10):sum);
                carry=sum/10;
                m=m.next;
            }else{
                int sum = n.data + carry;
                ret.addNode(sum);
                carry=0;
            }
            n=n.next;
        }
        return ret;
    }
    
    public static void main(String[] args) {
        LinkedList ll = new LinkedList();
        ll.addNode(1);
        ll.addNode(2);
        ll.addNode(3);
        ll.addNode(4);
        ll.addNode(5);
        ll.addNode(6);
        ll.addNode(7);
        ll.addNode(8);
        ll.addNode(9);
        ll.addNode(10);
        printList(ll);
        ll.deleteNode(2);
        printList(ll);
        //        ll.addNode(9);
        //        ll.deleteDuplicates2();
        System.out.println(ll.findNthLastElement(3).data);
        
        LinkedList lst1 = new LinkedList();
        LinkedList lst2 = new LinkedList();
        lst1.addNode(3);
        lst1.addNode(1);
        lst1.addNode(5);
        
        lst2.addNode(5);
        lst2.addNode(9);
//        lst2.addNode(2);
        
        LinkedList l3=ll.addLists(lst1, lst2);
        System.out.println("sum: " + l3);
        printList(l3);
    }
    

    static void ReversePrint(Node head) {
        if(head == null) return;
        ReversePrint(head.next);
        System.out.println(head.data);
    }
    
	static void printList(LinkedList head){
		Node n = head.head;
		while(n != null){
			System.out.print(n.data + "->");
			n = n.next;
		}
		System.out.println();
	}

}

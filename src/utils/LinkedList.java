package utils;

//[]<->[]<->[]<->[]<->[]
//					   ^
//					   |
//					 head

public class LinkedList<T> {
	
	private Node<T> head;
	private Node<T> tail;
	
	//Indsætter en node i listen.
	public void insert(Node<T> prev, T object){
		Node<T> newNode = new Node<T>(object, prev, prev.next);
		if(prev==head)
		{
			head = newNode;
		}
		if(prev.next!=null)
		{
			prev.next.prev = newNode;
		}
		prev.next = newNode;
		
	}
	
	Node<T> get(int index)
	{
		Node<T> n = head;
		for(int i=0;i<index;++i)
		{
			if(n==null)
				return null;
			n = n.prev;
		}
		return n;
	}
	
	//Tilføjer et objekt efter head.
	public void insert(T object){
		Node<T> newNode = new Node<T>(object, head, null);
		if(head==null){
			head = newNode;
			tail = newNode;
			head.next = null;
			tail.prev = null;
		}
		else{
			head.next = newNode;
			head.next.prev = head;
			head = newNode;
		}	
	}
	
	public Node<T> getHead(){
		return head;
	}
	
	public Node<T> getTail(){
		return tail;
	}
	
	public void remove(Node<T> n){
		if(n.prev!=null)
			n.prev.next = n.next;
		if(n.next!=null)
			n.next.prev = n.prev; 
		if(n==head)
			head = n.prev;
		if(n==tail)
			tail = n.next;
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.insert(1);
		list.insert(2);
		list.insert(3);
		list.insert(4);
		list.insert(list.get(1), 5);
		
		Node<Integer> node = list.head;
		while(node!=null)
		{
			System.out.println(node.getKey());
			node = node.prev;
		}
		
		System.out.println();
		System.out.println(list.getHead().getKey());
		System.out.println(list.getTail().getKey());
		System.out.println();
		
		list.remove(list.head);
		list.remove(list.tail);
		
		System.out.println(list.getHead().getKey());
		System.out.println(list.getTail().getKey());
	}
}

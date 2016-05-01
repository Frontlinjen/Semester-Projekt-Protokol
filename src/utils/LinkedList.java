package utils;

//[]<->[]<->[]<->[]<->[]
//					   ^
//					   |
//					 head
public class LinkedList<T> {
	
	private Node<T> head;
	//Indsætter en node i listen.
	public Node<T> insert(Node<T> prev, T object){
		Node<T> newNode = new Node<T>(object, prev, prev.next);
		//inserts an object at the tail
		if(prev==null)
		{
			Node<T> tail = head;
			while(tail.prev!=null)
			{
				tail = tail.prev;
			}
			newNode.next = tail;
			tail.prev = newNode;
		}
		if(prev==head)
		{
			head.next = newNode;
			newNode.prev = head;
			head = newNode;
			
		}
		if(prev.next!=null)
		{
			prev.next.prev = newNode;
			prev.next = newNode;
		}
		return newNode;
		
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
		if(head!=null)
		{
			head.next = newNode;
			newNode.prev = head;
		}
		head = newNode;
	}
	
	public Node<T> getHead(){
		return head;
	}
	
	public void remove(Node<T> n){
		if(n.prev!=null)
			n.prev.next = n.next;
		if(n.next!=null)
			n.next.prev = n.prev; 
		if(n==head)
			head = n.prev;
	}
	
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.insert(1);
		list.insert(2);
		list.insert(3);
		list.insert(4);
		list.insert(list.get(1), 5);
		list.remove(list.get(0));	
		
		
		Node<Integer> node = list.head;
		while(node!=null)
		{
			System.out.println(node.key);
			node = node.prev;
		}
	}
}

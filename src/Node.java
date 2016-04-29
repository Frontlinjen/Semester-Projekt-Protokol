public class Node<T> {
	T key;
	Node<T>prev;
	Node<T> next;
	
	public Node(T key, Node prev, Node next){
		this.key = key;
		this.prev = prev;
		this.next = next;
	}
}

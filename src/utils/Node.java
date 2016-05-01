package utils;
public class Node<T> {
	private T key;
	public T getKey() {
		return key;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public Node<T> getNext() {
		return next;
	}

	Node<T>prev;
	Node<T> next;
	
	public Node(T key, Node<T> prev, Node<T> next){
		this.key = key;
		this.prev = prev;
		this.next = next;
	}
}

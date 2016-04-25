import java.io.*;
import java.net.*;

public class SPPclient {
	
	 public static void main(String args[]) throws Exception{ 
		 BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		 DatagramSocket clientSocket = new DatagramSocket();
		 InetAddress IPAddress = InetAddress.getByName("localhost");
		 byte[] sendData = new byte[1024];
		 byte[] receiveData = new byte[1024];
		 String sentence = inFromUser.readLine();
		 sendData = sentence.getBytes();
		 DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		 clientSocket.send(sendPacket);
		 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		 clientSocket.receive(receivePacket);
		 String modifiedSentence = new String(receivePacket.getData());
		 System.out.println("FROM SERVER:" + modifiedSentence);
		 clientSocket.close();
		 }
	 
	 public void receivePacket(){
		 int Acknumber = 0;
		 int counter = 0;
	 }
	 
	 static class Node{
		 int key;
		 Node next;
		 
		 public Node(int key, Node next){
			 this.key = key;
			 this.next = next;
		 }
	 }
	 
}

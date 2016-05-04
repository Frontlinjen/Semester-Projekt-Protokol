package tests;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import SPP.ClientSocket;
import SPP.ServerSocket;
import SPP.SPPpacket;

public class SPPSocketTest {


	public static void main(String[]args) throws IOException{
		(new Server2()).start();
		ClientSocket socket = new ClientSocket("localhost", 33000);
		socket.connect();
		System.out.println("!!!! SENDING TEST !!!!");
		socket.sendData("Hi server!".getBytes());
		while(true)
		{
			byte[] dat = socket.getData();
			System.out.println("Client result: " + new String(dat));
		}
	}
	

}

class Server2 extends Thread{

	public void run() {
				ServerSocket server = new ServerSocket(33000);
				server.connect();
				server.sendData("Hi client!".getBytes());
				while(true)
				{
					byte[] dat = server.getData();
					System.out.println("Server result: " + new String(dat));
				}
		
	}
	
}
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
		ClientSocket socket = new ClientSocket("localhost", 32000);
		socket.connect();
		socket.sendData("Test".getBytes());
		
	}
	

}

class Server2 extends Thread{

	public void run() {
				ServerSocket server = new ServerSocket(33000);
				server.connect();
				byte[] dat = server.getData();
				System.out.println(new String(dat ));
		
	}
	
}
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
		try
		{
			socket.sendData("Hi server!".getBytes());
			while(socket.isConnected())
			{
				byte[] dat = socket.getData();
				String result = new String(dat);
				System.out.println("Client result: " + result);
				if(result.equals("Hi client!"))
				{
					socket.shutdown();
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Disconnected..");
		}
	}
	

}

class Server2 extends Thread{

	public void run() {
				ServerSocket server = new ServerSocket(33000);
				server.connect();
				try
				{
					server.sendData("Hi client!".getBytes());
					while(server.isConnected())
					{
						byte[] dat = server.getData();
						System.out.println("Server result: " + new String(dat));
					}
				}
				catch(Exception e)
				{
					System.out.println("Disconnected..");
				}
		
	}
	
}
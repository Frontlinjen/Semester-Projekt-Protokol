package tests;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import SPP.ClientSocket;
import SPP.ServerSocket;
import utils.LinkedList;
import SPP.SPPpacket;

public class SPPSocketTest {


	public static void main(String[]args) throws IOException{
		List<String> serverResults = new ArrayList<String>();
		(new Server2(serverResults)).start();
		ClientSocket socket = new ClientSocket("localhost", 33000);
		socket.connect();
		System.out.println("!!!! SENDING TEST !!!!");
		List<String> results = new ArrayList<String>();
		try
		{
			socket.sendData("Hi server!".getBytes());
			while(socket.isConnected())
			{
				byte[] dat = socket.getData();
				String result = new String(dat);
				results.add(result);
				if(result.equals("Hi client!"))
				{
					socket.shutdown();
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("client disconnected..");
		}
		System.out.println("Client recieved:");
		for (String s : results) {
			System.out.println(s);
		}
		System.out.println("Server recieved:");
		for (String string : serverResults) {
			System.out.println(string);
		}
	}
	

}

class Server2 extends Thread{
	List<String> results;
	Server2(List<String> results)
	{
		this.results = results;
	}
	public void run() {
				ServerSocket server = new ServerSocket(33000);
				server.connect();
				try
				{
					server.sendData("Hi client!".getBytes());
					while(server.isConnected())
					{
						byte[] dat = server.getData();
						results.add(new String(dat));
					}
				}
				catch(Exception e)
				{
					System.out.println("server disconnected..");
				}
		
	}
	
}
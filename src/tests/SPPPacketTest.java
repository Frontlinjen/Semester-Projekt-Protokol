package tests;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import SPP.SPPpacket;

public class SPPPacketTest {


	public static void main(String[]args) throws IOException{
		(new Server()).start();
		DatagramSocket socket = new DatagramSocket(32000);
		SPPpacket spp = new SPPpacket();
		spp.setData("Test".getBytes());
		byte[] data = spp.getByteStream();
		DatagramPacket packet = new DatagramPacket(data, data.length);
		packet.setPort(33000);
		packet.setAddress(InetAddress.getByName("localhost"));
		socket.send(packet);
		
	}
	

}

class Server extends Thread{

	public void run() {
			try {
				DatagramSocket server = new DatagramSocket(33000);
				byte[] data = new byte[128];
				DatagramPacket packet = new DatagramPacket(data, data.length);
				server.receive(packet);
				SPPpacket p = new SPPpacket(data);
				String s = new String(p.getData());
				System.out.println(s);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
}
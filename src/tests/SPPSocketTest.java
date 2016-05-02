package tests;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import SPP.SPPSocket;
import SPP.SPPpacket;

public class SPPSocketTest {


	public static void main(String[]args) throws IOException{
		(new Server2()).start();
		SPPSocket socket = new SPPSocket(-1, 32000, InetAddress.getByName("localhost"), 1);
		SPPpacket spp = new SPPpacket();
		spp.setData("Test".getBytes());
		byte[] data = spp.getByteStream();
		DatagramPacket packet = new DatagramPacket(data, data.length);
		packet.setPort(33000);
		packet.setAddress(InetAddress.getByName("localhost"));
		socket.sendPacket(spp);
		
	}
	

}

class Server2 extends Thread{

	public void run() {
			try {
				DatagramSocket server = new DatagramSocket(33000);
				byte[] inData = new byte[128];
				DatagramPacket packet = new DatagramPacket(inData, inData.length);
				server.receive(packet);
				//Copies only the data recived and removed the nulls 
				byte[] data = new byte[packet.getLength()];
				for (int i = 0; i < data.length; i++) {
					data[i] = inData[i];
				}
		
				SPPpacket p = new SPPpacket(data);
				for(byte b : p.getData())
				{
					System.out.print(b + " ");
				}
				System.out.print("\n");
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
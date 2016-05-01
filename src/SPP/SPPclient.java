package SPP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import utils.LinkedList;
import utils.Node;

public class SPPclient {
	

	LinkedList<SPPpacket> buffer = new LinkedList<SPPpacket>();
	Node<SPPpacket> readyPackagesIndex;
	int expectedSeq;
	DatagramSocket socket = null;
	DatagramPacket packet;
	
	public byte[] getNextPacketData(){
		return null;
		
	}
	
	public void listenPacket(int port){
		try{
			socket = new DatagramSocket(port);
		}
		catch(SocketException e){
			System.out.println("Unable to open socket on port: " + port);
			e.printStackTrace();
		}
	}
}

package SPP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import utils.LinkedList;


public class SPPserver {
	

	int currentSeq = 0;
	InetAddress dstIP;
	int dstSocket;
	SPPpacket lastSentPacket;
	LinkedList<SPPpacket> outBuffer = new LinkedList<SPPpacket>();
	DatagramSocket socket = null;
	
	//Recieves ACK messages
	public void ListenSocket(int port){
		try {
			
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Unable to open socket on port: " + port);
			e.printStackTrace();
		}
	}
	public void SendData(byte[] data){
			SPPpacket newPacket = new SPPpacket();
			newPacket.setSeqnr(currentSeq);
			newPacket.setData(data);
			outBuffer.insert(newPacket);
			byte[] packetBytes = newPacket.getByteStream();
			DatagramPacket dp = new DatagramPacket(packetBytes, packetBytes.length, dstIP, dstSocket);			
			try {
				socket.send(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void OnPacketAckTimeOut(SPPpacket id){
		
	}
}
	
	


package SPP;
import java.net.DatagramSocket;
import java.net.SocketException;
import utils.LinkedList;


public class SPPserver {
	

	int currentSeq = 0;
	SPPpacket lastSentPacket;
	LinkedList<SPPpacket> outBuffer = new LinkedList<SPPpacket>();
	DatagramSocket socket = null;
	
	public void ListenSocket(int port){
		try {
			
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Unable to open socket on port: " + port);
			e.printStackTrace();
		}
	}
	public SPPpacket SendData(Byte[] data){
		try{
			
		}
		catch(Exception e){
			
		}
		return null;
		}
	
	private void OnPacketAckTimeOut(SPPpacket id){
		
	}
}
	
	


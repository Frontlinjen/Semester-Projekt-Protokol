import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;


public class SPPserver {
	

	int currentSeq = 0;
	Node<SPPpackage> lastSentPacket;
	LinkedList<SPPpackage> outBuffer = new LinkedList<SPPpackage>();
	DatagramSocket socket = null;
	
	public void ListenSocket(int port){
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Unable to open socket on port: " + port);
			e.printStackTrace();
		}
	}
	public SPPpackage SendData(Byte[] data){
		try{
			
		}
		catch(Exception e){
			
		}
		return null;
		}
	
	private void OnPacketAckTimeOut(Node<SPPpackage> id){
		
	}
}
	
	


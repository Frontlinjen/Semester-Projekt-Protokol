import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

public class SPPserver {
	
	DatagramSocket socket = null;
	List<SPPpackage> buffer = new LinkedList<SPPpackage>();
	int seqack = 0;
	int seqsent;
	
	public void OpenSocket(int port){
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println("Unable to open socket on port: " + port);
			e.printStackTrace();
		}
		
	}
	
	public void CreatePacket(byte[] data){
		SPPpackage packet = new SPPpackage();
		packet.setData(data);
		packet.set   
	}
	
}

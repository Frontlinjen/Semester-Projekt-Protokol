package SPP;
import utils.LinkedList;
import utils.Node;

public class SPPclient {
	

	LinkedList<SPPpacket> buffer = new LinkedList<SPPpacket>();
	Node<SPPpacket> readyPackagesIndex;
	
	int expectedSeq;
	
	public byte[] getNextPacketData(){
		
	}
	
	public void listenPacket(int port){
		
	}
}

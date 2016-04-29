import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

import javax.xml.soap.Node;

public class SPPclient {
	

	List<SPPpacket> buffer = new LinkedList();
	Node<SPPpacket> readyPackagesIndex = new Node<SPPpacket>();
	int expectedSeq;
	
	public byte[] getNextPacketData(){
		
	}
	
	public void listenPacket(int port){
		
	}
}

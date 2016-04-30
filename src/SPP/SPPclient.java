package SPP;
import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.List;

import javax.xml.soap.Node;

import utils.LinkedList;

public class SPPclient {
	

	LinkedList<SPPpacket> buffer = new LinkedList<SPPacket>();
	Node<SPPpacket> readyPackagesIndex = new Node<SPPpacket>();
	int expectedSeq;
	
	public byte[] getNextPacketData(){
		
	}
	
	public void listenPacket(int port){
		
	}
}

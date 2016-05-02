package SPP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import SPP.SPPpacket;
import utils.LinkedList;
import utils.Node;

public class SPPclient {
	

	LinkedList<SPPpacket> buffer = new LinkedList<SPPpacket>();
	Node<SPPpacket> readyPackagesEnd;
	int expectedSeq = 0;
	DatagramSocket socket = null;
	SPPpacket packet = new SPPpacket();

	
	public byte[] getNextPacketData(){
		return null;
	}
	private void addPacketToBuffer(SPPpacket packet)
	{
		Node<SPPpacket> node = buffer.getHead();
		do
		{
			if(node.getKey().getSeqnr() < packet.getSeqnr())
			{
				break;
			}
		}while((node = node.getNext())!=null);
		Node<SPPpacket> n = buffer.insert(node, packet);
		//if prev is null, then this is the first element in the buffer
		if(n.getPrev()==null)
		{
			readyPackagesEnd = n;
		}
		if(readyPackagesEnd.getKey().getSeqnr()!=expectedSeq)
		{
			return;
		}
		else 
		{
			do
			{
				//The seq nr matched, add one more available package for the application.
				readyPackagesEnd = readyPackagesEnd.getNext();
				expectedSeq = (readyPackagesEnd.getKey().getSeqnr()+readyPackagesEnd.getKey().getData().length)+1;
			}while(expectedSeq==readyPackagesEnd.getNext().getKey().getSeqnr());
		}
	}
		
	
	public void recievePacket(SPPpacket p){
		if(p.getChecksum() == p.calculateChecksum()){
			addPacketToBuffer(p);	
		}	
	}
	
	public void sendACK(int ack)
	{
		
	}	
	
}

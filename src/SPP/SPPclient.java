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
	
	//First packet recieved should set the seqnr used for the connection
	boolean setSeq = true;

	public SPPclient(int startSeq)
	{
		expectedSeq = startSeq;
	}
	public SPPpacket getNextPacket(){
		System.out.println("SPPclient: Getting package");
		if(buffer.getTail()==null)
		{
			System.out.println("Buffer was empty");
			return null;
		}
		SPPpacket p = buffer.getTail().getKey();
		System.out.println("Got packet: " + p.getSeqnr() + " Expected: " + expectedSeq);
		if(p.getSeqnr() < expectedSeq)
		{
			System.out.println("Recieved packet already recieved.. discarding");
		}
		else if(p.getSeqnr() == expectedSeq || setSeq){
			buffer.remove(buffer.getTail());
			if(setSeq)
			{
				expectedSeq = p.getSeqnr();
				setSeq = false;
			}
			System.out.println("Returned expected packet: " + p);
			expectedSeq += p.getData().length + 1;
			
			return p;
		}
		return null;
	}
	private void addPacketToBuffer(SPPpacket packet)
	{
		System.out.println("Adding packet to buffer: " + packet.toString());
		Node<SPPpacket> node = buffer.getHead();
		if(node==null)
		{
			System.out.println("Added as first element in buffer.");
			buffer.insert(packet);
			readyPackagesEnd = node;
			return;
		}	
		do
		{
			if(node.getKey().getSeqnr() < packet.getSeqnr())
			{
				break;
			}
			
		}while((node = node.getNext())!=null);
		System.out.println("Element added");
		buffer.insert(node, packet);
		
//		if(buffer.getTail()!=expectedSeq)
//		{
//			System.out.println("Got packet: " + buffer.getTail().getKey().getSeqnr() + " Expected: " + expectedSeq);
//			return;
//		}
//		else 
//		{
//			do
//			{
//				//The seq nr matched, add one more available package for the application.
//				readyPackagesEnd = readyPackagesEnd.getNext();
//				expectedSeq = (readyPackagesEnd.getKey().getSeqnr()+readyPackagesEnd.getKey().getData().length)+1;
//			}while(expectedSeq==readyPackagesEnd.getNext().getKey().getSeqnr());
//		}
	}
		
	
	public void recievePacket(SPPpacket p){
			addPacketToBuffer(p);	
	}
	
	public void setSeqNr(int seqnr){
		this.expectedSeq = seqnr;
	}
	
}

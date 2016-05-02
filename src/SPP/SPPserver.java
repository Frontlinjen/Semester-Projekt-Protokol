package SPP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import utils.LinkedList;
import utils.Node;

class SPPTimeout extends TimerTask
{
	SPPserver packetHandler;
	DatagramPacket packet;
	public SPPTimeout(DatagramPacket packet, SPPserver reference)
	{
		this.packet = packet;
		packetHandler = reference;
	}
	@Override
	public void run() {
		packetHandler.OnPacketAckTimeOut(packet);
	}
}
class SeqTimerTuple
{
	public SeqTimerTuple(int seq, TimerTask task)
	{
		this.seq = seq;
		this.task = task;
	}
	int seq;
	public int getSeq() {
		return seq;
	}
	public TimerTask getTask() {
		return task;
	}
	TimerTask task;
}

public class SPPserver {
	
	Timer timeoutScheduler = new Timer();
	int currentSeq = 0;
	InetAddress dstIP;
	int remotePort;
	SPPpacket lastSentPacket;
	LinkedList<SeqTimerTuple> outBuffer = new LinkedList<SeqTimerTuple>();
	DatagramSocket socket = null;
	
	public SPPserver(InetAddress ip, int remotePort, DatagramSocket socket, int startSeq)
	{
		currentSeq = startSeq;
		dstIP = ip;
		this.remotePort = remotePort;
		this.socket = socket;
	}
	
	public void Send(SPPpacket data){
			data.setSeqnr(currentSeq);
			byte[] packetBytes = data.getByteStream();
			//Increase the seq to match the next packet
			currentSeq += data.getData().length+1;
			
			DatagramPacket dp = new DatagramPacket(packetBytes, packetBytes.length, dstIP, remotePort);
			TimerTask timeout = new SPPTimeout(dp, this);
			SeqTimerTuple tuple = new SeqTimerTuple(data.getSeqnr(), timeout);
			outBuffer.insert(tuple);
			//Sends the packet right away, then every 100th ms until an ACK is recieved 
			timeoutScheduler.scheduleAtFixedRate(timeout, 0, 10000);
			
			System.out.println("package: " + data.getSeqnr() + " has been sent");
			
	}
	private void SendPacket(DatagramPacket dp)
	{
		try {
			socket.send(dp);
			System.out.println("Send packet to: " + dp.getAddress() + ":" + dp.getPort() + " length: " + dp.getLength());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void OnAckRecieved(int ack)
	{
		Node<SeqTimerTuple> node = outBuffer.getHead();
		while(node!=null)
		{
			SeqTimerTuple obj = node.getKey()  ;
			if(obj.seq==ack)
			{
				obj.task.cancel();
				outBuffer.remove(node);
				System.out.println("Retransmission of node " + obj.seq + " ended!");
				
				// 1 in 20 chance of removing depricated tasks from the list
				if((Math.random()*20)==10)
					timeoutScheduler.purge();
				return;
			}
			node = node.getPrev();
		}
		System.out.println("Non-matching seq recieved! " + ack);
		
		
	}
	public void OnPacketAckTimeOut(DatagramPacket dp){
		//Resends packet
		this.SendPacket(dp);
	}
}
	
	


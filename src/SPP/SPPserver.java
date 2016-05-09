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
	boolean cancel = false;
	
	public void cancelNextRun(boolean b)
	{
		cancel = b;
	}
	public SPPTimeout(DatagramPacket packet, SPPserver reference)
	{
		this.packet = packet;
		packetHandler = reference;
	}
	@Override
	public void run() {
		if(cancel)
		{
			this.cancel();
		}
		else
		{
			packetHandler.OnPacketAckTimeOut(packet);
		}
		
	}
}
class SeqTimerTuple
{
	private int seq;
	private SPPTimeout task;
	public SeqTimerTuple(int seq, SPPTimeout task)
	{
		this.seq = seq;
		this.task = task;
	}
	
	public int getSeq() {
		return seq;
	}
	public SPPTimeout getTask() {
		return task;
	}
	
}

public class SPPserver {
	
	Timer timeoutScheduler = new Timer();
	int currentSeq = 0;
	final int resendDelay = 10;
	InetAddress dstIP;
	int remotePort;
	SPPpacket lastSentPacket;
	LinkedList<SeqTimerTuple> outBuffer = new LinkedList<SeqTimerTuple>();
	DatagramSocket socket = null;
	
	public SPPserver(InetAddress ip, int remotePort, DatagramSocket socket, int startSeq)
	{
		System.out.println("Server started with SeqID: " + startSeq);
		currentSeq = startSeq;
		dstIP = ip;
		this.remotePort = remotePort;
		this.socket = socket;
	}
	public void stopTimers()
	{
		timeoutScheduler.cancel();
	}
	public void Send(SPPpacket data, boolean retransmit){
			data.setSeqnr(currentSeq);
			byte[] packetBytes = data.getByteStream();			
			
			DatagramPacket dp = new DatagramPacket(packetBytes, packetBytes.length, dstIP, remotePort);
			if(retransmit)
			{
				SPPTimeout timeout = new SPPTimeout(dp, this);
				SeqTimerTuple tuple = new SeqTimerTuple(data.getSeqnr(), timeout);
				outBuffer.insert(tuple);
				
				System.out.println("SENDING PACKET: " + data);
				//Sends the packet right away, then every 100th ms until an ACK is recieved 
				timeoutScheduler.scheduleAtFixedRate(timeout, resendDelay, resendDelay);
				
			}
			System.out.println("package: " + data.getSeqnr() + " has been sent");
			currentSeq += data.getData().length +1;
			SendPacket(dp);
			
			
			
			
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
			if(obj.getSeq()==ack)
			{
				obj.getTask().cancelNextRun(true);
				outBuffer.remove(node);
				System.out.println("Retransmission of node " + obj.getSeq() + " ended! Now waiting for ack for: " + outBuffer.length());
				
				// 1 in 20 chance of removing depricated tasks from the list
				if((int)(Math.random()*20)==10)
					timeoutScheduler.purge();
				return;
			}
			node = node.getPrev();
		}
		System.out.println("Non-matching ack recieved! " + ack);
		
		
	}
	public void OnPacketAckTimeOut(DatagramPacket dp){
		//Resends packet
		System.out.println("Retransmitting packet: " + new SPPpacket(dp.getData()));
		this.SendPacket(dp);
	}
}
	
	


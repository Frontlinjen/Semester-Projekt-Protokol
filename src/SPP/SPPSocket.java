package SPP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SPPSocket {
	private SPPclient client;
	private SPPserver server;
	private SPPpacket packet;
	private DatagramSocket socket;
	final int MAX_PACKETSIZE = 2048;
	public SPPSocket(DatagramSocket s, int remotePort, InetAddress address, int clientSeq)
	{
		socket = s;
		server = new SPPserver(address, remotePort, s, (int)(Math.random()%Integer.MAX_VALUE));
		client = new SPPclient(clientSeq);
	}

	public SPPSocket(int localPort, int remotePort, InetAddress address, int startSeq) throws SocketException
	{
		this(localPort < 0 ? new DatagramSocket() : new DatagramSocket(localPort), remotePort, address, startSeq);
	}
	public void sendData(byte[] data)
	{
		SPPpacket newPacket = new SPPpacket();
		newPacket.setData(data);
		sendPacket(newPacket);
	}
	public void sendPacket(SPPpacket p)
	{
		server.Send(p);
	}
	public void setClientSeqNr(int seqnr){
		client.setSeqNr(seqnr);
	}
	public SPPpacket getPacket()
	{
		do{
		try
		{
			byte[] inBuffer = new byte[MAX_PACKETSIZE];
			DatagramPacket recievePacket = new DatagramPacket(inBuffer, inBuffer.length);
			socket.receive(recievePacket);
			
			//Copies only the data received and removed the nulls 
			byte[] data = new byte[recievePacket.getLength()];
			for (int i = 0; i < data.length; i++) {
				data[i] = inBuffer[i];
			}
			SPPpacket newPacket = new SPPpacket(data);
			
			if(packet.getChecksum() == packet.calculateChecksum()){
			
				client.recievePacket(newPacket);
				
				SPPpacket newerpacket = new SPPpacket(data);
				newerpacket.setAck();
				newerpacket.setAcknr(newPacket.getSeqnr());
				server.Send(newerpacket);
				
			if(newPacket.isAck())
			{
				server.OnAckRecieved(newPacket.getAcknr());
			}
			}
		}
		catch(IOException e)
		{
			System.out.println("Unable to get packet");
			e.printStackTrace();
		}
		return null;
	}
		while(client.getNextPacket()!= null);
	
		
	}

}

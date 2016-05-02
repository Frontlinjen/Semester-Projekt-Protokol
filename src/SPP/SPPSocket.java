package SPP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SPPSocket {
	private SPPclient client;
	private SPPserver server;
	private DatagramSocket socket;
	final int MAX_PACKETSIZE = 2048;
	public SPPSocket(DatagramSocket s, int remotePort, InetAddress address, int startSeq)
	{
		socket = s;
		server = new SPPserver(address, remotePort, s, startSeq);
		client = new SPPclient();
	}

	public SPPSocket(int localPort, int remotePort, InetAddress address, int startSeq) throws SocketException
	{
		this(new DatagramSocket(localPort), remotePort, address, startSeq);
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
	public SPPpacket getPacket()
	{
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
			client.recievePacket(newPacket);
			if(newPacket.isAck())
			{
				server.OnAckRecieved(newPacket.getAcknr());
			}
		}
		catch(IOException e)
		{
			System.out.println("Unable to get packet");
			e.printStackTrace();
		}
		return null;
	}
}

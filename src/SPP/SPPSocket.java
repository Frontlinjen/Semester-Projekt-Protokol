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

	public SPPSocket(DatagramSocket s, int remotePort, InetAddress address, int clientSeq)
	{
		socket = s;
		server = new SPPserver(address, remotePort, s, (int)(Math.random()*Integer.MAX_VALUE));
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
		sendPacket(newPacket, true);
		System.out.println("The data has been sent.");
	}
	public void sendPacket(SPPpacket p, boolean retransmit)
	{
		server.Send(p, retransmit);
		System.out.println("the following packet has been sent: " + p.getSeqnr());
	}
	public void setClientSeqNr(int seqnr){
		client.setSeqNr(seqnr);
	}
	public SPPpacket getPacket()
	{
		SPPpacket p = null;

		do{
			try
			{
				System.out.println("Waiting for expected packet..");
				byte[] inBuffer = new byte[MAX_PACKETSIZE];
				DatagramPacket recievePacket = new DatagramPacket(inBuffer, inBuffer.length);
				socket.receive(recievePacket);
				System.out.println("Got datagram from socket");
				//Copies only the data received and removed the nulls 
				byte[] data = new byte[recievePacket.getLength()];
				for (int i = 0; i < data.length; i++) {
					data[i] = inBuffer[i];
				}
				SPPpacket newPacket = new SPPpacket(data);
				System.out.println("PACKET RECIEVED: " + newPacket);
				if(newPacket.getChecksum() == newPacket.calculateChecksum()){
					if(newPacket.isFin())
					{
						System.out.println("Fin request recieved.. shutting down");
						shutdown();
					}
					
					client.recievePacket(newPacket);

					//No use to ack an ack, unless its an ACK SYN packet for 3-way handshake
					if(newPacket.isAck())
					{
						server.OnAckRecieved(newPacket.getAcknr());
						System.out.println("The following acknr has been recieved:" + newPacket.getAcknr());
					}
					
					if(!newPacket.isAck() || newPacket.isSyn()) //Replies to ACK-SYN
					{
					
						SPPpacket ackPacket = new SPPpacket();
						ackPacket.setAck();
						ackPacket.setAcknr(newPacket.getSeqnr());
						server.Send(ackPacket, false);
						System.out.println("The following acknr has been sent: " + ackPacket.getAcknr());
					}
				}
				else
				{
					System.out.println("The checksum didnt match!");
				}
			}
			catch(IOException e)
			{
				System.out.println("Unable to get packet");
				e.printStackTrace();
			}
			if(p!=null)
				System.out.println("Length: " + p.getData().length);
		}while(((p = client.getNextPacket())== null));
		
		System.out.println("Passing expected packet along..");
		return p;

	}
	
	public void shutdown(){
		SPPpacket Fin = new SPPpacket();
		Fin.setFin();
		server.Send(Fin, false);
		server.stopTimers();
		socket.close();
		server = null;
		client = null;
	}

}

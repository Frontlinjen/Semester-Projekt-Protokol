package SPP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerSocket {

	private DatagramSocket socket = null;
	private SPPSocket connection = null;
	public ServerSocket(int port)
	{
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isConnected()
	{
		return connection!=null;
	}
	
	public byte[] getData()
	{
		SPPpacket p = connection.getPacket();
		return p.getData();
	}
	public void sendData(byte[] data)
	{
		connection.sendData(data);
	}
	
	
	public void connect()
	{
		SPPpacket newPacket = null;
		DatagramPacket recievePacket = null;
		System.out.println("Waiting for connection...");
		do
		{
			byte[] inBuffer = new byte[2048];
			System.out.println("byte[] made");
			recievePacket = new DatagramPacket(inBuffer, inBuffer.length);
			System.out.println("Datagrampacket received.");
			try {
				socket.receive(recievePacket);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//Copies only the data recived and removed the nulls 
			byte[] data = new byte[recievePacket.getLength()];
			for (int i = 0; i < data.length; i++) {
				data[i] = inBuffer[i];
			}
			newPacket = new SPPpacket(data);
			System.out.println("SPPpacket made from the received packet");
			//If not a syn packet, ask them to try and reconnect!
			if(!newPacket.isSyn())
			{
				System.out.println("Recieved non-syn packet");
				SPPpacket rstPacket = new SPPpacket();
				rstPacket.setRst();
				byte[] byteStream = rstPacket.getByteStream();
				//The RST packet can be fire and forget, hence we don't need buffers
				try {
					socket.send(new DatagramPacket(byteStream, byteStream.length, recievePacket.getAddress(), recievePacket.getPort()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}while(!newPacket.isSyn());
			connection = new SPPSocket(socket, recievePacket.getPort(), recievePacket.getAddress(),  newPacket.getSeqnr());
			SPPpacket packet = new SPPpacket();
			packet.setAck();
			packet.setAcknr(newPacket.getSeqnr());
			packet.setSyn();
			//Sends SYN-ACK
			connection.sendPacket(packet);
			System.out.println("Connection made and a packet is send with ack and syn");
			
			SPPpacket ackPacket;
			do
			{
				ackPacket = connection.getPacket();
			}while(!ackPacket.isAck() || ackPacket.isRst());
			
			//Resets connection
			if(ackPacket.isRst())
			{
				connection = null;
				System.out.println("packet is reset");
			}
			else
			{
				System.out.println("Connection established to: " + recievePacket.getAddress());
			}
			
	}
	
}

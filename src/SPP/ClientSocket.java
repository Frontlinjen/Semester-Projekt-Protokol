package SPP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import SPP.SPPSocket;

public class ClientSocket {
	
		SPPSocket connection = null;
		int remotePort;
		InetAddress remoteIp;
		
		
		public ClientSocket(String ip, int port){
			System.out.println("ClientSocket til IP: " + ip + " med port: " + port);
			this.remotePort = port;
			try {
				this.remoteIp = InetAddress.getByName(ip);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		public boolean isConnected()
		{
			return connection!=null;
		}
		
		public byte[] getData() throws Exception
		{
			if(!isConnected())
				throw new Exception("Not connected");
			SPPpacket p;
			do
			{
				 p = connection.getPacket();
				 System.out.println(p.getData().length);
			}while(p.getData().length==0); //Skip control packages
			return p.getData();
		}
		public void sendData(byte[] data) throws Exception
		{
			if(!isConnected())
				throw new Exception("Not connected");
			connection.sendData(data);
		}
		
		public void connect(){
			try {
				SPPSocket clientSocket = new SPPSocket(-1, remotePort, remoteIp, 0);
				System.out.println("Sender SYN til: " + remoteIp);
				SPPpacket packet = new SPPpacket();
				packet.setSyn();
				packet.setSeqnr((int)Math.random()%Integer.MAX_VALUE);
				clientSocket.sendPacket(packet, true);
				
				SPPpacket Acknr;
				do{
					System.out.println("Venter p� ACKSYN");
					Acknr = clientSocket.getPacket();
				}while(!Acknr.isSyn() || Acknr.isRst());
				
				if(Acknr.isRst()){
					return;
				}
				connection = clientSocket;
				System.out.println("Forbindelse Oprettet");
			} catch (IOException e) {
				System.out.println("Fejl i oprettelse af forbindelsen.");
				e.printStackTrace();
			}	
		}
		
		public void shutdown(){
			connection.shutdown();
			connection = null;
		}
}

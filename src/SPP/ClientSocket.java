package SPP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import SPP.SPPSocket;

public class ClientSocket {
	
	//SYN til ipadresse, indtil den får SYNACK tilbage, derefter skal den oprette en
		//SPP socket så den kan snakke med serveren.
		
		DatagramSocket socket = null;
		SPPSocket connection = null;
		int remotePort;
		InetAddress remoteIp;
		
		
		public ClientSocket(String ip, int port){
			System.out.println("Der bliver connected til IP: " + ip + " med port: " + port);
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
		
		public byte[] getData()
		{
			SPPpacket p = connection.getPacket();
			return p.getData();
		}
		public void sendData(byte[] data)
		{
			connection.sendData(data);
		}
		
		public void connect(){
			try {
				SPPSocket clientSocket = new SPPSocket(-1, 8080, remoteIp, 0);
				System.out.println("Sender SYN til: " + remoteIp);
				SPPpacket packet = new SPPpacket();
				packet.setSyn();
				packet.setSeqnr((int)Math.random()%Integer.MAX_VALUE);
				clientSocket.sendPacket(packet);
				System.out.println("Venter p� ACKSYN");
				SPPpacket Acknr;
				do{
					Acknr = clientSocket.getPacket();
				}
				while(!Acknr.isSyn() || Acknr.isRst());
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
}

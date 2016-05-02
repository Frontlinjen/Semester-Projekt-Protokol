package SPP;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

import SPP.SPPSocket;


public class ClientSocket {
	
	//SYN til ipadresse, indtil den får SYNACK tilbage, derefter skal den oprette en
		//SPP socket så den kan snakke med serveren.
		
		public void sendSyn(){
			String serverName = "SPP";
			int serverPort = 8080;
			byte[] IP = new byte[]{127, 0, 0, 1};
			InetAddress dstIP = InetAddress.getByAddress(IP);
			int localPort;
			
			System.out.println("Skriv det portnummer du ønsker at forbinde fra.");
			Scanner sc = new Scanner(System.in);
			localPort = sc.nextInt();
			sc.close();
			try {
				SPPSocket clientSocket = new SPPSocket(localPort, 8080, dstIP);
				SPPpacket packet = new SPPpacket();
				packet.setSyn();
				clientSocket.send(packet);
			} catch (IOException e) {
				System.out.println("Fejl i oprettelse af forbindelsen.");
				e.printStackTrace();
			}
		}
		
		public void sendAck(){
			
		}
}

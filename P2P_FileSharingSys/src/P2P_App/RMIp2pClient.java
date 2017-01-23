package P2P_App;

import java.rmi.Naming;

public class RMIp2pClient {
	public static void main(String[] args) throws Exception {

		if (args.length == 3) {
			String url = new String("rmi://"+args[0]+"/p2pServices");
			RMIp2p rmip2p = (RMIp2p)Naming.lookup(url);
			
			//register peer client
			String serverReply = rmip2p.registerPeer(args[1], args[2]);
			System.out.println("IndexServer Reply: "+serverReply);
			
			//continued typing prompt
			if (args[0].equals("download")){
				String filename = args[1];
				serverReply = rmip2p.searchFile(filename);
				System.out.println(serverReply);
				
				//continued typing prompt
				serverReply = rmip2p.downloadFile(args[0], filename);
				System.out.println(serverReply);
			}
			System.out.println("PeerServer Reply: "+serverReply);
			
		} else {
			System.err.println("Usage: RMIp2pClient <index_server_IP> <Peer_name> <Peer_IP>");
		}
		
	}
}

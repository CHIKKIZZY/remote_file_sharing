package P2P_App;

import java.rmi.Naming;

public class RMIp2pServer {
	public static void main(String[] args) throws Exception {
		RMIp2pImpl rmip2pimpl = new RMIp2pImpl();
		Naming.rebind("p2pServices", rmip2pimpl);
		System.out.println("RMIp2p object bound to the name 'p2pServices' and is ready for use...");
	}
}

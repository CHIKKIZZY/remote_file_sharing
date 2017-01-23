package P2P_App;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIp2p extends Remote {
	public String registerPeer(String peerName, String peerIPAddress) throws RemoteException; 
	public String downloadFile(String peerName, String filename) throws RemoteException;
	public String searchFile(String filename) throws RemoteException;
	public String deleteFile(String filename) throws RemoteException;
}

package CentralServer;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import Peer.PeerClientIF;

public interface PeerServerIF extends Remote {
	String registerPeerClient(PeerClientIF peerClient) throws RemoteException;
	File sendFile(String filepath) throws RemoteException;
	PeerClientIF[] searchFile(String file) throws RemoteException;
}

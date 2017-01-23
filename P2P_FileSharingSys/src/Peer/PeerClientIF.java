package Peer;

import java.rmi.Remote;
import java.rmi.RemoteException;

import CentralServer.PeerServerIF;

public interface PeerClientIF extends Remote {
	void acceptFile(PeerClientIF choosenPeer, String filename) throws RemoteException;
	String[] getFiles() throws RemoteException;
	String getName() throws RemoteException;
	String getPeerDir() throws RemoteException;
	PeerServerIF getPeerServer() throws RemoteException;
}

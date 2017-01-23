package CentralServer;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import Peer.PeerClientIF;

public class PeerServer extends UnicastRemoteObject implements PeerServerIF {
	private static final long serialVersionUID = 1L;
	private ArrayList<PeerClientIF> peerClients;
	protected PeerServer() throws RemoteException {
		super();
	}
	
	public synchronized String registerPeerClient(PeerClientIF peerClient) throws RemoteException {
		//register peer and log files
		peerClients.add(peerClient);	
		
		String peerfiles = "\n";
		String[] files = peerClient.getFiles();	
		//list files
		for (int i=0; i<files.length; i++){
			peerfiles += files[i]+"\n";
		}
			
		return "\nPeer '"+peerClient.getName()+"' has registered with central server and logged the following files"+peerfiles+"\n";

	}
	
	public synchronized File sendFile(String filepath) throws RemoteException {
		File destFile = null;
		File srcFile = new File(filepath);
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//peerClients.get(fileDestination).acceptFile(file); 
		return destFile;
	}

	public synchronized PeerClientIF[] searchFile(String file) throws RemoteException {
		PeerClientIF[] peer = new PeerClientIF[peerClients.size()];
		int count = 0;
		for (int l=0; l<peerClients.size(); l++ ) {
			String[] filelist = peerClients.get(l).getFiles();
			for (int a=0; a<filelist.length; a++) {
				if (file.equals(filelist[a])) {
					peer[count] = peerClients.get(l);
					count++;
				}
			}
		}
		return peer;
		
		/*
		 * PeerClientIF peer = null;
		String listofpeers = "\n";
		for (int l=0; l<peerClients.size(); l++ ) {
			String[] filelist = peerClients.get(l).getFiles();
			for (int a=0; a<filelist.length; a++) {
				if (file.equals(filelist[a])) {
					listofpeers += peerClients.get(l).getName();
					peer = peerClients.get(l);
				}
			}
		}
		return peer;
		 */
	}
}

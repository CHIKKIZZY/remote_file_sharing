package Peer;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import CentralServer.PeerServerIF;


//to implement instances of this class as threads use 'Runnable' which will add the 'run' method
public class PeerClient extends UnicastRemoteObject implements PeerClientIF, Runnable { 
	private static final long serialVersionUID = 1L;
	private PeerServerIF peerServer;
	private String name = null;
	private String peerRootDirectoryPath = null;
	private String[] files;

	protected PeerClient(String name, PeerServerIF peerServer) throws RemoteException {
		this.name = name;
		this.peerServer = peerServer;
		
		//create list of files in peer root directory
		try{
			//get peer root directory pathname
			this.peerRootDirectoryPath = System.getProperty("user.dir");
		    System.out.print("Peer Directory is: "+peerRootDirectoryPath.replace("\\", "/")+"\n");
		    
		    //get all files in peer root directory
			File f = new File(peerRootDirectoryPath);
			this.files = f.list();
			
		}catch (Exception e){
		    System.out.println("Peer path Exception caught ="+e.getMessage());
		}		

		//register peer data structure, including files array
		System.out.println(peerServer.registerPeerClient(this));
	}
	
	public String getName() {
		return name;
	}
	public String[] getFiles() {
		return files;
	}
	public String getPeerDir() {
		return peerRootDirectoryPath;
	}
	public PeerServerIF getPeerServer() {
		return peerServer;
	}

	public void acceptFile(PeerClientIF chosenPeer, String filename) throws RemoteException {
		File srcFile;
		File destDir = new File(peerRootDirectoryPath);
		try {
			srcFile = chosenPeer.getPeerServer().sendFile(chosenPeer.getPeerDir()+"\\"+filename);
			try {
			    FileUtils.copyFileToDirectory(srcFile, destDir);
			    System.out.println("File '"+filename+"' has been downloaded\n");
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void run() {
		//read messages from command line
		Scanner cmdline = new Scanner(System.in);
		String command, task, file;
		while (true) {	//continue reading commands
			command = cmdline.nextLine();
			//TODO: do something with commands (7:30)
			task = command.substring(0, command.indexOf(' '));
			file = command.substring(command.indexOf(' '));
			
			if (task.equals("download")) {
				PeerClientIF[] peer;
				try {
					//returns a peer with file
					peer = peerServer.searchFile(file);
					if (peer != null) {
						//list peers with file
						System.out.println("The following Peers has the file you want:");
						for (int i=0; i<peer.length; i++) {
							if (peer[i] != null)
								System.out.println((i+1)+". "+peer[i].getName());
						}
						//prompt user to choose Peer to download from
						System.out.println("Enter number of Peer to download from");
						
						int choice = cmdline.nextInt();
						try {
							acceptFile(peer[choice], file);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		}
	}


}

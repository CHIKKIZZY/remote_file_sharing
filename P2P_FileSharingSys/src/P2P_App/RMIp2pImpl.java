package P2P_App;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIp2pImpl extends UnicastRemoteObject implements RMIp2p {
	private static String peerRootDirectoryPath;
	private static String[][] indexlist = new String[50][3];
	private static int endofarrayentry = 0;
	private static int numofpeers;
	private static final long serialVersionUID = 1L;
	protected RMIp2pImpl() throws RemoteException {
		super();
	}
	@Override
	public String registerPeer(String peerName, String peerIPAddress) throws RemoteException {
		try{
			//get peer root directory pathname
			peerRootDirectoryPath = System.getProperty("user.dir");
		    System.out.print("Peer Directory is: "+peerRootDirectoryPath.replace("\\", "/"));
		    
		    //get all files in peer root directory
			File f = new File(peerRootDirectoryPath);
			String[] files = f.list();
			String peerfiles = "\n";
			
			//transfer array files to indexlist
			for (int i=endofarrayentry; i<endofarrayentry+files.length; i++){
				indexlist[i][0] = peerName;
				indexlist[i][1] = peerIPAddress;
				indexlist[i][2] = files[i-endofarrayentry];
				peerfiles += files[i-endofarrayentry];
			}
			
			numofpeers += 1;	//update peer count
			return "\nPeer '"+peerName+"' has registered with central server and logged the following files"+peerfiles+"\n";
		}catch (Exception e){
		    return "Peer path Exception caught ="+e.getMessage();
		}		
	}
	@Override
	public String downloadFile(String peerName, String filename) throws RemoteException {
		/*
		File source = new File("H:\\work-temp\\file");
		File dest = new File("H:\\work-temp\\file2");
		try {
		    FileUtils.copyFileToDirectory(File srcFile, File destDir)
		} catch (IOException e) {
		    e.printStackTrace();
		}
		*/
		return "\nFile '"+filename+"' has been downloaded\n";
	}
	@Override
	public String searchFile(String filename) throws RemoteException {
		int numofpeerswithfile=0;
		String peerswithfile = "\n";
		for (int i=0; i<indexlist.length; i++) {
			if (indexlist[i][2].equals(filename)){
				peerswithfile += indexlist[i][0]+"\n";
				numofpeerswithfile += 1;
			}
		}
		return "\nthe following "+numofpeerswithfile+" peer(s) has the file"
			+filename+": "+peerswithfile+"Choose a peer to download from...";
	}
	@Override
	public String deleteFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}

package common;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote {
	public String getMessage () throws RemoteException;
	public File renderFO (File inputFO) throws RemoteException;
}


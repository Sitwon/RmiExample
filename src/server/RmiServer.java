package server;

import common.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
	public static final String MESSAGE = "Hello world";

	public RmiServer () throws RemoteException {
	}

	public String getMessage () {
		return MESSAGE;
	}

	public static void main (String args[]) {
		System.out.println("RMI server started");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
			System.out.println("Security manager installed.");
		} else {
			System.out.println("Security manager already exists.");
		}

		try {
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		try {
			RmiServer obj = new RmiServer();

			Naming.rebind("//localhost/RmiServer", obj);

			System.out.println("PeerServer bound in registry");
		} catch (Exception e) {
			System.err.println("RMI server exception:" + e);
			e.printStackTrace();
		}
	}
}


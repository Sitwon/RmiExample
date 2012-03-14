package server;

import common.*;
import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

import jp.co.antenna.XfoJavaCtl.*;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
	public static final String MESSAGE = "Hello world";

	public RmiServer () throws RemoteException {
	}

	public String getMessage () {
		return MESSAGE;
	}

	public File renderFO (File inputFO) {
		File outputPDF = new File(inputFO.getPath() + ".pdf");
		XfoObj axfo = null;
		try {
			axfo = new XfoObj();
			axfo.setDocumentURI(inputFO.getPath());
			axfo.setOutputFilePath(outputPDF.getPath());
			axfo.setExitLevel(4);
			axfo.execute();
		} catch (XfoException e) {
			System.err.println("Error rendering FO: " + e);
			System.err.println("ErrorLevel = " + e.getErrorLevel() + "\nErrorCode = " + e.getErrorCode() + "\n" + e.getErrorMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (axfo != null)
					axfo.releaseObjectEx();
			} catch (Exception e) {}
		}
		return outputPDF;
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


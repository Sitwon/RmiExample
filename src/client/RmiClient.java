package client;

import common.*;
import java.io.File;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;

public class RmiClient {
	RmiServerIntf obj = null;

	public String getMessage () {
		try {
			obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
			return obj.getMessage();
		} catch (Exception e) {
			System.err.println("RmiClient exception: " + e);
			e.printStackTrace();

			return e.getMessage();
		}
	}

	public File renderFO (File inputFO) {
		try {
			obj = (RmiServerIntf)Naming.lookup("//localhost/RmiServer");
			return obj.renderFO(inputFO);
		} catch (Exception e) {
			System.err.println("RmiClient exception: " + e);
			e.printStackTrace();

			return null;
		}
	}

	public static void main (String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		RmiClient cli = new RmiClient();

		System.out.println(cli.getMessage());

		if (args.length > 0 ) {
			File outputFile = cli.renderFO(new File(args[0]));
			if (outputFile != null) {
				System.out.println("Result: " + outputFile.getPath());
			} else {
				System.err.println("Render failed.");
			}
		} else {
			System.err.println("No FO provied.");
		}
	}
}


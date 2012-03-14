package client;

import common.*;
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

	public static void main (String args[]) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		RmiClient cli = new RmiClient();

		System.out.println(cli.getMessage());
	}
}


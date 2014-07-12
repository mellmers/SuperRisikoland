package server;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import client.SpielBeitreten;
import exc.MaximaleSpielerZahlErreichtException;
import inf.LoginInterface;
import inf.SpielBeitretenInterface;

public class Login implements LoginInterface, Serializable{
	
	private Vector<SpielBeitretenInterface> spieler = new Vector<SpielBeitretenInterface>();
	
	public Login(int port, String servername)
	{
		
	}

	public void spielStarten() {
		// TODO Auto-generated method stub
		
	}
	public void addClient(String name, int port) throws RemoteException, MaximaleSpielerZahlErreichtException, NotBoundException {
		if(this.spieler.size() < 6)
		{
			Registry registry = LocateRegistry.getRegistry("localhost",port);
			// evtl Client individualisieren
			SpielBeitreten client = (SpielBeitreten) registry.lookup(name);
			this.spieler.add(client);
			System.out.println("Spieler: " + name + " erstellt");	
		}
		else
		{
			throw new MaximaleSpielerZahlErreichtException();
			
		}
	}

}

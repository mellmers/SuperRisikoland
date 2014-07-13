package server;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import javax.swing.JOptionPane;

import cui.Spieler;
import cui.Spielfeld;
import inf.ServerInterface;
import inf.ClientInterface;
import inf.SpielerInterface;

public class Server extends UnicastRemoteObject implements ServerInterface, Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5627619432737445454L;
	private Vector<ClientInterface> clients = new Vector<ClientInterface>();
	private Vector<Spieler> spieler = new Vector<Spieler>();
	
	private String servername;
	Spielfeld spiel;
	private int spielVariante;
	
	
	protected Server() throws RemoteException
	{
		super();
	}
	

	
	public boolean addClient(String name, int port) throws RemoteException, NotBoundException 
	{
		if(this.clients.size() < 6)
		{
			Registry registry = LocateRegistry.getRegistry("localhost",port);
			// evtl Client individualisieren
			ClientInterface client = (ClientInterface) registry.lookup(name);
			this.clients.add(client);
			
			System.out.println("Client " + client.getSpielername() + " ist beigetreten.");
			return true;
		}
		JOptionPane.showMessageDialog(null, "Dieser Server ist leider voll.");		
		return false;
	}

	public void addSpieler(SpielerInterface spieler) throws RemoteException
	{
		if(!this.spieler.contains((Spieler) spieler))
		{
			this.spieler.add((Spieler) spieler);
			System.out.println("Spieler " + spieler.getName() + " hat seinen Charakter ausgewaehlt.");
		}
	}
	
	public ClientInterface getClient(int index)
	{
		return this.clients.elementAt(index);
	}
	
	public Spieler getSpieler(int index)
	{
		return this.spieler.elementAt(index);
	}
	
	public int getAlleClients()
	{
		return this.clients.size();
	}
	
	public int getAlleSpielerAnzahl()
	{
		return this.spieler.size();
		
	}
	public void spielBeginnen(int spielVariante) throws RemoteException
	{
		spiel = new Spielfeld(this.spieler, spielVariante);
	}

	public Spielfeld getSpiel() throws RemoteException
	{
		return this.spiel;
	}
}

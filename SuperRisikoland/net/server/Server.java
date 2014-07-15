package server;

import java.awt.Color;
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
import gui.SuperRisikolandGui;
import inf.ServerInterface;
import inf.ClientInterface;
import inf.SpielerInterface;
import inf.SpielfeldInterface;

public class Server extends UnicastRemoteObject implements ServerInterface, Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5627619432737445454L;
	private Vector<ClientInterface> clients = new Vector<ClientInterface>();
	private Vector<SpielerInterface> spieler = new Vector<SpielerInterface>();
	
	private String servername;
	SpielfeldInterface spiel;
	private int spielVariante;
	// Ausgelagerte Variablen
	private String logText = "Server gestartet.";
	
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

	public void addSpieler(int id, String name, String farbe, Color c) throws RemoteException
	{
			this.spieler.add(new Spieler(id, name, farbe, c));
			System.out.println("Spieler " + name + " hat seinen Charakter ausgewaehlt.");
	}
	
	public ClientInterface getClient(int index) throws RemoteException
	{
		return this.clients.elementAt(index);
	}
	
	public SpielerInterface getSpieler(int index) throws RemoteException
	{
		return this.spieler.elementAt(index);
	}
	
	public int getAlleClients() throws RemoteException
	{
		return this.clients.size();
	}
	
	public int getAlleSpielerAnzahl() throws RemoteException
	{
		return this.spieler.size();
		
	}
	public void spielBeginnen(int spielVariante) throws RemoteException
	{
		spiel = new Spielfeld(this, this.spieler, spielVariante);
	}

	public SpielfeldInterface getSpielfeldInterface() throws RemoteException
	{
		return this.spiel;
	}

	public void setLogText(String logText) throws RemoteException
	{
		this.logText += "\n" + logText;
	}
	
	public String getLogText() throws RemoteException
	{
		return this.logText;
	}
	
	public void updateClients()
	{
		
	}
}

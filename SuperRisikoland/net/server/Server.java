package server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import client.Client;
import cui.Spieler;
import exc.MaximaleSpielerZahlErreichtException;
import inf.ServerInterface;
import inf.ClientInterface;
import inf.SpielerInterface;

public class Server extends UnicastRemoteObject implements ServerInterface, Serializable, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5627619432737445454L;
	private Vector<ClientInterface> clients = new Vector<ClientInterface>();
	private Vector<Spieler> spieler = new Vector<Spieler>();
	
	private String servername;
	
	
	
	protected Server() throws RemoteException
	{
		super();
	}
	

	public void spielStarten() {
		// TODO Auto-generated method stub
		
	}
	
	public void addClient(String name, int port) throws RemoteException, MaximaleSpielerZahlErreichtException, NotBoundException {
		if(this.clients.size() < 6)
		{
			Registry registry = LocateRegistry.getRegistry("localhost",port);
			// evtl Client individualisieren
			ClientInterface client = (ClientInterface) registry.lookup(name);
			this.clients.add(client);
			
			System.out.println("Spieler: " + client.getSpielername() + " erstellt");
			
		}
		else
		{
			throw new MaximaleSpielerZahlErreichtException();
			
		}
	}
	
	/*public static void main(String[] args) throws RemoteException, MaximaleSpielerZahlErreichtException, NotBoundException
	{
		Login l = new Login(9999, "Jochen");
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void addSpieler(SpielerInterface spieler) throws RemoteException
	{
		this.spieler.add((Spieler) spieler);
		System.out.println(this.spieler.elementAt(0).getName());
	}


}

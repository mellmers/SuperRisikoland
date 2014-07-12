package server;
import inf.ClientInterface;
import inf.RemoteInterface;

import java.awt.BorderLayout;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;

import cui.Spielfeld;


public class Server extends JFrame{
	
	public Server()
	{
		super();
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,100);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		
	}
	
	public void serverErstellen(int port, String name) throws RemoteException
	{
		Spielfeld remote = new Spielfeld(2,1);
		Registry registry = LocateRegistry.createRegistry(port);
		registry.rebind(name, remote);
		System.out.println("start is started");
	}

	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException{
		/*Spielfeld remote = new Spielfeld(2,1);
		Registry registry = LocateRegistry.createRegistry(9999);
		registry.rebind("RMI", remote);
		System.out.println("start is started");
		
		//Registry clientRegistry = LocateRegistry.getRegistry("localhost",9998);
		//ClientInterface clientRemote = (ClientInterface) clientRegistry.lookup("Client");
		
		System.out.println("EIngabe:");
		int u = System.in.read();
		
		remote.getClient(0).zahl(u);
		remote.getClient(0).speichern();*/
	}
	// Test
	
}

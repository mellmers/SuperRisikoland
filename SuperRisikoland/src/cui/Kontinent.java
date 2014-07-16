package cui;

import inf.KontinentInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Kontinent extends UnicastRemoteObject  implements Serializable, KontinentInterface
{

	private String name;
	private int anzahlLaender;
	private int zusatzTruppen;

	public Kontinent(String name, int anzLaender, int zusatz) throws RemoteException {
		this.name = name;
		this.anzahlLaender = anzLaender;
		this.zusatzTruppen = zusatz;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getZusatzTruppen() throws RemoteException
	{
		return this.zusatzTruppen;
	}
	
	public int getAnzahlLaender()
	{
		return this.anzahlLaender;
	}

}


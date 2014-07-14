package cui;

import inf.KontinentInterface;
import inf.LandInterface;
import inf.SpielerInterface;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Land extends UnicastRemoteObject implements Serializable, LandInterface
{
	private KontinentInterface kontinent;
	private String name;
	private String einheit;
	private int truppenStaerke;
	private SpielerInterface besitzer;
	private LandInterface [] nachbarLaender;
	private int benutzteEinheiten = 0;
	private Color farbcode;
	
	public Land(KontinentInterface kontinent, String name, String einheit, int anzahlNachbarLaender, Color farbe) throws RemoteException
	{
		this.kontinent = kontinent;
		this.name = name;
		this.einheit = einheit;
		this.nachbarLaender = new Land[anzahlNachbarLaender];
		this.farbcode = farbe;
	}
	
	// Getter
	public String getName()
	{
		return this.name;
	}
	
	public int getTruppenstaerke()
	{
		return this.truppenStaerke;
	}
	
	public SpielerInterface getBesitzer()
	{
		return this.besitzer;
	}
	
	public String getEinheit() 
	{
		return this.einheit;
	}

	public KontinentInterface getKontinent() 
	{
		return kontinent;
	}
	
	// Setter
	public void setTruppenstaerke(int menge)
	{
		this.truppenStaerke += menge;
	}
	
	public void setBesitzer(SpielerInterface s) 
	{
		this.besitzer = s;
	}
	
	public void setNachbarLand(int landId, LandInterface land)
	{
		this.nachbarLaender[landId] = land;
	}
	
	// Funktionen
	public boolean istNachbar(LandInterface n) throws RemoteException
	{
		for(int i = 0; i < this.nachbarLaender.length; i++)
		{
			if(n.getName().equals(this.nachbarLaender[i].getName()))
			{
				return true;
			}
		}
		return false;
	}

	public int getBenutzteEinheiten() {
		return benutzteEinheiten;
	}

	public void erhoeheBenutzteEinheiten(int benutzteEinheiten) {
		this.benutzteEinheiten += benutzteEinheiten;
	}	
	
	public void setBenutzteEinheiten(int benutzteEinheiten)
	{
		this.benutzteEinheiten = benutzteEinheiten;
	}

	public Color getFarbcode()
	{
		return farbcode;
	}
}

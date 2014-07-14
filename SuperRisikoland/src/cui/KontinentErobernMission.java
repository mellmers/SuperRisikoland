package cui;

import inf.KontinentInterface;
import inf.MissionInterface;
import inf.SpielfeldInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class KontinentErobernMission extends Mission implements Serializable, MissionInterface
{
	private KontinentInterface ersterKontinent;
	private KontinentInterface zweiterKontinent;
	private int zusaetzlicherKontinent;
	private SpielfeldInterface spiel;

	public KontinentErobernMission(int ersterKontinent, int zweiterKontinent, int zusatzKontinent, SpielfeldInterface spiel) throws RemoteException
	{
		this.ersterKontinent = spiel.getKontinent(ersterKontinent);
		this.zweiterKontinent = spiel.getKontinent(zweiterKontinent);
		this.zusaetzlicherKontinent = zusatzKontinent;
		this.spiel = spiel;
	}
	

	public void setAufgabenText() throws RemoteException
	{
		this.aufgabenText = "Erobern Sie " + this.ersterKontinent.getName() + " und " + this.zweiterKontinent.getName() + (this.zusaetzlicherKontinent == 0 ? "." : ", sowie einen zusaetzlichen Kontinent ihrer Wahl.");
	}
	
	public void setMissionErfuelltText() throws RemoteException
	{
		this.missionErfuelltText = this.besitzer.getName() + " hat die beiden Kontinente " + this.ersterKontinent.getName() + " und " + this.zweiterKontinent.getName() + (this.zusaetzlicherKontinent == 0 ? "" : ", sowie einen zusaetzlichen Kontinent ihrer Wahl") + " erobert und somit seine Mission erfuellt";
	}
	
	
	public boolean missionErfuellt() throws RemoteException
	{
		if((zusaetzlicherKontinent == 1 && zufallsKontinentAbfragen(this.spiel) && kontinentAbfragen(this.ersterKontinent) && kontinentAbfragen(this.zweiterKontinent)) || (this.zusaetzlicherKontinent == 0 && kontinentAbfragen(this.ersterKontinent) && kontinentAbfragen(this.zweiterKontinent)))
		{
			return true;
		}
		return false;
	}
	
	public boolean kontinentAbfragen(KontinentInterface kontinent) throws RemoteException
	{
		int kontinentZaehler = 0;
	
		for( int j = 0 ; j < super.besitzer.getLaenderAnzahl() ; j++)
		{
			if(super.besitzer.getBesitzLandKontinent(j) == kontinent)
			{
				kontinentZaehler ++;
			}
		}
		if(kontinentZaehler == kontinent.getAnzahlLaender())
		{
			return true;
		}
		
		return false;
	}
	
	public boolean zufallsKontinentAbfragen(SpielfeldInterface spiel2) throws RemoteException
	{
		for( int i = 0 ; i < 6 ; i++)
		{
			int kontinentZaehler = 0;
			if(spiel2.getKontinent(i) != this.ersterKontinent && spiel2.getKontinent(i) != this.zweiterKontinent)
			{
				for( int j = 0 ; j < super.besitzer.getLaenderAnzahl() ; j++)
				{
					if(super.besitzer.getBesitzLandKontinent(j) == spiel2.getKontinent(i))
					{
						kontinentZaehler ++;
					}
				}
				if(kontinentZaehler == spiel2.getKontinent(i).getAnzahlLaender())
				{
					return true;
				}
			}
		}
		return false;
		
	}
}

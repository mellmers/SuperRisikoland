package cui;

import inf.MissionInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class AnzahlLaenderErobernMission extends Mission implements Serializable, MissionInterface
{

	private int anzahlLaender;
	
	public AnzahlLaenderErobernMission(int anzahlLaender)  throws RemoteException
	{
		this.anzahlLaender = anzahlLaender;
	}
	
	public void setAufgabenText() throws RemoteException
	{
		this.aufgabenText = "Erobern Sie " + this.anzahlLaender + " Laender Ihrer Wahl" + (this.anzahlLaender == 18 ? " und besetzen Sie jedes dieser Laender mit mindestens 2 Einheiten." : ".");
	}
	
	public boolean missionErfuellt() throws RemoteException
	{
		if(this.anzahlLaender == 24 && this.erforderteLaenderAnzahl())
		{
			return true;
		}
		if(this.anzahlLaender == 18 && this.erforderteLaenderAnzahl())
		{
			int zaehler = 0;
			for(int i = 0 ; i <= super.besitzer.getLaenderAnzahl() ; i++ )
			{
				if(this.besitzer.getLaenderEinheiten(i) >= 2)
				{
					zaehler ++;
				}
				if(zaehler == 18)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public void setMissionErfuelltText() throws RemoteException
	{
		this.missionErfuelltText = super.besitzer.getName() + " hat " + this.anzahlLaender + " Laender erobert" + (this.anzahlLaender == 18 ? ", sowie jedes dieser Laender mit mindestens 2 Einheiten besetzt" : "") + " und somit seine Mission erfuellt.";
	}
	
	public boolean erforderteLaenderAnzahl() throws RemoteException
	{
		if(super.besitzer.getLaenderAnzahl() >= this.anzahlLaender)
		{
			return true;
		}
		return false;
	}
}
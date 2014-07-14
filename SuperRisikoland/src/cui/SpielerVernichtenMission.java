package cui;

import inf.MissionInterface;
import inf.SpielerInterface;
import inf.SpielfeldInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public class SpielerVernichtenMission extends AnzahlLaenderErobernMission implements Serializable, MissionInterface
{
	
	private SpielerInterface gegner;
	
	public SpielerVernichtenMission(SpielfeldInterface spiel, int spielerId)  throws RemoteException
	{
		super(24);
		this.gegner = spiel.getSpieler(spielerId);
	}
	
	public void setAufgabenText() throws RemoteException
	{
		this.aufgabenText = (this.gegner == super.besitzer ? "Erobern Sie 24 Laender Ihrer Wahl." : "Vernichten Sie den Spieler " + this.gegner.getName() + ".");
	}
	
	public void setMissionErfuelltText() throws RemoteException
	{	
		this.missionErfuelltText = ((this.gegner == super.besitzer) ? super.besitzer.getName() + " hat 24 Laender erobert und somit seine Mission erfuellt" : "Der Spieler " + this.gegner.getName() + "wurde vernichtet und damit " + super.besitzer.getName() + "'s Mission erfuellt.");

	}
	
	public boolean missionErfuellt() throws RemoteException
	{
		if(super.besitzer == this.gegner && super.erforderteLaenderAnzahl())
		{
			return true;
		}
		if(super.besitzer != this.gegner && this.gegner.getLaenderAnzahl() == 0)
		{
			return true;
		}
		return false;
	}
}
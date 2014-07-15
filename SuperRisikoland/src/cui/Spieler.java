package cui;
import gui.SuperRisikolandGui;
import inf.KontinentInterface;
import inf.LandInterface;
import inf.MissionInterface;
import inf.SpielerInterface;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import javax.swing.ImageIcon;


public class Spieler extends UnicastRemoteObject implements Serializable, SpielerInterface
{
	private String name;
	private Vector<LandInterface> laender = new Vector<LandInterface>();
	private Vector<LandInterface> handkarten = new Vector<LandInterface>();
	private MissionInterface mission;
	private String spielerfarbe;
	private ImageIcon spielerIcon;
	private int spielerID;
	private Color colorSpieler;

	public Spieler (int spielerID, String spielername, String spielerfarbe, Color c) throws RemoteException
	{
		this.name = spielername;
		this.spielerfarbe = spielerfarbe;
		this.spielerID = spielerID;
		IO.println("Spieler " + this.name + " mit der Farbe " + this.spielerfarbe +" und mit SpielerID " + spielerID + " wurde erstellt.");
		this.colorSpieler = c;
	}
	
	public boolean einheitenVerteilen(Land land)
	{
		if(this.laender.contains(land))
		{	
			land.setTruppenstaerke(1);
			return true;
		}
		return false;
	}
	
	public int getLaenderAnzahl()
	{
		return this.laender.size();
	}
	
	public int getAnzahlHandkarten() throws RemoteException
	{
		return this.handkarten.size();
	}
	
	public int getLaenderEinheiten(int id) throws RemoteException
	{
		return this.laender.elementAt(id).getTruppenstaerke();
	}
	
	public void landHinzufuegen(LandInterface land)
	{
		this.laender.add(land);
	}
	public void landEntfernen(LandInterface land)
	{
		this.laender.removeElement(land);
	}
	
	public void handkartenHinzufuegen(LandInterface land)
	{
		this.handkarten.add(land);
	}
	
	public void handkartenLoeschen(int landId)
	{
		this.handkarten.remove(landId);
	}
	
	
	public boolean meinLand(LandInterface land) throws RemoteException
	{
		for(int i = 0 ; i < laender.size() ; i++)
		{
			if(laender.elementAt(i).getName().equals(land.getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	public KontinentInterface getBesitzLandKontinent(int landNummer) throws RemoteException
	{
		return this.laender.elementAt(landNummer).getKontinent();
	}
	
	public String getName() 
	{
		return this.name;
	}
	
	public void handkartenAusgeben() throws RemoteException
	{
		for(int i = 0 ; i < this.handkarten.size(); i++)
		{
			IO.println(i+1 + ". " + this.handkarten.elementAt(i).getName() + " " + this.handkarten.elementAt(i).getEinheit());
		}
	}
	public LandInterface spielerHandkarte(int index) throws RemoteException
	{
		return this.handkarten.elementAt(index);
	}
	
	
	public boolean istSerie(int hkEins, int hkZwei, int hkDrei) throws RemoteException
	{
		if((this.handkarten.elementAt(hkEins).getEinheit() == this.handkarten.elementAt(hkZwei).getEinheit() && this.handkarten.elementAt(hkEins).getEinheit() == this.handkarten.elementAt(hkDrei).getEinheit()) 
				|| this.handkarten.elementAt(hkEins).getEinheit() != this.handkarten.elementAt(hkZwei).getEinheit() && this.handkarten.elementAt(hkEins).getEinheit() != this.handkarten.elementAt(hkDrei).getEinheit() && this.handkarten.elementAt(hkZwei).getEinheit() != this.handkarten.elementAt(hkDrei).getEinheit() )
		{
			this.handkartenLoeschen(hkEins);
			this.handkartenLoeschen(hkZwei);
			this.handkartenLoeschen(hkDrei);
			return true;
		}
		return false;
	}

	public MissionInterface getMission() throws RemoteException
	{
		return mission;
	}

	public void setMission(MissionInterface missionInterface) throws RemoteException
	{
		this.mission = missionInterface;
	}

	public String getSpielerfarbe()
	{
		return spielerfarbe;
	}

	public ImageIcon getSpielerIcon()
	{
		return spielerIcon;
	}

	public void setSpielerIcon(ImageIcon spielerIcon)
	{
		this.spielerIcon = spielerIcon;
	}

	public int getSpielerID()
	{
		return spielerID;
	}

	public Color getColorSpieler()
	{
		return colorSpieler;
	}



}

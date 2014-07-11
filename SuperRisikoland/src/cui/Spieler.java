package cui;
import gui.SuperRisikolandGui;

import java.awt.Color;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;


public class Spieler implements Serializable
{
	private String name;
	private Vector<Land> laender = new Vector<Land>();
	private Vector<Land> handkarten = new Vector<Land>();
	private Mission mission;
	private String spielerfarbe;
	private ImageIcon spielerIcon;
	private int spielerID;
	private Color colorSpieler;

	public Spieler (int spielerID, String spielername, String spielerfarbe, Color c) 
	{
		this.name = spielername;
		this.spielerfarbe = spielerfarbe;
		this.spielerID = spielerID;
		SuperRisikolandGui.logText += "\nSpieler " + this.name + " mit der Farbe " + this.spielerfarbe +" und mit SpielerID " + spielerID + " wurde erstellt.";
		SuperRisikolandGui.logTextArea.setText(SuperRisikolandGui.logText);
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
	
	public int getAnzahlHandkarten()
	{
		return this.handkarten.size();
	}
	
	public int getLaenderEinheiten(int id)
	{
		return this.laender.elementAt(id).getTruppenstaerke();
	}
	
	public void landHinzufuegen(Land land)
	{
		this.laender.add(land);
	}
	public void landEntfernen(Land land)
	{
		this.laender.removeElement(land);
	}
	
	public void handkartenHinzufuegen(Land land)
	{
		this.handkarten.add(land);
	}
	
	public void handkartenLoeschen(int landId)
	{
		this.handkarten.remove(landId);
	}
	
	
	public boolean meinLand(Land land)
	{
		if(laender.contains(land))
		{
			return true;
		}
		SuperRisikolandGui.logText += "\nDieses Land gehört dir nicht!";
		SuperRisikolandGui.logTextArea.setText(SuperRisikolandGui.logText);
		IO.println("Dieses Land gehört dir nicht!");
		return false;
	}
	
	public Kontinent getBesitzLandKontinent(int landNummer)
	{
		return this.laender.elementAt(landNummer).getKontinent();
	}
	
	public String getName() 
	{
		return this.name;
	}
	
	public void handkartenAusgeben()
	{
		for(int i = 0 ; i < this.handkarten.size(); i++)
		{
			IO.println(i+1 + ". " + this.handkarten.elementAt(i).getName() + " " + this.handkarten.elementAt(i).getEinheit());
		}
	}
	
	public boolean istSerie(int hkEins, int hkZwei, int hkDrei)
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

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
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

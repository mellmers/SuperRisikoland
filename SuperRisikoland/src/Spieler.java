import java.util.Vector;


public class Spieler
{
	private String name;
	private Vector<Land> laender = new Vector<Land>();
	private Vector<Land> handkarten = new Vector<Land>();
	private Mission mission;

	public Spieler (int spielerID) 
	{
		//Mission mission = new Mission();
		//auftrag = mission.getMission();
		IO.println("Name des " + spielerID + ". Spielers?");
		this.name = IO.readString();
		IO.println("Spieler " + this.name + " mit SpielerID: " + spielerID + " erstellt.");
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
	
	public int getLaenderEinheiten(int Id)
	{
		return this.laender.elementAt(Id).getTruppenstaerke();
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
		IO.println("Dieses Land gehoert dir nicht!");
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
}

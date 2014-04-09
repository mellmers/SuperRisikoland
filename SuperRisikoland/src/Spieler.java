import java.util.Vector;


public class Spieler
{
	private String name;
	private String auftrag;
	Vector laender = new Vector();
	Vector handKarten = new Vector();

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
	
	public String getAuftrag() 
	{
		return auftrag;
	}
	
	public boolean meinLand(Land land)
	{
		if(laender.contains(land))
		{
			return true;
		}
		IO.println("Dieses Land gehoert dir nicht");
		return false;
	}
	
	public String getName() 
	{
		return this.name;
	}
}

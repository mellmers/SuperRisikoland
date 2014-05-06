
public class SpielerVernichtenMission extends AnzahlLaenderErobernMission{
	
	private Spieler gegner;
	
	public SpielerVernichtenMission(Spielfeld spiel, int spielerId)
	{
		super(24);
		this.gegner = spiel.getSpieler(spielerId);
	}
	
	public void setAufgabenText()
	{
		this.aufgabenText = (this.gegner == super.besitzer ? "Erobern Sie 24 Laender Ihrer Wahl." : "Vernichten Sie den Spieler " + this.gegner.getName() + ".");
	}
	
	public void setMissionErfuelltText()
	{	
		this.missionErfuelltText = ((this.gegner == super.besitzer) ? super.besitzer.getName() + " hat 24 Laender erobert und somit seine Mission erfuellt" : "Der Spieler " + this.gegner.getName() + "wurde vernichtet und damit " + super.besitzer.getName() + "'s Mission erfuellt.");

	}
	
	public boolean missionErfuellt()
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

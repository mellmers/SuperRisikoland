
public class AnzahlLaenderErobernMission extends Mission {

	private int anzahlLaender;
	
	public AnzahlLaenderErobernMission(int anzahlLaender)
	{
		this.anzahlLaender = anzahlLaender;
	}
	
	public void setAufgabenText()
	{
		this.aufgabenText = "Erobern Sie " + this.anzahlLaender + " Ihrer Wahl" + (this.anzahlLaender == 18 ? " und besetzen Sie jedes dieser Laender mit mindestens 2 Einheiten." : ".");
	}
	
	public boolean missionErfuellt()
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
	
	public void setMissionErfuelltText()
	{
		this.missionErfuelltText = super.besitzer.getName() + " hat " + this.anzahlLaender + " Laender erobert" + (this.anzahlLaender == 18 ? ", sowie jedes dieser Laender mit mindestens 2 Einheiten besetzt" : "") + " und somit seine Mission erfuellt.";
	}
	
	public boolean erforderteLaenderAnzahl()
	{
		if(super.besitzer.getLaenderAnzahl() >= this.anzahlLaender)
		{
			return true;
		}
		return false;
	}
}

package cui;
public class KontinentErobernMission extends Mission
{
	private Kontinent ersterKontinent;
	private Kontinent zweiterKontinent;
	private int zusaetzlicherKontinent;
	private Spielfeld spiel;

	public KontinentErobernMission(int ersterKontinent, int zweiterKontinent, int zusatzKontinent, Spielfeld spiel)
	{
		this.ersterKontinent = spiel.getKontinent(ersterKontinent);
		this.zweiterKontinent = spiel.getKontinent(zweiterKontinent);
		this.zusaetzlicherKontinent = zusatzKontinent;
		this.spiel = spiel;
	}
	
	public void setAufgabenText()
	{
		this.aufgabenText = "Erobern Sie " + this.ersterKontinent.getName() + " und " + this.zweiterKontinent.getName() + (this.zusaetzlicherKontinent == 0 ? "." : ", sowie einen zusaetzlichen Kontinent ihrer Wahl.");
	}
	
	public void setMissionErfuelltText()
	{
		this.missionErfuelltText = this.besitzer.getName() + " hat die beiden Kontinente " + this.ersterKontinent.getName() + " und " + this.zweiterKontinent.getName() + (this.zusaetzlicherKontinent == 0 ? "" : ", sowie einen zusaetzlichen Kontinent ihrer Wahl") + " erobert und somit seine Mission erfuellt";
	}
	
	
	public boolean missionErfuellt()
	{
		if((zusaetzlicherKontinent == 1 && zufallsKontinentAbfragen(this.spiel) && kontinentAbfragen(this.ersterKontinent) && kontinentAbfragen(this.zweiterKontinent)) || (this.zusaetzlicherKontinent == 0 && kontinentAbfragen(this.ersterKontinent) && kontinentAbfragen(this.zweiterKontinent)))
		{
			return true;
		}
		return false;
	}
	
	public boolean kontinentAbfragen(Kontinent kontinent)
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
	
	public boolean zufallsKontinentAbfragen(Spielfeld spiel)
	{
		for( int i = 0 ; i < 6 ; i++)
		{
			int kontinentZaehler = 0;
			if(spiel.getKontinent(i) != this.ersterKontinent && spiel.getKontinent(i) != this.zweiterKontinent)
			{
				for( int j = 0 ; j < super.besitzer.getLaenderAnzahl() ; j++)
				{
					if(super.besitzer.getBesitzLandKontinent(j) == spiel.getKontinent(i))
					{
						kontinentZaehler ++;
					}
				}
				if(kontinentZaehler == spiel.getKontinent(i).getAnzahlLaender())
				{
					return true;
				}
			}
		}
		return false;
		
	}
}

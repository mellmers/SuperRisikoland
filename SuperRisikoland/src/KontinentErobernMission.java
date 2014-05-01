public class KontinentErobernMission extends Mission
{
	private Kontinent ersterKontinent;
	private Kontinent zweiterKontinent;

	public KontinentErobernMission(Kontinent ersterKontinent, Kontinent zweiterKontinent)
	{
		this.ersterKontinent = ersterKontinent;
		this.zweiterKontinent = zweiterKontinent;
	}
	
	
	public boolean missionErfuellt()
	{
		if(kontinentAbfragen(this.ersterKontinent) && kontinentAbfragen(this.zweiterKontinent))
		{
			return true;
		}
		return false;
	}
	
	public boolean kontinentAbfragen(Kontinent kontinent)
	{
		int kontinentZaehler = 0;
	
		for( int j = 0 ; j < this.besitzer.getLaenderAnzahl() ; j++)
		{
			if(this.besitzer.getBesitzLandKontinent(j) == kontinent)
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
				for( int j = 0 ; j < this.besitzer.getLaenderAnzahl() ; j++)
				{
					if(this.besitzer.getBesitzLandKontinent(j) == spiel.getKontinent(i))
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

public class Land 
{
	private Kontinent kontinent;
	private String name;
	private String einheit;
	private int truppenStaerke;
	private Spieler besitzer;
	Land[] nachbarLaender;
	
	public Land(Kontinent kontinent, String name, String einheit, int anzahlNachbarLaender)
	{
		this.kontinent = kontinent;
		this.name = name;
		this.einheit = einheit;
		this.nachbarLaender = new Land[anzahlNachbarLaender];
	}
	
	// Getter
	public String getName()
	{
		return this.name;
	}
	
	public int getTruppenstaerke()
	{
		return this.truppenStaerke;
	}
	
	public Spieler getBesitzer()
	{
		return this.besitzer;
	}
	
	public String getEinheit() 
	{
		return this.einheit;
	}

	public Kontinent getKontinent() 
	{
		return kontinent;
	}
	
	// Setter
	public void setTruppenstaerke(int menge)
	{
		this.truppenStaerke += menge;
	}
	
	public void setBesitzer(Spieler s) 
	{
		this.besitzer = s;
	}
	
	// Funktionen
	public boolean istNachbar(Land n)
	{
		for(int i = 0; i < this.nachbarLaender.length; i++)
		{
			if(n.equals(this.nachbarLaender[i]))
			{
				return true;
			}
		}
		return false;
	}	
}

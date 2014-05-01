public class Kontinent {

	private String name;
	private int anzahlLaender;
	private int zusatzTruppen;

	public Kontinent(String name, int anzLaender, int zusatz) {
		this.name = name;
		this.anzahlLaender = anzLaender;
		this.zusatzTruppen = zusatz;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getZusatzTruppen()
	{
		return this.zusatzTruppen;
	}
	
	public int getAnzahlLaender()
	{
		return this.anzahlLaender;
	}

}


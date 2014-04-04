
public class Land {
	
	private String kontinent;
	private String name;
	private String einheit;
	private int truppenStaerke;
	private Spieler besitzer;
	Land[] nachbarLand;
	
	public String getName(){
		return this.name;
	}
	public int getTruppe(){
		return this.truppenStaerke;
	}
	public void setTruppe(int menge){
		this.truppenStaerke += menge;
	}
	
	
	public Land(String k, String n, String e, int anzahlNl){
		this.kontinent = k;
		this.name = n;
		this.einheit = e;
		this.nachbarLand = new Land[anzahlNl];

	}
	public boolean istNachbar(Land n){
		for(int i = 0; i < this.nachbarLand.length; i++){
			if(n.equals(this.nachbarLand[i])){
				return true;
			}
		}
		return false;
	}
	
	
	

	

	
	
	
	
	
	








	public String getEinheit() {
		return this.einheit;
	}

	public String getKontinent() {
		return kontinent;
	}

	public void setKontinent(String kontinent) {
		this.kontinent = kontinent;
	}
	
	

}

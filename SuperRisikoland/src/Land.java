public class Land {
	
	private String kontinent;
	private String name;
	private String einheit;
	private int truppenStaerke;
	private Spieler besitzer;
	Land[] nachbarLand;
	
	public Land(String k, String n, String e, int anzahlNl){
		this.kontinent = k;
		this.name = n;
		this.einheit = e;
		this.nachbarLand = new Land[anzahlNl];
	}
	
	// Getter
	public String getName(){
		return this.name;
	}
	
	public int getTruppenstaerke(){
		return this.truppenStaerke;
	}
	
	public String getEinheit() {
		return this.einheit;
	}

	public String getKontinent() {
		return kontinent;
	}
	
	// Setter
	public void setTruppenstaerke(int menge){
		this.truppenStaerke += menge;
	}
	
	public void setBesitzer(Spieler s) {
		this.besitzer = s;
	}

	public void setKontinent(String kontinent) {
		this.kontinent = kontinent;
	}
	
	
	// Funktionen
	public boolean istNachbar(Land n){
		for(int i = 0; i < this.nachbarLand.length; i++){
			if(n.equals(this.nachbarLand[i])){
				return true;
			}
		}
		return false;
	}	
}

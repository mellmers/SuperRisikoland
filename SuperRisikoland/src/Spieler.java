import java.util.Vector;


public class Spieler {
	
	private String name;
	private int spielerId;
	private String auftrag;
	Vector laender = new Vector();
	Vector handKarten = new Vector();

	public Spieler (String name) {
		//Mission mission = new Mission();
		//auftrag = mission.getMission();
		this.name = name;
	}
	public boolean einheitenVerteilen(Land land){
		if(this.laender.contains(land)){	
			land.setTruppenstaerke(1);
			return true;
		}
		return false;
	}

	
	public String getAuftrag() {
		return auftrag;
	}
	
	public boolean meinLand(Land land){
		if(laender.contains(land)){
			return true;
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
}

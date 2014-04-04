import java.util.Vector;


public class Spieler {
	
	private String name;
	private int spielerId;
	private String auftrag;
	Vector laender = new Vector();
	Vector handKarten = new Vector();

	public Spieler (String n) {
		Mission mission = new Mission();
		auftrag = mission.getMission();
		this.name = n;
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
	
}

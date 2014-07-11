package cui;

import gui.SuperRisikolandGui;

import java.io.Serializable;
import java.util.Vector;

public class Mission implements Serializable
{
	protected String aufgabenText;
	protected String missionErfuelltText;
	protected Spieler besitzer;
	
	public Mission(){
		
	}
	
	public Vector<Mission> missionenErstellen(Spielfeld spiel)
	{
		Vector<Mission> missionen = new Vector<Mission>();
		missionen.add(new AnzahlLaenderErobernMission(24));
  		missionen.add(new AnzahlLaenderErobernMission(18));
  		missionen.add(new KontinentErobernMission(1, 2, 1, spiel));
  		missionen.add(new KontinentErobernMission(2, 5, 1, spiel));
  		missionen.add(new KontinentErobernMission(3, 4, 0, spiel));
  		missionen.add(new KontinentErobernMission(1, 4, 0, spiel));
  		missionen.add(new KontinentErobernMission(0, 5, 0, spiel));
  		missionen.add(new KontinentErobernMission(0, 3, 0, spiel));
  		for( int spieler = 0 ; spieler < 6 ; spieler++)
  		{
  			missionen.add(( spieler < spiel.getAnzahlSpieler()) ? new SpielerVernichtenMission(spiel, spieler) : new AnzahlLaenderErobernMission(24));
  		}
  		
  		// Missionen verteilen
  		for(int i = 0 ; i < spiel.getAnzahlSpieler() ; i++)
  		{
  			int zufallsMission = (int) (Math.random()*missionen.size());
  			spiel.getSpieler(i).setMission(missionen.elementAt(zufallsMission));
  			spiel.getSpieler(i).getMission().setBesitzer(spiel.getSpieler(i));
  			spiel.getSpieler(i).getMission().setAufgabenText();
  			spiel.getSpieler(i).getMission().setMissionErfuelltText();
  			missionen.remove(zufallsMission);
  		}
  		
  		IO.println("Missionen wurden generiert.");
  		SuperRisikolandGui.logText += "\nMissionen wurden generiert.";
  		SuperRisikolandGui.logTextArea.setText(SuperRisikolandGui.logText);
  		for(int i = 0 ; i < spiel.getAnzahlSpieler() ; i++)
    	{
    		IO.println(spiel.getSpieler(i).getName() + "'s Mission:  " + spiel.getSpieler(i).getMission().getAufgabenText());
    	}
		return missionen;
	}
	
	protected void setBesitzer(Spieler besitzer)
	{
		this.besitzer = besitzer;
	}
	
	public String getAufgabenText()
	{
		return this.aufgabenText;
	}
	
	public String getMissionErfuelltText()
	{
		return this.missionErfuelltText;
	}


	public boolean missionErfuellt() {
		return false;
	}
	
	public void setAufgabenText()
	{
		
	}

	public void setMissionErfuelltText() {

	}
}

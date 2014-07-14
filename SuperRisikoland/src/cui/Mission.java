package cui;

import inf.MissionInterface;
import inf.SpielerInterface;
import inf.SpielfeldInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class Mission extends UnicastRemoteObject  implements Serializable, MissionInterface
{
	protected String aufgabenText;
	protected String missionErfuelltText;
	protected SpielerInterface besitzer;
	
	public Mission() throws RemoteException{
		
	}
	
	public Vector<MissionInterface> missionenErstellen(SpielfeldInterface spiel) throws RemoteException
	{
		Vector<MissionInterface> missionen = new Vector<MissionInterface>();
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
  		/*for(int i = 0 ; i < spiel.getAnzahlSpieler() ; i++)
  		{
  			int zufallsMission = (int) (Math.random()*missionen.size());
  			((Spieler) spiel.getSpieler(i)).setMission(missionen.elementAt(zufallsMission));
  			spiel.getSpieler(i).getMission().setBesitzer(spiel.getSpieler(i));
  			spiel.getSpieler(i).getMission().setAufgabenText();
  			spiel.getSpieler(i).getMission().setMissionErfuelltText();
  			missionen.remove(zufallsMission);
  		}
  		
  		IO.println("Missionen wurden generiert.");
  		//TODO spiel.getServer().setLogText("Missionen wurden generiert.");
  		for(int i = 0 ; i < spiel.getAnzahlSpieler() ; i++)
    	{
    		IO.println(spiel.getSpieler(i).getName() + "'s Mission:  " + spiel.getSpieler(i).getMission().getAufgabenText());
    	}*/
		return missionen;
	}
	
	public void setBesitzer(SpielerInterface besitzer)
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


	public boolean missionErfuellt() throws RemoteException {
		return false;
	}
	
	public void setAufgabenText() throws RemoteException
	{
		
	}

	public void setMissionErfuelltText() throws RemoteException {

	}

}

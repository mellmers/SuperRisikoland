public class Mission {
	protected String aufgabenText;
	protected String missionErfuelltText;
	protected Spieler besitzer;
	
	public Mission(){
		
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

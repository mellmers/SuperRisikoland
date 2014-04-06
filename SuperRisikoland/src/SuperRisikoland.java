public class SuperRisikoland {

	public static void main(String[] args) { 

		Spielfeld spiel;
		int anzahlSpieler;
		String[] spielerNamen;
        int spielVariante;
		
        // lasse Benutzer Anzahl der Spieler eingeben
		System.out.println("Anzahl der Spieler?");
		//KP was dahin kommt statt IO.readInt
		anzahlSpieler = System.in.read();

        // erzeuge Array von Spielernamen
        spielerNamen = new String[anzahlSpieler];
                
        // lasse Benutzer den Namen jedes Spielers eingeben
        for(int i = 0; i < anzahlSpieler; i++) 
        {
        System.out.println("Name des " + (i + 1) + ". Spielers?");
        spielerNamen[i] = new String(System.in.toString());
        }
                
        // lasse Bentzer Spielvariante (Aufgaben oder Welteroberung) auswaehlen
                
        // erzeuge neues Spiel
		//Spielfeld spiel = new Spielfeld(anzahlSpieler, spielerNamen, spielVariante);
		
		
        
        
        
        
        
        
        
        
        
        
		/*Spielfeld feld1 = new Spielfeld();
		feld1.spielerErstellen("Horst");
		feld1.spielerErstellen("Uschi");
		Spieler s1 = (Spieler) feld1.spieler.elementAt(0);
		Spieler s2 = (Spieler) feld1.spieler.elementAt(1);
		System.out.println("Einheit: "+feld1.getKarte(s1));
		System.out.println("Auftrag: "+s1.getAuftrag());

		feld1.nachbarnVerteilen();
		if(feld1.istNachbarn(41, 39)){
			System.out.println("sind Nachbarn.");
		}
		Spiel s = new Spiel();
		s.befreien(s1, s2, feld1.laender[0], feld1.laender[1], 3, 2);
	*/
	}
}


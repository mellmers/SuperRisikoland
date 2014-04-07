public class SuperRisikoland 
{

	public static void main(String[] args) 
	{ 
		int anzahlSpieler;
        int spielVariante;
		
        // Benutzereingabe: Anzahl der Spieler
		IO.println("Anzahl der Spieler? (2-6 Spieler sind moeglich)");
		anzahlSpieler = IO.readInt();
		while (anzahlSpieler < 2 || anzahlSpieler > 6)
		{
			IO.println("Anzahl der Spieler?");
			anzahlSpieler = IO.readInt();
		}
		
		// Bentzereingabe: Spielvariante (Mission oder Welteroberung)
        IO.println("Welche Spielvariante moechten Sie spielen?");
        IO.println("Spielvariante 1: Mit Mission   --> Geben Sie ein: 1");
        IO.println("Spielvariante 2: Welteroberung --> Geben Sie ein: 2");    
        spielVariante = IO.readInt();
 
		// erzeuge neues Spiel
		Spielfeld spiel = new Spielfeld(anzahlSpieler, spielVariante);
		
        // Benutzereingabe: Namen jedes Spielers
        for(int i = 0; i < anzahlSpieler; i++) 
        {
	       	spiel.spielerErstellen();
        }
        
        // Liste aller Laender ausgeben (mit oder ohne Besitzer)
        
        
        
        
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


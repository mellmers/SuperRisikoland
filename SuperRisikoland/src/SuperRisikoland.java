public class SuperRisikoland
{

	public static void main(String[] args)
	{ 
		int anzahlSpieler;
        int spielVariante;
        Spieler aktuellerSpieler;
        int aktuellerSpielerId;
		
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
 
		// erzeugt Spielfeld
		Spielfeld spiel = new Spielfeld(anzahlSpieler, spielVariante);
		
        // Benutzereingabe: Namen jedes Spielers
        for(int i = 0; i < anzahlSpieler; i++) 
        {
	       	spiel.spielerErstellen(i+1);
        }
        
        // Liste aller Laender ausgeben (mit oder ohne Besitzer)
        
        spiel.startLaenderVerteilen();
        aktuellerSpielerId = (int) (Math.random()*anzahlSpieler);
        aktuellerSpieler = spiel.getSpieler(aktuellerSpielerId);
        
        // Spielablauf
        while(true) 
        {
        	// LänderListe ausgeben
        	spiel.gesamtLaenderListeAusgeben();
        	
        	// aktueller Spieler
        	IO.println(aktuellerSpieler.getName() + " ist am Zug!");
        	
        	// Schritt 1: Einheiten verteilen/Neue Armeen
        	
        	spiel.neueArmeen(aktuellerSpieler);
        	
        	// Ende Schritt 1
        	
        	// Schritt 2: Befreien von Ländern
        	
        	spiel.abfrageSollGesamtLaenderListeAusgegebenWerden();
        	IO.println("Moechtest du angreifen? (j/n)");
        	char eingabe = IO.readChar();
        	
        	while(eingabe == 'j')
        	{
        		IO.println("Gib die LaenderID des Angriffslandes ein:");
        		int angId = IO.readInt();
        		IO.println("Gib die LaenderID des Verteidigerlandes ein:");
        		int verId = IO.readInt();
        		IO.println("Gib an mit wieviel Einheiten du angreifen moechtest:");
        		int angTruppen = IO.readInt();
	        	IO.println("Gib an mit wieviel Einheiten " + spiel.getLand(verId).getBesitzer().getName() +" verteidigen moechte:");
	        	int verTruppen = IO.readInt();
	        	
	        	spiel.befreien(aktuellerSpieler, angTruppen, verTruppen, angId, verId);
	
	        	spiel.abfrageSollGesamtLaenderListeAusgegebenWerden();
	        	IO.println("Moechtest du nochmal angreifen? (j/n)");
	        	eingabe = IO.readChar();
        	}
        	// Karte vergeben
        	if(spiel.getAnzahlEroberterLaender() > 0)
        	{
        		spiel.getKarte(aktuellerSpieler);
        	}
        	
        	// Ende Schritt 2
        	
        	// Schritt 3: Truppen nachziehen
        	
        	spiel.abfrageSollGesamtLaenderListeAusgegebenWerden();
        	IO.println("Moechtest du Truppen nachziehen? (j/n)");
        	eingabe = IO.readChar();
        	while(eingabe == 'j')
        	{
        		int start;
        		int ziel;
        		do
        		{
	        		IO.println("Nachziehen von:\n(LandID eingeben)");
	        		start = IO.readInt();
	        		IO.println("Nachziehen in:\n(LandID eingeben)");
	        		ziel = IO.readInt();
	        		
        		} while(!(spiel.sindNachbarn(start, ziel)) || spiel.landWurdeVerwendet(start) || spiel.getLand(start).getBesitzer() != aktuellerSpieler);
        		
        		spiel.einheitenNachziehen(start, ziel);
        		
        		spiel.abfrageSollGesamtLaenderListeAusgegebenWerden();
        		IO.println("Moechtest du Truppen nachziehen? (j/n)");
            	eingabe = IO.readChar();
        	}
        	
        	// Ende Schritt 3
        	
        	// TODO eroberteLaender clearen !!
        	spiel.setEroberteLaenderNull();
        	
        	// Spielerwechsel (nächster Spieler)
        	if(aktuellerSpielerId == anzahlSpieler-1)
        	{
        		aktuellerSpielerId = 0;
        	}
        	else
        	{
        		aktuellerSpielerId++;
        	}
        	aktuellerSpieler = spiel.getSpieler(aktuellerSpielerId);
        	// Spielerwechsel Ende
        }
	}
	
	
}


package cui;

import java.util.Vector;

public class SuperRisikolandCui
{
	public static void main(String[] args)
	{		
		// Spielvariblen, die nicht gespeichert werden muessen
		int anzahlSpieler;
        int spielVariante;
        Spieler aktuellerSpieler;
        int aktuellerSpielerId;
        Vector<Mission> missionen = new Vector<Mission>();

        
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
        	IO.println("Wie ist der Name des " + (i+1) + " Spielers?");
        	spiel.spielerErstellen(i, IO.readString(), null);
        }
        
        // Missionen generieren, wenn Spielvariante 1 gewaehlt wurde
        if(spielVariante == 1){
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
      			missionen.add(( spieler < anzahlSpieler) ? new SpielerVernichtenMission(spiel, spieler) : new AnzahlLaenderErobernMission(24));
      		}
      		IO.println("Missionen generiert");
      		
      		// Missionen verteilen
      		for(int i = 0 ; i < anzahlSpieler ; i++)
      		{
      			int zufallsMission = (int) (Math.random()*missionen.size());
      			spiel.getSpieler(i).setMission(missionen.elementAt(zufallsMission));
      			spiel.getSpieler(i).getMission().setBesitzer(spiel.getSpieler(i));
      			spiel.getSpieler(i).getMission().setAufgabenText();
      			spiel.getSpieler(i).getMission().setMissionErfuelltText();
      			missionen.remove(zufallsMission);
      		}
        }
        
        // Liste aller Laender ausgeben (mit oder ohne Besitzer)
        
        spiel.startLaenderVerteilen();
        aktuellerSpielerId = (int) (Math.random()*anzahlSpieler);
        aktuellerSpieler = spiel.getSpieler(aktuellerSpielerId);
        
        for(int i = 0 ; i < anzahlSpieler ; i++)
        {
        	IO.println(spiel.getSpieler(i).getName() + "   Mission:   " + spiel.getSpieler(i).getMission().getAufgabenText());
        }
        
        // Spielablauf
        while(true) // Spiel beenden einfuegen
        {
        	// LaenderListe ausgeben
        	spiel.gesamtLaenderListeAusgeben();
        	
        	// aktueller Spieler
        	IO.println(aktuellerSpieler.getName() + " ist am Zug!");
        	IO.println(aktuellerSpieler.getLaenderAnzahl() + " Laender im Besitz");
        	
        	// Mission des Spielers ausgeben
        	if(spielVariante == 1){
        		IO.println("" + aktuellerSpieler.getMission().getAufgabenText());
        	}
        	// Schritt 1: Einheiten verteilen/Neue Armeen
        	
        	spiel.neueArmeen(aktuellerSpieler);
        	
        	// Ende Schritt 1
        	
        	// Schritt 2: Befreien von Laendern
        	
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
	        		
        		} while(!(spiel.sindNachbarn(start, ziel)) || spiel.getLand(start).getBesitzer() != aktuellerSpieler);
        		
        		spiel.einheitenNachziehen(start, ziel);
        		
        		spiel.abfrageSollGesamtLaenderListeAusgegebenWerden();
        		IO.println("Moechtest du Truppen nachziehen? (j/n)");
            	eingabe = IO.readChar();
        	}
        	
        	// Ende Schritt 3
        	
        	// TODO eroberteLaender clearen !!
        	spiel.setEroberteLaenderNull();
        	spiel.setBenutzteEinheitenNull();
        	
        	// Spielerwechsel (naechster Spieler)
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


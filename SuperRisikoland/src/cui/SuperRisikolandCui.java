package cui;

import inf.SpielerInterface;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.Vector;

public class SuperRisikolandCui
{
	public static void main(String[] args) throws RemoteException
	{	
		int anzahlSpieler = 0;
        int spielVariante = 0;
        SpielerInterface aktuellerSpieler = null;
        int aktuellerSpielerId = 0;
        Vector<SpielerInterface> alleSpieler = new Vector<SpielerInterface>();
        Spielfeld spiel = null;
		
		char jaNein;
		do
		{
			IO.println("Moechtest du einen Spielstand laden? J/N");
			jaNein = IO.readChar();
		}while(jaNein != 'J' && jaNein != 'N');
		
		if(jaNein == 'J')
		{
			if(jaNein == 'J'){
	        	IO.println("Geben Sie bitte den Namen Ihres Speicherstandes an:");
	        	String name = IO.readString();
	        	try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name+".ser"))) 
	        	{
	        		spiel = (Spielfeld) ois.readObject();
	        		aktuellerSpieler = (SpielerInterface) ois.readObject();
	        	} catch (final ClassNotFoundException e) {
	        		IO.println("KLasse nicht gefunden!");
	        		jaNein = 'N';
	        	} catch (final FileNotFoundException e) {
	        		IO.println("Datei nicht gefunden!");
	        		jaNein = 'N';
				} catch (final IOException e) {
					IO.println("exception");
					jaNein = 'N';
	        	}
	        }
		}
		if(jaNein == 'N')
		{
			// Spielvariblen, die nicht gespeichert werden muessen
			
	        // vTest
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
			
	
	        // Benutzereingabe: Namen jedes Spielers
	        for(int i = 0; i < anzahlSpieler; i++) 
	        {
	        	IO.println("Wie ist der Name des " + (i+1) + " Spielers?");
	        	Spieler spieler = new Spieler(i, IO.readString(), null, null);
	        	alleSpieler.add(spieler);
	        }
	        spiel = new Spielfeld(null, alleSpieler, spielVariante);
	        
	        // Missionen generieren, wenn Spielvariante 1 gewaehlt wurde
	        if(spielVariante == 1){
	        	new Mission().missionenErstellen(spiel);
	        }
	        
	        // Liste aller Laender ausgeben (mit oder ohne Besitzer)
	        
	        aktuellerSpielerId = (int) (Math.random()*anzahlSpieler);
	        aktuellerSpieler = spiel.getSpieler(aktuellerSpielerId);
	        
	        if(spielVariante == 1)
	        {
	        	for(int i = 0 ; i < anzahlSpieler ; i++)
	        	{
	        		IO.println(spiel.getSpieler(i).getName() + "   Mission:   " + spiel.getSpieler(i).getMission().getAufgabenText());
	        	}
	        }
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
        	
        	while(!spiel.neueArmeen(aktuellerSpieler, false, 0, 0));
        	
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
	        	
	        	spiel.befreien(aktuellerSpieler, angTruppen, verTruppen, angId, verId, false);
	        	
	        	
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
        	
        	// Schritt 3: Umverteilung
        	
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
        		
        		spiel.einheitenNachziehen(start, ziel, 0, false);
        		
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
        	//Spiel Speichern?
        	IO.println("Moechten Sie das Spiel speichern?  J/N");
        	if(IO.readChar() == 'J')
        	{
        		IO.println("Bitte geben Sie den gewuenschten Namen ein?");
        		String name = IO.readString() + ".ser";
        		try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name)))
        		{
        				oos.writeObject(spiel);
        				oos.writeObject(aktuellerSpieler);
        				
        		} catch (final IOException e){}
        	}
        }
	}
}


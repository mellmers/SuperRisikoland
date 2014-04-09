public class SuperRisikoland
{

	public static void main(String[] args)
	{ 
		int anzahlSpieler;
        int spielVariante;
        Spieler aktuellerSpieler;
		
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
	       	spiel.spielerErstellen(i+1);
        }
        
        // Liste aller Laender ausgeben (mit oder ohne Besitzer)
        
        spiel.startLaenderVerteilen();
        
        aktuellerSpieler = spiel.spieler.elementAt(0);
        IO.println(aktuellerSpieler.getName() + " beginnt das Spiel!");
        
        while(aktuellerSpieler == spiel.spieler.elementAt(0)) 
        {
        	// Spielablauf
        	
        	// Schritt 1: Einheiten verteilen/Neue Armeen
        	//Variablen
        	int einheiten = 0;
        	int zuVerteilendeEinheiten = 0;
        	int landId = 0;
        	
        	IO.println(aktuellerSpieler.getName() + " muss nun " + spiel.laenderZaehlen(aktuellerSpieler) + " Einheiten verteilen.\n"
        			+ "Geben Sie die ID Ihres Landes ein, in dem Einheiten stationiert werden sollen.");
        	landId = IO.readInt();
        	while (landId < 0 || landId > 41)
        	{
        		IO.println("Falsche Eingabe!");
        		landId = IO.readInt();
        	}
	       	IO.println("Wie viele Einheiten sollen stationiert werden?");
	       	einheiten = IO.readInt();
	       	zuVerteilendeEinheiten = spiel.laenderZaehlen(aktuellerSpieler);
	       	while (einheiten < 0 || einheiten > spiel.laenderZaehlen(aktuellerSpieler))
	       	{
	       		IO.println("Falsche Eingabe! Es koennen maximal " + spiel.laenderZaehlen(aktuellerSpieler) + " Einheiten stationiert werden");
	       		einheiten = IO.readInt();
	        }
	        if(aktuellerSpieler.meinLand(spiel.laender[landId])) // else-Zweig ist in der Funktion definiert, falls false zurück kommt
	        {
	        	spiel.laender[landId].setTruppenstaerke(einheiten);
		       	zuVerteilendeEinheiten -= einheiten;
		       	IO.println(spiel.laender[landId].getTruppenstaerke() + " Einheiten auf " + spiel.laender[landId].getName());
        	}
        	while (zuVerteilendeEinheiten > 0)
        	{
        		IO.println(aktuellerSpieler.getName() + " muss noch " + zuVerteilendeEinheiten + " Einheiten verteilen.\n"
            			+ "Geben Sie die ID Ihres Landes ein, in dem Einheiten stationiert werden sollen.");
	        	landId = IO.readInt();
	        	while (landId < 0 || einheiten > 41)
	        	{
	        		IO.println("Falsche Eingabe!");
	        		landId = IO.readInt();
	        	}
	        	IO.println("Wie viele Einheiten sollen stationiert werden?");
	        	einheiten = IO.readInt();
	        	while (einheiten < 0 || einheiten > zuVerteilendeEinheiten)
	        	{
	        		IO.println("Falsche Eingabe! Es koennen maximal " + zuVerteilendeEinheiten + " Einheiten stationiert werden");
	        		einheiten = IO.readInt();
	        	}
	        	spiel.laender[landId].setTruppenstaerke(einheiten);
	        	zuVerteilendeEinheiten -= einheiten;
	        	IO.println(spiel.laender[landId].getTruppenstaerke() + " Einheiten auf " + spiel.laender[landId].getName());
        	}
        	// Serie einsetzen
        	
        	// Ende Schritt 1
        	
        	
        	aktuellerSpieler = null;
        }
	}
}


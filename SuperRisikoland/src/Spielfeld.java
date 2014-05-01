import java.util.Vector;

public class Spielfeld 
{
	private int spielvariante;
	
	private Kontinent[] kontinente = new Kontinent[7];
	private Land[] laender = new Land[44];
	private Vector<Land> ausgeteilteKarten = new Vector<Land>();
	private int spielerZahl;
	private Vector<Spieler> spieler = new Vector<Spieler>();
	private int maxSpieler = 7;
	private Vector<Land> eroberteLaender = new Vector<Land>();
	private int eingetauschteSerien = 0;
	private int zusatzEinheitenSerie = 4;
	
	// Getter
	public Kontinent getKontinent(int kontinentId)
	{
		return this.kontinente[kontinentId];
	}
	
	public int getAnzahlEroberterLaender()
	{
		return eroberteLaender.size();
	}
	
	public Land getLand(int laenderId)
	{
		return this.laender[laenderId];
	}
	
	public Spieler getSpieler(int spielerId)
	{
		return this.spieler.elementAt(spielerId);
	}
	
	// Setter
	
	public void setEroberteLaenderNull()
	{
		this.eroberteLaender.clear();
	}
	
	public Spielfeld(int anzahlSpieler, int spielVariante) 
	{
			this.spielvariante = spielVariante;
		
			kontinente[0] = new Kontinent ("Nord-Amerika", 9, 5);
			kontinente[1] = new Kontinent ("Sued-Amerika", 4, 2);
			kontinente[2] = new Kontinent ("Europa", 7, 5);
			kontinente[3] = new Kontinent ("Afrika", 6, 3);
			kontinente[4] = new Kontinent ("Asien", 12, 7);
			kontinente[5] = new Kontinent ("Australien", 4, 2);
			kontinente[6] = new Kontinent ("Joker", 2, 0);
			
			laender[0] = new Land(kontinente[0], "Alaska", "Soldat", 3);
			laender[1] = new Land(kontinente[0], "Nordwest-Territorium", "Kanone", 4);
			laender[2] = new Land(kontinente[0], "Groenland", "Pferd", 4);
			laender[3] = new Land(kontinente[0], "Alberta", "Soldat", 4);
			laender[4] = new Land(kontinente[0], "Ontario", "Pferd", 6);
			laender[5] = new Land(kontinente[0], "Quebec", "Kanone", 3);
			laender[6] = new Land(kontinente[0], "Weststaaten", "Soldat", 4);
			laender[7] = new Land(kontinente[0], "Oststaaten", "Kanone", 4);
			laender[8] = new Land(kontinente[0], "Mittelamerika", "Pferd", 3);
			laender[9] = new Land(kontinente[1], "Venezuela", "Kanone", 3);
			laender[10] = new Land(kontinente[1], "Peru", "Pferd", 3);
			laender[11] = new Land(kontinente[1], "Brasilien", "Kanone", 4);
			laender[12] = new Land(kontinente[1], "Argentinien", "Soldat", 2);
			laender[13] = new Land(kontinente[2], "Island", "Soldat", 3);
			laender[14] = new Land(kontinente[2], "Skandinavien", "Kanone", 4);
			laender[15] = new Land(kontinente[2], "Grossbritannien", "Kanone", 4);
			laender[16] = new Land(kontinente[2], "Mitteleuropa", "Pferd", 5);
			laender[17] = new Land(kontinente[2], "Ukraine", "Kanone", 6);
			laender[18] = new Land(kontinente[2], "Westeuropa", "Soldat", 4);
			laender[19] = new Land(kontinente[2], "Suedeuropa", "Pferd", 6);
			laender[20] = new Land(kontinente[3], "Nordwest-Afrika", "Soldat", 6);
			laender[21] = new Land(kontinente[3], "Aegypten", "Soldat", 4);
			laender[22] = new Land(kontinente[3], "Kongo", "Pferd", 3);
			laender[23] = new Land(kontinente[3], "Ost-Afrika", "Kanone", 5);
			laender[24] = new Land(kontinente[3], "Sued-Afrika", "Kanone", 3);
			laender[25] = new Land(kontinente[3], "Madagaskar", "Soldat", 2);
			laender[26] = new Land(kontinente[4], "Ural", "Pferd", 4);
			laender[27] = new Land(kontinente[4], "Sibirien", "Pferd", 5);
			laender[28] = new Land(kontinente[4], "Jakutien", "Pferd", 3);
			laender[29] = new Land(kontinente[4], "Kamtschatka", "Pferd", 5);
			laender[30] = new Land(kontinente[4], "Irkutsk", "Soldat", 4);
			laender[31] = new Land(kontinente[4], "Mongolei", "Kanone", 5);
			laender[32] = new Land(kontinente[4], "Japan", "Soldat", 2);
			laender[33] = new Land(kontinente[4], "Afghanistan", "Soldat", 5);
			laender[34] = new Land(kontinente[4], "China", "Pferd", 6);
			laender[35] = new Land(kontinente[4], "Mittlerer Osten", "Kanone", 5);
			laender[36] = new Land(kontinente[4], "Indien", "Soldat", 4);
			laender[37] = new Land(kontinente[4], "Siam", "Kanone", 3);
			laender[38] = new Land(kontinente[5], "Indonesien", "Pferd", 3);
			laender[39] = new Land(kontinente[5], "Neuguinea", "Pferd", 3);
			laender[40] = new Land(kontinente[5], "West-Australien", "Kanone", 3);
			laender[41] = new Land(kontinente[5], "Ost-Australien", "Soldat", 2);
			laender[42] = new Land(kontinente[6], "Joker", "Joker", 0);
			laender[43] = new Land(kontinente[6], "Joker", "Joker", 0);
			
			nachbarnVerteilen();
			
			IO.println("Spielfeld mit " + anzahlSpieler + " Spielern und Spielvariante " + spielVariante + " erstellt.");
		// 14x Soldat, 14x Pferd, 14x Kanone  + 2 "Joker" (Pferd, Soldat oder Kanone)1
	}
		
	public void gesamtLaenderListeAusgeben()
	{
		for (int i = 0; i < 42; i++)
		{
			IO.println("ID: " + i  + " " + this.laender[i].getKontinent().getName() + " Land: '" + this.laender[i].getName() + "' Besitzer: '" + this.laender[i].getBesitzer().getName() + "' Truppenstaerke: " + this.laender[i].getTruppenstaerke());
		}
	}
	
	public void abfrageSollGesamtLaenderListeAusgegebenWerden()
	{
		IO.println("Laenderliste ausgeben? (wenn gewuenscht, dann 'l' eingeben)");
		char c = IO.readChar();
		if (c == 'l')
		{
			this.gesamtLaenderListeAusgeben();
		}
	}

	public void nachbarnVerteilen()
	{
		laender[0].nachbarLaender[0] = laender[1];
		laender[0].nachbarLaender[1] = laender[3];
		laender[0].nachbarLaender[2] = laender[29];

		laender[1].nachbarLaender[0] = laender[2];
		laender[1].nachbarLaender[1] = laender[3];
		laender[1].nachbarLaender[2] = laender[4];
		laender[1].nachbarLaender[3] = laender[0];

		laender[2].nachbarLaender[0] = laender[1];
		laender[2].nachbarLaender[1] = laender[4];
		laender[2].nachbarLaender[2] = laender[5];
		laender[2].nachbarLaender[3] = laender[13];

		laender[3].nachbarLaender[0] = laender[0];
		laender[3].nachbarLaender[1] = laender[1];
		laender[3].nachbarLaender[2] = laender[4];
		laender[3].nachbarLaender[3] = laender[6];

		laender[4].nachbarLaender[0] = laender[1];
		laender[4].nachbarLaender[1] = laender[2];
		laender[4].nachbarLaender[2] = laender[3];
		laender[4].nachbarLaender[3] = laender[5];
		laender[4].nachbarLaender[4] = laender[6];
		laender[4].nachbarLaender[5] = laender[7];

		laender[5].nachbarLaender[0] = laender[7];
		laender[5].nachbarLaender[1] = laender[4];
		laender[5].nachbarLaender[2] = laender[2];

		laender[6].nachbarLaender[0] = laender[3];
		laender[6].nachbarLaender[1] = laender[4];
		laender[6].nachbarLaender[2] = laender[7];
		laender[6].nachbarLaender[3] = laender[8];

		laender[7].nachbarLaender[0] = laender[6];
		laender[7].nachbarLaender[1] = laender[5];
		laender[7].nachbarLaender[2] = laender[4];
		laender[7].nachbarLaender[3] = laender[8];

		laender[8].nachbarLaender[0] = laender[7];
		laender[8].nachbarLaender[1] = laender[6];
		laender[8].nachbarLaender[2] = laender[9];

		laender[9].nachbarLaender[0] = laender[8];
		laender[9].nachbarLaender[1] = laender[10];
		laender[9].nachbarLaender[2] = laender[11];

		laender[10].nachbarLaender[0] = laender[9];
		laender[10].nachbarLaender[1] = laender[11];
		laender[10].nachbarLaender[2] = laender[12];

		laender[11].nachbarLaender[0] = laender[9];
		laender[11].nachbarLaender[1] = laender[10];
		laender[11].nachbarLaender[2] = laender[12];
		laender[11].nachbarLaender[3] = laender[20];

		laender[12].nachbarLaender[0] = laender[11];
		laender[12].nachbarLaender[1] = laender[10];

		laender[13].nachbarLaender[0] = laender[2];
		laender[13].nachbarLaender[1] = laender[15];
		laender[13].nachbarLaender[2] = laender[14];

		laender[14].nachbarLaender[0] = laender[13];
		laender[14].nachbarLaender[1] = laender[15];
		laender[14].nachbarLaender[2] = laender[16];
		laender[14].nachbarLaender[3] = laender[17];

		laender[15].nachbarLaender[0] = laender[13];
		laender[15].nachbarLaender[1] = laender[14];
		laender[15].nachbarLaender[2] = laender[16];
		laender[15].nachbarLaender[3] = laender[18];

		laender[16].nachbarLaender[0] = laender[15];
		laender[16].nachbarLaender[1] = laender[14];
		laender[16].nachbarLaender[2] = laender[18];
		laender[16].nachbarLaender[3] = laender[19];
		laender[16].nachbarLaender[4] = laender[17];

		laender[17].nachbarLaender[0] = laender[14];
		laender[17].nachbarLaender[1] = laender[16];
		laender[17].nachbarLaender[2] = laender[19];
		laender[17].nachbarLaender[3] = laender[35];
		laender[17].nachbarLaender[4] = laender[33];
		laender[17].nachbarLaender[5] = laender[26];

		laender[18].nachbarLaender[0] = laender[15];
		laender[18].nachbarLaender[1] = laender[16];
		laender[18].nachbarLaender[2] = laender[19];
		laender[18].nachbarLaender[3] = laender[20];

		laender[19].nachbarLaender[0] = laender[16];
		laender[19].nachbarLaender[1] = laender[17];
		laender[19].nachbarLaender[2] = laender[18];
		laender[19].nachbarLaender[3] = laender[20];
		laender[19].nachbarLaender[4] = laender[21];
		laender[19].nachbarLaender[5] = laender[35];

		laender[20].nachbarLaender[0] = laender[11];
		laender[20].nachbarLaender[1] = laender[21];
		laender[20].nachbarLaender[2] = laender[22];
		laender[20].nachbarLaender[3] = laender[18];
		laender[20].nachbarLaender[4] = laender[19];
		laender[20].nachbarLaender[5] = laender[23];

		laender[21].nachbarLaender[0] = laender[19];
		laender[21].nachbarLaender[1] = laender[20];
		laender[21].nachbarLaender[2] = laender[23];
		laender[21].nachbarLaender[3] = laender[35];

		laender[22].nachbarLaender[0] = laender[20];
		laender[22].nachbarLaender[1] = laender[23];
		laender[22].nachbarLaender[2] = laender[24];
		
		laender[23].nachbarLaender[0] = laender[20];
		laender[23].nachbarLaender[1] = laender[21];
		laender[23].nachbarLaender[2] = laender[22];
		laender[23].nachbarLaender[3] = laender[24];
		laender[23].nachbarLaender[4] = laender[25];

		laender[24].nachbarLaender[0] = laender[22];
		laender[24].nachbarLaender[1] = laender[23];
		laender[24].nachbarLaender[2] = laender[25];

		laender[25].nachbarLaender[0] = laender[23];
		laender[25].nachbarLaender[1] = laender[24];

		laender[26].nachbarLaender[0] = laender[17];
		laender[26].nachbarLaender[1] = laender[33];
		laender[26].nachbarLaender[2] = laender[34];
		laender[26].nachbarLaender[3] = laender[27];

		laender[27].nachbarLaender[0] = laender[26];
		laender[27].nachbarLaender[1] = laender[28];
		laender[27].nachbarLaender[2] = laender[30];
		laender[27].nachbarLaender[3] = laender[34];
		laender[27].nachbarLaender[4] = laender[31];

		laender[28].nachbarLaender[0] = laender[27];
		laender[28].nachbarLaender[1] = laender[29];
		laender[28].nachbarLaender[2] = laender[30];

		laender[29].nachbarLaender[0] = laender[28];
		laender[29].nachbarLaender[1] = laender[30];
		laender[29].nachbarLaender[2] = laender[31];
		laender[29].nachbarLaender[3] = laender[32];
		laender[29].nachbarLaender[4] = laender[0];

		laender[30].nachbarLaender[0] = laender[27];
		laender[30].nachbarLaender[1] = laender[28];
		laender[30].nachbarLaender[2] = laender[29];
		laender[30].nachbarLaender[3] = laender[31];

		laender[31].nachbarLaender[0] = laender[27];
		laender[31].nachbarLaender[1] = laender[29];
		laender[31].nachbarLaender[2] = laender[30];
		laender[31].nachbarLaender[3] = laender[32];
		laender[31].nachbarLaender[4] = laender[34];

		laender[32].nachbarLaender[0] = laender[29];
		laender[32].nachbarLaender[1] = laender[31];

		laender[33].nachbarLaender[0] = laender[17];
		laender[33].nachbarLaender[1] = laender[26];
		laender[33].nachbarLaender[2] = laender[34];
		laender[33].nachbarLaender[3] = laender[35];
		laender[33].nachbarLaender[4] = laender[36];

		laender[34].nachbarLaender[0] = laender[26];
		laender[34].nachbarLaender[1] = laender[27];
		laender[34].nachbarLaender[2] = laender[31];
		laender[34].nachbarLaender[3] = laender[33];
		laender[34].nachbarLaender[4] = laender[36];
		laender[34].nachbarLaender[5] = laender[37];

		laender[35].nachbarLaender[0] = laender[17];
		laender[35].nachbarLaender[1] = laender[19];
		laender[35].nachbarLaender[2] = laender[21];
		laender[35].nachbarLaender[3] = laender[33];
		laender[35].nachbarLaender[4] = laender[36];

		laender[36].nachbarLaender[0] = laender[33];
		laender[36].nachbarLaender[1] = laender[34];
		laender[36].nachbarLaender[2] = laender[35];
		laender[36].nachbarLaender[3] = laender[37];

		laender[37].nachbarLaender[0] = laender[34];
		laender[37].nachbarLaender[1] = laender[36];
		laender[37].nachbarLaender[2] = laender[38];
		
		laender[38].nachbarLaender[0] = laender[37];
		laender[38].nachbarLaender[1] = laender[39];
		laender[38].nachbarLaender[2] = laender[40];

		laender[39].nachbarLaender[0] = laender[38];
		laender[39].nachbarLaender[1] = laender[40];
		laender[39].nachbarLaender[2] = laender[41];

		laender[40].nachbarLaender[0] = laender[38];
		laender[40].nachbarLaender[1] = laender[39];
		laender[40].nachbarLaender[2] = laender[41];

		laender[41].nachbarLaender[0] = laender[39];
		laender[41].nachbarLaender[1] = laender[40];
	}

	public boolean spielerErstellen(int spielerID)
	{
		if(this.spieler.size() < this.maxSpieler)
		{
			// Im Spielerobjekt Konstruktor wird die Aufforderung fuer den Namen verlangt
			this.spieler.add(new Spieler(spielerID));
			this.spielerZahl ++;
			return true;
		}
		return false;
	}

	public void startLaenderVerteilen()
	{
		int aufteilen = 42/this.spielerZahl;
		int rest = 42%this.spielerZahl;
		for(int i = 0; i < this.spieler.size() ; i++)
		{
			for(int j = 0; j < aufteilen ; j++)
			{
				getStartKarte(this.spieler.elementAt(i));
			}
		}
		if(rest > 0)
		{
			for(int k = 0; k < rest ; k++)
			{
				getStartKarte(this.spieler.elementAt(k));
			}
		}
		this.ausgeteilteKarten.clear();
	}
	
	public void getStartKarte(Spieler s) 
	{
		int i;
		do{
			i = (int) (Math.random()*42);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(laender[i]);
		this.laender[i].setBesitzer(s);
		s.landHinzufuegen(this.laender[i]);
		this.laender[i].setTruppenstaerke(1);
		IO.println(this.laender[i].getName() + " gehoert " + s.getName());
	}
	
	public void getKarte(Spieler s) 
	{
		int i;
		do
		{
			i = (int) (Math.random()*44);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(laender[i]);
		s.handkartenHinzufuegen(laender[i]);
	}

	public boolean sindNachbarn(int landIndex1, int landIndex2)
	{
		if(this.laender[landIndex1].istNachbar(this.laender[landIndex2]))
		{
			return true;
		}
		IO.println(this.laender[landIndex1].getName() + " und " + this.laender[landIndex2].getName() + " sind keine Nachbarlaender.");
		return false;
	}
	
	// Spiel-Funktionen
	
	public void einheitenNachziehen(int start, int ziel)
	{
		int menge;
		if(this.laender[start].getBesitzer() == this.laender[ziel].getBesitzer())
		{
			do
			{
				IO.println("Wieviele Einheiten moechtest du nachziehen?");
				menge = IO.readInt();
				if(laender[start].getTruppenstaerke() <= menge)
				{
					IO.println("So viele Einheiten koennen nicht nachgezogen werden!");
				}
			}
			while(laender[start].getTruppenstaerke() <= menge);
				
			laender[start].setTruppenstaerke(-1*menge);
			laender[ziel].setTruppenstaerke(menge);
		}
	}
	
	public int laenderZaehlen(Spieler s)
	{
		int anzLaender = 0;
		for(int i = 0; i < 43; i++)
		{
			if(this.laender[i].getBesitzer() == s)
			{
				anzLaender++;
			}
		}
		//IO.println(s.getName() + " besitzt " + anzLaender + " Laender.");
		anzLaender = anzLaender/3;
		if (anzLaender < 3)
		{
			return 3;
		}
		return anzLaender;
	}

	public void befreien(Spieler angreifer, int angTruppen, int verTruppen, int angId, int verId) {
			if (this.laender[angId].getTruppenstaerke() > 1 && this.laender[angId].istNachbar(this.laender[verId]) && angTruppen < this.laender[angId].getTruppenstaerke() && verTruppen <= this.laender[verId].getTruppenstaerke() && verTruppen > 0 && verTruppen < 3 && angTruppen > 0 && angTruppen < 4) 
			{
				int[] angWuerfel = new int[3];
				int[] verWuerfel = new int[3];
				if (angTruppen == 1) 
				{
					angWuerfel[0] = wuerfeln();
					verWuerfel = verteidigerWuerfeln(verTruppen);
				}
				else if (angTruppen == 2) 
				{
					angWuerfel[0] = wuerfeln();
					angWuerfel[1] = wuerfeln();
					verWuerfel = verteidigerWuerfeln(verTruppen);
				}
				else if (angTruppen == 3) 
				{
					angWuerfel[0] = wuerfeln();
					angWuerfel[1] = wuerfeln();
					angWuerfel[2] = wuerfeln();
					verWuerfel = verteidigerWuerfeln(verTruppen);
				}
				// Auswertung
				if(angTruppen<2 || verTruppen<2)
				{
					befreienAuswertung(angreifer, angTruppen, angWuerfel, verWuerfel, 1, angId, verId);
				}
				else 
				{
					befreienAuswertung(angreifer, angTruppen, angWuerfel, verWuerfel, 1, angId, verId);
					befreienAuswertung(angreifer, angTruppen, angWuerfel, verWuerfel, 2, angId, verId);
				}
			}
			else 
			{
				System.out.println("Angriff fehlgeschlagen!");
			}
		}
		
	public void befreienAuswertung(Spieler angreifer,int angTruppen, int[] angWuerfel, int[] verWuerfel, int anzRunden, int angId, int verId) {
				int hoechsteZahlAng = 0;
				int hoechsteZahlVer = 0;
				System.out.println(angWuerfel[0]);
				System.out.println(angWuerfel[1]);
				System.out.println(angWuerfel[2]);
				System.out.println(verWuerfel[0]);
				System.out.println(verWuerfel[1]);
				if(angWuerfel[0]>=angWuerfel[1] && angWuerfel[0]>=angWuerfel[2]){
					hoechsteZahlAng = angWuerfel[0];
					angWuerfel[0] = 0;
				}
				else if(angWuerfel[1]>=angWuerfel[0] && angWuerfel[1]>=angWuerfel[2]){
					hoechsteZahlAng = angWuerfel[1];
					angWuerfel[1] = 0;
				}
				else if(angWuerfel[2]>=angWuerfel[0] && angWuerfel[2]>=angWuerfel[1]){
					hoechsteZahlAng = angWuerfel[2];
					angWuerfel[2] = 0;
				}
				if(verWuerfel[0]>=verWuerfel[1]){
					hoechsteZahlVer = verWuerfel[0];
					verWuerfel[0] = 0;
				}
				else {
					hoechsteZahlVer = verWuerfel[1];
					verWuerfel[1] = 0;
				}
				System.out.println(hoechsteZahlAng);
				System.out.println(hoechsteZahlVer);
				if(hoechsteZahlAng > hoechsteZahlVer) {
					System.out.println("Runde "+ anzRunden +": Angreifer gewinnt.");
					this.laender[verId].setTruppenstaerke(-1);
					// Eroberung
					if (this.laender[verId].getTruppenstaerke() == 0) 
					{
						this.laender[verId].setBesitzer(angreifer);
						this.laender[verId].setTruppenstaerke(angTruppen);
						this.laender[angId].setTruppenstaerke(-1*angTruppen);
						angreifer.landHinzufuegen(this.laender[verId]);

						// Gewinnabfrage
						// Mission
						// TODO mission erfüllt?
						// Welteroberung
						if(this.welteroberung(angreifer))
						{
							// TODO Spielende, Siegfenster einblenden
						}
						//Ende Gewinnabfrage
						IO.println("Moechtest du Einheiten nachziehen? (j/n)");
				    	if (IO.readChar() == 'j')
				    	{
				    		this.einheitenNachziehen(angId, verId);	
				    	}
				    	
				    	this.eroberteLaender.add(this.laender[verId]);
					}
					// Ende Eroberung
				}
				else {
					System.out.println("Runde "+ anzRunden +": Verteidiger gewinnt.");
					this.laender[angId].setTruppenstaerke(-1);
				}
		}
		
	public int[] verteidigerWuerfeln(int verTruppen) {
			int[] verWuerfel = new int[2];
			if (verTruppen == 1) {
				verWuerfel[0] = wuerfeln();
			}
			else if (verTruppen == 2) {
				verWuerfel[0] = wuerfeln();
				verWuerfel[1] = wuerfeln();
			}
			return verWuerfel;
		}
		
	public int wuerfeln() 
		{
			return (int) (Math.random()*6+1);
		}
	
	public int serieEinsetzen(Spieler aktuellerSpieler)
	{
		int[] handkartenId = new int[3];
		for ( int i = 0; i < 3; i++)
		{
			IO.println(i+1 + ". Handkarte:");
			handkartenId[i] = IO.readInt();
		}
		if(aktuellerSpieler.istSerie(handkartenId[0], handkartenId[1], handkartenId[2]))
		{
			if(this.eingetauschteSerien == 0)
			{
				this.eingetauschteSerien++;
				return this.zusatzEinheitenSerie;
			}
			else if(this.eingetauschteSerien > 0 && this.eingetauschteSerien < 5)
			{
				this.eingetauschteSerien++;
				this.zusatzEinheitenSerie += 2;
				return this.zusatzEinheitenSerie;
			}
			else if(this.eingetauschteSerien == 5)
			{
				this.eingetauschteSerien++;
				this.zusatzEinheitenSerie += 3;
				return this.zusatzEinheitenSerie;
			}
			else
			{
				this.zusatzEinheitenSerie += 5;
				return this.zusatzEinheitenSerie;
			}
		}
		
		return 0;
	}

	public void neueArmeen(Spieler aktuellerSpieler)
		{	
			aktuellerSpieler.handkartenAusgeben();
			
			int zwischenSpeicherZusatzTruppenSerie = 0;
			if(aktuellerSpieler.getAnzahlHandkarten() == 5)
			{
				IO.println(aktuellerSpieler.getName() + " muss seine Handkarten einsetzen.");
				zwischenSpeicherZusatzTruppenSerie = this.serieEinsetzen(aktuellerSpieler);
			}
			else
			{
				IO.println("Moechtest du eine Serie einloesen? (j/n)");
				if(IO.readChar() == 'j')
				{
					zwischenSpeicherZusatzTruppenSerie = this.serieEinsetzen(aktuellerSpieler);
				}
			}
			int zuVerteilendeEinheiten = this.laenderZaehlen(aktuellerSpieler) + this.zusatzEinheitenKontinente(aktuellerSpieler) + zwischenSpeicherZusatzTruppenSerie;
			
			while (zuVerteilendeEinheiten > 0)
			{
		    	IO.println(aktuellerSpieler.getName() + " muss nun " + zuVerteilendeEinheiten + " Einheiten verteilen.\n"
		    			+ "Geben Sie die ID Ihres Landes ein, in dem Einheiten stationiert werden sollen.");
		    	int landId = IO.readInt();
		    	while (landId < 0 || landId > 41)
		    	{
		    		IO.println("Falsche Eingabe!/nLandID:");
		    		landId = IO.readInt();
		    	}
		       	IO.println("Wie viele Einheiten sollen stationiert werden?");
		       	int einheiten = IO.readInt();
		       	while (einheiten < 0 || einheiten > zuVerteilendeEinheiten)
		       	{
		       		IO.println("Falsche Eingabe! Es koennen maximal " + zuVerteilendeEinheiten+ " Einheiten stationiert werden!/nWie viele Einheiten sollen stationiert werden?");
		       		einheiten = IO.readInt();
		        }
		        if(aktuellerSpieler.meinLand(this.laender[landId])) // else-Zweig ist in der Funktion definiert, falls false zurück kommt
		        {
		        	this.laender[landId].setTruppenstaerke(einheiten);
			       	zuVerteilendeEinheiten -= einheiten;
			       	IO.println(this.laender[landId].getTruppenstaerke() + " Einheiten auf " + this.laender[landId].getName());
		    	}
	    	}
		}
	
	public int zusatzEinheitenKontinente(Spieler spieler)
	{
		int zusatzEinheiten = 0;
		for(int i = 0 ; i < 6 ; i++)
		{
			int laenderImBesitz = 0;
			for(int j = 0 ; j < spieler.getLaenderAnzahl() ; j++){
				if(spieler.getBesitzLandKontinent(j) == kontinente[i])
				{
					laenderImBesitz++;	
				}
			}
			if(laenderImBesitz == kontinente[i].getAnzahlLaender())
			{
				IO.println("Spieler " + spieler.getName() + " ist im Beitz von ganz " + kontinente[i].getName() + " und bekommt " + kontinente[i].getZusatzTruppen() + " zusätzliche Einheiten.");
				zusatzEinheiten += kontinente[i].getZusatzTruppen();
			}
		}
		return zusatzEinheiten;
	}

	public boolean landWurdeVerwendet(int land)
	{
		if(this.eroberteLaender.contains(this.laender[land]))
		{
			IO.println("Die Truppen des Landes " + this.laender[land].getName() + " wurden bereits im Kampf verwendet.");
			return true;
		}
		return false;
	}
	
	public boolean welteroberung(Spieler aktuellerSpieler)
	{
		if (aktuellerSpieler.getLaenderAnzahl() == 42)
		{
			return true;
		}
		return false;
	}
}

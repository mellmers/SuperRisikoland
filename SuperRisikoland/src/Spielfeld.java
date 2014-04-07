import java.util.Vector;

public class Spielfeld 
{
    public final int SPIELVARIANTE_AUFGABEN      = 0;
    public final int SPIELVARIANTE_WELTEROBERUNG = 1;
	
	private Kontinent[] kontinente = new Kontinent[7];
	private Land[] laender = new Land[44];
	private Vector<Land> ausgeteilteKarten = new Vector<Land>();
	private int spielerZahl;
	private Vector<Spieler> spieler = new Vector<Spieler>();
	private int maxSpieler = 7;
	private int spielerID = 1;

	
	public Spielfeld(int anzahlSpieler, int spielVariante) 
	{
			kontinente[0] = new Kontinent ("Nord-Amerika");
			kontinente[1] = new Kontinent ("Sued-Amerika");
			kontinente[2] = new Kontinent ("Europa");
			kontinente[3] = new Kontinent ("Afrika");
			kontinente[4] = new Kontinent ("Asien");
			kontinente[5] = new Kontinent ("Australien");
			kontinente[6] = new Kontinent ("Joker");
			
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
		// Kervins Funktion
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

	public boolean spielerErstellen()
	{
		if(this.spieler.size() < this.maxSpieler)
		{
			// Im Spielerobjekt Konstruktor wird die Aufforderung für den Namen verlangt
			this.spieler.add(new Spieler(this.spielerID));
			this.spielerID++;
			this.spielerZahl ++;
			return true;
		}
		return false;
	}

	public void startLaenderVerteilen()
	{
		int aufteilen = 42/this.spielerZahl;
		int rest = 42/this.spielerZahl;
		for(int i = 0; i < this.spieler.size() ; i++)
		{
			for(int j = 0; j < aufteilen ; j++)
			{
				getStartKarte(this.spieler.elementAt(i));		
			}
		}
		if(rest > 0)
		{
			int zahl = this.spielerZahl - 1;
			int a;
			for(int k = 0; k < rest ; k++)
			{
				a = (int) (Math.random()*zahl+1);
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
		this.laender[i].setTruppenstaerke(1);
	}
	
	public Land getKarte(Spieler s) 
	{
		int i;
		do
		{
			i = (int) (Math.random()*44);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(laender[i]);
		s.handKarten.add(laender[i]);
		return laender[i];
	}

	public void karteZurueck(Land l, Spieler s) 
	{
		s.handKarten.remove(l);
		this.ausgeteilteKarten.remove(l);
	}

	public boolean sindNachbarn(int landIndex1, int landIndex2)
	{
		if(this.laender[landIndex1].istNachbar(this.laender[landIndex2]))
		{
			return true;
		}
		return false;
	}
	
	public boolean einheitenZiehen(Spieler s,int menge, int start, int ziel)
	{
		if(sindNachbarn(start, ziel) && start != ziel && s.meinLand(this.laender[start]) && s.meinLand(this.laender[ziel]) && (laender[start].getTruppenstaerke() > menge))
		{
			laender[start].setTruppenstaerke(-1*menge);
			laender[ziel].setTruppenstaerke(menge);
			return true;
		}
		return false;
	}
	
}

import java.util.Vector;

public class Spielfeld {

	public Land[] laender = new Land[44];
	public Vector ausgeteilteKarten = new Vector();
	public int spielerZahl;
	public Vector spieler = new Vector();
	public int maxSpieler = 7;


	public Spielfeld(){
		laender[0] = new Land("Nordamerika", "Alaska", "Soldat", 3);
		laender[1] = new Land("Nordamerika", "Nordwest-Territorium", "Kanone", 4);
		laender[2] = new Land("Nordamerika", "Groenland", "Pferd", 4);
		laender[3] = new Land("Nordamerika", "Alberta", "Soldat", 4);
		laender[4] = new Land("Nordamerika", "Ontario", "Pferd", 6);
		laender[5] = new Land("Nordamerika", "Quebec", "Kanone", 3);
		laender[6] = new Land("Nordamerika", "Weststaaten", "Soldat", 4);
		laender[7] = new Land("Nordamerika", "Oststaaten", "Kanone", 4);
		laender[8] = new Land("Nordamerika", "Mittelamerika", "Pferd", 3);
		laender[9] = new Land("Suedamerika", "Venezuela", "Kanone", 3);
		laender[10] = new Land("Suedamerika", "Peru", "Pferd", 3);
		laender[11] = new Land("Suedamerika", "Brasilien", "Kanone", 4);
		laender[12] = new Land("Suedamerika", "Argentinien", "Soldat", 2);
		laender[13] = new Land("Europa", "Island", "Soldat", 3);
		laender[14] = new Land("Europa", "Skandinavien", "Kanone", 4);
		laender[15] = new Land("Europa", "Grossbritannien", "Kanone", 4);
		laender[16] = new Land("Europa", "Mitteleuropa", "Pferd", 5);
		laender[17] = new Land("Europa", "Ukraine", "Kanone", 6);
		laender[18] = new Land("Europa", "Westeuropa", "Soldat", 4);
		laender[19] = new Land("Europa", "Suedeuropa", "Pferd", 6);
		laender[20] = new Land("Afrika", "Nordwest-Afrika", "Soldat", 6);
		laender[21] = new Land("Afrika", "Aegypten", "Soldat", 4);
		laender[22] = new Land("Afrika", "Kongo", "Pferd", 3);
		laender[23] = new Land("Afrika", "Ost-Afrika", "Kanone", 5);
		laender[24] = new Land("Afrika", "Sued-Afrika", "Kanone", 3);
		laender[25] = new Land("Afrika", "Madagaskar", "Soldat", 2);
		laender[26] = new Land("Asien", "Ural", "Pferd", 4);
		laender[27] = new Land("Asien", "Sibirien", "Pferd", 5);
		laender[28] = new Land("Asien", "Jakutien", "Pferd", 3);
		laender[29] = new Land("Asien", "Kamtschatka", "Pferd", 5);
		laender[30] = new Land("Asien", "Irkutsk", "Soldat", 4);
		laender[31] = new Land("Asien", "Mongolei", "Kanone", 5);
		laender[32] = new Land("Asien", "Japan", "Soldat", 2);
		laender[33] = new Land("Asien", "Afghanistan", "Soldat", 5);
		laender[34] = new Land("Asien", "China", "Pferd", 6);
		laender[35] = new Land("Asien", "Mittlerer Osten", "Kanone", 5);
		laender[36] = new Land("Asien", "Indien", "Soldat", 4);
		laender[37] = new Land("Asien", "Siam", "Kanone", 3);
		laender[38] = new Land("Australien", "Indonesien", "Pferd", 3);
		laender[39] = new Land("Australien", "Neuguinea", "Pferd", 3);
		laender[40] = new Land("Australien", "West-Australien", "Kanone", 3);
		laender[41] = new Land("Australien", "Ost-Australien", "Soldat", 2);
		laender[42] = new Land("Joker", "Joker", "Joker", 0);
		laender[43] = new Land("Joker", "Joker", "Joker", 0);


		// 14x Soldat, 14x Pferd, 14x Kanone  + 2 "Joker" (Pferd, Soldat oder Kanone)
	}

	public void nachbarnVerteilen(){
		laender[0].nachbarLand[0] = laender[1];
		laender[0].nachbarLand[1] = laender[3];
		laender[0].nachbarLand[2] = laender[29];

		laender[1].nachbarLand[0] = laender[2];
		laender[1].nachbarLand[1] = laender[3];
		laender[1].nachbarLand[2] = laender[4];
		laender[1].nachbarLand[3] = laender[0];

		laender[2].nachbarLand[0] = laender[1];
		laender[2].nachbarLand[1] = laender[4];
		laender[2].nachbarLand[2] = laender[5];
		laender[2].nachbarLand[3] = laender[13];

		laender[3].nachbarLand[0] = laender[0];
		laender[3].nachbarLand[1] = laender[1];
		laender[3].nachbarLand[2] = laender[4];
		laender[3].nachbarLand[3] = laender[6];

		laender[4].nachbarLand[0] = laender[1];
		laender[4].nachbarLand[1] = laender[2];
		laender[4].nachbarLand[2] = laender[3];
		laender[4].nachbarLand[3] = laender[5];
		laender[4].nachbarLand[4] = laender[6];
		laender[4].nachbarLand[5] = laender[7];

		laender[5].nachbarLand[0] = laender[7];
		laender[5].nachbarLand[1] = laender[4];
		laender[5].nachbarLand[2] = laender[2];

		laender[6].nachbarLand[0] = laender[3];
		laender[6].nachbarLand[1] = laender[4];
		laender[6].nachbarLand[2] = laender[7];
		laender[6].nachbarLand[3] = laender[8];

		laender[7].nachbarLand[0] = laender[6];
		laender[7].nachbarLand[1] = laender[5];
		laender[7].nachbarLand[2] = laender[4];
		laender[7].nachbarLand[3] = laender[8];

		laender[8].nachbarLand[0] = laender[7];
		laender[8].nachbarLand[1] = laender[6];
		laender[8].nachbarLand[2] = laender[9];

		laender[9].nachbarLand[0] = laender[8];
		laender[9].nachbarLand[1] = laender[10];
		laender[9].nachbarLand[2] = laender[11];

		laender[10].nachbarLand[0] = laender[9];
		laender[10].nachbarLand[1] = laender[11];
		laender[10].nachbarLand[2] = laender[12];

		laender[11].nachbarLand[0] = laender[9];
		laender[11].nachbarLand[1] = laender[10];
		laender[11].nachbarLand[2] = laender[12];
		laender[11].nachbarLand[3] = laender[20];

		laender[12].nachbarLand[0] = laender[11];
		laender[12].nachbarLand[1] = laender[10];

		laender[13].nachbarLand[0] = laender[2];
		laender[13].nachbarLand[1] = laender[15];
		laender[13].nachbarLand[2] = laender[14];

		laender[14].nachbarLand[0] = laender[13];
		laender[14].nachbarLand[1] = laender[15];
		laender[14].nachbarLand[2] = laender[16];
		laender[14].nachbarLand[3] = laender[17];

		laender[15].nachbarLand[0] = laender[13];
		laender[15].nachbarLand[1] = laender[14];
		laender[15].nachbarLand[2] = laender[16];
		laender[15].nachbarLand[3] = laender[18];

		laender[16].nachbarLand[0] = laender[15];
		laender[16].nachbarLand[1] = laender[14];
		laender[16].nachbarLand[2] = laender[18];
		laender[16].nachbarLand[3] = laender[19];
		laender[16].nachbarLand[4] = laender[17];

		laender[17].nachbarLand[0] = laender[14];
		laender[17].nachbarLand[1] = laender[16];
		laender[17].nachbarLand[2] = laender[19];
		laender[17].nachbarLand[3] = laender[35];
		laender[17].nachbarLand[4] = laender[33];
		laender[17].nachbarLand[5] = laender[26];

		laender[18].nachbarLand[0] = laender[15];
		laender[18].nachbarLand[1] = laender[16];
		laender[18].nachbarLand[2] = laender[19];
		laender[18].nachbarLand[3] = laender[20];

		laender[19].nachbarLand[0] = laender[16];
		laender[19].nachbarLand[1] = laender[17];
		laender[19].nachbarLand[2] = laender[18];
		laender[19].nachbarLand[3] = laender[20];
		laender[19].nachbarLand[4] = laender[21];
		laender[19].nachbarLand[5] = laender[35];

		laender[20].nachbarLand[0] = laender[11];
		laender[20].nachbarLand[1] = laender[21];
		laender[20].nachbarLand[2] = laender[22];
		laender[20].nachbarLand[3] = laender[18];
		laender[20].nachbarLand[4] = laender[19];
		laender[20].nachbarLand[5] = laender[23];

		laender[21].nachbarLand[0] = laender[19];
		laender[21].nachbarLand[1] = laender[20];
		laender[21].nachbarLand[2] = laender[23];
		laender[21].nachbarLand[3] = laender[35];

		laender[22].nachbarLand[0] = laender[20];
		laender[22].nachbarLand[1] = laender[23];
		laender[22].nachbarLand[2] = laender[24];
		
		laender[23].nachbarLand[0] = laender[20];
		laender[23].nachbarLand[1] = laender[21];
		laender[23].nachbarLand[2] = laender[22];
		laender[23].nachbarLand[3] = laender[24];
		laender[23].nachbarLand[4] = laender[25];

		laender[24].nachbarLand[0] = laender[22];
		laender[24].nachbarLand[1] = laender[23];
		laender[24].nachbarLand[2] = laender[25];

		laender[25].nachbarLand[0] = laender[23];
		laender[25].nachbarLand[1] = laender[24];

		laender[26].nachbarLand[0] = laender[17];
		laender[26].nachbarLand[1] = laender[33];
		laender[26].nachbarLand[2] = laender[34];
		laender[26].nachbarLand[3] = laender[27];

		laender[27].nachbarLand[0] = laender[26];
		laender[27].nachbarLand[1] = laender[28];
		laender[27].nachbarLand[2] = laender[30];
		laender[27].nachbarLand[3] = laender[34];
		laender[27].nachbarLand[4] = laender[31];

		laender[28].nachbarLand[0] = laender[27];
		laender[28].nachbarLand[1] = laender[29];
		laender[28].nachbarLand[2] = laender[30];

		laender[29].nachbarLand[0] = laender[28];
		laender[29].nachbarLand[1] = laender[30];
		laender[29].nachbarLand[2] = laender[31];
		laender[29].nachbarLand[3] = laender[32];
		laender[29].nachbarLand[4] = laender[0];

		laender[30].nachbarLand[0] = laender[27];
		laender[30].nachbarLand[1] = laender[28];
		laender[30].nachbarLand[2] = laender[29];
		laender[30].nachbarLand[3] = laender[31];

		laender[31].nachbarLand[0] = laender[27];
		laender[31].nachbarLand[1] = laender[29];
		laender[31].nachbarLand[2] = laender[30];
		laender[31].nachbarLand[3] = laender[32];
		laender[31].nachbarLand[4] = laender[34];

		laender[32].nachbarLand[0] = laender[29];
		laender[32].nachbarLand[1] = laender[31];

		laender[33].nachbarLand[0] = laender[17];
		laender[33].nachbarLand[1] = laender[26];
		laender[33].nachbarLand[2] = laender[34];
		laender[33].nachbarLand[3] = laender[35];
		laender[33].nachbarLand[4] = laender[36];

		laender[34].nachbarLand[0] = laender[26];
		laender[34].nachbarLand[1] = laender[27];
		laender[34].nachbarLand[2] = laender[31];
		laender[34].nachbarLand[3] = laender[33];
		laender[34].nachbarLand[4] = laender[36];
		laender[34].nachbarLand[5] = laender[37];

		laender[35].nachbarLand[0] = laender[17];
		laender[35].nachbarLand[1] = laender[19];
		laender[35].nachbarLand[2] = laender[21];
		laender[35].nachbarLand[3] = laender[33];
		laender[35].nachbarLand[4] = laender[36];

		laender[36].nachbarLand[0] = laender[33];
		laender[36].nachbarLand[1] = laender[34];
		laender[36].nachbarLand[2] = laender[35];
		laender[36].nachbarLand[3] = laender[37];

		laender[37].nachbarLand[0] = laender[34];
		laender[37].nachbarLand[1] = laender[36];
		laender[37].nachbarLand[2] = laender[38];
		
		laender[38].nachbarLand[0] = laender[37];
		laender[38].nachbarLand[1] = laender[39];
		laender[38].nachbarLand[2] = laender[40];

		laender[39].nachbarLand[0] = laender[38];
		laender[39].nachbarLand[1] = laender[40];
		laender[39].nachbarLand[2] = laender[41];

		laender[40].nachbarLand[0] = laender[38];
		laender[40].nachbarLand[1] = laender[39];
		laender[40].nachbarLand[2] = laender[41];

		laender[41].nachbarLand[0] = laender[39];
		laender[41].nachbarLand[1] = laender[40];
	}

	public boolean spielerErstellen(String name){
		if(this.spieler.size() < this.maxSpieler){
			this.spieler.add(new Spieler(name));
			this.spielerZahl ++;
			return true;
		}
		return false;
	}

	public void startLaenderVerteilen(){
		int aufteilen = 42/this.spielerZahl;
		int rest = 42/this.spielerZahl;
		for(int i = 0; i < this.spieler.size() ; i++){
			for(int j = 0; j < aufteilen ; j++){
				getStartKarte((Spieler) this.spieler.elementAt(i));		
			}
		}
		if(rest > 0){
			int zahl = this.spielerZahl - 1;
			int a;
			for(int k = 0; k < rest ; k++){
				a = (int) (Math.random()*zahl+1);
				getStartKarte((Spieler) this.spieler.elementAt(k));
			}
		}
		this.ausgeteilteKarten.clear();
		
	}
	
	public void getStartKarte(Spieler s) {
		int i;
		do{
			i = (int) (Math.random()*42);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(laender[i]);
		this.laender[i].setBesitzer(s);
		this.laender[i].setTruppenstaerke(1);
	}
	
	public Land getKarte(Spieler s) {
		int i;
		do{
			i = (int) (Math.random()*44);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(laender[i]);
		s.handKarten.add(laender[i]);
		return laender[i];
	}

	public void karteZurueck(Land l, Spieler s) {
		s.handKarten.remove(l);
		this.ausgeteilteKarten.remove(l);
	}


	public boolean istNachbarn(int n, int k){
		if(this.laender[n].istNachbar(this.laender[k])){
			return true;
		}
		return false;
	}
	public boolean einheitenZiehen(Spieler s,int menge, int start, int ziel){
		if(istNachbarn(start, ziel) && start != ziel && s.meinLand(this.laender[start]) && s.meinLand(this.laender[ziel]) && (laender[start].getTruppenstaerke() > menge)){
			laender[start].setTruppenstaerke(-1*menge);
			laender[ziel].setTruppenstaerke(menge);
			return true;
		}
		return false;
	}


	public static void main(String[] args) { 
		Spielfeld feld1 = new Spielfeld();
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
	}
}

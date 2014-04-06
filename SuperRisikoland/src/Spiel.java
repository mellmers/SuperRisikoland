public class Spiel {

	public Spiel() {
		
	}
	
	public void befreien(Spieler angreifer, Spieler verteidiger, Land ang, Land ver, int angTruppen, int verTruppen) {
		//if (ang.getTruppenstaerke() > 1 && ang.istNachbar(ver)) {
			int[] angWuerfel = new int[3];
			int[] verWuerfel = new int[3];
			if (angTruppen == 1) {
				angWuerfel[0] = wuerfeln();
				verWuerfel = verteidigerWuerfeln(verTruppen);
			}
			else if (angTruppen == 2) {
				angWuerfel[0] = wuerfeln();
				angWuerfel[1] = wuerfeln();
				verWuerfel = verteidigerWuerfeln(verTruppen);
			}
			else if (angTruppen == 3) {
				angWuerfel[0] = wuerfeln();
				angWuerfel[1] = wuerfeln();
				angWuerfel[2] = wuerfeln();
				verWuerfel = verteidigerWuerfeln(verTruppen);
			}
			// Auswertung
			if(angTruppen<2 || verTruppen<2){
				befreienAuswertung(angWuerfel, verWuerfel, 1);
			}
			else {
				befreienAuswertung(angWuerfel, verWuerfel, 1);
				befreienAuswertung(angWuerfel, verWuerfel, 2);
			}
		/*}
		else {
			System.out.println("Angriff fehlgeschlagen!");
		}*/
	}
	
	public void befreienAuswertung(int[] angWuerfel, int[] verWuerfel, int anzRunden) {
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
			}
			else {
				System.out.println("Runde "+ anzRunden +": Verteidiger gewinnt.");
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
	
	public int wuerfeln() {
		return (int) (Math.random()*6+1);
	}
}

package cui;


import gui.SuperRisikolandGui;
import inf.KontinentInterface;
import inf.LandInterface;
import inf.MissionInterface;
import inf.ServerInterface;
import inf.SpielerInterface;
import inf.SpielfeldInterface;
import inf.SuperRisikoLandGuiInterface;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Spielfeld implements SpielfeldInterface, Serializable
{
	private int spielvariante;
	
	private KontinentInterface[] kontinente = new KontinentInterface[7];
	private LandInterface[] laender = new LandInterface[44];
	private Vector<LandInterface> ausgeteilteKarten = new Vector<LandInterface>();
	private int anzahlSpieler;
	private Vector<SpielerInterface> spieler = new Vector<SpielerInterface>();
	private int maxSpieler = 7;
	private Vector<MissionInterface> missionen;
	private Vector<LandInterface> eroberteLaender = new Vector<LandInterface>();
	private int eingetauschteSerien = 0;
	private int zusatzEinheitenSerie = 4;
	
	private int zuVerteilendeEinheitenGui = 0;
	private int verteilteEinheitenGui = 0;
	
	private ServerInterface server;
	
	public Spielfeld(ServerInterface server, Vector<SpielerInterface> alleSpieler, int spielVariante) throws RemoteException
	{
		this.server = server;
		this.spieler = alleSpieler;
		this.anzahlSpieler = this.spieler.size();
		this.setSpielvariante(spielVariante);
		
		//server.setLogText("Spielfeld mit " + this.anzahlSpieler + " Spielern und Spielvariante " + spielVariante + " erstellt.");
		IO.println("Spielfeld mit " + anzahlSpieler + " Spielern und Spielvariante " + spielVariante + " erstellt.");
		
		//server.setLogText(this.aktuellerSpieler.getName() + " ist nun an der Reihe.");

		this.kontinenteEinlesen();

		this.laenderEinlesen();

		this.nachbarnVerteilen();

		// Startlaender werden verteilt
		this.startLaenderVerteilen();

		// Missionen werden erstellt
		if(spielVariante == 1)
		{
			missionen = new Mission().missionenErstellen(this);
			for(int i = 0 ; i < this.getAnzahlSpieler() ; i++)
	  		{
	  			int zufallsMission = (int) (Math.random()*missionen.size());
	  			((Spieler) this.getSpieler(i)).setMission(missionen.elementAt(zufallsMission));
	  			this.getSpieler(i).getMission().setBesitzer(this.getSpieler(i));
	  			this.getSpieler(i).getMission().setAufgabenText();
	  			this.getSpieler(i).getMission().setMissionErfuelltText();
	  			missionen.remove(zufallsMission);
	  		}// Missionen werden erstellt und im Spielerobjekt abgespeichert
		}
		// 14x Soldat, 14x Pferd, 14x Kanone  + 2 "Joker" (Pferd, Soldat oder Kanone)
	}
	
	// Getter
	public KontinentInterface getKontinent(int kontinentId) throws RemoteException
	{
		return this.kontinente[kontinentId];
	}
	
	public int getAnzahlEroberterLaender()
	{
		return eroberteLaender.size();
	}
	
	public LandInterface getLand(int laenderId) throws RemoteException
	{
		return this.laender[laenderId];
	}
	
	public SpielerInterface getSpieler(int spielerId) throws RemoteException
	{
		return this.spieler.elementAt(spielerId);
	}
	
	public int getZuVerteilendeEinheitenGui(SpielerInterface aktuellerSpieler) throws RemoteException
	{
		zuVerteilendeEinheitenGui = this.laenderZaehlen(  aktuellerSpieler) + this.zusatzEinheitenKontinente(   aktuellerSpieler) + this.checkSerie(   aktuellerSpieler) - this.verteilteEinheitenGui;
		return zuVerteilendeEinheitenGui;
	}
	
	// Setter
	
	public int getSpielvariante()
	{
		return spielvariante;
	}

	public void setSpielvariante(int spielvariante)
	{
		this.spielvariante = spielvariante;
	}

	public int getAnzahlSpieler() throws RemoteException
	{
		return this.anzahlSpieler;
	}

	public ServerInterface getServer() throws RemoteException
	{
		return this.server;
	}

	public void setEroberteLaenderNull() throws RemoteException
	{
		this.eroberteLaender.clear();
	}
	
	public void kontinenteEinlesen() throws RemoteException
	{
		kontinente[0] = new Kontinent ("Nord-Amerika", 9, 5);
		kontinente[1] = new Kontinent ("Sued-Amerika", 4, 2);
		kontinente[2] = new Kontinent ("Europa", 7, 5);
		kontinente[3] = new Kontinent ("Afrika", 6, 3);
		kontinente[4] = new Kontinent ("Asien", 12, 7);
		kontinente[5] = new Kontinent ("Australien", 4, 2);
		kontinente[6] = new Kontinent ("Joker", 2, 0);
	}
	
	public void laenderEinlesen() throws RemoteException
	{
		laender[0] = new Land(kontinente[0], "Alaska", "Soldat", 3, new Color(10,0,0));
		laender[1] = new Land(kontinente[0], "Nordwest-Territorium", "Kanone", 4, new Color(20,0,0));
		laender[2] = new Land(kontinente[0], "Groenland", "Pferd", 4, new Color(30,0,0));
		laender[3] = new Land(kontinente[0], "Alberta", "Soldat", 4, new Color(40,0,0));
		laender[4] = new Land(kontinente[0], "Ontario", "Pferd", 6, new Color(50,0,0));
		laender[5] = new Land(kontinente[0], "Quebec", "Kanone", 3, new Color(60,0,0));
		laender[6] = new Land(kontinente[0], "Weststaaten", "Soldat", 4, new Color(70,0,0));
		laender[7] = new Land(kontinente[0], "Oststaaten", "Kanone", 4, new Color(80,0,0));
		laender[8] = new Land(kontinente[0], "Mittelamerika", "Pferd", 3, new Color(90,0,0));
		laender[9] = new Land(kontinente[1], "Venezuela", "Kanone", 3, new Color(220,0,0));
		laender[10] = new Land(kontinente[1], "Peru", "Pferd", 3, new Color(240,0,0));
		laender[11] = new Land(kontinente[1], "Brasilien", "Kanone", 4, new Color(230,0,0));
		laender[12] = new Land(kontinente[1], "Argentinien", "Soldat", 2, new Color(250,0,0));
		laender[13] = new Land(kontinente[2], "Island", "Soldat", 3, new Color(0,10,0));
		laender[14] = new Land(kontinente[2], "Skandinavien", "Kanone", 4, new Color(0,70,0));
		laender[15] = new Land(kontinente[2], "Grossbritannien", "Kanone", 4, new Color(0,20,0));
		laender[16] = new Land(kontinente[2], "Mitteleuropa", "Pferd", 5, new Color(0,40,0));
		laender[17] = new Land(kontinente[2], "Ukraine", "Kanone", 6, new Color(0,60,0));
		laender[18] = new Land(kontinente[2], "Westeuropa", "Soldat", 4, new Color(0,30,0));
		laender[19] = new Land(kontinente[2], "Suedeuropa", "Pferd", 6, new Color(0,50,0));
		laender[20] = new Land(kontinente[3], "Nordwest-Afrika", "Soldat", 6, new Color(0,250,0));
		laender[21] = new Land(kontinente[3], "Aegypten", "Soldat", 4, new Color(0,240,0));
		laender[22] = new Land(kontinente[3], "Kongo", "Pferd", 3, new Color(0,220,0));
		laender[23] = new Land(kontinente[3], "Ost-Afrika", "Kanone", 5, new Color(0,230,0));
		laender[24] = new Land(kontinente[3], "Sued-Afrika", "Kanone", 3, new Color(0,210,0));
		laender[25] = new Land(kontinente[3], "Madagaskar", "Soldat", 2, new Color(0,200,0));
		laender[26] = new Land(kontinente[4], "Ural", "Pferd", 4, new Color(0,0,50));
		laender[27] = new Land(kontinente[4], "Sibirien", "Pferd", 5, new Color(0,0,60));
		laender[28] = new Land(kontinente[4], "Jakutien", "Pferd", 3, new Color(0,0,90));
		laender[29] = new Land(kontinente[4], "Kamtschatka", "Pferd", 5, new Color(0,0,100));
		laender[30] = new Land(kontinente[4], "Irkutsk", "Soldat", 4, new Color(0,0,80));
		laender[31] = new Land(kontinente[4], "Mongolei", "Kanone", 5, new Color(0,0,70));
		laender[32] = new Land(kontinente[4], "Japan", "Soldat", 2, new Color(0,0,110));
		laender[33] = new Land(kontinente[4], "Afghanistan", "Soldat", 5, new Color(0,0,20));
		laender[34] = new Land(kontinente[4], "China", "Pferd", 6, new Color(0,0,40));
		laender[35] = new Land(kontinente[4], "Mittlerer Osten", "Kanone", 5, new Color(0,0,10));
		laender[36] = new Land(kontinente[4], "Indien", "Soldat", 4, new Color(0,0,30));
		laender[37] = new Land(kontinente[4], "Siam", "Kanone", 3, new Color(0,0,120));
		laender[38] = new Land(kontinente[5], "Indonesien", "Pferd", 3, new Color(0,0,250));
		laender[39] = new Land(kontinente[5], "Neuguinea", "Pferd", 3, new Color(0,0,240));
		laender[40] = new Land(kontinente[5], "West-Australien", "Kanone", 3, new Color(0,0,230));
		laender[41] = new Land(kontinente[5], "Ost-Australien", "Soldat", 2, new Color(0,0,220));
		laender[42] = new Land(kontinente[6], "Joker", "Joker", 0, null);
		laender[43] = new Land(kontinente[6], "Joker", "Joker", 0, null);
	}
		
	public void nachbarnVerteilen() throws RemoteException
	{
		laender[0].setNachbarLand(0, laender[1]);
		laender[0].setNachbarLand(1, laender[3]);
		laender[0].setNachbarLand(2, laender[29]);

		laender[1].setNachbarLand(0, laender[2]);
		laender[1].setNachbarLand(1, laender[3]);
		laender[1].setNachbarLand(2, laender[4]);
		laender[1].setNachbarLand(3, laender[0]);

		laender[2].setNachbarLand(0, laender[1]);
		laender[2].setNachbarLand(1, laender[4]);
		laender[2].setNachbarLand(2, laender[5]);
		laender[2].setNachbarLand(3, laender[13]);

		laender[3].setNachbarLand(0, laender[0]);
		laender[3].setNachbarLand(1, laender[1]);
		laender[3].setNachbarLand(2, laender[4]);
		laender[3].setNachbarLand(3, laender[6]);

		laender[4].setNachbarLand(0, laender[1]);
		laender[4].setNachbarLand(1, laender[2]);
		laender[4].setNachbarLand(2, laender[3]);
		laender[4].setNachbarLand(3, laender[5]);
		laender[4].setNachbarLand(4, laender[6]);
		laender[4].setNachbarLand(5, laender[7]);

		laender[5].setNachbarLand(0, laender[7]);
		laender[5].setNachbarLand(1, laender[4]);
		laender[5].setNachbarLand(2, laender[2]);

		laender[6].setNachbarLand(0, laender[3]);
		laender[6].setNachbarLand(1, laender[4]);
		laender[6].setNachbarLand(2, laender[7]);
		laender[6].setNachbarLand(3, laender[8]);

		laender[7].setNachbarLand(0, laender[6]);
		laender[7].setNachbarLand(1, laender[5]);
		laender[7].setNachbarLand(2, laender[4]);
		laender[7].setNachbarLand(3, laender[8]);

		laender[8].setNachbarLand(0, laender[7]);
		laender[8].setNachbarLand(1, laender[6]);
		laender[8].setNachbarLand(2, laender[9]);

		laender[9].setNachbarLand(0, laender[12]);
		laender[9].setNachbarLand(1, laender[10]);
		laender[9].setNachbarLand(2, laender[11]);

		laender[10].setNachbarLand(0, laender[9]);
		laender[10].setNachbarLand(1, laender[11]);
		laender[10].setNachbarLand(2, laender[8]);

		laender[11].setNachbarLand(0, laender[9]);
		laender[11].setNachbarLand(1, laender[10]);
		laender[11].setNachbarLand(2, laender[12]);
		laender[11].setNachbarLand(3, laender[20]);

		laender[12].setNachbarLand(0, laender[11]);
		laender[12].setNachbarLand(1, laender[10]);

		laender[13].setNachbarLand(0, laender[2]);
		laender[13].setNachbarLand(1, laender[15]);
		laender[13].setNachbarLand(2, laender[14]);

		laender[14].setNachbarLand(0, laender[13]);
		laender[14].setNachbarLand(1, laender[15]);
		laender[14].setNachbarLand(2, laender[16]);
		laender[14].setNachbarLand(3, laender[17]);

		laender[15].setNachbarLand(0, laender[13]);
		laender[15].setNachbarLand(1, laender[14]);
		laender[15].setNachbarLand(2, laender[16]);
		laender[15].setNachbarLand(3, laender[18]);

		laender[16].setNachbarLand(0, laender[15]);
		laender[16].setNachbarLand(1, laender[14]);
		laender[16].setNachbarLand(2, laender[18]);
		laender[16].setNachbarLand(3, laender[19]);
		laender[16].setNachbarLand(4, laender[17]);

		laender[17].setNachbarLand(0, laender[14]);
		laender[17].setNachbarLand(1, laender[16]);
		laender[17].setNachbarLand(2, laender[19]);
		laender[17].setNachbarLand(3, laender[35]);
		laender[17].setNachbarLand(4, laender[33]);
		laender[17].setNachbarLand(5, laender[26]);

		laender[18].setNachbarLand(0, laender[15]);
		laender[18].setNachbarLand(1, laender[16]);
		laender[18].setNachbarLand(2, laender[19]);
		laender[18].setNachbarLand(3, laender[20]);

		laender[19].setNachbarLand(0, laender[16]);
		laender[19].setNachbarLand(1, laender[17]);
		laender[19].setNachbarLand(2, laender[18]);
		laender[19].setNachbarLand(3, laender[20]);
		laender[19].setNachbarLand(4, laender[21]);
		laender[19].setNachbarLand(5, laender[35]);

		laender[20].setNachbarLand(0, laender[11]);
		laender[20].setNachbarLand(1, laender[21]);
		laender[20].setNachbarLand(2, laender[22]);
		laender[20].setNachbarLand(3, laender[18]);
		laender[20].setNachbarLand(4, laender[19]);
		laender[20].setNachbarLand(5, laender[23]);

		laender[21].setNachbarLand(0, laender[19]);
		laender[21].setNachbarLand(1, laender[20]);
		laender[21].setNachbarLand(2, laender[23]);
		laender[21].setNachbarLand(3, laender[35]);

		laender[22].setNachbarLand(0, laender[20]);
		laender[22].setNachbarLand(1, laender[23]);
		laender[22].setNachbarLand(2, laender[24]);
		
		laender[23].setNachbarLand(0, laender[20]);
		laender[23].setNachbarLand(1, laender[21]);
		laender[23].setNachbarLand(2, laender[22]);
		laender[23].setNachbarLand(3, laender[24]);
		laender[23].setNachbarLand(4, laender[25]);

		laender[24].setNachbarLand(0, laender[22]);
		laender[24].setNachbarLand(1, laender[23]);
		laender[24].setNachbarLand(2, laender[25]);

		laender[25].setNachbarLand(0, laender[23]);
		laender[25].setNachbarLand(1, laender[24]);

		laender[26].setNachbarLand(0, laender[17]);
		laender[26].setNachbarLand(1, laender[33]);
		laender[26].setNachbarLand(2, laender[34]);
		laender[26].setNachbarLand(3, laender[27]);

		laender[27].setNachbarLand(0, laender[26]);
		laender[27].setNachbarLand(1, laender[28]);
		laender[27].setNachbarLand(2, laender[30]);
		laender[27].setNachbarLand(3, laender[34]);
		laender[27].setNachbarLand(4, laender[31]);

		laender[28].setNachbarLand(0, laender[27]);
		laender[28].setNachbarLand(1, laender[29]);
		laender[28].setNachbarLand(2, laender[30]);

		laender[29].setNachbarLand(0, laender[28]);
		laender[29].setNachbarLand(1, laender[30]);
		laender[29].setNachbarLand(2, laender[31]);
		laender[29].setNachbarLand(3, laender[32]);
		laender[29].setNachbarLand(4, laender[0]);

		laender[30].setNachbarLand(0, laender[27]);
		laender[30].setNachbarLand(1, laender[28]);
		laender[30].setNachbarLand(2, laender[29]);
		laender[30].setNachbarLand(3, laender[31]);

		laender[31].setNachbarLand(0, laender[27]);
		laender[31].setNachbarLand(1, laender[29]);
		laender[31].setNachbarLand(2, laender[30]);
		laender[31].setNachbarLand(3, laender[32]);
		laender[31].setNachbarLand(4, laender[34]);

		laender[32].setNachbarLand(0, laender[29]);
		laender[32].setNachbarLand(1, laender[31]);

		laender[33].setNachbarLand(0, laender[17]);
		laender[33].setNachbarLand(1, laender[26]);
		laender[33].setNachbarLand(2, laender[34]);
		laender[33].setNachbarLand(3, laender[35]);
		laender[33].setNachbarLand(4, laender[36]);

		laender[34].setNachbarLand(0, laender[26]);
		laender[34].setNachbarLand(1, laender[27]);
		laender[34].setNachbarLand(2, laender[31]);
		laender[34].setNachbarLand(3, laender[33]);
		laender[34].setNachbarLand(4, laender[36]);
		laender[34].setNachbarLand(5, laender[37]);

		laender[35].setNachbarLand(0, laender[17]);
		laender[35].setNachbarLand(1, laender[19]);
		laender[35].setNachbarLand(2, laender[21]);
		laender[35].setNachbarLand(3, laender[33]);
		laender[35].setNachbarLand(4, laender[36]);

		laender[36].setNachbarLand(0, laender[33]);
		laender[36].setNachbarLand(1, laender[34]);
		laender[36].setNachbarLand(2, laender[35]);
		laender[36].setNachbarLand(3, laender[37]);

		laender[37].setNachbarLand(0, laender[34]);
		laender[37].setNachbarLand(1, laender[36]);
		laender[37].setNachbarLand(2, laender[38]);
		
		laender[38].setNachbarLand(0, laender[37]);
		laender[38].setNachbarLand(1, laender[39]);
		laender[38].setNachbarLand(2, laender[40]);

		laender[39].setNachbarLand(0, laender[38]);
		laender[39].setNachbarLand(1, laender[40]);
		laender[39].setNachbarLand(2, laender[41]);

		laender[40].setNachbarLand(0, laender[38]);
		laender[40].setNachbarLand(1, laender[39]);
		laender[40].setNachbarLand(2, laender[41]);

		laender[41].setNachbarLand(0, laender[39]);
		laender[41].setNachbarLand(1, laender[40]);
	}
	
	public void gesamtLaenderListeAusgeben() throws RemoteException
	{
		for (int i = 0; i < 42; i++)
		{
			IO.println("ID: " + i  + " " + this.laender[i].getKontinent().getName() + " Land: '" + this.laender[i].getName() + "' Besitzer: '" + this.laender[i].getBesitzer().getName() + "' Truppenstaerke: " + this.laender[i].getTruppenstaerke() + "   Eingesetzte Einheiten: " + this.laender[i].getBenutzteEinheiten());
		}
	}
	
	public void abfrageSollGesamtLaenderListeAusgegebenWerden() throws RemoteException
	{
		IO.println("Laenderliste ausgeben? (wenn gewuenscht, dann 'l' eingeben)");
		char c = IO.readChar();
		if (c == 'l')
		{
			this.gesamtLaenderListeAusgeben();
		}
	}

	public boolean spielerErstellen(int spielerID, String spielername, String spielerfarbe, Color c) throws RemoteException
	{
		// TODO If-Clause weg, weil man durch Gui nicht mehr als 6 Spieler auswaehlen kann
		if(this.spieler.size() < this.maxSpieler)
		{
			this.spieler.add(new Spieler(spielerID, spielername, spielerfarbe, c));
			this.anzahlSpieler++;
			return true;
		}
		return false;
	}

	public void startLaenderVerteilen() throws RemoteException
	{
		int aufteilen = 42/this.anzahlSpieler;
		int rest = 42%this.anzahlSpieler;
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
	
	public void getStartKarte(SpielerInterface spielerInterface) throws RemoteException 
	{
		int i;
		do
		{
			i = (int) (Math.random()*42);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(laender[i]);
		this.laender[i].setBesitzer(spielerInterface);
		spielerInterface.landHinzufuegen(this.laender[i]);
		this.laender[i].setTruppenstaerke(1);
	}
	
	public void getKarte(SpielerInterface aktuellerSpieler) throws RemoteException 
	{
		int i;
		do
		{
			i = (int) (Math.random()*44);
		}while(this.ausgeteilteKarten.contains(laender[i]));
		this.ausgeteilteKarten.add(  laender[i]);
		aktuellerSpieler.handkartenHinzufuegen(laender[i]);
	}

	public boolean sindNachbarn(int landIndex1, int landIndex2) throws RemoteException
	{
		if(this.laender[landIndex1].istNachbar(this.laender[landIndex2]))
		{
			return true;
		}
		IO.println(this.laender[landIndex1].getName() + " und " + this.laender[landIndex2].getName() + " sind keine Nachbarlaender.");
		return false;
	}
	
	// Spiel-Funktionen
	public boolean einheitenNachKampfNachziehen(int start, int ziel, int anzahl, boolean gui) throws RemoteException
	{
		int menge = anzahl;
		if(this.laender[start].getBesitzer().equals(this.laender[ziel].getBesitzer()))
		{
			if(!gui)
			{
				do
				{
					IO.println("Wieviele Einheiten moechtest du nachziehen?");
					menge = IO.readInt();
					if(laender[start].getTruppenstaerke() <= menge)
					{
						IO.println((laender[start].getTruppenstaerke() <= menge || menge > (laender[start].getTruppenstaerke() - laender[start].getBenutzteEinheiten())) ? "So viele Einheiten koennen nicht nachgezogen werden!" : menge + " Einheiten sind von " + this.laender[start].getName() + " nach " + this.laender[ziel].getName() + " gezogen.");
					}
				}
				while(laender[start].getTruppenstaerke() <= menge);
			}
			this.laender[start].setTruppenstaerke(-1*menge);
			this.laender[ziel].setTruppenstaerke(menge);
			this.laender[ziel].erhoeheBenutzteEinheiten(menge);
			// Wenn ein Land eroberte wurde und nachgezogen wird muessen die benutzten Einheiten verschoben werden
    		if(this.laender[start].getBenutzteEinheiten() > 0)
    		{
    			if(this.laender[start].getBenutzteEinheiten() > menge)
    			{
    				this.laender[start].erhoeheBenutzteEinheiten(-menge);	
    			}
    			else
    			{
    				this.laender[start].setBenutzteEinheiten(0);
    			}
    		}
    		return true;
		}
		return false;
	}
	
	public boolean einheitenNachziehen(int start, int ziel, int anzahl, boolean gui) throws RemoteException
	{
		int menge = anzahl;
		
		if(this.laender[start].getBesitzer().equals(this.laender[ziel].getBesitzer()))
		{
			if(!gui)
			{
				do
				{
					IO.println("Wieviele Einheiten moechtest du nachziehen?");
					menge = IO.readInt();
					if(laender[start].getTruppenstaerke() <= menge)
					{
						IO.println((laender[start].getTruppenstaerke() <= menge || menge > (laender[start].getTruppenstaerke() - laender[start].getBenutzteEinheiten())) ? "So viele Einheiten koennen nicht nachgezogen werden!" : menge + " Einheiten sind von " + this.laender[start].getName() + " nach " + this.laender[ziel].getName() + " gezogen.");
					}
				}
				while(laender[start].getTruppenstaerke() <= menge || menge > (laender[start].getTruppenstaerke() - laender[start].getBenutzteEinheiten()));
			}
			this.laender[start].setTruppenstaerke(-1*menge);
			this.laender[ziel].setTruppenstaerke(menge);
			this.laender[ziel].erhoeheBenutzteEinheiten(menge);
			return true;
		}
		return false;
		
	}
	
	public void setBenutzteEinheitenNull() throws RemoteException
	{
		for(int i = 0 ; i < this.laender.length ; i++)
		{
			this.laender[i].setBenutzteEinheiten(0);
		}
	}
	public int laenderZaehlen(SpielerInterface aktuellerSpieler) throws RemoteException
	{
		int anzLaender = 0;
		for(int i = 0; i < 43; i++)
		{
			if(this.laender[i].getBesitzer() == aktuellerSpieler)
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

	public void befreien(SpielerInterface aktuellerSpieler, int angTruppen, int verTruppen, int angId, int verId, boolean gui) throws RemoteException 
	{
			if (this.laender[angId].getTruppenstaerke() > 1 && this.laender[angId].istNachbar(this.laender[verId]) && angTruppen < this.laender[angId].getTruppenstaerke() && verTruppen <= this.laender[verId].getTruppenstaerke() && verTruppen > 0 && verTruppen < 3 && angTruppen > 0 && angTruppen < 4 && this.laender[verId].getBesitzer() != this.laender[angId].getBesitzer()) 
			{
				int[] angWuerfel = new int[3];
				int[] verWuerfel = new int[3];
				/*if(this.laender[angId].getBenutzteEinheiten() <= angTruppen)
				{
					this.laender[angId].erhoeheBenutzteEinheiten(angTruppen);
				}
				*/
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
				if(this.laender[angId].getBenutzteEinheiten() < angTruppen)
				{
					this.laender[angId].setBenutzteEinheiten(angTruppen);
				}
				if(angTruppen<2 || verTruppen<2)
				{
					befreienAuswertung(aktuellerSpieler, angTruppen, angWuerfel, verWuerfel, 1, angId, verId, gui);
				}
				else 
				{
					
					befreienAuswertung(aktuellerSpieler, angTruppen, angWuerfel, verWuerfel, 1, angId, verId, gui);
					befreienAuswertung(aktuellerSpieler, angTruppen, angWuerfel, verWuerfel, 2, angId, verId, gui);
				}
			}
			else
			{
				server.setLogText("Angriff von " + aktuellerSpieler.getName() + " ist fehlgeschlagen!");
				System.out.println("Angriff fehlgeschlagen!");
			}
		}
		
	public void befreienAuswertung(SpielerInterface aktuellerSpieler,int angTruppen, int[] angWuerfel, int[] verWuerfel, int anzRunden, int angId, int verId, boolean gui) throws RemoteException 
	{
				int hoechsteZahlAng = 0;
				int hoechsteZahlVer = 0;
				System.out.println(angWuerfel[0]);
				System.out.println(angWuerfel[1]);
				System.out.println(angWuerfel[2]);
				System.out.println(verWuerfel[0]);
				System.out.println(verWuerfel[1]);
				server.setLogText(aktuellerSpieler.getName() + "'s Wurf:\n" +angWuerfel[0] + "  " + angWuerfel[1] + "  " +angWuerfel[2]);
				server.setLogText(laender[verId].getBesitzer().getName() + "'s Wurf:\n" +verWuerfel[0] + "  " + verWuerfel[1]);
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
					System.out.println("Runde "+ anzRunden + ": Angreifer gewinnt.");
					server.setLogText("Runde " + anzRunden + ": " + aktuellerSpieler.getName() + " gewinnt.");
					this.laender[verId].setTruppenstaerke(-1);
					// Eroberung
					if (this.laender[verId].getTruppenstaerke() == 0) 
					{
						this.laender[verId].getBesitzer().landEntfernen(this.laender[verId]);
						this.laender[verId].setBesitzer(aktuellerSpieler);
						this.laender[verId].setTruppenstaerke(angTruppen);
						this.laender[angId].setTruppenstaerke(-1*angTruppen);
						aktuellerSpieler.landHinzufuegen(this.laender[verId]);
						if(gui)
						{
							for(int i = 0 ; i < server.getAlleClients() ; i++)
							{
								server.getClient(i).besitzerAktualisieren();
							}
						}
						
						// benutzte einheiten im angegriffenen Land erhoehen und im angreifenden senken
						this.laender[verId].setBenutzteEinheiten(angTruppen);
						if(this.laender[angId].getBenutzteEinheiten() <= angTruppen)
						{
							this.laender[angId].setBenutzteEinheiten(0);
						}
						else
						{
							this.laender[angId].erhoeheBenutzteEinheiten(-angTruppen);
						}
						
						
						// Gewinnabfrage
						
						// TODO mission erfuellt?
						
						//this.missionErfuelltUndGewonnen(aktuellerSpieler);
						
						// Welteroberung
						if(this.welteroberung(aktuellerSpieler))
						{
							// TODO Spielende, Siegfenster einblenden
						}
						//Ende Gewinnabfrage
						if(gui)
						{
							this.getKarte(aktuellerSpieler);
						}
						else
						{
							IO.println("Moechtest du Einheiten nachziehen? (j/n)");
					    	if (IO.readChar() == 'j')
					    	{
					    		this.einheitenNachKampfNachziehen(angId, verId, 0, false);
	
					    	}
						}
				    	
				    	this.eroberteLaender.add(this.laender[verId]);
					}
					// Ende Eroberung
				}
				else {
					System.out.println("Runde "+ anzRunden + ": Verteidiger gewinnt.");
					server.setLogText("Runde " + anzRunden + ": " + laender[verId].getBesitzer().getName() + " gewinnt.");
					this.laender[angId].setTruppenstaerke(-1);
					if(this.laender[angId].getBenutzteEinheiten() > 0)
					{
						this.laender[angId].erhoeheBenutzteEinheiten((- 1));
					}
					
					// TODO im angreifenden Land benutzte einheiten runterzaehlen
					
				}
		}
	
	public void missionErfuelltUndGewonnen(SpielerInterface aktuellerSpieler) throws RemoteException
	{
		// Hat angreifer seine Mission erfuellt?
		if(aktuellerSpieler.getMission().missionErfuellt())
		{
			IO.println(aktuellerSpieler.getMission().getMissionErfuelltText() + "");
			IO.println(aktuellerSpieler.getName() + " hat das Spiel gewonnen.");
		}
			
		// Wurde eine Mission eines anderen Spielers erfuellt?
		
		for(int t = 0 ; t < this.anzahlSpieler ; t++)
		{
			if(this.getSpieler(t).getMission().missionErfuellt() && this.getSpieler(t) != aktuellerSpieler)
			{
				IO.println(this.getSpieler(t).getMission().getMissionErfuelltText());
				IO.println((aktuellerSpieler.getMission().missionErfuellt() ? "Leider hat " + this.getSpieler(t).getName() + "trotzdem nicht gewonnen, da " + aktuellerSpieler.getName() + " seine Mission im eigenen Zug erfuellt hat." : this.getSpieler(t).getName() + " hat das Spiel gewonnen."));
			}

		}
	}
		
	public int wuerfeln()
		{
			return (int) (Math.random()*6+1);
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

	public int serieEinsetzen(SpielerInterface aktuellerSpieler, int[] karten, boolean gui) throws RemoteException
	{
		int[] handkartenId = new int[3];
		if(!gui)
		{
			for ( int i = 0; i < 3; i++)
			{
				IO.println(i+1 + ". Handkarte:");
				handkartenId[i] = IO.readInt();
			}
		}
		else
		{
			handkartenId = karten;
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
	
	public int checkSerie(SpielerInterface aktuellerSpieler) throws RemoteException // nur für Gui
	{/*
		if(aktuellerSpieler.getAnzahlHandkarten() == 5)
		{
			server.setLogText(aktuellerSpieler.getName() + " muss seine Handkarten einsetzen und eine Serie einloesen!");
		}
		else
		{
			//Popup, ob spieler serie einloesen moechte
			// JDialog mit entsprechendem panel starten
        	int selectedOption = JOptionPane.showOptionDialog(null, "Moechtest du eine Serie einloesen?", "Serie einloesen?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        	// wenn okay gedrueckt wurde
        	if(selectedOption == 0)
        	{
        		int zwischenSpeicherZusatzTruppenSerie = this.serieEinsetzen(aktuellerSpieler, true); // TODO Handkarten uebergeben
        		return zwischenSpeicherZusatzTruppenSerie;
        	}
		}*/
		return 0;
	}

	public boolean neueArmeen(SpielerInterface aktuellerSpieler, boolean gui, int landId, int einheiten) throws RemoteException
	{	
		int zwischenSpeicherZusatzTruppenSerie = 0;
		
		if(!gui) // nur CUI
		{
			aktuellerSpieler.handkartenAusgeben();
			
			if(aktuellerSpieler.getAnzahlHandkarten() == 5)
			{
				IO.println(aktuellerSpieler.getName() + " muss seine Handkarten einsetzen und eine Serie einloesen!");
				zwischenSpeicherZusatzTruppenSerie = this.serieEinsetzen( aktuellerSpieler,null, gui);
			}
			else
			{
				IO.println("Moechtest du eine Serie einloesen? (j/n)");
				if(IO.readChar() == 'j')
				{
					zwischenSpeicherZusatzTruppenSerie = this.serieEinsetzen(aktuellerSpieler,null, gui);
				}
			}
		}
		if(!gui)
		{
			int zuVerteilendeEinheiten = 0;
			zuVerteilendeEinheiten = this.laenderZaehlen(aktuellerSpieler) + this.zusatzEinheitenKontinente(   aktuellerSpieler) + zwischenSpeicherZusatzTruppenSerie;
			while (zuVerteilendeEinheiten > 0)
			{
				IO.println(aktuellerSpieler.getName() + " muss nun " + zuVerteilendeEinheiten + " Einheiten verteilen.\n"
		    			+ "Geben Sie die ID Ihres Landes ein, in dem Einheiten stationiert werden sollen.");
		    	
	    		landId = IO.readInt();
		    	while (landId < 0 || landId > 41)
		    	{
		    		IO.println("Falsche Eingabe!\nLandID:");
		    		landId = IO.readInt();
		    	}
		       	IO.println("Wie viele Einheiten sollen stationiert werden?");
		       	einheiten = IO.readInt();
		       	while (einheiten < 0 || einheiten > zuVerteilendeEinheiten)
		       	{
		       		IO.println("Falsche Eingabe! Es koennen maximal " + zuVerteilendeEinheiten+ " Einheiten stationiert werden!/nWie viele Einheiten sollen stationiert werden?");
		       		einheiten = IO.readInt();
		       	}
		       	// Abfrage, ob einheiten verteilt werden koennen
	       		if((   aktuellerSpieler).meinLand(this.laender[landId]))
	            {
	            	this.laender[landId].setTruppenstaerke(einheiten);
	    	       	zuVerteilendeEinheiten -= einheiten;
	    	       	IO.println(this.laender[landId].getTruppenstaerke() + " Einheiten auf " + this.laender[landId].getName());
	    	       	return true;
	        	}
	       		else
	       		{
	        		IO.println("Dieses Land gehoert dir nicht!");
	        		return false;
	            }
			}
		}
		else // GUI
		{
        	this.laender[landId].setTruppenstaerke(einheiten);
	       	this.verteilteEinheitenGui += einheiten;
	       	this.zuVerteilendeEinheitenGui -= einheiten;
	       	server.setLogText(aktuellerSpieler.getName() + " hat " + einheiten + " Einheiten auf " + this.laender[landId].getName() + " stationiert.");
	       	if(this.zuVerteilendeEinheitenGui == 0)
	       	{
	       		return true;
	       	}
		}
		return false;
	}
	
	public int zusatzEinheitenKontinente(SpielerInterface aktuellerSpieler) throws RemoteException
	{
		int zusatzEinheiten = 0;
		for(int i = 0 ; i < 6 ; i++)
		{
			int laenderImBesitz = 0;
			for(int j = 0 ; j < aktuellerSpieler.getLaenderAnzahl() ; j++){
				if(aktuellerSpieler.getBesitzLandKontinent(j) == kontinente[i])
				{
					laenderImBesitz++;	
				}
			}
			if(laenderImBesitz == kontinente[i].getAnzahlLaender())
			{
				IO.println("Spieler " + aktuellerSpieler.getName() + " ist im Beitz von ganz " + kontinente[i].getName() + " und bekommt " + kontinente[i].getZusatzTruppen() + " zusaetzliche Einheiten.");
				zusatzEinheiten += kontinente[i].getZusatzTruppen();
			}
		}
		return zusatzEinheiten;
	}

	public boolean landWurdeVerwendet(int land) throws RemoteException
	{
		if(this.eroberteLaender.contains(this.laender[land]))
		{
			IO.println("Die Truppen des Landes " + this.laender[land].getName() + " wurden bereits im Kampf verwendet.");
			return true;
		}
		return false;
	}
	
	public boolean welteroberung(SpielerInterface aktuellerSpieler) throws RemoteException
	{
		if (aktuellerSpieler.getLaenderAnzahl() == 42)
		{
			return true;
		}
		return false;
	}
	public boolean naechsterSpieler() throws RemoteException
	{
		for(int i = 0; i < this.spieler.size(); i++)
		{
			if(server.getAktuellerSpieler().equals(this.spieler.elementAt(i)))
			{
				if(i == this.spieler.size()-1)
				{
					server.setAktuellerSpieler(this.spieler.elementAt(0));
					return true;
				}
				else
				{
					server.setAktuellerSpieler(this.spieler.elementAt(i+1));
				return true;
				}
			}
		}
		return false;
	}
}

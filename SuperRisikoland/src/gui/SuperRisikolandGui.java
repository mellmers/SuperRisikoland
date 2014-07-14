package gui;

import inf.ServerInterface;
import inf.SpielfeldInterface;
import inf.SuperRisikoLandGuiInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.SliderUI;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import gui.Spielstart;
import cui.Land;
import cui.Spieler;
import cui.Spielfeld;

public class SuperRisikolandGui extends JFrame implements ActionListener, Serializable, SuperRisikoLandGuiInterface
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8931464798393030135L;
	// Variablen
	private Dimension screen;
	private int b, h;
	
	private String logTextGui = "";
	private JTextArea logTextArea = new JTextArea();
	public static Color aktuellerFarbcode =  new Color(0,0,0);
	private JLabel labelStatus = new JLabel("Armeen verteilen", SwingConstants.CENTER);
	private JButton buttonSpeichern = new JButton("Speichern");
	private JButton buttonLaden = new JButton("neues Spiel/Laden");
	private JButton buttonMission = new JButton("Mission anzeigen");
	private int verbleibendeZeit = 30;
	private JLabel labelVerbleibendeZeit = new JLabel("verbleibende Zeit: " + verbleibendeZeit, SwingConstants.CENTER);
	private JButton buttonPhaseBeenden = new JButton("Phase Beenden"), buttonBestaetigung = new JButton("Bestaetigung");
	final private JLabel[] labelArrayKontinente = {new JLabel("Nord-Amerika (5 Einheiten):", SwingConstants.RIGHT), new JLabel("Sued-Amerika (2 Einheiten):", SwingConstants.RIGHT), new JLabel("Europa (5 Einheiten):", SwingConstants.RIGHT), new JLabel("Afrika (3 Einheiten):", SwingConstants.RIGHT), new JLabel("Asien (7 Einheiten):", SwingConstants.RIGHT), new JLabel("Australien (2 Einheiten):", SwingConstants.RIGHT)};
	private JLabel[] labelArrayKontinenteBesitzer = {new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER)};
	
	private JPanel panelCharAktuellerSpieler, panelEigenerChar, panelHandkarten;
	
	private transient BufferedImage map;
	
	private ImageIcon[] iihandkarten = new ImageIcon[43];
	private JLabel[] labelHandkarten = {new JLabel(""),new JLabel(""),new JLabel(""),new JLabel(""),new JLabel("")};
	private ImageIcon[] iiCharakter = {null,null,null,null,null,null};
	private JLabel labelCharAktuellerSpieler = new JLabel(""), labelEigenerChar = new JLabel("");
	
	private Spieler aktuellerSpieler, eigenerSpieler;
	private ServerInterface server;
	//TODO private SpielfeldInterface spiel;
	private Spielfeld spiel;
	private Land aktuellesLand;
	private int aktuellesLandId;
	
	JSlider sliderMap;
		
	public SuperRisikolandGui() throws RemoteException
	{
		super();
		Vector<Spieler> spieler = new Vector<Spieler>();
		spieler.add(new Spieler(0, "Hans", "Blau", new Color(0,0,250)));
		spieler.add(new Spieler(1, "Peter", "Rot", new Color(250,0,0)));
		spieler.add(new Spieler(2, "Franz", "Gruen", new Color(0,250,0)));
		aktuellerSpieler = spieler.get(0);
		eigenerSpieler = spieler.get(1);
		spiel = new Spielfeld(null, spieler, 1);
		// Bildschirmgroesse auslesen und Breite und Hoehe abspeichern
		this.screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.b = (int) screen.getWidth();
		this.h = (int) screen.getHeight();
		initialize();
	}
	public SuperRisikolandGui(ServerInterface server, Spieler aktSpieler, Spieler eigenerSpieler, boolean geladen)  throws RemoteException
	{
		super();
		
		this.server = server;
		//TODO this.spiel = server.getSpielfeldInterface();
		this.aktuellerSpieler = aktSpieler;
		this.eigenerSpieler = eigenerSpieler;
		
		if(geladen)
		{
			// alle Spieler + Spielvariante ausgeben, wenn aus Spielstand geladen wurde
			server.setLogText("Spielfeld mit " + spiel.getAnzahlSpieler() + " Spielern und Spielvariante " + spiel.getSpielvariante() + " geladen.");
			for (int i = 0; i < spiel.getAnzahlSpieler(); i++)
			{
				Spieler s = (Spieler) spiel.getSpieler(i);
				server.setLogText("Spieler " + s.getName() + " mit der Farbe " + s.getSpielerfarbe() +" und mit SpielerID " + s.getSpielerID() + " wurde geladen.");
			}
		}
		// Bildschirmgroesse auslesen und Breite und Hoehe abspeichern
		this.screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.b = (int) screen.getWidth();
		this.h = (int) screen.getHeight();
		initialize();
	}
	
	private void initialize() throws RemoteException
	{	
		this.panelCharAktuellerSpieler = new JPanel();
		this.panelHandkarten = new JPanel(new GridLayout(1,5));
		this.panelEigenerChar = new JPanel();
		bilderEinlesen(); // Bilder werden eingelesen und abgespeichert
		this.setTitle("Super Risikoland");
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				super.windowClosing(e);
				int selectedOption = JOptionPane.showOptionDialog(null, "Willst du das Spiel speichern?", "Beenden", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Speichern", "Beenden", "Abbrechen"}, "Speichern");
				if(selectedOption == 0){
					try
					{
						speichern();
					} catch (RemoteException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 
				else if(selectedOption == 1){
					System.exit(0);
				}
			}
			
		});
		
		this.setLayout(new BorderLayout());
		
		// Oben Speichern + Laden + Mission
		final JPanel nord = new JPanel();
		
		final JPanel panelMenu = new JPanel(new GridLayout(3,1));
		panelMenu.setPreferredSize(new Dimension(this.b/100*10, this.h/100*8));
		//panelMenu.setBorder(BorderFactory.createLineBorder(Color.yellow));
		
		this.buttonSpeichern.addActionListener(this);
		this.buttonLaden.addActionListener(this);
		this.buttonMission.addActionListener(this);

		panelMenu.add(this.buttonSpeichern);
		panelMenu.add(this.buttonLaden);
		panelMenu.add(this.buttonMission);
		
		// Oben Kontinente + Timer
		final JPanel panelKontinenteTimer = new JPanel(new GridLayout());
		//panelKontinenteTimer.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Kontinente 1
		final JPanel kontinente1 = new JPanel(new GridLayout(3, 2));
		kontinente1.setPreferredSize(new Dimension(this.b/100*30, this.h/100*8));
		for(int i = 0; i < 3; i++)
		{
			this.labelArrayKontinente[i].setFont(new Font(null, Font.BOLD, this.b/106));
			this.labelArrayKontinenteBesitzer[i].setFont(new Font(null, Font.BOLD, this.b/87));
			kontinente1.add(this.labelArrayKontinente[i]);
			kontinente1.add(this.labelArrayKontinenteBesitzer[i]);
		}
		panelKontinenteTimer.add(kontinente1);
		
		// Status + Timer
		JPanel panelMissionTimerStatus = new JPanel();
		panelMissionTimerStatus.setLayout(new GridLayout(2,1));
		JPanel panelPhase = new JPanel(new GridLayout(1,2)), panelZeitBestaetigung = new JPanel(new GridLayout(1,2));
		labelStatus.setFont(new Font(null, Font.BOLD, 24));
		labelStatus.setForeground(Color.red);
		labelStatus.setPreferredSize(new Dimension(this.b/100*15, this.h/100*4));
		this.buttonPhaseBeenden.setPreferredSize(new Dimension(this.b/100*15, this.h/100*4));
		this.buttonPhaseBeenden.addActionListener(this);
		this.buttonPhaseBeenden.setEnabled(false);
		this.labelVerbleibendeZeit.setFont(new Font(null, Font.BOLD, this.b/80));
		this.labelVerbleibendeZeit.setPreferredSize(new Dimension(this.b/100*15, this.h/100*4));
		this.buttonBestaetigung.setPreferredSize(new Dimension(this.b/100*15, this.h/100*4));
		this.buttonBestaetigung.addActionListener(this);
		panelPhase.add(labelStatus);
		panelPhase.add(this.buttonPhaseBeenden);
		panelZeitBestaetigung.add(this.labelVerbleibendeZeit);
		panelZeitBestaetigung.add(this.buttonBestaetigung);
		panelMissionTimerStatus.add(panelPhase);
		panelMissionTimerStatus.add(panelZeitBestaetigung);
		panelKontinenteTimer.add(panelMissionTimerStatus);
		
		// Kontinente 2
		final JPanel kontinente2 = new JPanel(new GridLayout(3, 2));
		kontinente2.setPreferredSize(new Dimension(this.b/100*30, this.h/100*8));
		for(int i = 3; i < 6; i++)
		{
			this.labelArrayKontinente[i].setAlignmentX(RIGHT_ALIGNMENT);
			this.labelArrayKontinente[i].setFont(new Font(null, Font.BOLD, this.b/106));
			this.labelArrayKontinenteBesitzer[i].setFont(new Font(null, Font.BOLD, this.b/87));
			kontinente2.add(this.labelArrayKontinente[i]);
			kontinente2.add(this.labelArrayKontinenteBesitzer[i]);
		}
		panelKontinenteTimer.add(kontinente2);
		// panelMenu und panelKontinenteTimer zum Gesamt Nord Panel hinzufuegen
		nord.add(panelMenu);
		nord.add(panelKontinenteTimer);
		
		// Mitte - Karte
		JPanel mitte = new riskoMap();
		
		// Unten - Spielinfos
		//groessen:
		int bKarten = this.h/100*50;
		int bChar = this.h/100*16;
		int bLog = this.b-bKarten-2*bChar;
		
		final JPanel sued = new JPanel(new GridBagLayout());
		// aktuellerSpieler Char
        
		this.panelCharAktuellerSpieler.setPreferredSize(new Dimension((int)bChar, this.h/100*16));
		sued.add(this.panelCharAktuellerSpieler);
		
		// Textarea fuer Log
		logTextArea.setEditable(false);
		
		// Log
		JScrollPane log = new JScrollPane(logTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
		log.setPreferredSize(new Dimension(bLog, this.h/100*16));
		sued.add(log);
		
		// Karten
		this.panelHandkarten.setPreferredSize(new Dimension(bKarten, this.h/100*16));
		sued.add(this.panelHandkarten);
		
		// eigener Char
        
		this.panelEigenerChar.setPreferredSize(new Dimension(bChar, this.h/100*16));
		sued.add(this.panelEigenerChar);
		
		// Ausrichtung der Panels
		this.add(nord, BorderLayout.NORTH);
		this.add(mitte, BorderLayout.CENTER);
		this.add(sued, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setResizable(false);
		
		// Aktualsierung erstellen
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new Runnable()
		{
			public void run()
			{
				try
				{
					aktualisieren();
				} catch (RemoteException e)
				{
					e.printStackTrace();
				}
			}
		},0,1,TimeUnit.SECONDS);
	}
	
	private void aktualisieren() throws RemoteException
	{
		this.logTextArea.setText(server.getLogText() + this.getLogTextGui());
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.buttonPhaseBeenden))
		{
			switch(this.labelStatus.getText())
			{
			case "Serie eintauschen":
				break;
			case "Armeen verteilen":
				this.labelStatus.setText("Befreiung");
				break;
			case "Befreiung":
				this.labelStatus.setText("Einheiten nachziehen");
				break;
			case "Einheiten nachziehen":
				this.labelStatus.setText("Befreiung");
				this.labelStatus.setText("Umverteilen");
				break;
			case "Umverteilen":
				this.labelStatus.setText("Serie eintauschen");
				break;
			default:
				break;
			}
		}
		if (e.getSource().equals(this.buttonBestaetigung))
		{
			switch(this.labelStatus.getText())
			{
			case "Serie eintauschen":
				break;
			case "Armeen verteilen":
				try
				{
					if(aktuellerSpieler.meinLand(aktuellesLand) && this.sliderMap.getValue() > 0 && this.sliderMap.getValue() <= spiel.getZuVerteilendeEinheitenGui(aktuellerSpieler))
					{
						if(!this.spiel.neueArmeen(aktuellerSpieler, true, aktuellesLandId , this.sliderMap.getValue()))
						{
							this.buttonPhaseBeenden.setEnabled(true);
						}
						repaint();
					}
					
				} catch (RemoteException e1)
				{
					e1.printStackTrace();
				}
				break;
			case "Befreiung":
				
				break;
			case "Einheiten nachziehen":
				
				break;
			case "Umverteilen":
				
				break;
			default:
				break;
			}
		}
		if (e.getSource().equals(this.buttonSpeichern)) // Speichern
		{
			try
			{
				speichern();
			} catch (RemoteException e1)
			{
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.buttonLaden)) // Laden
		{
			new Spielstart(this);
		}
		if (e.getSource().equals((this.buttonMission)))
		{
			if(this.aktuellerSpieler.getMission() == null)
			{
				this.setLogTextGui("Keine Missionen vorhanden.");
			}
			else
			{
				this.setLogTextGui(this.aktuellerSpieler.getMission().getAufgabenText());
			}
		}
	}
	
	private void bilderEinlesen() throws RemoteException
	{
		try
		{
			//Bilder einlesen
			Image[] handkarten = new Image[43];
			for (int i = 0; i < handkarten.length; i++)
			{
				handkarten[i] = null;
				this.iihandkarten[i] = null;
			}
			handkarten[0] = ImageIO.read(new File("res/karten/infanterie/aegypten.png"));
			handkarten[1] = ImageIO.read(new File("res/karten/infanterie/afghanistan.png"));
			handkarten[2] = ImageIO.read(new File("res/karten/infanterie/alaska.png"));
			handkarten[3] = ImageIO.read(new File("res/karten/infanterie/alberta.png"));
			handkarten[4] = ImageIO.read(new File("res/karten/infanterie/argentinien.png"));
			handkarten[5] = ImageIO.read(new File("res/karten/infanterie/indien.png"));
			handkarten[6] = ImageIO.read(new File("res/karten/infanterie/irkutsk.png"));
			handkarten[7] = ImageIO.read(new File("res/karten/infanterie/island.png"));
			handkarten[8] = ImageIO.read(new File("res/karten/infanterie/japan.png"));
			handkarten[9] = ImageIO.read(new File("res/karten/infanterie/madagaskar.png"));
			handkarten[10] = ImageIO.read(new File("res/karten/infanterie/nordwest-afrika.png"));
			handkarten[11] = ImageIO.read(new File("res/karten/infanterie/ost-australien.png"));
			handkarten[12] = ImageIO.read(new File("res/karten/infanterie/westeuropa.png"));
			handkarten[13] = ImageIO.read(new File("res/karten/infanterie/weststaaten.png"));
			handkarten[14] = ImageIO.read(new File("res/karten/kavallerie/china.png"));
			handkarten[15] = ImageIO.read(new File("res/karten/kavallerie/groenland.png"));
			handkarten[16] = ImageIO.read(new File("res/karten/kavallerie/indonesien.png"));
			handkarten[17] = ImageIO.read(new File("res/karten/kavallerie/jakutien.png"));
			handkarten[18] = ImageIO.read(new File("res/karten/kavallerie/kamtschatka.png"));
			handkarten[19] = ImageIO.read(new File("res/karten/kavallerie/kongo.png"));
			handkarten[20] = ImageIO.read(new File("res/karten/kavallerie/mittelamerika.png"));
			handkarten[21] = ImageIO.read(new File("res/karten/kavallerie/mitteleuropa.png"));
			handkarten[22] = ImageIO.read(new File("res/karten/kavallerie/neuguinea.png"));
			handkarten[23] = ImageIO.read(new File("res/karten/kavallerie/ontario.png"));
			handkarten[24] = ImageIO.read(new File("res/karten/kavallerie/peru.png"));
			handkarten[25] = ImageIO.read(new File("res/karten/kavallerie/sibirien.png"));
			handkarten[26] = ImageIO.read(new File("res/karten/kavallerie/suedeuropa.png"));
			handkarten[27] = ImageIO.read(new File("res/karten/kavallerie/ural.png"));
			handkarten[28] = ImageIO.read(new File("res/karten/artillerie/brasilien.png"));
			handkarten[29] = ImageIO.read(new File("res/karten/artillerie/grossbritannien.png"));
			handkarten[30] = ImageIO.read(new File("res/karten/artillerie/mittlerer_osten.png"));
			handkarten[31] = ImageIO.read(new File("res/karten/artillerie/mongolei.png"));
			handkarten[32] = ImageIO.read(new File("res/karten/artillerie/nordwest-territorium.png"));
			handkarten[33] = ImageIO.read(new File("res/karten/artillerie/ost-afrika.png"));
			handkarten[34] = ImageIO.read(new File("res/karten/artillerie/oststaaten.png"));
			handkarten[35] = ImageIO.read(new File("res/karten/artillerie/quebec.png"));
			handkarten[36] = ImageIO.read(new File("res/karten/artillerie/siam.png"));
			handkarten[37] = ImageIO.read(new File("res/karten/artillerie/skandinavien.png"));
			handkarten[38] = ImageIO.read(new File("res/karten/artillerie/sued-afrika.png"));
			handkarten[39] = ImageIO.read(new File("res/karten/artillerie/ukraine.png"));
			handkarten[40] = ImageIO.read(new File("res/karten/artillerie/venezuela.png"));
			handkarten[41] = ImageIO.read(new File("res/karten/artillerie/west-australien.png"));
			handkarten[42] = ImageIO.read(new File("res/karten/joker.png"));
			Image imgDaisy = ImageIO.read(new File("res/charakter/daisy.png"));
			Image imgLuigi = ImageIO.read(new File("res/charakter/luigi.png"));
			Image imgMario = ImageIO.read(new File("res/charakter/mario.png"));
			Image imgPeach = ImageIO.read(new File("res/charakter/peach.png"));
			Image imgWaluigi = ImageIO.read(new File("res/charakter/waluigi.png"));
			Image imgWario = ImageIO.read(new File("res/charakter/wario.png"));
			// ImageIcon erstellen
			for (int i = 0; i < handkarten.length; i++)
			{
				this.iihandkarten[i] = new ImageIcon(handkarten[i].getScaledInstance(this.h/100*10, this.h/100*16, Image.SCALE_SMOOTH));
			}
			this.iiCharakter[0] = new ImageIcon(imgDaisy);
			this.iiCharakter[1] = new ImageIcon(imgLuigi);
			this.iiCharakter[2] = new ImageIcon(imgMario);
			this.iiCharakter[3] = new ImageIcon(imgPeach);
			this.iiCharakter[4] = new ImageIcon(imgWaluigi);
			this.iiCharakter[5] = new ImageIcon(imgWario);
			// ImageIcon dem Spieler zuordnen
			for (int i = 0; i < this.spiel.getAnzahlSpieler(); i++)
			{
				if(this.spiel.getSpieler(i) != null)
				{
					this.spiel.getSpieler(i).setSpielerIcon(this.iiCharakter[this.spiel.getSpieler(i).getSpielerID()]);
				}
			}
			// ImageIcon dem aktuellenSpieler und dem eigenenChar zuordnen
			this.labelCharAktuellerSpieler.setIcon(this.aktuellerSpieler.getSpielerIcon());
			this.labelEigenerChar.setIcon(this.eigenerSpieler.getSpielerIcon());
			// Label durchsichtig machen
			this.labelCharAktuellerSpieler.setOpaque(false);
			this.labelEigenerChar.setOpaque(false);
			// Label dem Panel hinzufuegen
			this.panelCharAktuellerSpieler.add(this.labelCharAktuellerSpieler);
			this.panelEigenerChar.add(this.labelEigenerChar);
			// Handkarten hinzufuegen
			for (int i = 0; i < this.labelHandkarten.length; i++)
			{
				this.labelHandkarten[i].setIcon(this.iihandkarten[i]);
				this.panelHandkarten.add(this.labelHandkarten[i]);
			}
		}
		catch(IIOException e)
		{
			server.setLogText("Ein oder mehrere Bilder konnten nicht geladen werden!");
		}
		catch(IOException e){}
	}
	
	public void speichern() throws RemoteException
	{
		// SpeichernDialog erstellen
		// Erstellung eines FileFilters fuer Spielstaende	
        FileFilter filter = new FileNameExtensionFilter("Risiko-Spielstaende", "ser");    
        JFileChooser speichern = new JFileChooser(new File(System.getProperty("user.home")));
        // Filter wird dem JFileChooser hinzugefuegt
        speichern.addChoosableFileFilter(filter);
        // Nur Verzeichnisse auswaehlbar
        speichern.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Dialog zum Speichern von Dateien anzeigen
        int rueckgabeWert = speichern.showDialog(null, "Spielstand speichern");
        
        // Abfrage, ob auf "Speichern" geklickt wurde
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
        	File spielstand = speichern.getSelectedFile();
        	try(final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(spielstand.getPath().endsWith(".ser") ? spielstand.getPath() : spielstand + ".ser")))
        	{
        		oos.writeObject(this.spiel);
        		oos.writeObject(this.aktuellerSpieler);
        		// Ausgabe der gespeicherten Datei
        		server.setLogText("Erfolgreich gespeichert unter: " + spielstand.getPath());
        	} catch (FileNotFoundException e1)
			{
        		server.setLogText(e1.getMessage());
			} catch (IOException e1)
			{
				server.setLogText(e1.getMessage());
			}
        }
	}
	
	public class riskoMap extends JPanel
	{
		// Variablen der Map
		JLabel[] landBeschreibung = {new JLabel("Truppenstaerke: ", SwingConstants.RIGHT), new JLabel("Land: ", SwingConstants.RIGHT), new JLabel("Besitzer: ", SwingConstants.RIGHT)};
		final JLabel landTruppenstaerke = new JLabel(""), landTruppenstaerke2 = new JLabel("");
		final JLabel landname = new JLabel(""), landname2 = new JLabel("");
		final JLabel landBesitzer = new JLabel(""), landBesitzer2 = new JLabel("");
		
		public riskoMap()
		{
			// Slider fuer Truppenauswahl, etc erstellen
			sliderMap = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 0);
			sliderMap.setBounds(b/100*2, h/100*82, b/3, h/25);
			// Die Abstände zwischen den Teilmarkierungen werden festgelegt
			sliderMap.setMajorTickSpacing(5);
			sliderMap.setMinorTickSpacing(1);
			// Standardmarkierungen werden erzeugt 
			sliderMap.createStandardLabels(1);
			// Zeichnen der Markierungen wird aktiviert
			sliderMap.setPaintTicks(true);
			// Zeichnen der Labels wird aktiviert
			sliderMap.setPaintLabels(true);
			// slidermap background durchsichtig
			sliderMap.setOpaque(false);
			getContentPane().add(sliderMap);
			// Label fuer Truppenstaerke, Besitzer und Name erstellen
			JPanel panelLabelFuerLand = new JPanel(new GridLayout(3, 3));
			panelLabelFuerLand.setBounds(-b/100*2,h/100*65,b/100*33,h/8);
			panelLabelFuerLand.setOpaque(false);
			for (int i = 0; i < landBeschreibung.length; i++)
			{
				landBeschreibung[i].setFont(new Font(null, Font.BOLD, 20));
			}
			landTruppenstaerke.setFont(new Font(null, Font.BOLD, 20));
			landname.setFont(new Font(null, Font.BOLD, 20));
			landBesitzer.setFont(new Font(null, Font.BOLD, 20));
			landTruppenstaerke2.setFont(new Font(null, Font.BOLD, 20));
			landname2.setFont(new Font(null, Font.BOLD, 20));
			landBesitzer2.setFont(new Font(null, Font.BOLD, 20));
			panelLabelFuerLand.add(landBeschreibung[0]);
			panelLabelFuerLand.add(landTruppenstaerke);
			panelLabelFuerLand.add(landTruppenstaerke2);
			panelLabelFuerLand.add(landBeschreibung[1]);
			panelLabelFuerLand.add(landname);
			panelLabelFuerLand.add(landname2);
			panelLabelFuerLand.add(landBeschreibung[2]);
			panelLabelFuerLand.add(landBesitzer);
			panelLabelFuerLand.add(landBesitzer2);
			getContentPane().add(panelLabelFuerLand);
			
			this.addMouseMotionListener(new MouseMotionAdapter()
			{
				public void mouseMoved(MouseEvent e)
				{
					try {
						tooltipErstellen(e);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			});
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					int x = 0, y = 0;
					try
					{
						x = (int) e.getX();
						y = (int) e.getY();
						aktuellerFarbcode = new Color(map.getRGB(x, y));
					}
					catch(ArrayIndexOutOfBoundsException  ex){}
					catch(NullPointerException nex){}
					
					/*logText += "\nX: " + x + " Y: " + y;
					logText += "\nRot: " + aktuellerFarbcode.getRed();
					logText += "\nGruen: " + aktuellerFarbcode.getGreen();
					logText += "\nBlau: " + aktuellerFarbcode.getBlue();*/
					
					// Farbcode abspeichern/abfragen welches Land es ist
					try{
						if(aktuellerFarbcode.getRed() == 10){
							setLandBeschreibung(0,e);
						}else if(aktuellerFarbcode.getRed() == 20){
							setLandBeschreibung(1,e);
						} else if(aktuellerFarbcode.getRed() == 30){
							setLandBeschreibung(2,e);
						}else if(aktuellerFarbcode.getRed() == 40){
							setLandBeschreibung(3,e);
						}else if(aktuellerFarbcode.getRed() == 50){
							setLandBeschreibung(4,e);
						}else if(aktuellerFarbcode.getRed() == 60){
							setLandBeschreibung(5,e);
						}else if(aktuellerFarbcode.getRed() == 70){
							setLandBeschreibung(6,e);
						}else if(aktuellerFarbcode.getRed() == 80){
							setLandBeschreibung(7,e);
						}else if(aktuellerFarbcode.getRed() == 90){
							setLandBeschreibung(8,e);
						}else if(aktuellerFarbcode.getRed() == 240){
							setLandBeschreibung(9,e);
						}else if(aktuellerFarbcode.getRed() == 220){
							setLandBeschreibung(10,e);
						}else if(aktuellerFarbcode.getRed() == 230){
							setLandBeschreibung(11,e);
						}else if(aktuellerFarbcode.getRed() == 250){
							setLandBeschreibung(12,e);
						}else if(aktuellerFarbcode.getGreen() == 10){
							setLandBeschreibung(13,e);
						}else if(aktuellerFarbcode.getGreen() == 70){
							setLandBeschreibung(14,e);
						}else if(aktuellerFarbcode.getGreen() == 20){
							setLandBeschreibung(15,e);
						}else if(aktuellerFarbcode.getGreen() == 40){
							setLandBeschreibung(16,e);
						}else if(aktuellerFarbcode.getGreen() == 60){
							setLandBeschreibung(17,e);
						}else if(aktuellerFarbcode.getGreen() == 30){
							setLandBeschreibung(18,e);
						}else if(aktuellerFarbcode.getGreen() == 50){
							setLandBeschreibung(19,e);
						}else if(aktuellerFarbcode.getGreen() == 250){
							setLandBeschreibung(20,e);
						}else if(aktuellerFarbcode.getGreen() == 240){
							setLandBeschreibung(21,e);
						}else if(aktuellerFarbcode.getGreen() == 220){
							setLandBeschreibung(22,e);
						}else if(aktuellerFarbcode.getGreen() == 230){
							setLandBeschreibung(23,e);
						}else if(aktuellerFarbcode.getGreen() == 210){
							setLandBeschreibung(24,e);
						}else if(aktuellerFarbcode.getGreen() == 200){
							setLandBeschreibung(25,e);
						}else if(aktuellerFarbcode.getBlue() == 50){
							setLandBeschreibung(26,e);
						}else if(aktuellerFarbcode.getBlue() == 60){
							setLandBeschreibung(27,e);
						}else if(aktuellerFarbcode.getBlue() == 90){
							setLandBeschreibung(28,e);
						}else if(aktuellerFarbcode.getBlue() == 100){
							setLandBeschreibung(29,e);
						}else if(aktuellerFarbcode.getBlue() == 80){
							setLandBeschreibung(30,e);
						}else if(aktuellerFarbcode.getBlue() == 70){
							setLandBeschreibung(31,e);
						}else if(aktuellerFarbcode.getBlue() == 110){
							setLandBeschreibung(32,e);
						}else if(aktuellerFarbcode.getBlue() == 20){
							setLandBeschreibung(33,e);
						}else if(aktuellerFarbcode.getBlue() == 40){
							setLandBeschreibung(34,e);
						}else if(aktuellerFarbcode.getBlue() == 10){
							setLandBeschreibung(35,e);
						}else if(aktuellerFarbcode.getBlue() == 30){
							setLandBeschreibung(36,e);
						}else if(aktuellerFarbcode.getBlue() == 120){
							setLandBeschreibung(37,e);
						}else if(aktuellerFarbcode.getBlue() == 250){
							setLandBeschreibung(38,e);
						}else if(aktuellerFarbcode.getBlue() == 240){
							setLandBeschreibung(39,e);
						}else if(aktuellerFarbcode.getBlue() == 230){
							setLandBeschreibung(40,e);
						}else if(aktuellerFarbcode.getBlue() == 220){
							setLandBeschreibung(41,e);
						}else if(aktuellerFarbcode.getRed() == 238){  //aktuellesLand null setzen, wenn auf kein Land geklickt wird
							landTruppenstaerke.setText("");
							landname.setText("");
							landBesitzer.setText("");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			//Graphics2D g2 = (Graphics2D) g;
			//renderSettings(g2);
			createMap(b, h/100*69);
			g.drawImage(map, 0, 0, this);
		}
		
		private void createMap(int b, int h)
		{
			map = new BufferedImage(b, h, BufferedImage.TYPE_INT_RGB);
			ImageIcon mapIcon = new ImageIcon("res/karteFarbcodes.png");
			
			Graphics g = map.getGraphics();
			g.setColor(getContentPane().getBackground());
			g.fillRect(0,0,b, h);
			g.drawImage(mapIcon.getImage(), 10, 10, b, h, this);
		}
		
		private void renderSettings(Graphics2D g)
		{
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		}
		
		private void tooltipErstellen(MouseEvent e) throws RemoteException
		{
			int x = 0, y = 0;
			try
			{
				x = (int) e.getX();
				y = (int) e.getY();
				aktuellerFarbcode = new Color(map.getRGB(x, y));
			}
			catch(ArrayIndexOutOfBoundsException  ex){}
			catch(NullPointerException nex){}
			
			/*logText += "\nX: " + x + " Y: " + y;
			logText += "\nRot: " + aktuellerFarbcode.getRed();
			logText += "\nGr���n: " + aktuellerFarbcode.getGreen();
			logText += "\nBlau: " + aktuellerFarbcode.getBlue();*/
			
			// Tooltip Einstellungen
			ToolTipManager.sharedInstance().setInitialDelay(500); // 0,5 Sekunden Verz���gerung bis Tooltip angezeigt wird
			
			// Farbcode abspeichern/abfragen welches Land es ist und Tooltip setzen
			if(aktuellerFarbcode.getRed() == 10){
				setTooltip(0);
			}else if(aktuellerFarbcode.getRed() == 20){
				setTooltip(1);
			} else if(aktuellerFarbcode.getRed() == 30){
				setTooltip(2);
			}else if(aktuellerFarbcode.getRed() == 40){
				setTooltip(3);
			}else if(aktuellerFarbcode.getRed() == 50){
				setTooltip(4);
			}else if(aktuellerFarbcode.getRed() == 60){
				setTooltip(5);
			}else if(aktuellerFarbcode.getRed() == 70){
				setTooltip(6);
			}else if(aktuellerFarbcode.getRed() == 80){
				setTooltip(7);
			}else if(aktuellerFarbcode.getRed() == 90){
				setTooltip(8);
			}else if(aktuellerFarbcode.getRed() == 240){
				setTooltip(9);
			}else if(aktuellerFarbcode.getRed() == 220){
				setTooltip(10);
			}else if(aktuellerFarbcode.getRed() == 230){
				setTooltip(11);
			}else if(aktuellerFarbcode.getRed() == 250){
				setTooltip(12);
			}else if(aktuellerFarbcode.getGreen() == 10){
				setTooltip(13);
			}else if(aktuellerFarbcode.getGreen() == 70){
				setTooltip(14);
			}else if(aktuellerFarbcode.getGreen() == 20){
				setTooltip(15);
			}else if(aktuellerFarbcode.getGreen() == 40){
				setTooltip(16);
			}else if(aktuellerFarbcode.getGreen() == 60){
				setTooltip(17);
			}else if(aktuellerFarbcode.getGreen() == 30){
				setTooltip(18);
			}else if(aktuellerFarbcode.getGreen() == 50){
				setTooltip(19);
			}else if(aktuellerFarbcode.getGreen() == 250){
				setTooltip(20);
			}else if(aktuellerFarbcode.getGreen() == 240){
				setTooltip(21);
			}else if(aktuellerFarbcode.getGreen() == 220){
				setTooltip(22);
			}else if(aktuellerFarbcode.getGreen() == 230){
				setTooltip(23);
			}else if(aktuellerFarbcode.getGreen() == 210){
				setTooltip(24);
			}else if(aktuellerFarbcode.getGreen() == 200){
				setTooltip(25);
			}else if(aktuellerFarbcode.getBlue() == 50){
				setTooltip(26);
			}else if(aktuellerFarbcode.getBlue() == 60){
				setTooltip(27);
			}else if(aktuellerFarbcode.getBlue() == 90){
				setTooltip(28);
			}else if(aktuellerFarbcode.getBlue() == 100){
				setTooltip(29);
			}else if(aktuellerFarbcode.getBlue() == 80){
				setTooltip(30);
			}else if(aktuellerFarbcode.getBlue() == 70){
				setTooltip(31);
			}else if(aktuellerFarbcode.getBlue() == 110){
				setTooltip(32);
			}else if(aktuellerFarbcode.getBlue() == 20){
				setTooltip(33);
			}else if(aktuellerFarbcode.getBlue() == 40){
				setTooltip(34);
			}else if(aktuellerFarbcode.getBlue() == 10){
				setTooltip(35);
			}else if(aktuellerFarbcode.getBlue() == 30){
				setTooltip(36);
			}else if(aktuellerFarbcode.getBlue() == 120){
				setTooltip(37);
			}else if(aktuellerFarbcode.getBlue() == 250){
				setTooltip(38);
			}else if(aktuellerFarbcode.getBlue() == 240){
				setTooltip(39);
			}else if(aktuellerFarbcode.getBlue() == 230){
				setTooltip(40);
			}else if(aktuellerFarbcode.getBlue() == 220){
				setTooltip(41);
			}else if(aktuellerFarbcode.getRed() == 238){  //Tooltip ausblenden, wenn man mit dem Cursor auf keinem Land ist
				this.setToolTipText("");
			}
		}
		
		private void setTooltip(int landId) throws RemoteException
		{
			Land l = (Land) spiel.getLand(landId);
			String besitzer = l.getBesitzer() != null ? l.getBesitzer().getName() : "kein Besitzer";
			Color cBesitzer = l.getBesitzer() != null ? l.getBesitzer().getColorSpieler() : Color.white;
			UIManager.put("ToolTip.background", cBesitzer);
			UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.red));
			this.setToolTipText("<html>" + landBeschreibung[0].getText() + l.getTruppenstaerke() + "<br>" + landBeschreibung[1].getText() 
					+ l.getName() + "<br>" + landBeschreibung[2].getText() + besitzer +"</html>");
		}
		
		private void setLandBeschreibung(int landId, MouseEvent e) throws RemoteException
		{
			aktuellesLand = (Land) spiel.getLand(landId);
			aktuellesLandId = landId;
			if(e.getButton() == 3)
			{
				landTruppenstaerke2.setForeground(aktuellesLand.getBesitzer().getColorSpieler());
				landname2.setForeground(aktuellesLand.getBesitzer().getColorSpieler());
				landBesitzer2.setForeground(aktuellesLand.getBesitzer().getColorSpieler());
				landTruppenstaerke2.setText(""+ aktuellesLand.getTruppenstaerke());
				landname2.setText(aktuellesLand.getName());
				if(aktuellesLand.getBesitzer() != null)
				{
					landBesitzer2.setText(aktuellesLand.getBesitzer().getName());
				} else { landBesitzer2.setText("kein Besitzer"); }
			}
			else
			{
				landTruppenstaerke.setForeground(aktuellesLand.getBesitzer().getColorSpieler());
				landname.setForeground(aktuellesLand.getBesitzer().getColorSpieler());
				landBesitzer.setForeground(aktuellesLand.getBesitzer().getColorSpieler());
				landTruppenstaerke.setText(""+ aktuellesLand.getTruppenstaerke());
				landname.setText(aktuellesLand.getName());
				if(aktuellesLand.getBesitzer() != null)
				{
					landBesitzer.setText(aktuellesLand.getBesitzer().getName());
				} else { landBesitzer.setText("kein Besitzer"); }
			}
		}
	}

	public String getLogTextGui()
	{
		return logTextGui;
	}

	public void setLogTextGui(String logTextGui)
	{
		this.logTextGui += "\n" + logTextGui;
	}
	
	public static void main(String[] args) throws RemoteException
	{
		new SuperRisikolandGui();
	}
}

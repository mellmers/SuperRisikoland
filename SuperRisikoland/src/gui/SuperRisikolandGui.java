package gui;

import inf.LandInterface;
import inf.ServerInterface;
import inf.SpielerInterface;
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
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import gui.Spielstart;
import cui.Spieler;
import cui.Spielfeld;

public class SuperRisikolandGui extends JFrame implements ActionListener, Serializable, SuperRisikoLandGuiInterface
{
	private static final long serialVersionUID = 8931464798393030135L;
	// Variablen
	private Dimension screen;
	private int b, h;
	private int iconGroesse;
	
	private String logTextGui = "";
	private JTextArea logTextArea = new JTextArea();
	public static Color aktuellerFarbcode =  new Color(0,0,0);
	private JLabel labelStatus = new JLabel("Kein Status", SwingConstants.CENTER);
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
	private JPanel panelMap;
	private Image mainMap;
	JLabel labelMap;
	
	private ImageIcon[] iihandkarten = new ImageIcon[43];
	private JLabel[] labelHandkarten = {new JLabel(""),new JLabel(""),new JLabel(""),new JLabel(""),new JLabel("")};
	private ImageIcon[] iiCharakter = {null,null,null,null,null,null};
	private ImageIcon[] iiIcon = {null,null,null,null,null,null};
	private JLabel[] labelIcons = new JLabel[42];
	private JLabel labelCharAktuellerSpieler = new JLabel(""), labelEigenerChar = new JLabel("");
	
	private SpielerInterface aktuellerSpieler, eigenerSpieler;
	private ServerInterface server;
	private SpielfeldInterface spiel;
	// TODO private Spielfeld spiel;
	private LandInterface aktuellesLand;
	private LandInterface aktuellesLandRK;
	private int aktuellesLandId;	
	private int aktuellesLandRKId;
	
	
	
	// Variablen der Map
	JLabel[] landBeschreibung = {new JLabel("Truppenstaerke: ", SwingConstants.RIGHT), new JLabel("Land: ", SwingConstants.RIGHT), new JLabel("Besitzer: ", SwingConstants.RIGHT)};
	final JLabel landTruppenstaerke = new JLabel(""), landTruppenstaerke2 = new JLabel("");
	final JLabel landname = new JLabel(""), landname2 = new JLabel("");
	final JLabel landBesitzer = new JLabel(""), landBesitzer2 = new JLabel("");
	JSlider sliderMap;
	
	Object[] kreise = new Object[42];
		
	public SuperRisikolandGui() throws RemoteException
	{
		super();
		Vector<SpielerInterface> spieler = new Vector<SpielerInterface>();
		spieler.add(new Spieler(0, "Daisy", "Blau", new Color(250,111,43)));
		spieler.add(new Spieler(1, "Luigi", "Rot", new Color(22,169,14)));
		spieler.add(new Spieler(2, "Mario", "Gruen", new Color(249,15,46)));
		spieler.add(new Spieler(3, "Peach", "Gruen", new Color(251,175,221)));
		spieler.add(new Spieler(4, "Waluigi", "Gruen", new Color(203,113,255)));
		spieler.add(new Spieler(5, "Wario", "Gruen", new Color(255,239,0)));
		aktuellerSpieler = spieler.get(0);
		eigenerSpieler = spieler.get(1);
		spiel = new Spielfeld(null, spieler, 1);
		// Bildschirmgroesse auslesen und Breite und Hoehe abspeichern
		this.screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.b = (int) screen.getWidth();
		this.h = (int) screen.getHeight();
		initialize();
	}
	public SuperRisikolandGui(ServerInterface server, SpielerInterface eigenerSpieler, boolean geladen)  throws RemoteException
	{
		super();
		
		this.server = server;
		this.spiel = server.getSpielfeldInterface();
		this.aktuellerSpieler = server.getAktuellerSpieler();
		this.eigenerSpieler = eigenerSpieler;
		
		if(geladen)
		{
			// alle Spieler + Spielvariante ausgeben, wenn aus Spielstand geladen wurde
			server.setLogText("Spielfeld mit " + spiel.getAnzahlSpieler() + " Spielern und Spielvariante " + spiel.getSpielvariante() + " geladen.");
			for (int i = 0; i < spiel.getAnzahlSpieler(); i++)
			{
				SpielerInterface s =  spiel.getSpieler(i);
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
		this.setTitle("Super Risikoland von " + eigenerSpieler.getName());
		
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
		labelStatus.setFont(new Font(null, Font.BOLD, this.b/80));
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
		panelMap = new riskoMap();
		
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
		this.add(panelMap, BorderLayout.CENTER);
		this.add(sued, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setResizable(false);
		
		// Aktualisierung erstellen
		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new Runnable()
		{
			public void run()
			{
				try
				{
					aktualisieren();
					aktualisierenPhase();
					handKartenAktualisieren();
				} catch (RemoteException e)
				{
					e.printStackTrace();
				}
			}
		},0,1,TimeUnit.SECONDS);
	}
	
	protected void aktualisierenPhase() throws RemoteException 
	{
		if(this.aktuellerSpieler.equals(eigenerSpieler))
		{
			switch(server.getAktuellePhase())
			{
			case "Serie eintauschen":
				this.aktuellerSpieler = server.getAktuellerSpieler();
				this.labelCharAktuellerSpieler.setIcon(this.aktuellerSpieler.getSpielerIcon());
				this.buttonPhaseBeenden.setEnabled(true);
				break;
			case "Armeen verteilen":
				this.buttonPhaseBeenden.setEnabled(false);
				break;
			case "Befreiung":
				this.buttonPhaseBeenden.setEnabled(true);
				break;
			case "Einheiten nachziehen":
				this.buttonPhaseBeenden.setEnabled(false);
				break;
			case "Umverteilen":
				this.buttonPhaseBeenden.setEnabled(true);
				break;
			default:
				break;
			}	
		}
	}
	
	protected void aktualisieren() throws RemoteException
	{
		this.labelStatus.setText(server.getAktuellePhase());
		this.logTextArea.setText(server.getLogText() + this.getLogTextGui());
		
		// Land 1 aktualisieren
		if(aktuellesLand != null)
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
		if(aktuellesLandRK != null)
		{
			landTruppenstaerke2.setForeground(aktuellesLandRK.getBesitzer().getColorSpieler());
			landname2.setForeground(aktuellesLandRK.getBesitzer().getColorSpieler());
			landBesitzer2.setForeground(aktuellesLandRK.getBesitzer().getColorSpieler());
			landTruppenstaerke2.setText(""+ aktuellesLandRK.getTruppenstaerke());
			landname2.setText(aktuellesLandRK.getName());
			if(aktuellesLandRK.getBesitzer() != null)
			{
				landBesitzer2.setText(aktuellesLandRK.getBesitzer().getName());
			} else { landBesitzer2.setText("kein Besitzer"); }
		}
	}

	public void besitzerAktualisieren() throws RemoteException
	{
		for(int i = 0 ; i < labelIcons.length ; i++)
		{
			labelIcons[i].setIcon(iiIcon[spiel.getLand(i).getBesitzer().getSpielerID()]);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(this.aktuellerSpieler.equals(eigenerSpieler))
		{
			if (e.getSource().equals(this.buttonPhaseBeenden))
			{
				try {
					switch(server.getAktuellePhase())
					{
					case "Serie eintauschen":
						server.setAktuellePhase("Armeen verteilen");
						this.labelStatus.setText(server.getAktuellePhase());
						break;
					case "Armeen verteilen":
						break;
					case "Befreiung":
						int selectedOption = JOptionPane.showOptionDialog(null,"Bist du sicher, dass du keine weitere Befreiung ausfuehren moechtest?", "Befreiung beenden", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ja","Nein"}, "Ja");
						if(selectedOption == 0)
						{
							server.setAktuellePhase("Umverteilen");
							this.labelStatus.setText(server.getAktuellePhase());
						}
						break;
					case "Einheiten nachziehen":
						server.setAktuellePhase("Umverteilen");
						this.labelStatus.setText(server.getAktuellePhase());
						break;
					case "Umverteilen":
						try 
						{
							if(spiel.naechsterSpieler())
							{
								server.setAktuellePhase("Serie eintauschen");
								this.labelStatus.setText(server.getAktuellePhase());
							}
						} catch (RemoteException e1) 
						{
							e1.printStackTrace();
						}
						break;
					default:
						break;
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			if (e.getSource().equals(this.buttonBestaetigung))
			{
				try {
					switch(server.getAktuellePhase())
					{
					case "Serie eintauschen":
						break;
					case "Armeen verteilen":
						try
						{
							if(aktuellesLand != null)
							{
								if(aktuellerSpieler.meinLand(aktuellesLand) && this.sliderMap.getValue() > 0 && this.sliderMap.getValue() <= spiel.getZuVerteilendeEinheitenGui((SpielerInterface) aktuellerSpieler))
								{
									if(this.spiel.neueArmeen((SpielerInterface)aktuellerSpieler, true, aktuellesLandId , this.sliderMap.getValue()))
									{
										server.setAktuellePhase("Befreiung");
										this.labelStatus.setText(server.getAktuellePhase());
									}
								}
							}
						} catch (RemoteException e1)
						{
							e1.printStackTrace();
						}
						break;
					case "Befreiung":
						try {
							if(aktuellesLand != null && aktuellesLandRK != null)
							{
								if(aktuellerSpieler.meinLand(aktuellesLand) && !aktuellerSpieler.meinLand(aktuellesLandRK)) // Wenn befreiung möglich ist
								{
									spiel.befreien(aktuellerSpieler, this.sliderMap.getValue(), getVerteigerTruppen(aktuellesLandRK.getBesitzer()), aktuellesLandId, aktuellesLandRKId, true);
									if(aktuellesLandRK.getBesitzer().equals(aktuellerSpieler) && aktuellesLand.getTruppenstaerke() > 1)
									{
										int selectedOption = JOptionPane.showOptionDialog(null,"Moechtest du Einheiten nachziehen?", "Einheiten nachziehen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Ja","Nein"}, "Ja");
										if(selectedOption == 0)
										{
											server.setAktuellePhase("Einheiten nachziehen");
											this.labelStatus.setText(server.getAktuellePhase());
										}
									}	
								}
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						break;
					case "Einheiten nachziehen":
						try {
							if(sliderMap.getValue() > 0 && sliderMap.getValue() < aktuellesLand.getTruppenstaerke())
							{
								try {
									if(spiel.einheitenNachKampfNachziehen(aktuellesLandId, aktuellesLandRKId, this.sliderMap.getValue(), true))
									{
										server.setAktuellePhase("Befreiung");
										this.labelStatus.setText(server.getAktuellePhase());
										System.out.println("hat geklappt");
									}
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
								
							}
							else
							{
								setLogTextGui("Soviel Einheiten koennen nicht verschoben werden");
							}
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						break;
					case "Umverteilen":
						try {
							if(aktuellesLand.getBesitzer().equals(aktuellerSpieler)){
								//if(aktuellesLand.getTruppenstaerke() <= this.sliderMap.getValue() || this.sliderMap.getValue() > (aktuellesLand.getTruppenstaerke() - aktuellesLand.getBenutzteEinheiten()))
								
									try {
										if(spiel.einheitenNachziehen(aktuellesLandId, aktuellesLandRKId, this.sliderMap.getValue(), true))
										{
											setLogTextGui(this.sliderMap.getValue() + " Einheiten wurden von " + aktuellesLand.getName() + " nach " + aktuellesLandRK.getName() + " verschoben.");
										}
									} catch (RemoteException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					default:
						break;
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			try {
				
					this.setLogTextGui(this.aktuellerSpieler.getMission().getAufgabenText());
				
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void bilderEinlesen() throws RemoteException
	{
		try
		{
			//Bilder einlesen
			// Karte
			
			
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
			Image iconDaisy = ImageIO.read(new File("res/charakterIcons/DaisyIcon.png"));
			Image iconLuigi = ImageIO.read(new File("res/charakterIcons/LuigiIcon.png"));
			Image iconMario = ImageIO.read(new File("res/charakterIcons/MarioIcon.png"));
			Image iconPeach = ImageIO.read(new File("res/charakterIcons/PeachIcon.png"));
			Image iconWaluigi = ImageIO.read(new File("res/charakterIcons/WaluigiIcon.png"));
			Image iconWario = ImageIO.read(new File("res/charakterIcons/WarioIcon.png"));
			// ImageIcon erstellen
			for (int i = 0; i < handkarten.length; i++)
			{
				this.iihandkarten[i] = new ImageIcon(handkarten[i].getScaledInstance(this.h/100*10, this.h/100*16, Image.SCALE_SMOOTH));
			}
			this.iiCharakter[0] = new ImageIcon(imgDaisy.getScaledInstance(this.h/100*16,this.h/100*15, Image.SCALE_SMOOTH));
			this.iiCharakter[1] = new ImageIcon(imgLuigi.getScaledInstance(this.h/100*16,this.h/100*15, Image.SCALE_SMOOTH));
			this.iiCharakter[2] = new ImageIcon(imgMario.getScaledInstance(this.h/100*16,this.h/100*15, Image.SCALE_SMOOTH));
			this.iiCharakter[3] = new ImageIcon(imgPeach.getScaledInstance(this.h/100*16,this.h/100*15, Image.SCALE_SMOOTH));
			this.iiCharakter[4] = new ImageIcon(imgWaluigi.getScaledInstance(this.h/100*16,this.h/100*15, Image.SCALE_SMOOTH));
			this.iiCharakter[5] = new ImageIcon(imgWario.getScaledInstance(this.h/100*16,this.h/100*15, Image.SCALE_SMOOTH));
			this.iconGroesse = (int) (b*0.023);
			this.iiIcon[0] = new ImageIcon(iconDaisy.getScaledInstance(iconGroesse, iconGroesse, Image.SCALE_SMOOTH));
			this.iiIcon[1] = new ImageIcon(iconLuigi.getScaledInstance(iconGroesse, iconGroesse, Image.SCALE_SMOOTH));
			this.iiIcon[2] = new ImageIcon(iconMario.getScaledInstance(iconGroesse, iconGroesse, Image.SCALE_SMOOTH));
			this.iiIcon[3] = new ImageIcon(iconPeach.getScaledInstance(iconGroesse, iconGroesse, Image.SCALE_SMOOTH));
			this.iiIcon[4] = new ImageIcon(iconWaluigi.getScaledInstance(iconGroesse, iconGroesse, Image.SCALE_SMOOTH));
			this.iiIcon[5] = new ImageIcon(iconWario.getScaledInstance(iconGroesse, iconGroesse, Image.SCALE_SMOOTH));
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
	
	public int verteidigen(LandInterface land) throws RemoteException
	{
		String[] option;
		if(land.getTruppenstaerke() > 1)
		{
				option = new String[]{"1", "2"};
		}
		else
		{
			option = new String[]{"1"};
		}
		int selectedOption = JOptionPane.showOptionDialog(null, "Das Land " + land.getName() + ",\nwelches sich in deinem Besitz befindet,\nwird angegriffen.\nMit wie Vielen Einheiten willst du verteidigen?", "Angriff!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, option, "1");
		return selectedOption +1;
	}
	
	public int getVerteigerTruppen(SpielerInterface gegenSpieler) throws RemoteException
	{
		int verTruppen = server.getClientByName(gegenSpieler.getName()).verteidigen(aktuellesLandRK);
		return verTruppen;
	}
	
	public class riskoMap extends JPanel

	{		
		public riskoMap() throws RemoteException

		{
			// Slider fuer Truppenauswahl, etc erstellen
			sliderMap = new JSlider(SwingConstants.HORIZONTAL, 0, 50, 0);
			sliderMap.setBounds(b/100*2, h/100*65, b/100*28, h/100*20);
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
			panelLabelFuerLand.setBounds(0,h/100*60,b/100*33,h/8);
			panelLabelFuerLand.setOpaque(false);
			for (int i = 0; i < landBeschreibung.length; i++)
			{
				landBeschreibung[i].setFont(new Font(null, Font.BOLD, b/96));
			}
			landTruppenstaerke.setFont(new Font(null, Font.BOLD, b/96));
			landname.setFont(new Font(null, Font.BOLD, b/96));
			landBesitzer.setFont(new Font(null, Font.BOLD, b/96));
			landTruppenstaerke2.setFont(new Font(null, Font.BOLD, b/96));
			landname2.setFont(new Font(null, Font.BOLD, b/96));
			landBesitzer2.setFont(new Font(null, Font.BOLD, b/96));
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
			// BesitzerIcons auf den Laendern erscheinen lassen
			besitzerBildAnzeigen();
			

			this.addMouseMotionListener(new MouseMotionAdapterMap());
			this.addMouseListener(new MouseAdapterMap());
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			//Graphics2D g2 = (Graphics2D) g;
			//renderSettings(g2);
			createMap(b, h/100*69);
			createMainMap(b, h/100*69);
			g.drawImage(map, 0, 0, this);
			g.drawImage(mainMap, 0, 0, this);
		}
		public void createMainMap(int b, int h)
		{
			mainMap = new BufferedImage(b, h, BufferedImage.TYPE_INT_RGB);
			ImageIcon mainMapIcon = new ImageIcon("res/karte.png");
			
			Graphics g2 = mainMap.getGraphics();
			g2.setColor(getContentPane().getBackground());
			g2.fillRect(0,0,b, h);
			g2.drawImage(mainMapIcon.getImage(), 0, 10, b, h, this);
		}
		
		public void besitzerBildAnzeigen() throws RemoteException
		{
			int abstandVonOben = 1080/100*8;
			for(int i = 0 ; i < labelIcons.length ; i++)
			{
				labelIcons[i] = new JLabel();
				labelIcons[i].setIcon(iiIcon[spiel.getLand(i).getBesitzer().getSpielerID()]);
				getContentPane().add(labelIcons[i]);
			}
			labelIcons[0].setBounds(282*b/1920-iconGroesse/2, 105*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[1].setBounds(417*b/1920-iconGroesse/2, 111*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[2].setBounds(747*b/1920-iconGroesse/2, 79*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[3].setBounds(425*b/1920-iconGroesse/2, 175*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[4].setBounds(521*b/1920-iconGroesse/2, 181*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[5].setBounds(605*b/1920-iconGroesse/2, 176*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[6].setBounds(444*b/1920-iconGroesse/2, 243*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[7].setBounds(549*b/1920-iconGroesse/2, 261*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[8].setBounds(490*b/1920-iconGroesse/2, 349*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[9].setBounds(591*b/1920-iconGroesse/2, 484*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[10].setBounds(613*b/1920-iconGroesse/2, 409*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[11].setBounds(691*b/1920-iconGroesse/2, 477*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[12].setBounds(615*b/1920-iconGroesse/2, 600*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[13].setBounds(874*b/1920-iconGroesse/2, 175*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[14].setBounds(890*b/1920-iconGroesse/2, 244*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[15].setBounds(1021*b/1920-iconGroesse/2, 169*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[16].setBounds(1012*b/1920-iconGroesse/2, 245*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[17].setBounds(1117*b/1920-iconGroesse/2, 220*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[18].setBounds(953*b/1920-iconGroesse/2, 292*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[19].setBounds(1040*b/1920-iconGroesse/2, 284*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[20].setBounds(952*b/1920-iconGroesse/2, 410*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[21].setBounds(1041*b/1920-iconGroesse/2, 372*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[22].setBounds(1046*b/1920-iconGroesse/2, 493*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[23].setBounds(1102*b/1920-iconGroesse/2, 440*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[24].setBounds(1059*b/1920-iconGroesse/2, 587*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[25].setBounds(1146*b/1920-iconGroesse/2, 579*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[26].setBounds(1261*b/1920-iconGroesse/2, 196*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[27].setBounds(1315*b/1920-iconGroesse/2, 167*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[28].setBounds(1407*b/1920-iconGroesse/2, 133*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[29].setBounds(1633*b/1920-iconGroesse/2, 163*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[30].setBounds(1436*b/1920-iconGroesse/2, 191*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[31].setBounds(1420*b/1920-iconGroesse/2, 263*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[32].setBounds(1540*b/1920-iconGroesse/2, 326*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[33].setBounds(1228*b/1920-iconGroesse/2, 284*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[34].setBounds(1371*b/1920-iconGroesse/2, 332*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[35].setBounds(1135*b/1920-iconGroesse/2, 346*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[36].setBounds(1283*b/1920-iconGroesse/2, 382*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[37].setBounds(1381*b/1920-iconGroesse/2, 411*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[38].setBounds(1417*b/1920-iconGroesse/2, 492*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[39].setBounds(1551*b/1920-iconGroesse/2, 517*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[40].setBounds(1470*b/1920-iconGroesse/2, 612*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
			labelIcons[41].setBounds(1562*b/1920-iconGroesse/2, 603*h/1080-iconGroesse/2+abstandVonOben, iconGroesse, iconGroesse);
		}
		
		private void createMap(int b, int h)
		{
			map = new BufferedImage(b, h, BufferedImage.TYPE_INT_RGB);
			ImageIcon mapIcon = new ImageIcon("res/karteFarbcodes.png");
			
			Graphics g = map.getGraphics();
			g.setColor(getContentPane().getBackground());
			g.fillRect(0,0,b, h);
			g.drawImage(mapIcon.getImage(), 0, 10, b, h, this);
		}
		
		private void renderSettings(Graphics2D g)
		{
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		}
	}
	
	public class MouseMotionAdapterMap implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e){}

		public void mouseMoved(MouseEvent e)
		{
			try {
				tooltipErstellen(e);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
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
		ToolTipManager.sharedInstance().setInitialDelay(500); // 0,5 Sekunden Verzoegerung bis Tooltip angezeigt wird
		
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
			panelMap.setToolTipText("");
		}
	}
	
	private void setTooltip(int landId) throws RemoteException
	{
		LandInterface l =   spiel.getLand(landId);
		String besitzer = l.getBesitzer() != null ? l.getBesitzer().getName() : "kein Besitzer";
		Color cBesitzer = l.getBesitzer() != null ? l.getBesitzer().getColorSpieler() : Color.white;
		UIManager.put("ToolTip.background", cBesitzer);
		UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.red));
		panelMap.setToolTipText("<html>" + landBeschreibung[0].getText() + l.getTruppenstaerke() + "<br>" + landBeschreibung[1].getText() 
				+ l.getName() + "<br>" + landBeschreibung[2].getText() + besitzer +"</html>");
	}
	
	public class MouseAdapterMap implements MouseListener
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
				e1.printStackTrace();
			}
		}

		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
	}
	
	private void setLandBeschreibung(int landId, MouseEvent e) throws RemoteException
	{
		if(!server.getAktuellePhase().equals("Einheiten nachziehen"))
		{
			if(e.getButton() == 3)
			{
				aktuellesLandRK =   spiel.getLand(landId);
				aktuellesLandRKId = landId;
				landTruppenstaerke2.setForeground(aktuellesLandRK.getBesitzer().getColorSpieler());
				landname2.setForeground(aktuellesLandRK.getBesitzer().getColorSpieler());
				landBesitzer2.setForeground(aktuellesLandRK.getBesitzer().getColorSpieler());
				landTruppenstaerke2.setText(""+ aktuellesLandRK.getTruppenstaerke());
				landname2.setText(aktuellesLandRK.getName());
				if(aktuellesLandRK.getBesitzer() != null)
				{
					landBesitzer2.setText(aktuellesLandRK.getBesitzer().getName());
				} else { landBesitzer2.setText("kein Besitzer"); }
			}
			else
			{
				aktuellesLandId = landId;
				aktuellesLand = spiel.getLand(landId);
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

	// Getter und Setter
	
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
	public void handKartenAktualisieren() throws RemoteException
	{
		for(int i = 0 ; i < iihandkarten.length ; i++)
		{
			for(int j = 0 ; j < eigenerSpieler.getAnzahlHandkarten() ; j++)
			{
				if(spiel.getLand(i).equals(eigenerSpieler.spielerHandkarte(j)))
				{
					labelHandkarten[j].setIcon(iihandkarten[i]);
			
				}
				if(eigenerSpieler.spielerHandkarte(j) == null)
				{
					labelHandkarten[j].setIcon(null);
				}
			}
		}
	}
}

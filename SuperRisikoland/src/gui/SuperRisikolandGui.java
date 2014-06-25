package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import cui.Spielfeld;

public class SuperRisikolandGui extends JFrame
{
	
	public SuperRisikolandGui(int anzahlSpieler, int spielVariante, String[] spielername, Color[] spielerfarbe)
	{
		super();
		Spielfeld spiel = new Spielfeld(anzahlSpieler, spielVariante);
		this.spielername = new String[spielername.length];
		this.spielerfarbe = new Color[spielerfarbe.length];
		for (int i = 0; i < spielername.length; i++)
		{
			if(spielername[i] != null)
			{
				spiel.spielerErstellen(i, spielername[i], spielerfarbe[i]);
				this.spielername[i] = spielername[i];
				this.spielerfarbe[i] = spielerfarbe[i];
			}
		}
		this.screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.b = (int) screen.getWidth();
		this.h = (int) screen.getHeight();
		initialize();
	}
	
	// Variablen
	private Dimension screen;
	private int b;
	private int h;
	
	public JTextArea logText = new JTextArea();
	private JButton menu = new JButton("Menü");
	private int verbleibendeZeit = 30;
	private JLabel labelVerbleibendeZeit = new JLabel("verbleibende Zeit: " + verbleibendeZeit, SwingConstants.CENTER);
	final private JLabel[] labelArrayKontinente = {new JLabel("Nord-Amerika (5 Einheiten):", SwingConstants.RIGHT), new JLabel("Sï¿½d-Amerika (2 Einheiten):", SwingConstants.RIGHT), new JLabel("Europa (5 Einheiten):", SwingConstants.RIGHT), new JLabel("Afrika (3 Einheiten):", SwingConstants.RIGHT), new JLabel("Asien (7 Einheiten):", SwingConstants.RIGHT), new JLabel("Australien (2 Einheiten):", SwingConstants.RIGHT)};
	private JLabel[] labelArrayKontinenteBesitzer = {new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER), new JLabel("kein Besitzer", SwingConstants.CENTER)};
	
	private JPanel panelCharAktuellerSpieler;
	private JPanel panelEigenerChar;
	
	private String[] spielername;
	private Color[] spielerfarbe;
	
	private void initialize()
	{				
		this.setTitle("Super Risikoland");
		
		// Fenster wird bei "x" geschlossen
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		// Oben Menü
		final JPanel nord = new JPanel();
		
		final JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new GridLayout());
		panelMenu.setPreferredSize(new Dimension(this.b/100*10, this.h/100*8));
		panelMenu.setBorder(BorderFactory.createLineBorder(Color.yellow));
		panelMenu.add(this.menu);
		
		// Oben Kontinente + Timer
		final JPanel panelKontinenteTimer = new JPanel();
		panelKontinenteTimer.setLayout(new GridLayout());
		panelKontinenteTimer.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Kontinente 1
		
		final JPanel kontinente1 = new JPanel();
		kontinente1.setLayout(new GridLayout(3, 2));
		kontinente1.setPreferredSize(new Dimension(this.b/100*30, this.h/100*8));
		for(int i = 0; i < 3; i++)
		{
			this.labelArrayKontinente[i].setFont(new Font(null, Font.BOLD, 18));
			this.labelArrayKontinenteBesitzer[i].setFont(new Font(null, Font.BOLD, 22));
			kontinente1.add(this.labelArrayKontinente[i]);
			kontinente1.add(this.labelArrayKontinenteBesitzer[i]);
		}
		panelKontinenteTimer.add(kontinente1);
		// Timer
		this.labelVerbleibendeZeit.setFont(new Font(null, Font.BOLD, 24));
		this.labelVerbleibendeZeit.setPreferredSize(new Dimension(this.b/100*30, this.h/100*8));
		panelKontinenteTimer.add(labelVerbleibendeZeit);
		// Kontinente 2
		final JPanel kontinente2 = new JPanel();
		kontinente2.setLayout(new GridLayout(3, 2));
		kontinente2.setPreferredSize(new Dimension(this.b/100*30, this.h/100*8));
		for(int i = 3; i < 6; i++)
		{
			this.labelArrayKontinente[i].setAlignmentX(RIGHT_ALIGNMENT);
			this.labelArrayKontinente[i].setFont(new Font(null, Font.BOLD, 18));
			this.labelArrayKontinenteBesitzer[i].setFont(new Font(null, Font.BOLD, 22));
			kontinente2.add(this.labelArrayKontinente[i]);
			kontinente2.add(this.labelArrayKontinenteBesitzer[i]);
		}
		panelKontinenteTimer.add(kontinente2);
		// panelMenu und panelKontinenteTimer zum Gesamt Nord Panel hinzufügen
		nord.add(panelMenu);
		nord.add(panelKontinenteTimer);
		
		// Mitte - Karte
		final JPanel mitte = new JPanel()
		{
			public void paint(final Graphics g) 
			{
				super.paint(g);
		        final Toolkit tk = this.getToolkit();
		        g.drawImage(tk.getImage("res/karteFarbcodes.png"), 0, 0, b, h/100*76, this);
		    }
		};
		
		// Unten - Spielinfos
		final JPanel sued = new JPanel();
		sued.setLayout(new GridBagLayout());
		//aktuellerSpieler Char
		this.panelCharAktuellerSpieler = new JPanel(){
			public void paint(final Graphics g) 
			{
				super.paint(g);
		        final Toolkit tk = this.getToolkit();
		        // Breite des aktuellenChars an Bildschirm anpassen
		        double size = b/100*9.5;
		        g.drawImage(tk.getImage("res/charakter/mario.png"), 0, 0, (int)size, h/100*16, this);
		    }
		};
        double bAktuellerChar = this.b/100*9.5;
		this.panelCharAktuellerSpieler.setPreferredSize(new Dimension((int)bAktuellerChar, this.h/100*16));
		sued.add(this.panelCharAktuellerSpieler);
		// Textarea für Log
		logText.setEditable(false);
		// Log
		JScrollPane log = new JScrollPane(logText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        double bLog = this.b/100*54.5;
		log.setPreferredSize(new Dimension((int)bLog, this.h/100*16));
		sued.add(log);
		//Karten
		JPanel karten = new JPanel();
		karten.setLayout(new GridLayout());
		double bKarten = b/100*26.5;
		karten.setPreferredSize(new Dimension((int)bKarten, this.h/100*16));
		sued.add(karten);
		//eigener Char
		this.panelEigenerChar = new JPanel(){
			public void paint(final Graphics g) 
			{
				super.paint(g);
		        final Toolkit tk = this.getToolkit();
		        double size = b/100*9.5;
		        g.drawImage(tk.getImage("res/charakter/wario.png"), 0, 0, (int)size, h/100*16,this);
		    }
		};
        double bEigenerChar = this.b/100*9.5;
		this.panelEigenerChar.setPreferredSize(new Dimension((int)bEigenerChar, this.h/100*16));
		sued.add(this.panelEigenerChar);
		
		// Ausrichtung der Panels
		this.add(nord, BorderLayout.NORTH);
		this.add(mitte, BorderLayout.CENTER);
		this.add(sued, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public static void main(String[] args)
	{
		/*try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		// GUI starten
		
		new Spielstart();
		//new SuperRisikolandGui(0, 0, args, null);
		
		// GUI ENDE
	}
}

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cui.Mission;
import cui.Spieler;
import cui.Spielfeld;

public class NeuesSpiel extends JFrame implements ActionListener
{
	
	// Variablen
	private Vector<JTextField> textFieldSpieler = new Vector<JTextField>();
	private Vector<JPanel> panelSpieler = new Vector<JPanel>();
	private Vector<JButton> buttonSpielerHinzufuegen = new Vector<JButton>();
	private JLabel labelCharakter1, labelCharakter2, labelCharakter3, labelCharakter4, labelCharakter5, labelCharakter6;
	private JButton buttonSpielStarten, buttonZurueck;
	private int anzahlSpieler;
	private JRadioButton radioButtonWelteroberung, radioButtonMissionen;
	
	Color colorDaisy = new Color(250,111,43);
	Color colorLuigi = new Color(22,169,14);
	Color colorMario = new Color(249,15,46);
	Color colorPeach = new Color(251,175,221);
	Color colorWaluigi = new Color(124,83,188);
	Color colorWario = new Color(255,239,0);
	
	private Color[] colorChars = {this.colorDaisy, this.colorLuigi, this.colorMario, this.colorPeach, this.colorWaluigi, this.colorWario};
	private String[] color = {"Orange","Gruen","Rot","Pink","Lila","Gelb"};
	private String[] spielernamen = new String[6];
	
	public NeuesSpiel()
	{
		super();
		try
		{
			initialize();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void initialize() throws IOException
	{
		this.setSize(600, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("neues Spiel");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		// Panel Spielerliste
		final JPanel spielerListe = new JPanel(new GridLayout(3, 4));
		
		// Panel für TextField und Button
		for(int i = 0; i < 6; i++)
		{
			JPanel panel = new JPanel(new FlowLayout());
			this.panelSpieler.add(panel);
		}
		
		// Spieler hinzufügen einbetten
		for(int i = 0; i < 6; i++)
		{
			// Textfield erstellen
			final JTextField spieler = new JTextField("SPIELERNAME");
			spieler.addActionListener(this);
			
			spieler.setPreferredSize(new Dimension(90, 20));
			this.textFieldSpieler.add(spieler);
			// erstellenButton erstellen
			final JButton button = new JButton("Add");
			this.buttonSpielerHinzufuegen.add(button);
			button.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					actionSpielerHinzufuegen(spieler, button);
				}
			});
			button.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e) {
	                int key = e.getKeyCode();
	                if (key == KeyEvent.VK_ENTER) {         
	                	actionSpielerHinzufuegen(spieler, button);
	                }
	            }
			}
			);
			spieler.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e) 
				{
	                int key = e.getKeyCode();
	                if (key == KeyEvent.VK_ENTER) 
	                {	        
	                	actionSpielerHinzufuegen(spieler, button);
	                }
	            }
			}
			);
			button.setPreferredSize(new Dimension(90, 20));
		}
		// "Spieler hinzufügen" in Panel schreiben
		for(int i = 0; i < 6; i++)
		{
			this.panelSpieler.elementAt(i).add(this.textFieldSpieler.elementAt(i));
			this.panelSpieler.elementAt(i).add(this.buttonSpielerHinzufuegen.elementAt(i));
		}

		this.bilderEinlesen();
		spielerListe.add(labelCharakter1);
		spielerListe.add(new JLabel("Daisy"));
		spielerListe.add(this.panelSpieler.elementAt(0));
		spielerListe.add(labelCharakter2);
		spielerListe.add(new JLabel("Luigi"));
		spielerListe.add(this.panelSpieler.elementAt(1));
		spielerListe.add(labelCharakter3);
		spielerListe.add(new JLabel("Mario"));
		spielerListe.add(this.panelSpieler.elementAt(2));
		spielerListe.add(labelCharakter4);
		spielerListe.add(new JLabel("Peach"));
		spielerListe.add(this.panelSpieler.elementAt(3));
		spielerListe.add(labelCharakter5);
		spielerListe.add(new JLabel("Waluigi"));
		spielerListe.add(this.panelSpieler.elementAt(4));
		spielerListe.add(labelCharakter6);
		spielerListe.add(new JLabel("Wario"));
		spielerListe.add(this.panelSpieler.elementAt(5));
		
		// Starten/Zurueck zum Menu/SpielVariante
		final JPanel menu = new JPanel(new GridLayout(3, 1));

		// Spielvariante
		this.radioButtonWelteroberung = new JRadioButton("Welteroberung");
		this.radioButtonMissionen = new JRadioButton("Missionen");
		this.radioButtonMissionen.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.radioButtonMissionen);
		bg.add(this.radioButtonWelteroberung);
		final JPanel spielVariante = new JPanel(new GridLayout(1, 2));
		spielVariante.add(this.radioButtonWelteroberung);
		spielVariante.add(this.radioButtonMissionen);		
		
		this.buttonSpielStarten = new JButton("Spiel starten");
		this.buttonSpielStarten.setEnabled(false);
		this.buttonSpielStarten.addActionListener(this);
		this.buttonSpielStarten.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {         
                	actionSpielStarten();
                }
            }
		}
		);
		this.buttonZurueck = new JButton("Zurück zum Menü");
		this.buttonZurueck.addActionListener(this);
		this.buttonZurueck.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {         
                	actionButtonZurueck();
                }
            }
		}
		);

		menu.add(spielVariante);
		menu.add(this.buttonSpielStarten);
		menu.add(this.buttonZurueck);
		
		this.add(spielerListe, BorderLayout.CENTER);
		this.add(menu, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		this.setVisible(true);
	}
	
	private void bilderEinlesen()
	{
		try
		{
			// Bilder einlesen
			Image imgDaisy = ImageIO.read(new File("res/charakterIcons/DaisyIcon.png"));
			Image imgLuigi = ImageIO.read(new File("res/charakterIcons/LuigiIcon.png"));
			Image imgMario = ImageIO.read(new File("res/charakterIcons/MarioIcon.png"));
			Image imgPeach = ImageIO.read(new File("res/charakterIcons/PeachIcon.png"));
			Image imgWaluigi = ImageIO.read(new File("res/charakterIcons/WaluigiIcon.png"));
			Image imgWario = ImageIO.read(new File("res/charakterIcons/WarioIcon.png"));
			// Icons einlesen
			ImageIcon daisyIcon = new ImageIcon(imgDaisy);
			ImageIcon luigiIcon = new ImageIcon(imgLuigi);
			ImageIcon marioIcon = new ImageIcon(imgMario);
			ImageIcon peachIcon = new ImageIcon(imgPeach);
			ImageIcon waluigiIcon = new ImageIcon(imgWaluigi);
			ImageIcon warioIcon = new ImageIcon(imgWario);
			// Label Icons erstellen
			labelCharakter1 = new JLabel(daisyIcon);
			labelCharakter2 = new JLabel(luigiIcon);
			labelCharakter3 = new JLabel(marioIcon);
			labelCharakter4 = new JLabel(peachIcon);
			labelCharakter5 = new JLabel(waluigiIcon);
			labelCharakter6 = new JLabel(warioIcon);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.buttonSpielStarten))
		{
			actionSpielStarten();
		}
		
		if (e.getSource().equals(this.buttonZurueck))
		{
			actionButtonZurueck();
		}
	}

	private void actionSpielStarten()
	{		
		// Spielvariante wird ermittelt
		int spielVariante = 2;
		if(this.radioButtonMissionen.isSelected())
		{
			spielVariante = 1;
		}
		SuperRisikolandGui.logText = ""; // Logtext wird bei neuem Spiel gelöscht
		// Spielfeld wird erstellt
		Spielfeld spiel = new Spielfeld(this.anzahlSpieler, spielVariante);
		for (int i = 0; i < this.spielernamen.length; i++)
		{
			if(this.spielernamen[i] != null)
			{
				spiel.spielerErstellen(i, this.spielernamen[i], this.color[i], this.colorChars[i]);
			}
		}
		// Startländer werden verteilt
		spiel.startLaenderVerteilen();
		// Missionen werden erstellt
		if(spielVariante == 1)
		{
			new Mission().missionenErstellen(spiel); // Missionen werden erstellt und im Spielerobjekt abgespeichert
		}
		// aktuellerSpieler wird Random festgelegt
		int rndZahl = (int) (Math.random()*this.anzahlSpieler); // Randomzahl zwischen 0 und der Spieleranzahl
		Spieler aktuellerSpieler = spiel.getSpieler(rndZahl);
		// Gui wird aufgerufen
		new SuperRisikolandGui(spiel, aktuellerSpieler, false);
		dispose();
	}
	
	private void actionButtonZurueck()
	{
		new Spielstart();
		dispose();
	}
	
	private void actionSpielerHinzufuegen(JTextField textField, JButton button)
	{
		if(button.getText() == "Ändern")
		{
			for(int i=0; i < this.colorChars.length; i++)
			{
				if(this.buttonSpielerHinzufuegen.elementAt(i) == button)
				{
					this.spielernamen[0] = null;
				}
			}
			button.setBackground(null);
			textField.setEnabled(true);
			button.setText("Add");
			this.anzahlSpieler--;
			if(this.anzahlSpieler < 2)
			{
				this.buttonSpielStarten.setEnabled(false);
			}
		}
		else if (button.getText() == "Add" && !textField.getText().equals(""))
		{
			for(int i=0; i < this.colorChars.length; i++)
			{
				if(this.buttonSpielerHinzufuegen.elementAt(i) == button)
				{
					this.spielernamen[i] = textField.getText();
					button.setBackground(this.colorChars[i]);
				}
			}
			textField.setEnabled(false);
			button.setText("Ändern");
			this.anzahlSpieler++;
			if(this.anzahlSpieler >= 2)
			{
				this.buttonSpielStarten.setEnabled(true);
			}
		}
		else
		{
			//Popup, da kein Name eingegeben wurde
			// Variablen
            String name = "";
           
            // Panel für JDialog
            // verändert den Dialog zu Textfeld mit Okay button
            String[] options = {"OK"};
            JPanel panel = new JPanel();
            JLabel lbl = new JLabel("Spielername: ");
            JTextField txt = new JTextField(10);
            panel.add(lbl);
            panel.add(txt);
   
            // Dialog wiederholen bis vernünftiger Name angegeben wurde
            while( name.equals("")){
            	// JDialog mit entsprechendem panel starten
            	int selectedOption = JOptionPane.showOptionDialog(null, panel, "Bitte geben Sie einen Namen ein", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
            	
            	// wenn okay gedrückt wurde
            	if(selectedOption == 0)
            	{
            		name = txt.getText();
            		if (!name.equals(""))
            		{
	            		for(int i=0; i < this.colorChars.length; i++)
						{
							if(this.buttonSpielerHinzufuegen.elementAt(i) == button)
							{
								button.setBackground(this.colorChars[i]);
							}
						}
						textField.setText(name);
						textField.setEnabled(false);
						button.setText("Ändern");
						this.anzahlSpieler++;
            		}
            	}
            }
		}
	}	
}

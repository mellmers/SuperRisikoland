package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cui.Spieler;
import cui.Spielfeld;

public class Spielstart extends JFrame implements ActionListener
{
	// Variablen
	
	private JButton buttonNeuesSpiel = new JButton("neues Spiel");
	private JButton buttonSpielLaden = new JButton("Spiel laden");
	private JFrame altesFrame = null;
		
	public Spielstart()
	{
		super();
		initialize();
	}
	
	public Spielstart(JFrame f)
	{
		super();
		initialize();
		this.altesFrame = f;
	}
	
	public void initialize()
	{
		this.setSize(403, 512);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Spielstart");
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		final JPanel titel = new JPanel()
		{
			public void paint(final Graphics g) 
			{
				super.paint(g);
		        final Toolkit tk = this.getToolkit();
		        g.drawImage(tk.getImage("res/risiko.jpg"), 0, 0, this);
		    }
		};
		Dimension dimtitel = new Dimension(403, 432);
		titel.setPreferredSize(dimtitel);
		titel.setToolTipText("'SuperRisikoland' von Julius Hetzel, Moritz Ellmers und Kervin Zeller");

		final JPanel buttons = new JPanel(new GridLayout(2, 1));
		this.buttonNeuesSpiel.addActionListener(this);
		this.buttonNeuesSpiel.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) 
			{
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {         
                	actionNeuesSpiel();
                }
            }
		}
		);
		this.buttonSpielLaden.addActionListener(this);
		this.buttonSpielLaden.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e)
			{
				int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) {         
                	spielLaden();
                }
			}
		});

		buttons.add(this.buttonNeuesSpiel);
		buttons.add(this.buttonSpielLaden);
		
		this.add(titel, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		this.setVisible(true);
	}
	
	

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(this.buttonNeuesSpiel)) 
		{ 
			this.actionNeuesSpiel();
		}
		if (e.getSource().equals(this.buttonSpielLaden))
		{
			this.spielLaden();
		}
		
	}
	
	private void actionNeuesSpiel()
	{
		new NeuesSpiel();
		dispose();
		if(this.altesFrame != null)
		{
			this.altesFrame.dispose();
		}
	}
	
	private void spielLaden()
	{
		// LadenDialog erstellen
		// Erstellung eines FileFilters für Spielstände	
        FileFilter filter = new FileNameExtensionFilter("Risiko-Spielstände", "ser");         
        JFileChooser laden = new JFileChooser(new File(System.getProperty("user.home")));
        // Filter wird dem JFileChooser hinzugefügt
        laden.addChoosableFileFilter(filter);
        // Nur Dateien auswählbar
        laden.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = laden.showDialog(null, "Spielstand laden");
        
        // Abfrage, ob auf "Öffnen" geklickt wurde
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
	    	File spielstand = laden.getSelectedFile();
	    	String spielstandPfad = spielstand.getPath().endsWith(".ser") ? spielstand.getPath() : spielstand.getPath() + ".ser" ;
	    	try(final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(spielstandPfad)))
	    	{
	    		// Spielfeld laden
	    		Spielfeld spiel = (Spielfeld) ois.readObject();
	    		// aktuellen Spieler laden
	    		Spieler aktuellerSpieler = (Spieler) ois.readObject();
	    		// Überprüfung und Ausgabe der ausgewählten Datei
	    		SuperRisikolandGui.logText = "Spielstand " + laden.getSelectedFile().getName() +" wurde erfolgreich geladen!";
	    		// Gui erstellen		
	    		new SuperRisikolandGui(spiel, aktuellerSpieler, true);
	    		if(this.altesFrame != null)
	    		{
	    			this.altesFrame.dispose();
	    		}
	    		this.dispose();
	    	} catch (FileNotFoundException e1)
			{
				SuperRisikolandGui.logText += "\n" + e1.getMessage();
			} catch (IOException e1)
			{
				SuperRisikolandGui.logText += "\n" + e1.getMessage();
			} catch (ClassNotFoundException e1)
			{
				SuperRisikolandGui.logText += "\n" + e1.getMessage();
			}
	    	// Logtext in TextArea schreiben
	        SuperRisikolandGui.logTextArea.setText(SuperRisikolandGui.logText);
		}
	}
}
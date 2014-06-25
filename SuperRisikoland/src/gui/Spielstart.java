package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Spielstart extends JFrame implements ActionListener
{
	public Spielstart()
	{
		super();
		initialize();
	}
	
	// Variablen
	
	private JButton buttonNeuesSpiel = new JButton("neues Spiel");
	private JButton buttonSpielLaden = new JButton("Spiel laden");
	
	public void initialize()
	{
		this.setSize(403, 512);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Spielstart");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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

		final JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2, 1));
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
                	actionSpielLaden();
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
			this.actionSpielLaden();
		}
		
	}
	
	private void actionNeuesSpiel()
	{
		new NeuesSpiel();
		dispose();
	}
	private void actionSpielLaden()
	{
		// TODO Julius: Hier ist Platz f�r FileDialog und danach SuperRisikolandGui �ffnen und Daten einlesen
		
		// Erstellung eines FileFilters f�r Spielst�nde	
        FileFilter filter = new FileNameExtensionFilter("Risiko-Spielst�nde", "csv");         
        JFileChooser laden = new JFileChooser(new File(System.getProperty("user.home")));
        // Filter wird dem JFileChooser hinzugef�gt
        laden.addChoosableFileFilter(filter);
        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = laden.showDialog(null, "Spielstand laden");
        
        // Abfrage, ob auf "�ffnen" geklickt wurde
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
             // Ausgabe der ausgewaehlten Datei
            System.out.println("Die zu �ffnende Datei ist: " + laden.getSelectedFile().getName());
            if(laden.getSelectedFile().getAbsolutePath().endsWith(".csv"))
            {
            	 System.out.println("Erfolg!");
            }
        }
	}
}
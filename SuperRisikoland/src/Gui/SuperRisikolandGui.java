package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SuperRisikolandGui extends JFrame
{
	
	public SuperRisikolandGui()
	{
		super();
		initialize();
		
	}
	
	// Variablen
	
	public JTextArea logText;
	
	private void initialize()
	{
		this.setSize(1920, 1080);
		this.setTitle("Super Risikoland");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		
		// Oben Menü + Kontinente + Timer
		final JPanel nord = new JPanel();
		nord.setLayout(new GridBagLayout());
		Dimension dimNord = new Dimension(1920, 100);
		nord.setPreferredSize(dimNord);
		nord.add(new JButton("Menü"));
		nord.add(new JButton("Länder1"));
		// Spielzeit
		JLabel rundenzeit = new JLabel("Rundenzeit");
		nord.add(rundenzeit);
		nord.add(new JButton("Länder2"));
		
		// Mitte - Karte
		final JPanel mitte = new JPanel();
		mitte.setLayout(new GridLayout());
		mitte.add(new JButton("Button5"));
		
		// Unten - Spielinfos
		final JPanel sued = new JPanel();
		sued.setLayout(new GridBagLayout());
		Dimension dimSued = new Dimension(1920, 200);
		sued.setPreferredSize(dimSued);
		sued.add(new JButton("Button6"));
		// Textarea für Log
		logText = new JTextArea();
		logText.setEditable(false);
		// Log
		JScrollPane log = new JScrollPane(logText, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Dimension dimLog = new Dimension(800, 200);
		log.setPreferredSize(dimLog);
		sued.add(log);
		//Karten
		JPanel karten = new JPanel();
		karten.setLayout(new GridBagLayout());
		Dimension dimKarten = new Dimension(650,200);
		karten.setPreferredSize(dimKarten);
		sued.add(karten);
		
		// Ausrichtung der Panels
		this.add(nord, BorderLayout.NORTH);
		this.add(mitte, BorderLayout.CENTER);
		this.add(sued, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		this.setVisible(true);
		this.pack();
	}
	
}

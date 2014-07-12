package client;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

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
import gui.Spielstart;
import gui.SuperRisikolandGui;
import inf.LoginInterface;
import inf.SpielBeitretenInterface;

public class SpielBeitreten extends JFrame implements SpielBeitretenInterface, Serializable, ActionListener
{
		// Variablen
		private String spielername;
		private JLabel[] labelSpielername = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};
		private JLabel[] labelCharakter = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};
		private JButton buttonAuswahlBestaetigen = new JButton("Auswahl bestaetigen");
		
		Color colorDaisy = new Color(250,111,43);
		Color colorLuigi = new Color(22,169,14);
		Color colorMario = new Color(249,15,46);
		Color colorPeach = new Color(251,175,221);
		Color colorWaluigi = new Color(124,83,188);
		Color colorWario = new Color(255,239,0);
		
		private Color[] colorChars = {this.colorDaisy, this.colorLuigi, this.colorMario, this.colorPeach, this.colorWaluigi, this.colorWario};
		private String[] color = {"Orange","Gruen","Rot","Pink","Lila","Gelb"};
		
		Spieler spieler;
		int spielerId;
		
		LoginInterface server;
		
		public SpielBeitreten(LoginInterface server, String name, int port, String servername)
		{
			super();
			this.spielername = name;
			this.server = server;
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
			this.setSize(600, 300);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setTitle("Spiel beitreten");
			
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			this.setLayout(new BorderLayout());
			
			// Panel Spielerliste
			JPanel liste = new JPanel(new GridLayout(3, 4));
			JPanel[] charakterSpieler = {new JPanel(new GridLayout(2,1)),new JPanel(new GridLayout(2,1)),new JPanel(new GridLayout(2,1)),new JPanel(new GridLayout(2,1)),new JPanel(new GridLayout(2,1)),new JPanel(new GridLayout(2,1))};
			charakterSpieler[0].add(new JLabel("Charakter: Daisy"));
			charakterSpieler[0].add(labelSpielername[0]);
			charakterSpieler[1].add(new JLabel("Charakter: Luigi"));
			charakterSpieler[1].add(labelSpielername[1]);
			charakterSpieler[2].add(new JLabel("Charakter: Mario"));
			charakterSpieler[2].add(labelSpielername[2]);
			charakterSpieler[3].add(new JLabel("Charakter: Peach"));
			charakterSpieler[3].add(labelSpielername[3]);
			charakterSpieler[4].add(new JLabel("Charakter: Waluigi"));
			charakterSpieler[4].add(labelSpielername[4]);
			charakterSpieler[5].add(new JLabel("Charakter: Wario"));
			charakterSpieler[5].add(labelSpielername[5]);
			this.bilderEinlesen();
			liste.add(labelCharakter[0]);
			liste.add(charakterSpieler[0]);
			liste.add(labelCharakter[1]);
			liste.add(charakterSpieler[1]);
			liste.add(labelCharakter[2]);
			liste.add(charakterSpieler[2]);
			liste.add(labelCharakter[3]);
			liste.add(charakterSpieler[3]);
			liste.add(labelCharakter[4]);
			liste.add(charakterSpieler[4]);
			liste.add(labelCharakter[5]);
			liste.add(charakterSpieler[5]);
			
			for (int i = 0; i < labelCharakter.length; i++)
			{
				labelCharakter[i].addMouseListener(new MouseAdapter()
				{
					public void mouseClicked(MouseEvent e)  
				    {
						for (int j = 0; j < labelCharakter.length; j++)
						{
							if(e.getSource() == labelCharakter[j])
							{
								for (int k = 0; k < labelCharakter.length; k++)
								{
									labelCharakter[k].setEnabled(true);
									labelSpielername[k].setText("");
								}
								//labelCharakter[j].setEnabled(false);
								spielerId = j;
								labelSpielername[j].setText("Spieler: " + spielername);
								buttonAuswahlBestaetigen.setEnabled(true);
							}
						}
						
				    }
				});
			}
			
			// Button Auswahl bestaetigen
			final JPanel panelAuswahlBestaetigen = new JPanel(new FlowLayout(FlowLayout.CENTER));
			this.buttonAuswahlBestaetigen.setEnabled(false);
			this.buttonAuswahlBestaetigen.addActionListener(this);
			this.buttonAuswahlBestaetigen.addKeyListener(new KeyAdapter()
			{
				public void keyPressed(KeyEvent e) {
	                int key = e.getKeyCode();
	                if (key == KeyEvent.VK_ENTER) 
	                {
	                	actionAuswahlBestaetigen();
	                }
	            }
			}
			);
			
			panelAuswahlBestaetigen.add(this.buttonAuswahlBestaetigen);
			
			this.add(liste, BorderLayout.CENTER);
			this.add(panelAuswahlBestaetigen, BorderLayout.SOUTH);
			
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
				labelCharakter[0] = new JLabel(daisyIcon);
				labelCharakter[1] = new JLabel(luigiIcon);
				labelCharakter[2] = new JLabel(marioIcon);
				labelCharakter[3] = new JLabel(peachIcon);
				labelCharakter[4] = new JLabel(waluigiIcon);
				labelCharakter[5] = new JLabel(warioIcon);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource().equals(this.buttonAuswahlBestaetigen))
			{
				actionAuswahlBestaetigen();
			}
		}
		
		private void actionAuswahlBestaetigen()
		{
			this.spieler = new Spieler(spielerId, spielername, color[spielerId], colorChars[spielerId]);
			server.addSpieler(this.spieler);
		}
}
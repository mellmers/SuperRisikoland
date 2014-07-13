package client;

import inf.ServerInterface;
import inf.RemoteInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import cui.Spieler;
import exc.MaximaleSpielerZahlErreichtException;
import gui.Spielstart;
import gui.SuperRisikolandGui;

public class SpielBeitreten extends JFrame implements ActionListener, Serializable
{
	ServerInterface server;
	
	private JButton buttonVerbinden= new JButton("Verbinden");
	private JTextField textfieldName = new JTextField(), textfieldServer = new JTextField();
	private JSpinner port = new JSpinner(new SpinnerNumberModel(9999, 1000, 9999, 1));
	
	// zweites Frame
	// Variablen
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
	
	public SpielBeitreten()
	{
		super();
		initialize();
	}
	
	public void initialize(){
		this.setTitle("Verbindung zum Risikoserver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350,150);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel(new GridLayout(3,2));

		JLabel labelName = new JLabel("Dein Name: ", SwingConstants.RIGHT);
		panel.add(labelName);
		panel.add(textfieldName);
		JLabel labelPort = new JLabel("Portnummer: ", SwingConstants.RIGHT);
		labelPort.setToolTipText("");
		panel.add(labelPort);
		panel.add(port);
		JLabel labelServer = new JLabel("Servername: ", SwingConstants.RIGHT);
		labelServer.setToolTipText("");
		panel.add(labelServer);
		panel.add(textfieldServer);
		
		JPanel panelVerbindung = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonVerbinden.addActionListener(this);
		panelVerbindung.add(buttonVerbinden);
		
		this.add(panel, BorderLayout.CENTER);
		this.add(panelVerbindung, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	public void initializeSpielBeitreten() throws IOException
	{
		JFrame spielBeitreten = new JFrame();
		spielBeitreten.setSize(600, 300);
		spielBeitreten.setResizable(false);
		spielBeitreten.setLocationRelativeTo(null);
		spielBeitreten.setTitle("Spiel beitreten");
		
		spielBeitreten.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		spielBeitreten.setLayout(new BorderLayout());
		
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
							labelSpielername[j].setText("Spieler: " + textfieldName.getText().trim());
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
                	try {
						actionAuswahlBestaetigen();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
		}
		);
		
		panelAuswahlBestaetigen.add(this.buttonAuswahlBestaetigen);
		
		spielBeitreten.add(liste, BorderLayout.CENTER);
		spielBeitreten.add(panelAuswahlBestaetigen, BorderLayout.SOUTH);
		
		// Fenster anzeigen
		spielBeitreten.setVisible(true);
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
	
	
	private void actionAuswahlBestaetigen() throws RemoteException
	{
		this.spieler = new Spieler(spielerId, textfieldName.getText().trim(), color[spielerId], colorChars[spielerId]);
		server.addSpieler(this.spieler);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.buttonAuswahlBestaetigen))
		{
			try {
				actionAuswahlBestaetigen();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource().equals(this.buttonVerbinden)) 
		{ 
			if(this.textfieldName.getText().trim().equals(""))
			{
				textfieldLeerAbfrage("Dein Name: ", this.textfieldName);
			}
			else if(this.textfieldServer.getText().trim().equals(""))
			{
				textfieldLeerAbfrage("Servername: ", this.textfieldServer);
			}
			else
			{
				try {
					verbindungStarten();
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MaximaleSpielerZahlErreichtException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	public void textfieldLeerAbfrage(String labelText, JTextField tf)
	{
		// Popup, da kein Name eingegeben wurde
        // Panel f�r JDialog
        // ver�ndert den Dialog zu Textfeld mit Okay button
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel lbl = new JLabel(labelText);
        JTextField txt = new JTextField(10);
        panel.add(lbl);
        panel.add(txt);

        // Dialog wiederholen bis vern�nftiger Name angegeben wurde
        while(txt.getText().trim().equals("")){
        	// JDialog mit entsprechendem panel starten
        	int selectedOption = JOptionPane.showOptionDialog(null, panel, "Textfeld darf nicht leer sein!", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
        	
        	// wenn okay gedr�ckt wurde
        	if(selectedOption == 0)
        	{
        		tf.setText(txt.getText().trim());
        	}
        }
	}
	
	public void verbindungStarten() throws NotBoundException, MaximaleSpielerZahlErreichtException, IOException
	{
		//SERVER
		Registry registry = LocateRegistry.getRegistry("localhost",(int)port.getValue());
		server = (ServerInterface) registry.lookup(this.textfieldServer.getText().trim());
		//CLIENT
		Client client = new Client(server, this.textfieldName.getText().trim(),(int) this.port.getValue(), this.textfieldServer.getText().trim());
		registry.rebind(textfieldName.getText(), client);
		server.addClient(textfieldName.getText(),(int)port.getValue());
		this.dispose();
		initializeSpielBeitreten();	
	}
	
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException, MaximaleSpielerZahlErreichtException{
		//Client wird gestartet
		new SpielBeitreten();
	}
}

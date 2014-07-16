package server;
import inf.ServerInterface;
import inf.SpielerInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cui.Spieler;
import cui.Spielfeld;


public class Login extends JFrame implements ActionListener{
	
	final JSpinner spinnerPort = new JSpinner(new SpinnerNumberModel(9999, 1000, 9999, 1));
	final JTextField textfieldServername = new JTextField();
	private JButton buttonServerErstellen = new JButton("Erstellen");
	private JFrame neuLaden;
	
	//zweites Fenster
	JPanel panelSpielernamen = new JPanel(new GridLayout(7,1));
	private JButton buttonSpielStarten = new JButton("Spiel starten");
	private JButton buttonNeuesSpiel = new JButton("neues Spiel");
	private JButton buttonSpielLaden = new JButton("Spiel laden");
	private JButton buttonGeladenesSpielStarten = new JButton("Spiel laden");
	private JRadioButton radioButtonWelteroberung = new JRadioButton("Welteroberung"), radioButtonMissionen = new JRadioButton("Missionen");
	ServerInterface server;
	private JLabel[] labelMitspieler = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};
	
	public Login()
	{
		super();
		initialize();	
	}
	
	public void initialize()
	{
		this.setTitle("Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350,100);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(2,2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel labelPort = new JLabel("Port:");
		JLabel labelServername = new JLabel("Servername:");

		textfieldServername.setText("Servername");
		textfieldServername.setPreferredSize(new Dimension(120, 30));
		spinnerPort.setPreferredSize(new Dimension(120, 30));
		buttonServerErstellen.addActionListener(this);
		buttonServerErstellen.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ENTER) 
                {
                	actionServerErstellen();
                }
			}
		});

		panel.add(labelPort);
		panel.add(spinnerPort);
		panel.add(labelServername);
		panel.add(textfieldServername);
		buttonPanel.add(buttonServerErstellen);
		this.add(panelSpielernamen, BorderLayout.NORTH);
		this.add(panel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);	
	}
	public void initializeNeuOderLaden()
	{
		neuLaden = new JFrame();
		neuLaden.setTitle(textfieldServername.getText().trim());
		neuLaden.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		neuLaden.setSize(120,90);
		neuLaden.setResizable(false);
		neuLaden.setLocationRelativeTo(null);
		neuLaden.setLayout(new FlowLayout());
		JPanel panelButtons = new JPanel(new GridLayout(2,1));
		this.buttonNeuesSpiel.addActionListener(this);
		this.buttonSpielLaden.addActionListener(this);
		
		panelButtons.add(this.buttonNeuesSpiel);
		panelButtons.add(this.buttonSpielLaden);
		neuLaden.add(panelButtons);
		neuLaden.setVisible(true);
	}
	
	public void initializeLogin() throws RemoteException
	{
		JFrame login = new JFrame();
		login.setTitle(textfieldServername.getText().trim());
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setSize(350,210);
		login.setResizable(false);
		login.setLocationRelativeTo(null);
		login.setLayout(new BorderLayout());
		
		for (int i = 0; i < this.labelMitspieler.length; i++)
		{
			this.panelSpielernamen.add(this.labelMitspieler[i]);
		}
		
		JPanel panelButtons = new JPanel(new GridLayout(1,1));
		
		// Spielvariante
		this.radioButtonMissionen.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.radioButtonMissionen);
		bg.add(this.radioButtonWelteroberung);
		final JPanel spielVariante = new JPanel(new GridLayout(1, 2));
		spielVariante.add(this.radioButtonWelteroberung);
		spielVariante.add(this.radioButtonMissionen);
		if(server.getLaden())
		{
			this.buttonGeladenesSpielStarten.addActionListener(this);
			this.buttonGeladenesSpielStarten.setEnabled(false);
			panelButtons.add(buttonGeladenesSpielStarten);
		}
		else
		{
			this.buttonSpielStarten.addActionListener(this);
			this.buttonSpielStarten.setEnabled(false);
			panelButtons.add(buttonSpielStarten);
		}
		
		login.add(spielVariante,BorderLayout.CENTER);
		login.add(panelButtons,BorderLayout.SOUTH);
		login.add(this.panelSpielernamen, BorderLayout.NORTH);
		login.setVisible(true);
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
	
	public void aktualisieren() throws RemoteException
	{
		for(int i = 0 ; i < server.getAlleClients(); i++)
		{
			if(server.getClient(i) != null && !this.labelMitspieler[i].equals(server.getClient(i).getSpielername()))
			{
				this.labelMitspieler[i].setText("Spieler " + server.getClient(i).getSpielername() + " beigetreten.");
			}
			for(int j = 0 ; j < server.getAlleSpielerAnzahl(); j++)
			{
				if(server.getClient(i).getSpielername().equals(server.getSpieler(j).getName()))	
				{
					this.labelMitspieler[i].setText("Spieler " + server.getClient(i).getSpielername() + " beigetreten und hat die Farbe " + server.getSpieler(j).getSpielerfarbe());
				}
			}
			if(server.getAlleSpielerAnzahl() >= 2)
			{
				this.buttonSpielStarten.setEnabled(true);
			}
		}

	
	}

	public void spielstart() throws RemoteException
	{
		int spielVariante = 0;
		if(this.radioButtonMissionen.isSelected())
		{
			spielVariante = 1;
		}
		if(this.radioButtonWelteroberung.isSelected())
		{
			spielVariante = 2;
		}
		server.spielBeginnen(spielVariante);
		for(int i = 0; i < server.getAlleClients(); i++)
		{
			server.getClient(i).neuesSpielStarten(this.getPassendenSpieler(i));
			server.setLogText("Spieler " + server.getSpieler(i).getName() + " mit der Farbe " + server.getSpieler(i).getSpielerfarbe() +" und mit SpielerID " + server.getSpieler(i).getSpielerID() + " wurde erstellt.");
		}
	}
	
	public SpielerInterface getPassendenSpieler(int i) throws RemoteException
	{
		for(int j = 0 ; j < server.getAlleSpielerAnzahl(); j++)
		{
			if(server.getClient(i).getSpielername().equals(server.getSpieler(j).getName()))
			{
				return server.getSpieler(j); 
			}
		}
		return null;
	}

	public void serverErstellen() throws RemoteException
	{
		server = new Server();
		Registry registry = LocateRegistry.createRegistry((int) spinnerPort.getValue());
		registry.rebind(textfieldServername.getText().trim(), server);
		System.out.println("Server gestartet.");
	}
	
	private void actionServerErstellen()
	{
		if(this.textfieldServername.getText().trim().equals(""))
		{
			//Popup, da kein Name eingegeben wurde
            // Panel f�r JDialog
            // ver�ndert den Dialog zu Textfeld mit Okay button
            String[] options = {"OK"};
            JPanel panel = new JPanel();
            JLabel lbl = new JLabel("Servername: ");
            JTextField txt = new JTextField(10);
            panel.add(lbl);
            panel.add(txt);
   
            // Dialog wiederholen bis vern�nftiger Name angegeben wurde
            while( txt.getText().trim().equals("")){
            	// JDialog mit entsprechendem panel starten
            	int selectedOption = JOptionPane.showOptionDialog(null, panel, "Feld darf nicht leer sein!", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
            	
            	// wenn okay gedr�ckt wurde
            	if(selectedOption == 0)
            	{
            		this.textfieldServername.setText(txt.getText().trim());
            	}
            }
		}
		else
		{
			try {
				serverErstellen();
				this.dispose();
				initializeNeuOderLaden();
			} catch (RemoteException e1) {
				e1.printStackTrace();
				System.out.println("Fehler");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.buttonServerErstellen)) 
		{
			actionServerErstellen();		
		}
		if(e.getSource().equals(this.buttonSpielStarten))
		{
			try {
				this.spielstart();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(this.buttonSpielLaden))
		{
			try {
				server.setLaden(true);
				spielLaden();
				initializeLogin();
				neuLaden.dispose();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource().equals(this.buttonNeuesSpiel))
		{
			try {
				server.setLaden(false);
				initializeLogin();
				neuLaden.dispose();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void spielLaden() throws RemoteException
	{
		// LadenDialog erstellen
		// Erstellung eines FileFilters f�r Spielst�nde	
        FileFilter filter = new FileNameExtensionFilter("Risiko-Spielst�nde", "ser");         
        JFileChooser laden = new JFileChooser(new File(System.getProperty("user.home")));
        // Filter wird dem JFileChooser hinzugef�gt
        laden.addChoosableFileFilter(filter);
        // Nur Dateien ausw�hlbar
        laden.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // Dialog zum Oeffnen von Dateien anzeigen
        int rueckgabeWert = laden.showDialog(null, "Spielstand laden");
        
        // Abfrage, ob auf "�ffnen" geklickt wurde
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
	    	File spielstand = laden.getSelectedFile();
	    	String spielstandPfad = spielstand.getPath().endsWith(".ser") ? spielstand.getPath() : spielstand.getPath() + ".ser" ;
	    	server.spielLaden(spielstandPfad);
	    	
	    	/*for(int i = 0; i < server.getAlleClients(); i++)
			{
				server.getClient(i).neuesSpielStarten(this.getPassendenSpieler(i));
			}*/
        }
	}
	
	public void spielerLaden()
	{
		//Vector<SpielerInterface> spieler = server.getAlleSpieler();
		
	}

	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException{
		new Login();
	}
}

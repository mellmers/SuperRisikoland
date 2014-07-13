package server;
import inf.RemoteInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cui.Spieler;
import cui.Spielfeld;
import exc.MaximaleSpielerZahlErreichtException;


public class Login extends JFrame implements ActionListener{
	
	final JSpinner spinnerPort = new JSpinner(new SpinnerNumberModel(9999, 1000, 9999, 1));
	final JTextField textfieldServername = new JTextField();
	private JButton buttonServerErstellen = new JButton("Erstellen");
	
	//zweites Fenster
	JPanel panelSpielernamen = new JPanel(new GridLayout(7,1));
	private JButton buttonNeuesSpiel = new JButton("Neues Spiel");
	private JButton buttonSpielLaden = new JButton("Spiel laden");
	private JRadioButton radioButtonWelteroberung = new JRadioButton("Welteroberung"), radioButtonMissionen = new JRadioButton("Missionen");
	Server server;
	
	public Login()
	{
		super();
		initialize();	
	}
	public void aktualisieren()
	{
		for(int i = 0 ; i < server.getAlleSpielerAnzahl(); i++)
		{
			if(server.getSpieler(i) != null)
			{
				JLabel labelSpieler = new JLabel(server.getSpieler(i).getName());
				this.panelSpielernamen.add(labelSpieler);
			}
		}

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
		
		
		
		panel.add(labelPort);
		panel.add(spinnerPort);
		panel.add(labelServername);
		panel.add(textfieldServername);
		buttonPanel.add(buttonServerErstellen);
		this.add(panel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public void initializeLogin()
	{
		JFrame login = new JFrame();
		login.setTitle(textfieldServername.getText().trim());
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setSize(350,210);
		login.setResizable(false);
		login.setLocationRelativeTo(null);
		login.setLayout(new BorderLayout());
		JPanel panelButtons = new JPanel(new GridLayout(2,1));
		
		// Spielvariante
		this.radioButtonMissionen.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.radioButtonMissionen);
		bg.add(this.radioButtonWelteroberung);
		final JPanel spielVariante = new JPanel(new GridLayout(1, 2));
		spielVariante.add(this.radioButtonWelteroberung);
		spielVariante.add(this.radioButtonMissionen);		
		
		panelButtons.add(buttonNeuesSpiel);
		panelButtons.add(buttonSpielLaden);
		
		login.add(spielVariante,BorderLayout.CENTER);
		login.add(panelButtons,BorderLayout.SOUTH);
		login.add(this.panelSpielernamen, BorderLayout.NORTH);
		login.setVisible(true);
		aktualisieren();
	}
	
	public void Spielstart()
	{
		
	}
	
	public void serverErstellen() throws RemoteException
	{
		server = new Server();
		//Spielfeld remote = new Spielfeld(2,1);
		Registry registry = LocateRegistry.createRegistry((int) spinnerPort.getValue());
		registry.rebind(textfieldServername.getText().trim(), server);
		System.out.println("Server is started.");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.buttonServerErstellen)) 
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
					initializeLogin();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("Fehler");
				}
			}		
		}
	}

	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException{
		new Login();
	}
}

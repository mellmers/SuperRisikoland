package client;

import inf.LoginInterface;
import inf.RemoteInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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

public class Client extends JFrame implements ActionListener, Serializable{
	static Spieler spieler = new Spieler(1,"Karl","blau",new Color(0,0,0));
	private JButton buttonVerbinden= new JButton("Verbinden");
	private JTextField textfieldName = new JTextField(), textfieldServer = new JTextField();
	private JSpinner port = new JSpinner(new SpinnerNumberModel(9999, 1000, 9999, 1));
	
	public Client()
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
	
	public void verbindungStarten() throws RemoteException, NotBoundException, MaximaleSpielerZahlErreichtException
	{
		//SERVER
		Registry registry = LocateRegistry.getRegistry("localhost",(int)port.getValue());
		LoginInterface server = (LoginInterface) registry.lookup(this.textfieldServer.getText().trim());
		//CLIENT
		SpielBeitreten client = new SpielBeitreten(this.textfieldName.getText().trim(),(int) this.port.getValue(), this.textfieldServer.getText().trim());
		registry.rebind(textfieldName.getText(), client);
		server.addClient(textfieldName.getText(),(int)port.getValue());
		
	}
	
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException, MaximaleSpielerZahlErreichtException{
		//Client wird gestartet
		new Client();
	}
}

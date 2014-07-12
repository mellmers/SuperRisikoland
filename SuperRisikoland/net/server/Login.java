package server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import cui.Spieler;
import client.SpielBeitreten;
import exc.MaximaleSpielerZahlErreichtException;
import inf.LoginInterface;
import inf.SpielBeitretenInterface;

public class Login extends JFrame implements LoginInterface, Serializable, ActionListener{
	
	private Vector<SpielBeitretenInterface> clients = new Vector<SpielBeitretenInterface>();
	private Vector<Spieler> spieler = new Vector<Spieler>();
	
	private String servername;
	JPanel panel = new JPanel(new GridLayout(7,1));
	private JButton buttonNeuesSpiel = new JButton("Neues Spiel");
	private JButton buttonSpielLaden = new JButton("Spiel laden");
	private JRadioButton radioButtonWelteroberung = new JRadioButton("Welteroberung"), radioButtonMissionen = new JRadioButton("Missionen");
	
	
	public Login(int port, String servername)
	{
		this.servername = servername;
		initialize();
	}
	
	public void initialize()
	{
		this.setTitle(this.servername);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350,210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
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
		
		this.add(spielVariante,BorderLayout.CENTER);
		this.add(panelButtons,BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.NORTH);
		this.setVisible(true);
	}

	public void spielStarten() {
		// TODO Auto-generated method stub
		
	}
	
	public void addSpieler(Spieler dieserSpieler)
	{
		this.spieler.add(dieserSpieler);
	}
	
	public void addClient(String name, int port) throws RemoteException, MaximaleSpielerZahlErreichtException, NotBoundException {
		if(this.clients.size() < 6)
		{
			/*Registry registry = LocateRegistry.getRegistry("localhost",port);
			// evtl Client individualisieren
			SpielBeitreten client = (SpielBeitreten) registry.lookup(name);
			this.spieler.add(client);*/
			JLabel spielerLabel = new JLabel(name, SwingConstants.CENTER);
			this.panel.add(spielerLabel);
			this.setVisible(true);
			
			System.out.println("Spieler: " + name + " erstellt");
			
		}
		else
		{
			throw new MaximaleSpielerZahlErreichtException();
			
		}
	}
	
	/*public static void main(String[] args) throws RemoteException, MaximaleSpielerZahlErreichtException, NotBoundException
	{
		Login l = new Login(9999, "Jochen");
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
		l.addClient("Paul",9999);
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}

package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cui.Spieler;
import exc.MaximaleSpielerZahlErreichtException;
import gui.SuperRisikolandGui;
import inf.ClientInterface;
import inf.RemoteInterface;

public class Client extends JFrame implements ClientInterface, ActionListener{
	static Spieler spieler = new Spieler(1,"Karl","blau",new Color(0,0,0));
	String name;
	JButton button;
	JTextField nameTxt;
	int port;
	public Client()
	{
		super();
		initialize();
		
	}
	
	public void initialize(){
		this.setTitle("Risiko");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,100);
		this.setLocationRelativeTo(null);
		
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		
		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setPreferredSize(new Dimension(200, 200));
		
		button = new JButton();
		button.setBackground(null);
		button.setText("Verbinden");
		button.addActionListener(this);
		panel.add(button);
		
		name = "";
		nameTxt = new JTextField();
		nameTxt.setPreferredSize(new Dimension(90, 20));
		panel.add(nameTxt);
		
		this.add(panel);
		
		this.setVisible(true);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.button)) 
		{ 
			name = nameTxt.getText();
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
	
	public void verbindungStarten() throws RemoteException, NotBoundException, MaximaleSpielerZahlErreichtException
	{
		//SERVER
		Registry registry = LocateRegistry.getRegistry("localhost",9999);
		RemoteInterface remote = (RemoteInterface) registry.lookup("RMI");
		//CLIENT
		SuperRisikolandGui srg = new SuperRisikolandGui(remote, spieler, true);
		registry.rebind(this.name, srg);
		remote.addClient(this.name);
	}
	
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException, MaximaleSpielerZahlErreichtException{
		//SERVER
		new Client();
		/*Registry registry = LocateRegistry.getRegistry("localhost",9999);
		RemoteInterface remote = (RemoteInterface) registry.lookup("RMI");
		//CLIENT
		SuperRisikolandGui srg = new SuperRisikolandGui(remote, spieler, true);
		registry.rebind("Karl", srg);
		///
		System.out.println("hallo");
		//remote.laenderEinlesen();
		remote.test();
		remote.addClient("Karl");
		System.out.println("hallo2");
		Color c =  new Color(0,0,0);
		//remote.spielerErstellen(0, "Bernd", "rot", c);
		 * 
		 */
		
		
	}

	

	
}

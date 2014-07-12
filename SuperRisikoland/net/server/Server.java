package server;
import inf.ClientInterface;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cui.Spielfeld;
import exc.MaximaleSpielerZahlErreichtException;


public class Server extends JFrame implements ActionListener{
	
	final JSpinner spinnerPort = new JSpinner(new SpinnerNumberModel(9999, 1000, 9999, 1));
	final JTextField textfieldServername = new JTextField();
	private JButton buttonServerErstellen = new JButton("Erstellen");
	
	public Server()
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
		
		
		
		panel.add(labelPort);
		panel.add(spinnerPort);
		panel.add(labelServername);
		panel.add(textfieldServername);
		buttonPanel.add(buttonServerErstellen);
		this.add(panel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public void Spielstart()
	{
		
	}
	
	public void serverErstellen() throws RemoteException
	{
		Spielfeld remote = new Spielfeld(2,1);
		Registry registry = LocateRegistry.createRegistry((int) spinnerPort.getValue());
		registry.rebind(textfieldServername.getText().trim(), remote);
		System.out.println("start is started");
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
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("Fehler");
				}
			}		
		}
	}

	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException{
		new Server();
	}
}

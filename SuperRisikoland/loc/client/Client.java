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
import java.rmi.server.UnicastRemoteObject;
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
import inf.ServerInterface;
import inf.ClientInterface;

public class Client extends UnicastRemoteObject implements ClientInterface, Serializable, ActionListener
{
	
	private String spielername;
	ServerInterface server;
	
	public Client(ServerInterface server, String name, int port, String servername) throws RemoteException
	{
		super();
		this.spielername = name;
		this.server = server;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	public String getSpielername() {
		return spielername;
	}	
}
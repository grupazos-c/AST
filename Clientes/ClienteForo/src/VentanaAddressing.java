import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class VentanaAddressing extends JFrame {

	/**
	 * Serial version que si no eclipse se enfada...
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAddressing frame = new VentanaAddressing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public VentanaAddressing() {
		/***********************************
		 * Frame principal
		 ***********************************/
		setBackground(new Color(102, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 15, 15));
		setMinimumSize(new Dimension(650, 250));
		setContentPane(contentPane);
		
		/***********************************
		 * Declaracion de panels
		 ***********************************/
		
		/*
		 * Panel de botones
		 */
		JPanel botonPanel = new JPanel();
		botonPanel.setBackground(Color.WHITE);
		botonPanel.setBorder(new EmptyBorder(10, 5, 5, 10));
		
		JButton enviarJB = new JButton("Enviar");
		JButton recibirJB = new JButton("recibir");
		
		GridLayout botonLayout = new GridLayout(1, 2, 25, 0);
		botonPanel.setLayout(botonLayout);
		
		botonPanel.add(enviarJB);
		botonPanel.add(recibirJB);
		
		/*
		 * Panel de post
		 */
		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new EmptyBorder(2, 2, 2, 2));
		panelCliente.setBackground(new Color(222, 222, 222));
		panelCliente.setLayout(new BoxLayout(panelCliente, BoxLayout.Y_AXIS));
		
		/*
		 * Panel de post
		 */
		JPanel panelPost = new JPanel();
		panelPost.setBorder(new EmptyBorder(2, 2, 2, 2));
		panelPost.setBackground(new Color(255, 255, 255));
		panelPost.setLayout(new BoxLayout(panelPost, BoxLayout.Y_AXIS));
		
		/***********************************
		 * GridBagLayout
		 ***********************************/
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		// Panel del botones
		contentPane.add(botonPanel);
		layout.putConstraint(SpringLayout.WEST, botonPanel, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, botonPanel, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, botonPanel, 5, SpringLayout.EAST, contentPane);
		// Panel de cliente
		contentPane.add(panelCliente);
		layout.putConstraint(SpringLayout.NORTH, panelCliente, 5, SpringLayout.NORTH, botonPanel);
		layout.putConstraint(SpringLayout.EAST, panelCliente, 5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, panelCliente, 5, SpringLayout.WEST, contentPane);
		// Panel de post
		contentPane.add(panelPost);
		layout.putConstraint(SpringLayout.NORTH, panelPost, 5, SpringLayout.NORTH, panelCliente);
		layout.putConstraint(SpringLayout.EAST, panelPost, 5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, panelPost, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, panelPost, 5, SpringLayout.SOUTH, contentPane);
		
		/***********************************
		 * Action Listener
		 ***********************************/
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();

				if (boton == enviarJB) {
					enviar();

				} else if (boton == recibirJB) {
					recibir();

				}
			}
		};
		enviarJB.addActionListener(al);
		recibirJB.addActionListener(al);
		
	}

	protected void recibir() {
		// TODO Auto-generated method stub
		
	}

	protected void enviar() {
		String post = JOptionPane.showInputDialog(null, "Introduzca el ID de Post");
		String destino = JOptionPane.showInputDialog(null, "Introduzca el ID de Post");
		
//		int respuesta = ClienteSOAP.leerPostWSAddressing(post, destino);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

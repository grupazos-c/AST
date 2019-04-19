

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.soap.SOAPException;

public class VentanaClienteForo extends JFrame {

	/**
	 * Serial version que si no eclipse se enfada....
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JPanel foroPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaClienteForo frame = new VentanaClienteForo();
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
	public VentanaClienteForo() {
		/***********************************
		 * Frame principal
		 ***********************************/
		setBackground(new Color(102, 153, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 688, 563);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 15, 15));
		setMinimumSize(new Dimension(650, 250));
		setContentPane(contentPane);

		/***********************************
		 * Declaracion de panels
		 ***********************************/
		/*
		 * Panel de Logo
		 */
		JPanel LogoPanel = new JPanel();
		LogoPanel.setBackground(Color.WHITE);
		LogoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		LogoPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		BufferedImage logo = null;
		BufferedImage lupa = null;
		BufferedImage refresh = null;
		try {
			logo = ImageIO.read(new File("VigoCoffeeLovers2.png"));
			lupa = ImageIO.read(new File("Lupa.png"));
			refresh = ImageIO.read(new File("Refresh.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(logo));
		picLabel.setBackground(Color.WHITE);
		LogoPanel.add(picLabel);

		/*
		 * Sobre el panel de sing in
		 */
		JPanel SingPanel = new JPanel();
		SingPanel.setBorder(new EmptyBorder(5, 5, 3, 5));
		SingPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		SingPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		SingPanel.setBackground(new Color(255, 255, 255));

		JLabel usernameJL = new JLabel("Nickname: ");
		JTextField usernameJT = new JTextField();
		JButton registrarJB = new JButton("Registrarse");
		JLabel pwdJL = new JLabel("Password: ");
		JPasswordField pwdJT = new JPasswordField();
		JButton npostJB = new JButton("Nuevo Post");

		GridLayout Singlayout = new GridLayout(3, 2, 1, 5);
		SingPanel.setLayout(Singlayout);

		SingPanel.add(usernameJL);
		SingPanel.add(usernameJT);
		SingPanel.add(pwdJL);
		SingPanel.add(pwdJT);
		SingPanel.add(registrarJB);
		SingPanel.add(npostJB);

		/*
		 * Sobre el panel de búsqueda
		 */
		JPanel buscarPanel = new JPanel();
		buscarPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		buscarPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		buscarPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buscarPanel.setBackground(new Color(255, 255, 255));

		JTextField buscarJT = new JTextField("Buscar...");
		JButton buscarJB = new JButton(new ImageIcon(lupa));
		JButton refreshJB = new JButton(new ImageIcon(refresh));
		JRadioButton bsucarJR1 = new JRadioButton("Por Tags", true);
		JRadioButton bsucarJR2 = new JRadioButton("Por Posts", false);
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(bsucarJR1);
		bgroup.add(bsucarJR2);

		GridBagLayout buscarLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		buscarPanel.setLayout(buscarLayout);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		buscarPanel.add(buscarJT, c);

		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 0;
		buscarPanel.add(buscarJB, c);

		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 1;
		buscarPanel.add(refreshJB, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		buscarPanel.add(bsucarJR1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		buscarPanel.add(bsucarJR2, c);

		/*
		 * Sobre el panel del Foro
		 */
		foroPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		foroPanel.setBackground(new Color(255, 255, 255));
		foroPanel.setLayout(new BoxLayout(foroPanel, BoxLayout.Y_AXIS));
		foroPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(foroPanel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/***********************************
		 * GridBagLayout
		 ***********************************/
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		// Panel del logo
		contentPane.add(LogoPanel);
		layout.putConstraint(SpringLayout.WEST, LogoPanel, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, LogoPanel, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, LogoPanel, 5, SpringLayout.WEST, SingPanel);
		// Panel de SingIn
		contentPane.add(SingPanel);
		layout.putConstraint(SpringLayout.NORTH, SingPanel, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, SingPanel, 5, SpringLayout.EAST, contentPane);
		// Panel de busqueda
		contentPane.add(buscarPanel);
		layout.putConstraint(SpringLayout.NORTH, buscarPanel, 0, SpringLayout.SOUTH, SingPanel);
		layout.putConstraint(SpringLayout.EAST, buscarPanel, 5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, buscarPanel, 0, SpringLayout.EAST, LogoPanel);
		// Panel de foro
		contentPane.add(scroll);
		layout.putConstraint(SpringLayout.NORTH, scroll, 5, SpringLayout.SOUTH, LogoPanel);
		layout.putConstraint(SpringLayout.EAST, scroll, 5, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.WEST, scroll, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, scroll, 5, SpringLayout.SOUTH, contentPane);

		/***********************************
		 * Action Listener
		 ***********************************/
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton boton = (JButton) e.getSource();

				if (boton == registrarJB) {
					String usuario = usernameJT.getText();
					String pass = String.valueOf(pwdJT.getPassword());
					System.out.println("Registro de user: " + usuario + " con contraseña: " + pass);
					registrar(usuario, pass);

				} else if (boton == npostJB) {
					String usuario = usernameJT.getText();
					String pass = String.valueOf(pwdJT.getPassword());
					System.out.println("Nuevo Post de: " + usuario + " con contraseña: " + pass);
					nuevoPost(usuario, pass);

				} else if (boton == buscarJB) {
					String busqueda = buscarJT.getText();
					if (bsucarJR1.isSelected()) {
						System.out.println("Búsqueda por tag: " + busqueda);
						bucarXTag(busqueda);
					} else {
						System.out.println("Búsqueda de post: " + busqueda);
						bucar(busqueda);
					}

				} else if (boton == refreshJB) {
					System.out.println("Refrescar");
					refresh();
				}
			}
		};
		registrarJB.addActionListener(al);
		npostJB.addActionListener(al);
		buscarJB.addActionListener(al);
		refreshJB.addActionListener(al);
		
		refresh();

	}

	public void addPost(String username, String Post, ArrayList<String> tags) {
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel.setBackground(new Color(222, 222, 222));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JTextArea usernameJL = new JTextArea(username + ":");
		usernameJL.setBorder(new EmptyBorder(0, 5, 0, 0));
		usernameJL.setFont(new Font("Pacifico", Font.BOLD, 12));
		usernameJL.setBackground(new Color(222, 222, 222));
		// JLabel usernameJL = new JLabel(username + ":");
		panel.add(usernameJL);

		JPanel postpanel = new JPanel();
		postpanel.setBorder(new EmptyBorder(2, 2, 2, 2));
		postpanel.setBackground(new Color(255, 255, 255));
		postpanel.setLayout(new BoxLayout(postpanel, BoxLayout.Y_AXIS));

		String postTagged = (Post);
		for (String string : tags) {
			postTagged = postTagged.concat(" #" + string);
		}
		JTextArea postJL = new JTextArea(postTagged);
		postJL.setLineWrap(true);
		postJL.setWrapStyleWord(true);
		postJL.setPreferredSize(new Dimension(10, 60));

		postpanel.add(postJL);
		panel.add(postpanel);

		foroPanel.add(panel);
		foroPanel.updateUI();
	}

	private void registrar(String usuario, String pass) {
		try {
			int respuesta = ClienteSOAP.registrar(usuario, pass);
			switch (respuesta) {
			case -1:
				error("El usuario: " + usuario + " ya figura en nuestra base de datos.");
				break;
			case -2:
				error("Actualmente los caracteres \";\" y \"'\" no están permitidos en nuestra base de datos");
				break;
			case 0:
				error("El usuario: " + usuario + " ha sido registrado satisfactoriamente con contraseña: " + pass); // Muy
																													// seguro
																													// todo
																													// xd
				break;
			default:
				error("Unexpected Error");
				break;
			}
		} catch (UnsupportedOperationException | SOAPException e) {
			error("SOAP Exception");
			e.printStackTrace();
		}

	}

	protected void bucarXTag(String busqueda) {
		try {
			foroPanel.removeAll();
			ArrayList<Integer> respuesta = ClienteSOAP.buscarXtag(busqueda);
			for (Integer integer : respuesta) {
				leerPost(integer);
			}
		} catch (UnsupportedOperationException | SOAPException e) {
			error("SOAP Exception");
			e.printStackTrace();
		}
		foroPanel.updateUI();
	}

	protected void bucar(String busqueda) {
		try {
			foroPanel.removeAll();
			ArrayList<Integer> respuesta = ClienteSOAP.buscar(busqueda);
			for (Integer integer : respuesta) {
				leerPost(integer);
			}
		} catch (UnsupportedOperationException | SOAPException e) {
			e.printStackTrace();
		}
		foroPanel.updateUI();
	}

	protected void refresh() {
		foroPanel.removeAll();
		try {
			int maximo = ClienteSOAP.contarPost();
			for (int i = maximo; i > (maximo - 10) && i > 0; i--) {
				leerPost(i);
			}
		} catch (UnsupportedOperationException | SOAPException e) {
			error("SOAP Exception");
			e.printStackTrace();
		}
	}

	private void leerPost(Integer integer) {
		try {
			Post post = ClienteSOAP.leerPost(String.valueOf(integer));
			addPost(post.getAutor(), post.getPost(), post.getTags());
		} catch (UnsupportedOperationException | SOAPException e) {
			error("SOAP Exception");
			e.printStackTrace();
		} catch (NullPointerException e) {
			error("El Post con ID: " + integer + " no se ecuentra disponible actualmente");
		}
	}

	protected void nuevoPost(String usuario, String pass) {
		String post = JOptionPane.showInputDialog(null, "Introduzca el Post");
		if (post != null) {
			String[] tags = new String[10];
			int i = 0;
			do {
				tags[i] = JOptionPane.showInputDialog(null,
						"Introduzca un Tag\n Pulse cancelar para dejar de introducir tags");
			} while ((i < 9) && (tags[i++] != null));
//			System.out.println("Tags recogidos: " + Arrays.toString(tags));
			String[] tags2 = new String[i - 1];
			for (i = i - 2; i >= 0; i--) {
				tags2[i] = tags[i];
			}
//			System.out.println("Tags pasados: " + Arrays.toString(tags2));
			try {
				int respuesta = ClienteSOAP.subirPost(post, tags2, usuario, pass);

				switch (respuesta) {
				case -3:
					error("Actualmente los caracteres \";\" y \"'\" no están permitidos en nuestra base de datos");
					break;
				case -2:
					error("El JDBC_Driver no funciona correctamente");
					break;
				case -1:
					error("SQL Exception");
					break;
				case -5:
					error("Error al conectar con el servicio noticia");
					break;
				case -4:
					error("Unexpected Exception");
					break;
				case -6:
					error("El par usuario contraseña es incorrecto");
					break;
				default:
					foroPanel.removeAll();
					leerPost(respuesta);
					foroPanel.updateUI();
					break;
				}
			} catch (UnsupportedOperationException | SOAPException e) {
				error("SOAP Exception");
				e.printStackTrace();
			}
		}
	}

	private void error(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

}

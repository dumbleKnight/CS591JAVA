package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import backend.Bank;

import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPage {
	static public Bank bank;
	private JFrame frame;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage window = new MainPage();
					window.frame.setVisible(true);
					
					MainPage.bank = new Bank();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));
		contentPane.add(Box.createHorizontalGlue());
		
		
		JLabel lblNewLabel = new JLabel("I'M A");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//lblNewLabel.setBounds(70, 104, 116, 49);
		contentPane.add(lblNewLabel);
		
		String roles[] = { "User", "Manager" };
		JComboBox role_comboBox = new JComboBox(roles);
		//role_comboBox.setBounds(168, 104, 92, 49);
		role_comboBox.setMaximumSize(new Dimension(100, 100));
		contentPane.add(role_comboBox);
		
		
		JButton Confirm_btn = new JButton("OK");
		Confirm_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String role = (String) role_comboBox.getSelectedItem();
				
				if(role.equals("Manager")) {
					new ManagerMainPage();
					frame.dispose();
				}
				else if(role.equals("User")) {
					new LogIn();
					frame.dispose();
				}
			}
		});
		//Confirm_btn.setBounds(272, 116, 97, 25);
		contentPane.add(Confirm_btn);
		contentPane.add(Box.createHorizontalGlue());
		frame.add(contentPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}

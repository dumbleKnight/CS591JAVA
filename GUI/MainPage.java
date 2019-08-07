import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import backend.Bank;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPage {
	static public Bank bank;
	private JFrame frame;

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
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("I'M A");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(70, 104, 116, 49);
		frame.getContentPane().add(lblNewLabel);
		
		String roles[] = { "User", "Manager" };
		JComboBox role_comboBox = new JComboBox(roles);
		role_comboBox.setBounds(168, 104, 92, 49);
		frame.getContentPane().add(role_comboBox);
		
		
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
		Confirm_btn.setBounds(272, 116, 97, 25);
		frame.getContentPane().add(Confirm_btn);
		
		frame.setVisible(true);
	}
}

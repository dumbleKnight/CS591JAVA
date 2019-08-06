import backend.Bank;
import backend.User;

import java.awt.EventQueue;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogIn {
	private JFrame frame;
	private JTextField uname;
	private JTextField psw;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogIn() {
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
		
		uname = new JTextField();
		uname.setBounds(154, 85, 116, 22);
		frame.getContentPane().add(uname);
		uname.setColumns(10);
		
		psw = new JTextField();
		psw.setBounds(154, 141, 116, 22);
		frame.getContentPane().add(psw);
		psw.setColumns(10);
		
		JLabel lblSignIn = new JLabel("Log In Page");
		lblSignIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignIn.setBounds(154, 13, 126, 43);
		frame.getContentPane().add(lblSignIn);
		
		JButton login_btn = new JButton("Sign In");
		login_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(MainPage.bank.logIn(uname.getText(), psw.getText()));
				System.out.println(uname.getText() + " " + psw.getText());
				if(MainPage.bank.logIn(uname.getText(), psw.getText())) {
					UserMainPage.user = MainPage.bank.getUser(uname.getText());
					new UserMainPage();

					frame.dispose();
				}
				else {
					System.out.println("Invalid input");
				}
			}
		});
		login_btn.setBounds(94, 189, 97, 25);
		frame.getContentPane().add(login_btn);
		
		lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(57, 88, 85, 16);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(57, 144, 56, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton signup = new JButton("Sign Up");
		signup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new CreateUser();
				
				frame.dispose();
			}
		});
		signup.setBounds(220, 189, 97, 25);
		frame.getContentPane().add(signup);
		
		frame.setVisible(true);
	}
}

package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

import backend.Bank;
import backend.User;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserMainPage {
	static User user;
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMainPage window = new UserMainPage();
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
	public UserMainPage() {
		initialize();
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        MainPage.bank.writeUser();
		        MainPage.bank.writeAccount();
		        MainPage.bank.writeTransaction();
		        MainPage.bank.writeProperty();
		    	System.exit(0);
		    }
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel greeting_label = new JLabel("Hello, " + user.getName() + "!");
		greeting_label.setHorizontalAlignment(SwingConstants.CENTER);
		greeting_label.setBounds(144, 32, 135, 32);
		frame.getContentPane().add(greeting_label);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> option_list = new JList<String>(model);
		option_list.setBounds(89, 89, 268, 113);
		frame.getContentPane().add(option_list);
		
		JButton logout_btn = new JButton("Log Out");
		logout_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Bank bank = MainPage.bank;
				new MainPage();
				MainPage.bank = bank;
				
				frame.dispose();
			}
		});
		logout_btn.setBounds(182, 215, 97, 25);
		frame.getContentPane().add(logout_btn);
		
		model.addElement("Create account");
		model.addElement("Check account");
		model.addElement("Deposit");
		model.addElement("Withdraw");
		model.addElement("Transfer");
		model.addElement("Close account");
		
		option_list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {


		           String selectedItem = (String) option_list.getSelectedValue();
		           System.out.println(selectedItem);
		           
		           switch(selectedItem) {
		           case "Create account":
		        	   new CreateAccount();
		        	   frame.dispose();
		        	   
		        	   break;
		           case "Close account":
		        	   new CloseAccount();
		        	   frame.dispose();
		        	   
		        	   break;
		           case "Deposit":
		        	   new DepositMoney();
		        	   frame.dispose();
		        	   
		        	   break;
		           case "Withdraw":
		        	   new WithdrawMoney();
		        	   frame.dispose();
		        	   
		        	   break;
		           case "Transfer":
		        	   new TransferMoney();
		        	   frame.dispose();
		        	   
		        	   break;
		           case "Check account":
		        	   new CheckAccount();
		        	   frame.dispose();
		        	   
		        	   break;
		           }
			    }
			}
		});
		
		frame.setVisible(true);
	}
}

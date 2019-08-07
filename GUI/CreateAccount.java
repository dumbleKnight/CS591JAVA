package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateAccount {

	private JFrame frame;
	private JTextField balance_txt;
	private JButton confirm_btn;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateAccount window = new CreateAccount();
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
	public CreateAccount() {
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
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		
		contentPane.add(Box.createVerticalGlue());
		DefaultListModel<String> model = new DefaultListModel<>();
		JList type_list = new JList(model);
		//type_list.setBounds(117, 69, 195, 64);
		type_list.setFixedCellWidth(150);
		type_list.setFixedCellHeight(30);
		contentPane.add(type_list);
		//type_list.setPreferredSize(new Dimension(117, 69));
		contentPane.add(type_list);
		
		model.addElement("Saving account");
		model.addElement("Checking account");
		model.addElement("Security account");
		
		contentPane.add(Box.createVerticalGlue());
		
		JPanel balancePane = new JPanel();
		balancePane.setLayout(new BoxLayout(balancePane, BoxLayout.LINE_AXIS));
		JLabel lblNewLabel = new JLabel("Balance: ");
		//lblNewLabel.setBounds(117, 152, 62, 16);
		
		balancePane.add(lblNewLabel);
		
		balance_txt = new JTextField(10);
		balance_txt.setMaximumSize(new Dimension(100, 20));
		//balance_txt.setBounds(196, 146, 116, 22);
		balancePane.add(balance_txt);
		//balance_txt.setColumns(10);
		
		contentPane.add(Box.createVerticalGlue());
		
		contentPane.add(balancePane);
		
		contentPane.add(Box.createVerticalGlue());
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		
		JButton cancelButton = new JButton("Cancel");
        cancelButton.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            new UserMainPage();
            frame.dispose();
          }
        });
        cancelButton.setBounds(100, 181, 100, 39);
        buttonPane.add(cancelButton);
        
		confirm_btn = new JButton("Confirm");
		confirm_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String account_type = (String) type_list.getSelectedValue();
				Double balance = Double.valueOf(balance_txt.getText());
				
				System.out.println(account_type + " " + balance);
				
				String is_success = null;
				switch(account_type){
					case "Saving account":
						is_success = MainPage.bank.createSavingAccount(UserMainPage.user.getUid(), balance);
						break;
					case "Checking account":
						is_success = MainPage.bank.createCheckingAccount(UserMainPage.user.getUid(), balance);
						break;
					case "Security account":
						is_success = MainPage.bank.createSecurityAccount(UserMainPage.user.getUid(), balance);
						break;
				}
				
				if(is_success != null) {
					new UserMainPage();
					System.out.println(UserMainPage.user.getAccount(is_success).getMoney());
					frame.dispose();
				}
				else {
					System.out.println("Failed to create account");
				}
			}
		});
		
		confirm_btn.setBounds(152, 181, 138, 39);
		buttonPane.add(confirm_btn);
		
		
		contentPane.add(buttonPane);
		contentPane.add(Box.createVerticalGlue());
		frame.add(contentPane, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}

}

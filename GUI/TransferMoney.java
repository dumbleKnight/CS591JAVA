package GUI;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;

import backend.Account;
import backend.AccountType;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransferMoney {

	private JFrame frame;
	private JTextField aid_txt;
	private JTextField money_txt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransferMoney window = new TransferMoney();
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
	public TransferMoney() {
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
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList account_list = new JList(model);
		account_list.setBounds(102, 27, 219, 95);
		frame.getContentPane().add(account_list);
		
		//String[] accounts = new String[] {"Fake Account | Type | Balance"};
		ArrayList<String> account_info = new ArrayList<String>();
		for(String aid: UserMainPage.user.getAccountId()) {
			String info;
			Account account = UserMainPage.user.getAccount(aid);
			AccountType type = account.accountType();
			
			switch(type) {
			case Checking:
				account_info.add(aid + " | " + "Checking"  + " | " + account.getMoney());
				break;
			case Saving:
				account_info.add(aid + " | " + "Saving"  + " | " + account.getMoney());
				break;
			case Security:
				account_info.add(aid + " | " + "Security"  + " | " + account.getMoney());
				break;
			}
		}
		
		for(String account: account_info)
			model.addElement(account);
		
		JLabel lblNewLabel = new JLabel("To");
		lblNewLabel.setBounds(101, 135, 108, 32);
		frame.getContentPane().add(lblNewLabel);
		
		aid_txt = new JTextField();
		aid_txt.setBounds(144, 140, 116, 22);
		frame.getContentPane().add(aid_txt);
		aid_txt.setColumns(10);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setBounds(89, 180, 56, 16);
		frame.getContentPane().add(lblAmount);
		
		money_txt = new JTextField();
		money_txt.setBounds(144, 180, 116, 22);
		frame.getContentPane().add(money_txt);
		money_txt.setColumns(10);
		
		JButton confirm_btn = new JButton("Confirm");
		confirm_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String account = (String) account_list.getSelectedValue();
				String another_aid = aid_txt.getText();
				Double money = Double.valueOf(money_txt.getText());
				System.out.println(account + " " + another_aid + " " + money);
				
				String account_id = account.split(" | ")[0];
				
				if(MainPage.bank.sendMoney(money, another_aid, account_id)) {
					new UserMainPage();
					frame.dispose();
				}
				else {
					System.out.println("Failed to transfer the money");
				}
				
			}
		});
		confirm_btn.setBounds(165, 215, 97, 25);
		frame.getContentPane().add(confirm_btn);
		
		frame.setVisible(true);
	}
}

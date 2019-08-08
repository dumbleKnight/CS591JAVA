package GUI;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

import backend.Account;
import backend.AccountType;

public class WithdrawMoney {

	private JFrame frame;
	private JTextField money_txt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WithdrawMoney window = new WithdrawMoney();
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
	public WithdrawMoney() {
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
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList account_list = new JList(model);
		account_list.setBounds(77, 53, 297, 120);
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
		
		
		money_txt = new JTextField();
		money_txt.setBounds(226, 180, 116, 22);
		frame.getContentPane().add(money_txt);
		money_txt.setColumns(10);
		
		String s1[] = { "currency1", "currency2", "currency3" };
		JComboBox currency_combobox = new JComboBox(s1);
		currency_combobox.setBounds(104, 180, 97, 22);
		frame.getContentPane().add(currency_combobox);
		
		JButton Confirm = new JButton("Confirm");
		Confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String account = (String) account_list.getSelectedValue();
				String currency = (String) currency_combobox.getSelectedItem();
				Double money = Double.valueOf(money_txt.getText());
				System.out.println(account + " " + currency + " " + money);
				
				String account_id = account.split(" | ")[0];
				if(MainPage.bank.withdrawMoney(account_id, money)) {
					new UserMainPage();
					frame.dispose();
				}
				else {
					System.out.println("Failed to withdraw the money");
				}
			}
		});
		Confirm.setBounds(176, 215, 97, 25);
		frame.getContentPane().add(Confirm);
		
		frame.setVisible(true);
	}

}

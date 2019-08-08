package GUI;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import backend.Account;
import backend.AccountType;

public class CheckAccount {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckAccount window = new CheckAccount();
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
	public CheckAccount() {
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
		
		account_list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
					String account = (String) account_list.getSelectedValue();
					System.out.println("Jump to the relevant page: " + account);
					
					String account_id = account.split(" | ")[0];
					AccountType type = UserMainPage.user.getAccount(account_id).accountType();
					if(type.equals(AccountType.Checking) || type.equals(AccountType.Saving)) {
						new AccountPageNormal(account_id);
						
			        	frame.dispose();
					}
					else if(type.equals(AccountType.Security)) {
						SecuritiesInterface.account_id = account_id;
						SecuritiesInterface.createSecuritiesOptions();
						
			        	frame.dispose();
					}
					
			    }
			}
		});
		
		JButton cancelButton = new JButton("Back");
        cancelButton.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            new UserMainPage();
            frame.dispose();
          }
        });
        cancelButton.setBounds(100, 200, 100, 39);
        frame.getContentPane().add(cancelButton);
		
		frame.setVisible(true);
	}

}

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
					
					new AccountPageNormal();
					
		        	frame.dispose();
			    }
			}
		});
		
		frame.setVisible(true);
	}

}

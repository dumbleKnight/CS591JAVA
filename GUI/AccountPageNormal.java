import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountPageNormal {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountPageNormal window = new AccountPageNormal();
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
	public AccountPageNormal() {
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
		
		JLabel account_label = new JLabel("account: XXX");
		account_label.setHorizontalAlignment(SwingConstants.CENTER);
		account_label.setBounds(113, 23, 202, 35);
		frame.getContentPane().add(account_label);
		
		JLabel balance_label = new JLabel("balance: XXX");
		balance_label.setHorizontalAlignment(SwingConstants.CENTER);
		balance_label.setBounds(113, 72, 202, 35);
		frame.getContentPane().add(balance_label);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList transaction_list = new JList(model);
		transaction_list.setBounds(51, 120, 338, 82);
		frame.getContentPane().add(transaction_list);
		
		String[] transactions = new String[]{"Fake Transaction"};
		for(String transaction: transactions)
			model.addElement(transaction);
		
		JButton back_btn = new JButton("HomePage");
		back_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new UserMainPage();
	        	frame.dispose();
			}
		});
		back_btn.setBounds(163, 215, 97, 25);
		frame.getContentPane().add(back_btn);
		
		frame.setVisible(true);
	}
}

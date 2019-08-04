import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CloseAccount {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CloseAccount window = new CloseAccount();
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
	public CloseAccount() {
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
		String[] accounts = new String[] {"Fake Account"};
		for(String account: accounts)
			model.addElement(account);
		
		JButton Confirm = new JButton("Confirm");
		Confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String account = (String) account_list.getSelectedValue();
				System.out.println(account);
				
				new UserMainPage();
	        	frame.dispose();
			}
		});
		Confirm.setBounds(176, 186, 97, 25);
		frame.getContentPane().add(Confirm);
		
		frame.setVisible(true);
	}
}

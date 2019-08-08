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

public class ManagerMainPage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerMainPage window = new ManagerMainPage();
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
	public ManagerMainPage() {
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
		JList user_list = new JList(model);
		user_list.setBounds(56, 50, 330, 157);
		frame.getContentPane().add(user_list);
		
		ArrayList<String> user_info = new ArrayList<String>();
		for(String user_id: MainPage.bank.getUserList()) {
			user_info.add(user_id + " | " + MainPage.bank.getUser(user_id).getName());
		}
		
		for(String user: user_info)
			model.addElement(user);
		
		user_list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		           String selectedItem = (String) user_list.getSelectedValue();
		           System.out.println(selectedItem);
		           
		           String user_id = selectedItem.split(" | ")[0];
		           new ManagerCheckPage(user_id);
		           frame.dispose();
			    }
			}
		});
		
		JButton cancelButton = new JButton("Back");
        cancelButton.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            new MainPage();
            frame.dispose();
          }
        });
        cancelButton.setBounds(100, 200, 100, 39);
        frame.getContentPane().add(cancelButton);
		
		frame.setVisible(true);
	}
}

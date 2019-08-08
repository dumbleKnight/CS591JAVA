package GUI;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import java.awt.BorderLayout;

public class ManagerCheckPage {
	private String uid;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerCheckPage window = new ManagerCheckPage("");
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
	public ManagerCheckPage(String uid) {
		this.uid = uid;
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
		
		DefaultListModel<String> model = new DefaultListModel<>();
		frame.getContentPane().setLayout(null);
		JList user_record_list = new JList(model);
		user_record_list.setBounds(60, 57, 304, 119);
		frame.getContentPane().add(user_record_list);
		
		ArrayList<String> user_info = new ArrayList<String>();
		for(String record: MainPage.bank.getUser(uid).toString().split("\\|"))
		  model.addElement(record);
		
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

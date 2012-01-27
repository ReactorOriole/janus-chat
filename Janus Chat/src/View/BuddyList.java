package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class BuddyList {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuddyList window = new BuddyList();
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
	public BuddyList() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JButton btnAddBuddy = new JButton("Add Buddy");
		frame.getContentPane().add(btnAddBuddy, BorderLayout.SOUTH);
		
		JTextArea txtrBuddylist = new JTextArea();
		txtrBuddylist.setText("BuddyList");
		frame.getContentPane().add(txtrBuddylist, BorderLayout.CENTER);
	}

}

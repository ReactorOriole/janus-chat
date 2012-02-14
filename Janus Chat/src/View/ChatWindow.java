package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public class ChatWindow implements ActionListener{

	private JFrame frame;
	private JTextField sendTextField;
	private JEditorPane editorPane= null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Controller.JanusTransformer jt = new Controller.JanusTransformer("s", "s", "s");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatWindow window = new ChatWindow();
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
	public ChatWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 675, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(this);
		panel_1.add(btnSend, BorderLayout.EAST);
		
		sendTextField = new JTextField();
		panel_1.add(sendTextField, BorderLayout.CENTER);
		sendTextField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);
	    
		JComboBox textCombo = new JComboBox();
		textCombo.setModel(new DefaultComboBoxModel(new String[] {"Arial", "Arial Black", "Comic Sans MS", "Courier New", "Georgia", "Impact", "Times New Roman", "Trebuchet MS", "Verdana"}));
		panel_2.add(textCombo);
		
		JComboBox sizeCombo = new JComboBox();
		sizeCombo.setModel(new DefaultComboBoxModel(new String[] {"6", "8", "10", "12", "14", "16", "20", "24", "30", "36", "44"}));
		sizeCombo.setSelectedIndex(2);
		panel_2.add(sizeCombo);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Send")){
			try {
				editorPane.setPage("../Model/ClientData/TextLog.xml");
				editorPane.repaint();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

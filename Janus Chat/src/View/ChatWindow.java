package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.Document;

import org.w3c.dom.NodeList;

import Controller.JanusMessageService;
import Controller.JanusProcessor;
import Controller.JanusTransformer;
import Controller.JanusUpdater;

public class ChatWindow implements ActionListener{

	private JFrame frmChatWindow;
	private JTextField sendTextField;
	private JScrollPane scrollPane;
	private static JEditorPane editorPane= null;
	private final static String TEXTLOG = "src/Model/ClientData/TextLog.xml";
	private final static String XSLFILE = "src/Model/ClientData/ChatLog.xsl";
	private final static String TEMPFILE = "temp.html";
	private static final String PREFFILE = "src/Model/ClientData/TextPreferences.xml";
	private final String FONTFILE = "src/Model/ClientData/Fonts.xml";

	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public ChatWindow() {
		initialize();
		run();

	}

	private void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmChatWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatWindow = new JFrame();
		frmChatWindow.setTitle("Chat Window");
		frmChatWindow.setBounds(100, 100, 675, 425);
		frmChatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatWindow.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frmChatWindow.getContentPane().add(panel);
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

		//get the user preferences to set the default on the combo box(s)
		ArrayList<String> s = new ArrayList<String>();
		s = getComboPreferences();

		//read all of the fonts/colors/sizes
		File f3 = new File(FONTFILE);
		JanusProcessor jp = new JanusProcessor(f3);
		NodeList nodes = (NodeList)jp.xpathQuery("/font/fonts/font/text()");
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < nodes.getLength(); i++) {
			al.add(nodes.item(i).getNodeValue()); 
		}
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));

		//add actionlistener and update the preferences file
		JComboBox textCombo = new JComboBox( al.toArray() );
		textCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String font = (String)cb.getSelectedItem();
				JanusUpdater.update("font", font);
			}
		});
		textCombo.setSelectedItem(s.get(0));
		panel_2.add(textCombo);

		//sizes
		al.clear();
		nodes = jp.xpathQuery("/font/sizes/size/text()");
		for (int i = 0; i < nodes.getLength(); i++) {
			al.add(nodes.item(i).getNodeValue()); 
		}
		//add actionlistener
		JComboBox sizeCombo = new JComboBox( al.toArray() );
		sizeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String size = (String)cb.getSelectedItem();
				JanusUpdater.update("size", size);
			}
		});	
		sizeCombo.setSelectedItem(s.get(1));
		panel_2.add(sizeCombo);

		//colors
		al.clear();
		nodes = jp.xpathQuery("/font/colors/color/text()");
		for (int i = 0; i < nodes.getLength(); i++) {
			al.add(nodes.item(i).getNodeValue()); 
		}
		//add actionlistener to combo
		JComboBox colorCombo = new JComboBox(al.toArray() );
		colorCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String color = (String)cb.getSelectedItem();
				JanusUpdater.update("color", color);
			}
		});	
		colorCombo.setSelectedItem(s.get(2));
		panel_2.add(colorCombo);

		//add enter key listener so we send a message when enter is pressed
		sendTextField.addKeyListener(new KeyAdapter(){ 		
			public void keyPressed(KeyEvent e){ 
				if ((int)e.getKeyChar()==10) 
					sendMessage();
			} 
		});

		JanusMessageService.receiveMessages();
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		scrollPane = new JScrollPane(editorPane);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			public void adjustmentValueChanged(AdjustmentEvent e){
				editorPane.select(editorPane.getHeight()+1000,0);
			}});
		panel.add(scrollPane, BorderLayout.CENTER);
		updateWindow();
	}

	private ArrayList<String> getComboPreferences() {
		//setting up the queries to get the user prefs
		JanusProcessor jp = new JanusProcessor(new File(PREFFILE));
		NodeList nodes = (NodeList)jp.xpathQuery("/defaults/font/text()");
		ArrayList<String> al = new ArrayList<String>();
		al.add(nodes.item(0).getNodeValue()); 
		nodes = (NodeList)jp.xpathQuery("/defaults/size/text()");
		al.add(nodes.item(0).getNodeValue());
		nodes = (NodeList)jp.xpathQuery("/defaults/color/text()");
		al.add(nodes.item(0).getNodeValue());
		return al;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Send")){
			sendMessage();
		}
	}

	private void sendMessage() {
		try {
			JanusMessageService.sendMessage(sendTextField.getText());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		sendTextField.setText("");
	}

	public static void updateWindow() {
		try{
			JanusTransformer.transform(TEXTLOG, XSLFILE, TEMPFILE);
			File f = new File(TEMPFILE);
			editorPane.setPage(f.toURI().toURL());					
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Document doc = editorPane.getDocument();
				doc.putProperty(Document.StreamDescriptionProperty, null);

			}
		});

	}
}

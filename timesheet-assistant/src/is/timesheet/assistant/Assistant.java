package is.timesheet.assistant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Assistant extends JFrame {
    
    /**
	 * serialVersionUID;
	 */
	private static final long serialVersionUID = 5567702774127922167L;
	
	private JPanel mainPanel;
	
	private JPanel inputPanel;
	
	private JTextArea console;
	
	private JTextField currentTask;
	
	public Assistant() {
		setTitle("Timesheet - Assistant");
		setMinimumSize(new Dimension(700, 300));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(1, 2));
		
		inputPanel.add(new JLabel("  Current Task : "));
		currentTask = new JTextField();
		inputPanel.add(currentTask);
		
		mainPanel.add(inputPanel, BorderLayout.PAGE_START);
		console = new JTextArea();
		mainPanel.add(console, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setContentPane(mainPanel);
		
		pack();
		setVisible(true);
	}
	
	public void log(Object toLog) {
		console.append(toLog.toString());
		console.append(System.lineSeparator());
	}
	
	public String getCurrentTask() {
		return this.currentTask.getText();
	}
}
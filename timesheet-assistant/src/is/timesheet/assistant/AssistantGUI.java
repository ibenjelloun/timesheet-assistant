package is.timesheet.assistant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import is.timesheet.WorkstationLockListening;

/**
 * The Class Assistant.
 */
public class AssistantGUI extends JFrame {
    
    /** serialVersionUID;. */
	private static final long serialVersionUID = 5567702774127922167L;
	
	/** The main panel. */
	private JPanel mainPanel;
	
	/** The input panel. */
	private JPanel inputPanel;
	
	/** The console. */
	private JTextArea console;
	
	/** The current task field. */
	private JTextField currentTaskField;
	
	/** The current task. */
	private String currentTask;

	/** The current task label. */
	private JLabel currentTaskLabel;

	/** The lock listener. */
	private WorkstationLockListening lockListener;
	
	/**
	 * Instantiates a new assistant.
	 */
	public AssistantGUI() {
		setTitle("Timesheet - Assistant");
		setMinimumSize(new Dimension(700, 300));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(1, 2));
		
		currentTaskLabel = new JLabel("  Current Task : ");
		inputPanel.add(currentTaskLabel);
		currentTaskField = new JTextField();
		currentTaskField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
					if(currentTask != null) {
						lockListener.beforeCurrentTaskChange();
					}
					currentTask = currentTaskField.getText();
					currentTaskLabel.setText("  Current Task : " + currentTask);
				}
			}
		});
		inputPanel.add(currentTaskField);
		
		mainPanel.add(inputPanel, BorderLayout.PAGE_START);
		console = new JTextArea();
		JScrollPane consolePane = new JScrollPane(console);
		mainPanel.add(consolePane, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setContentPane(mainPanel);
		
		pack();
		setVisible(true);
	}
	
	/**
	 * Log.
	 *
	 * @param toLog the to log
	 */
	public void log(Object toLog) {
		console.append(toLog.toString());
		console.append(System.lineSeparator());
	}
	
	/**
	 * Gets the current task.
	 *
	 * @return the current task
	 */
	public String getCurrentTask() {
		return this.currentTask;
	}

	
	/**
	 * Sets the lock listener.
	 *
	 * @param workstationLockListening the new lock listener
	 */
	public void setLockListener(WorkstationLockListening workstationLockListening) {
		this.lockListener = workstationLockListening;
	}
}
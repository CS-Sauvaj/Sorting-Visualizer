package fr.hugosimony.sortingvizualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Game extends JFrame implements ComponentListener{

	private static final long serialVersionUID = 1L;

	public static final Color black = new Color(0,0,0);
	public static final Color white = new Color(255,255,255);
	public static final Color selected = new Color(0,255,0);
	public static final Color background = new Color(130,130,130);
	
	public static int columns = 100;
	public static int lines = 101;
	public static int[] tab = new int[columns];
	public static Bar[] barsTab = new Bar[columns];
	
	public static boolean paused = true;
	public static int actualColumn = -1;
	public static Color actualColor = black;
	
	static Game game;

	public static JPanel panel_all = new JPanel();
	public static JPanel panel_visualizer = new JPanel();
	
	public static JRadioButton bubbleSort = new JRadioButton("Bubble Sort");
	public static JRadioButton quickSort = new JRadioButton("Quick Sort");
	
	public static JSlider speed = new JSlider(1,200);
	
	public static JSlider linesSlider = new JSlider(10,400);
	public static JSlider columnsSlider = new JSlider(10,250);
	
	public static JButton sortButton = new JButton("Sort");
	
	public Game(){
		
		this.setTitle("Sorting Visualizer    -----    Made by Hugo Simony-Jungo");
		this.setSize(700, 700);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		   @Override
		   public void windowClosed(WindowEvent e) {
		      System.exit(0);
		   }
		});
		this.addComponentListener(this);
		this.setMinimumSize(new Dimension(550,620));
		
		game = this;
		
		initializeTab();
		
	    panel_all.setLayout(new BorderLayout());
	    
	    Font sortingFont = new Font("Arial", Font.BOLD, 20);
		JPanel sortPanel = new JPanel();
		
		sortPanel.add(bubbleSort);
		sortPanel.add(quickSort);
		
		//*************************************************
		// Config des boutons
		
		bubbleSort.setFont(sortingFont);
		bubbleSort.setBackground(background);
		bubbleSort.setFocusPainted(false);
		bubbleSort.setSelected(true); // Selected by default
		bubbleSort.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if(quickSort.isSelected()) {
					quickSort.setSelected(false);
					bubbleSort.setSelected(true);
				}
				else
					bubbleSort.setSelected(true);
			}
		});
		
		quickSort.setFont(sortingFont);
		quickSort.setBackground(background);
		quickSort.setFocusPainted(false);
		quickSort.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				if(bubbleSort.isSelected()) {
					bubbleSort.setSelected(false);
					quickSort.setSelected(true);
				}
				else
					quickSort.setSelected(true);
			}
		});
	    
		//*************************************************
		// Visualizer
		
	    sortPanel.setBackground(background);
	    
	    panel_all.add("North", sortPanel);
	    
	    panel_visualizer.setLayout(new GridLayout(1, columns));
	    
	    
	    for (int i = 0; i < columns; i++) {
	    	barsTab[i] = new Bar(tab[i], black);
	    	panel_visualizer.add(barsTab[i]);
	    }
	    

	    panel_visualizer.setBackground(background);
	    panel_all.add("Center", panel_visualizer);
	    
	    
	    //*************************************************
	    // Play Panel
	    
	    JPanel configPanel = new JPanel();
	    configPanel.setLayout(new GridLayout(2, 1));
	    
	    	JPanel moreConfigPanel = new JPanel();
	    	moreConfigPanel.setLayout(new GridLayout(1, 2));
	    	
	    		Font configLinesColumnsFont = new Font("Arial", Font.BOLD, 10);
	    		
	    		JPanel linesPanel = new JPanel();
	    		linesPanel.setLayout(new GridLayout(2, 1));
	    			
	    			JLabel line = new JLabel("Range of the Integers of the list (1 to x)");
	    			line.setFont(configLinesColumnsFont);
	    			line.setVerticalAlignment(JLabel.CENTER);
	    			line.setHorizontalAlignment(JLabel.CENTER);
	    			line.setBackground(background);
	    			linesPanel.add(line);
	    				
		    		linesSlider.setFont(configLinesColumnsFont);
		    		linesSlider.setBackground(background);
		    		linesSlider.setValue(100);
		    		linesSlider.setMajorTickSpacing(30);
		    		linesSlider.setMinorTickSpacing(10);
		    		linesSlider.setPaintTicks(false);
		    		linesSlider.setPaintLabels(true);
		    		linesSlider.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							lines = linesSlider.getValue() + 1;
							initializeTab();
							reloadScreen();
						}
					});
		    		
		    	linesPanel.setBackground(background);
		    	linesPanel.add(linesSlider);
		    		
		    	
		    	JPanel columnsPanel = new JPanel();
		    	columnsPanel.setLayout(new GridLayout(2, 1));
		    	
	    			JLabel column = new JLabel("Length of the list");
	    			column.setFont(configLinesColumnsFont);
	    			column.setVerticalAlignment(JLabel.CENTER);
	    			column.setHorizontalAlignment(JLabel.CENTER);
	    			column.setBackground(background);
	    			columnsPanel.add(column);
	    			
		    		columnsSlider.setFont(configLinesColumnsFont);
		    		columnsSlider.setBackground(background);
		    		columnsSlider.setValue(100);
		    		columnsSlider.setMajorTickSpacing(30);
		    		columnsSlider.setMinorTickSpacing(10);
		    		columnsSlider.setPaintTicks(false);
		    		columnsSlider.setPaintLabels(true);
		    		columnsSlider.addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							columns = columnsSlider.getValue();
							tab = new int[columns];
							barsTab = new Bar[columns];
							initializeTab();
							reloadScreen();
						}
					});
	    		
		    	columnsPanel.setBackground(background);
	    		columnsPanel.add(columnsSlider);
	    		
	    		moreConfigPanel.add(linesPanel);
	    		moreConfigPanel.add(columnsPanel);
	    		
    		moreConfigPanel.setBackground(background);
		    configPanel.add(moreConfigPanel);
	    		
		    JPanel playPanel = new JPanel();
		    playPanel.setLayout(new GridLayout(1, 3));
		    
			    
			    Font restartButtonFont = new Font("Arial", Font.BOLD, 40);
			    
			    JButton restartButton = new JButton("Shuffle");
			    restartButton.setFont(restartButtonFont);
			    
			    restartButton.addActionListener((ActionEvent e) -> {
			    	paused = true; 
			    	sortButton.setEnabled(true); 
			    	sortButton.setText("Sort");
			    	linesSlider.setEnabled(true); 
			    	columnsSlider.setEnabled(true); 
			    	resetTab(); reloadScreen();
			    });
			    
			    playPanel.add(restartButton);
			    
			    JPanel speedPanel = new JPanel();
			    speedPanel.setLayout(new GridLayout(2, 1));
			    
			    	Font speedFont = new Font("Arial", Font.BOLD, 20);
			    	JLabel speedLabel = new JLabel("Speed");
			    	speedLabel.setFont(speedFont);
			    	speedLabel.setVerticalAlignment(JLabel.CENTER);
			    	speedLabel.setHorizontalAlignment(JLabel.CENTER);
			    	
			    	speedLabel.setBackground(background);
			    	speedPanel.add(speedLabel);
			    	
			    	speed.setMinorTickSpacing(1);
			    	speed.setMajorTickSpacing(1);
			    	speed.setPaintTicks(false);
			    	speed.setPaintLabels(false);
			    	speed.setFont(speedFont);
					
			    	speed.setBackground(background);
					speedPanel.add(speed);
			    
			    speedPanel.setBackground(background);
			    playPanel.add(speedPanel);
			    
			    Font sortButtonFont = new Font("Arial", Font.BOLD, 40);
			    
			    sortButton.setFont(sortButtonFont);
			    
			    sortButton.addActionListener((ActionEvent e) -> {startGoodSorting();});
			    
			    playPanel.add(sortButton);
			    
		    
		    playPanel.setBackground(background);
		    configPanel.add(playPanel);
		    
	    configPanel.setBackground(background);
	    panel_all.add("South", configPanel);
	    
	    //*************************************************
	    
	    panel_all.setBackground(background);
	    
	    add(panel_all);
	    
		reloadScreen();
	    
	}
	
	private void initializeTab() {
		for(int i = 0; i<columns; i++) {
			tab[i] = (int) (Math.random() * (lines-1)) + 1;
		}
	}
	
	private void resetTab() {
		for(int i = 0; i<columns; i++) {
			tab[i] = (int) (Math.random() * (lines-1)) + 1;
		}
		actualColor = black;
		actualColumn = -1;
	}
	
	public static void reloadScreen() {
		
		panel_all.remove(panel_visualizer);
		panel_visualizer = new JPanel();
		panel_visualizer.setLayout(new GridLayout(1,columns));
		panel_visualizer.setBackground(background);
		
		for(int i_ = 0; i_<tab.length; i_++) {
			if(i_==actualColumn)
				actualColor = selected;
			else
				actualColor = black;
			barsTab[i_] = new Bar(tab[i_], actualColor);
			panel_visualizer.add(barsTab[i_]);
		}
		
		panel_all.add("Center", panel_visualizer);
		game.validate();
	}
	
	public static void swap(int i, int j) {
		int x = tab[i];
		tab[i] = tab[j];
		tab[j] = x;
	}
	
	public void startGoodSorting() {
		Timer timer = new Timer();
		paused = false;
		linesSlider.setEnabled(false);
		columnsSlider.setEnabled(false);
		sortButton.setEnabled(false);
		sortButton.setText("Sorting");
		if(bubbleSort.isSelected())
			timer.schedule(new BubbleSort(), 0, 1);
			 
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		reloadScreen();
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		reloadScreen();
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		reloadScreen();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		reloadScreen();
	}
	
}

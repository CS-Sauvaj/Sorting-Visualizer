package fr.hugosimony.sortingvizualizer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Bar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private int height;
	private Color color;
	private int frameHeight;
	
	public Bar(int height, Color color) {
		this.height = height;
		this.color = color;
		frameHeight = Game.game.getSize().height;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);   
		float mult = ((int)((frameHeight-200)/Game.lines));
		g.fillRect(0, frameHeight-200-(int)(height*mult), 1000, (int) (height*mult));
		g.setFont(new Font("Arial", Font.PLAIN, 12));
	}

}

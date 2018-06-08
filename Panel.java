package my_game_framework;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Panel extends JPanel implements KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean[] keyboardState = new boolean[525];
	public Panel()
	{
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		BufferedImage blankCursorImg = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0,0),	 null);
		this.setCursor(blankCursor);
		
		this.addKeyListener(this);
		
	}
	
	public abstract void  Draw(Graphics2D g2d);
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
		Draw(g2d);
	}
	
	public static boolean isKeyboardEmpty()
	{
		for(boolean b : keyboardState)
		{
			if (b == true)
				return false;
		}
		return true;
	}
	public static boolean keyboardKeyState(int key)
	{
		return keyboardState[key];
	}
	
	public void keyPressed(KeyEvent e)
	{
		keyboardState[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e)
	{
		keyboardState[e.getKeyCode()] = false;
		//keyReleasedFramework(e); to be implemented if there's a way to restart the game
		//once a person has lost
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		//
	}
	
	//public abstract void keyReleasedFramework(KeyEvent e); will be implemented in framework
	//once i figure out how to do a game over
	

}

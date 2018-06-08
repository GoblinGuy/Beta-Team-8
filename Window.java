package my_game_framework;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window()
	{
		setTitle("Game");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new Framework());
		this.setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new Window();
			}
		});
	}
}

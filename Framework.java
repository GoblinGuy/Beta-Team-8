package my_game_framework;

import java.awt.Color;
import java.awt.Graphics2D;

public class Framework extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int frameWidth;
	public static int frameHeight;
	
	public static final long secInNanosec = 1000000000L;
	
	public  static final long milisecInNanosec = 1000000L;
	
	private final int GAME_FPS  = 16; //can be changed later
	
	private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
	
	public static enum GameState{STARTING, VISUALIZING, PLAYING, GAME_CONTENT_LOADING}
	
	
	public static GameState gameState;
	
	private long gameTime;
	
	private long lastTime;
	
	private Game game;
	
	
	public Framework(int width, int height)
	{
		super(); //sets the default of a panel
		frameWidth = width;
		frameHeight = height;
		gameState = GameState.VISUALIZING;
		
		Thread gameThread = new Thread() {
			public void run() {
				GameLoop();
			}
		};
		gameThread.start();
	}
	
	
	
	private void GameLoop()
	{

		long beginTime, timeTaken, timeLeft;
		long visualizingTime = 0;
		long lastVisualizingTime = System.nanoTime();
		
		while(true)
		{
			beginTime = System.nanoTime();
			
			switch(gameState)
			{
				case VISUALIZING:
					if(this.getWidth() < 1 || visualizingTime < secInNanosec)
					{
						visualizingTime += System.nanoTime() - 	lastVisualizingTime;
						lastVisualizingTime = System.nanoTime();
					}
					
														
					gameState = GameState.STARTING;
					break;
					
					
				case STARTING:
					newGame();
					break;
					 
					 
				case PLAYING:
					gameTime += System.nanoTime() - lastTime;
					game.UpdateGame(gameTime);
					lastTime = System.nanoTime();
					break;
					
				case GAME_CONTENT_LOADING:
					break;
			}
			repaint(); //calls Panel' paintComponent method
			
			timeTaken = System.nanoTime()  - beginTime;
			timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec;
			
			if (timeLeft < 10)
				timeLeft = 10;
			try {
				Thread.sleep(timeLeft);
				
			} catch(InterruptedException ex) {
				System.out.println("Putting Thread to sleep went wrong in framework");
			}
		}
	}
	
	public void Draw(Graphics2D g2d)
	{
		switch(gameState)
		{
			case PLAYING:
				game.Draw(g2d);
				break;
			case VISUALIZING:
				break;
			case STARTING:
				break;
			case GAME_CONTENT_LOADING:
				g2d.setColor(Color.WHITE);
				g2d.drawString("Loading Level...", frameWidth / 2, frameHeight / 2);
				
			break;
		}
	}
	
	public void newGame()
	{
		gameTime = 0;
		lastTime = System.nanoTime();
		game = new Game();
	}
	
	//don't forget about restart game
	

}

package my_game_framework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game {

	//put scooting on hardrock as music
	
	private Player player; 
	private Enemy enemy;
	
	//maybe enemies later
	
	private BufferedImage backgroundImg;
	
	//private File backgroundPath = new File("C:\\Users\\Madox\\Pictures\\doubleDragonImg\\backgroundb.png");
	
	public Game()
	{
		Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
		
		Thread threadForInitGame = new Thread()
				{
					public void run()
					{
						
						LoadContent();
						Initialize();
						
						Framework.gameState = Framework.GameState.PLAYING;
					}
					
				};
				
				threadForInitGame.start();
	}
	
	public void Initialize()
	{
		player = new Player();
		enemy = new Enemy(300,300);
		
	}
	
	public void LoadContent()
	{
		try
		{
			backgroundImg = ImageIO.read(new File("Images\\mission1.png"));
		}
		catch(IOException ex) { System.out.print("Error getting file");}
	}
	
	public void UpdateGame(long gameTime)
	{
		player.Update();
		enemy.Update(player.x, player.y); // My son, make your fields private boy. Stop this witchcraft immediately and use getters and setters. 
		player.checkHit(enemy);
		enemy.checkHit(player);
	}
	
	public void Draw(Graphics2D g2d)
	{
		g2d.drawImage(backgroundImg, 0, 0, Framework.frameWidth + 600, Framework.frameHeight + 400, null);
		player.Draw(g2d);
		enemy.Draw(g2d);
		
		
	}
	
	
}

package my_game_framework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Game {

	private Random randCoordinate ;
	private Player player; 
	private ArrayList<Enemy> enemyList;
	private int enemyCount = 3;  //changes depending on level
	private int level;
	private int levelMax = 5;
	private boolean endOfLevel = false;
	
	//maybe enemies later
	
	private BufferedImage backgroundImg1;
	private BufferedImage backgroundImg2;
	private BufferedImage arrowImg;
	
	public Game()
	{
		//Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
		randCoordinate = new Random();
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
		level = 1;
		enemyList = new ArrayList<Enemy>();
		generateEnemies();
		
	}
	
	public void generateEnemies()
	{
		for(int i = 0; i < enemyCount; i++)
		{
			enemyList.add( new Enemy(  randCoordinate.nextInt(((500 - 400) + 1) + 200) , randCoordinate.nextInt(((389 - 300) + 1) + 300)) );
		}
	}
	
	public void LoadContent()
	{
		try
		{
			backgroundImg1 = ImageIO.read(new File("Images\\mission1.png"));
			backgroundImg2 = ImageIO.read(new File("Images\\mission2.png"));
			arrowImg = ImageIO.read(new File("Images\\arrow.png"));
		}
		catch(IOException ex) { System.out.print("Error getting file");}
	}
	
	public void UpdateGame(long gameTime)
	{
		player.Update();
		UpdateEnemies();
		player.checkHit(enemyList);
		isEnemyHit();
		
		if(enemyList.isEmpty())
		{
			endOfLevel = true;
			if(player.x >= 520 && level <= levelMax)
			{
				level++;
				endOfLevel = false;
				player.ResetPlayer();
				newLevel();
			}
		}
		
		
	}
	
	public void newLevel()
	{
		switch(level)
		{
			case 2:
				enemyCount = 5;
				break;
			case 3:
				enemyCount = 7;
				break;
			case 4:
				enemyCount = 9;
				break;
			case 5:
				enemyCount = 11;
				break;
		}
		enemyList = new ArrayList<Enemy>(enemyCount);
		generateEnemies();
	}
	
	public void UpdateEnemies()
	{
		for(Enemy em : enemyList)
		{
			em.Update(player.x, player.y);
		}
	}
	
	public void isEnemyHit()
	{
		for(int i = enemyList.size() - 1; i >= 0;  i--)
		{
			if(enemyList.get(i).checkHit(player))
				enemyList.remove(enemyList.get(i));
				
		}
	}
	
		
	public void Draw(Graphics2D g2d)
	{
		switch(level)
		{
			case 1:
				g2d.drawImage(backgroundImg1, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
				break;
			case 2:
				g2d.drawImage(backgroundImg2, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
				break;
		}
		
		if (endOfLevel)
		{
			g2d.drawImage(arrowImg, 510, 100, 30, 50, null);
		}
		
		player.Draw(g2d);
		
		for(Enemy em: enemyList)
		{
			em.Draw(g2d);
		}
		
	}
	
	
}

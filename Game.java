package my_game_framework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
*Game class interacts with the objects on the screen, and decides when the level is done
**/
public class Game {

	private Random randCoordinate ; //will be used to generate the coordinates of new enemies
	private Player player; //the player that will manipulated through the keyboard
	private ArrayList<Enemy> enemyList; 
	private int enemyCount = 3;  //changes depending on level
	private int level; //represents current level
	private int levelMax = 5;
	private boolean endOfLevel = false; //when all enemies have been defeated, this is set to true
	
	
	//list of backgrounds used during the course of the game (Only used two, but there are 5 levels
	private BufferedImage backgroundImg1;
	private BufferedImage backgroundImg2;
	
	
	//represents the arrow that will be shown once a stage is over
	private BufferedImage arrowImg;
	
	public Game()
	{
		Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING; //I'm working to get rid of this later
		randCoordinate = new Random();
		Thread threadForInitGame = new Thread() //Essentially: starts the game(Look at Playing case in Framework to understand)
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
	//loads up the game for the first time
	public void Initialize()
	{
		player = new Player();
		level = 1;
		enemyList = new ArrayList<Enemy>();
		generateEnemies(); //used to generate enemies based on the counter on enemyCount
		
	}
	
	//every new enemy generated will have a random coordinate(this doesn't exactly work as intetended right now, since the enemies display too far up)
	public void generateEnemies()
	{
		for(int i = 0; i < enemyCount; i++)
		{
			enemyList.add( new Enemy(  randCoordinate.nextInt(((500 - 400) + 1) + 200) , randCoordinate.nextInt(((389 - 300) + 1) + 300)) );
		}
	}
	//Creates the images that will be used
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
	//Framework calls it inside of its loop, if gameState is set to playing
	public void UpdateGame(long gameTime) //TBH, not sure why gameTime is passed here(Still have to edit gameTime)
	{
		player.Update();
		UpdateEnemies();
		player.checkHit(enemyList);
		isEnemyHit();
		
		if(enemyList.isEmpty())
		{
			endOfLevel = true;
			if(player.x >= 520 && level <= levelMax) //520 is just the outermost edge to the right(should probably be placed in a variable)
			{
				level++;
				endOfLevel = false;
				player.ResetPlayer();
				newLevel();
			}
		}
		
		
	}
	//"Resets the enemyList with different amount of enemies(I don't know if we want more enemies in the higher stages
	//I just had more enemies made for testing purposes
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
	
	//Every enemy gets updated(ie, they change position)
	public void UpdateEnemies()
	{
		for(Enemy em : enemyList)
		{
			em.Update(player.x, player.y);
		}
	}
	
	//iterates through enemies and checks if they're hit, if they're did (ie if checkHit returns true) remove that enemy from the enemyList, so that its no longer updating
	
	public void isEnemyHit()
	{
		for(int i = enemyList.size() - 1; i >= 0;  i--)
		{
			if(enemyList.get(i).checkHit(player))
				enemyList.remove(enemyList.get(i));
				
		}
	}
	
	//responsible for drawing the enemies to the screen	
	public void Draw(Graphics2D g2d)
	{
		switch(level) //draws a different background based on the level
		{
			case 1:
				g2d.drawImage(backgroundImg1, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
				break;
			case 2:
				g2d.drawImage(backgroundImg2, 0, 0, Framework.frameWidth, Framework.frameHeight, null);
				break;
		}
		
		if (endOfLevel) //draws the arrow(once all the enemies have been defeated
		{
			g2d.drawImage(arrowImg, 510, 100, 30, 50, null);
		}
		
		player.Draw(g2d);
		
		for(Enemy em: enemyList) //and of course draws the enemies
		{
			em.Draw(g2d);
		}
		
	}
	
	
}

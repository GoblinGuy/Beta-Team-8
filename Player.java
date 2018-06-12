package my_game_framework;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player {
	//sets the bounds that the player can move in
	private int upperY = 240;
	private int upperX = 520;
	private int lowerX = 40;
	//unfortunately I forgot to make  one for bottomY, so the player can still down offscreen
	
	//Responsible for deciding what frame to draw to the screen(used in Update,and Draw)
	private int frame = 0;
	
	//responsible for determining when a player is attacking, and direction
	public boolean isAttacking;
	public boolean facingLeft = false; 
	
	//meant to be used for attack animation cooldown(so that the player doesn't attack too soon)(subject to change)
	private long startTime = 0;
	private long endTime;
	private long coolDown = 100000000000l;
	
	//Coordinate of player
	public int x;
	public int y;
	
	//Images that will be used in animation
	BufferedImage playerImg, playerImg2, playerImg3, playerImgLeft, playerImgLeft2,playerImgLeft3, playerImgAttack, playerImgAttackLeft;
	
	//I didn't use these(but I'm guessing they could be for forcing the images to conform to a default size
	//private int playerWidth; 
	//private int playerHeight;
	
	//Look in the loadContent method(I just made this a separate thing for no reason)
	File playerPath = new File("Images\\billy.png");
	
	//responsible for initializing the player's coordinates, and images
	public Player()
	{
		Initialize();
		LoadContent();
		
	}

	private void Initialize()
	{
		ResetPlayer();
		
	}
	
	private void LoadContent()
	{
		try
		{
		   playerImg = ImageIO.read(playerPath);
		   playerImg2 = ImageIO.read(new File("Images\\billy2.png"));
		   playerImg3 = ImageIO.read(new File("Images\\billy3.png"));
		   playerImgLeft = ImageIO.read(new File ("Images\\billyLeft.png"));
		   playerImgLeft2 = ImageIO.read(new File("Images\\billyLeft2.png"));
		   playerImgLeft3 = ImageIO.read(new File("Images\\billyLeft3.png"));
		   playerImgAttack = ImageIO.read(new File("Images\\billyPunch.png"));
		   playerImgAttackLeft = ImageIO.read(new File("Images\\billyLeftPunch.png"));
		   playerWidth  = playerImg.getWidth();
		   playerHeight = playerImg.getHeight();
		}
		catch(IOException e)
		{
			System.out.println("Didn't load the image");
		}
	}
	
	public void ResetPlayer()
	{
		x = 200; 
		y = 300;
	}
	
	//Runs through enemyList, and sees if any of them are hitting player
	public void checkHit(ArrayList<Enemy> mob)
	{
		for(Enemy em : mob)
		{
			if(em.facingLeft != this.facingLeft && em.isAttacking == true && em.xDistancefromP <= 35 && em.yDistancefromP <= 3)
			{
				//System.out.println("I'm hit!");
			}
		}
		
	}
	
	//update player's position, or wether or not they're attacking through keyboard input
	public void Update()
	{
		
		if(!Panel.isKeyboardEmpty())
		{
			if(Panel.keyboardKeyState(KeyEvent.VK_J))
			{
				if(startTime != 0)
				{
					endTime = System.nanoTime();
					isAttacking = Math.abs(startTime - endTime) > coolDown;
					startTime = 0;
				}
				else
				{
					startTime = System.nanoTime();
					isAttacking = true;
				}
			}
			else
			{		
				isAttacking = false;
				if(Panel.keyboardKeyState(KeyEvent.VK_W) || Panel.keyboardKeyState(KeyEvent.VK_A) || Panel.keyboardKeyState(KeyEvent.VK_S) || Panel.keyboardKeyState(KeyEvent.VK_D) )
				{
					if(Panel.keyboardKeyState(KeyEvent.VK_W) && y > upperY)
					{
						
						y -= 5;
						
					}
						
					
					if(Panel.keyboardKeyState(KeyEvent.VK_A) && x > lowerX )
					{
						x-= 5;
						facingLeft = true;
					}
						
					
					if(Panel.keyboardKeyState(KeyEvent.VK_D) && x < upperX)
					{
						x+= 5;
						facingLeft = false;
					}
						
					
					if(Panel.keyboardKeyState(KeyEvent.VK_S))
					{
						y+= 5;
						
					}
					frame++;
					frame = frame % 6;
				}
				
			}
			
			
			
			
			
			
		}
			
	}
	
	public void Draw(Graphics2D g2d)
	{
		if (!facingLeft)
		{
			switch(frame)
			{
				case 0:
				case 1:
					g2d.drawImage(playerImg, x, y, null);
					break;
				case 2:
				case 3:
					g2d.drawImage(playerImg2, x, y, null);
					break;
				case 4:
				case 5:
					g2d.drawImage(playerImg3, x, y, null);
					break;
					
			}
			if(isAttacking)
			{
				g2d.drawImage(playerImgAttack, x, y, null);
				isAttacking = false;
			}
		}
		else
		{
			switch(frame)
			{
			case 0:
			case 1:
				g2d.drawImage(playerImgLeft, x, y, null);
				break;
			case 2:
			case 3:
				g2d.drawImage(playerImgLeft2, x, y, null);
				break;
			case 4:
			case 5:
				g2d.drawImage(playerImgLeft3, x, y, null);
				break;
			}
			if(isAttacking)
			{
				g2d.drawImage(playerImgAttackLeft, x, y, null);
				isAttacking = false;
			}
		}
			
		
		
	}
	
	
}

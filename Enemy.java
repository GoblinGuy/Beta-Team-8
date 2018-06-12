package my_game_framework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//pretty much a carbon copy of player except for update method, and the fact that it has a health bar
public class Enemy {
	
	
	public int x, y;
	private int health = 10;
	
	int frame = 0;
	
	private BufferedImage img;
	private BufferedImage img2, img3;
	private BufferedImage imgLeft, img2Left, img3Left,imgAttack, imgAttackLeft;
	
	public boolean facingLeft;
	public boolean isAttacking = false;
	
	
	//these find enemy's distance from player
	public int xDistancefromP;
	public int yDistancefromP;
	
	//enemy gets set coordinates from Game
	public Enemy(int x, int y)
	{
		Initialize(x, y);
		LoadContent();
		
	}
	
	public int getHealthCount()
	{
		return health;
	}
	
	private void Initialize(int x, int y)
	{
		resetEnemy(x, y);
	}
	
	private void  LoadContent()
	{
		try
		{
			img = ImageIO.read(new File("Images\\billy.png"));
			img2 = ImageIO.read(new File("Images\\billy2.png"));
			img3 = ImageIO.read(new File("Images\\billy3.png"));
			imgAttack = ImageIO.read(new File("Images\\billyPunch.png"));
			imgAttackLeft = ImageIO.read(new File("Images\\billyLeftPunch.png"));
			imgLeft = ImageIO.read(new File ("Images\\billyLeft.png"));
			img2Left = ImageIO.read(new File("Images\\billyLeft2.png"));
			img3Left = ImageIO.read(new File("Images\\billyLeft3.png"));
		}
		catch(IOException e)
		{
			System.out.print("Didn't work");
		}
	}
	
	/**
	public void resetEnemy(int x, int y) //might get rid of this method
	{
		health = 5;
		this.x = x;
		this.y = y;
		
	}
	**/
	//checkHit receives a copy of player and checks if its attacking, its direction, and distance
	public boolean checkHit (Player player) 
	{
		if(player.facingLeft != this.facingLeft && player.isAttacking && Math.abs(player.x - this.x) <= 35 && Math.abs(player.y - this.y) <= 5 )
		{
			health--;
			return (health <= 0);
		}
		return false;
	}
	
	//Updates the enemy's position
	public void Update(int x, int y)
	{
		xDistancefromP = Math.abs(this.x - x);
		yDistancefromP = Math.abs(this.y - y);
		//System.out.println("Enemy's xDistance: " + xDistancefromP + "  Enemy's yDistance: " + yDistancefromP);
		
		
		if((xDistancefromP > 20 || yDistancefromP > 3)) 
		{
			isAttacking = false;
			if(this.x > x)
			{
				this.x -= 3;
				facingLeft = true;
				
			}
			else
			{
				
				this.x+= 3;	
				facingLeft = false;
					
			}
				
			if(this.y > y)
			{
				this.y -= 3;
				
			}
			else
			{			
				this.y += 3;
					
			}
				
			frame++;
			frame = frame % 6;
			
		}
		else
		{
			isAttacking = true;
		}
		
		
	}
	
	public void Draw(Graphics2D g2d)
	{
		if(!facingLeft)
		{
			switch(frame)
			{
				case 0:	
				case 1:
					g2d.drawImage(img, x, y, null);
					break;
				case 2:	
				case 3:
					g2d.drawImage(img2, x, y, null);
					break;
				case 4:
				case 5:
					g2d.drawImage(img3, x, y, null);
					break;
			}
			if(isAttacking)
			{
				g2d.drawImage(imgAttack, x, y, null);
				isAttacking = false;
			}
		}
		else
		{
			switch(frame)
			{
				case 0:	
				case 1:
					g2d.drawImage(imgLeft, x, y, null);
					break;
				case 2:	
				case 3:
					g2d.drawImage(img2Left, x, y, null);
					break;
				case 4:
				case 5:
					g2d.drawImage(img3Left, x, y, null);
					break;
			}
			if(isAttacking)
			{
				g2d.drawImage(imgAttackLeft, x, y, null);
				isAttacking = false;
				
			}
		}
		
		
	}
}

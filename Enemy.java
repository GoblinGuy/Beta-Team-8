package my_game_framework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy {
	
	
	public int x, y;
	private int health = 5;
	
	int frame = 0;
	
	private BufferedImage img;
	private BufferedImage img2, img3;
	private BufferedImage imgLeft, img2Left, img3Left,imgAttack, imgAttackLeft;
	private boolean facingLeft;
	
	public boolean isAttacking = false;
	
	
	
	public int xDistancefromP;
	public int yDistancefromP;
	
	public Enemy(int x, int y)
	{
		Initialize(x, y);
		LoadContent();
		
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
	
	
	public void resetEnemy(int x, int y)
	{
		health = 5;
		this.x = x;
		this.y = y;
		
	}
	public void checkHit(Player player)
	{
		if(player.isAttacking && Math.abs(player.x - this.x) <= 35 && Math.abs(player.y - this.y) <= 5 )
		{
			health--;
			if(health <= 0)
			{
				resetEnemy(300, 350);
			}
		}
	}

	public void Update(int x, int y)
	{
		xDistancefromP = Math.abs(this.x - x);
		yDistancefromP = Math.abs(this.y - y);
		System.out.println("Enemy's xDistance: " + xDistancefromP + "  Enemy's yDistance: " + yDistancefromP);
		
		
		if((xDistancefromP > 35 || yDistancefromP > 3)) //y doesn't change, even if x does 
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
			frame = frame % 4;
			
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

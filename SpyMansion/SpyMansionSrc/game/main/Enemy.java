package game.main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends GameObject{
		int xcoord;
		int ycoord;
		String type;
		int row;
		int col;
		BufferedImage img;
		String path = "SpyMansionSrc\\game\\main\\guard.png";
		public Enemy(int r, int c, int x, int y, ID id) {
			super(r,c,x,y,id);
			try{
				File file = new File(path);
				System.out.println(file.getPath());
				System.out.println(file.getCanonicalPath());
			    img = ImageIO.read(new File(path));
			}
			catch ( IOException exc ){
			    exc.printStackTrace();
			}
		}
		
		public int getXcoord() {
			return xcoord;
		}
		public int getYcoord() {
			return ycoord;
		}
		public void setXcoord(int x)
		{
			xcoord = x;
		}
		public void setYcoord(int y)
		{
			ycoord = y;
		}
		public String getType() {
			return type;
		}
		public int move() {
			int move = (int)(Math.random()*4+1);
			if(move == 1)
				if (this.x < 607) {
					this.x = x + 32;
					//col = col +1;
					return 1;
					}	
				else move = (int)(Math.random()*4+1);
			if(move == 2)	
				if ( this.x > 32 ) {
					this.x = x - 32;
					//col = col -1;
					return 2;
					}
				else move = (int)(Math.random()*4+1);
			if(move == 3)
				if (this.y > 32) {
					this.y = y - 32;
					//row = row -1;
					return 3;
					}
				else move = (int)(Math.random()*4+1);
			if(move == 4)
				if (this.y < 575) {
					this.y = y + 32;
					//row = row + 1;
					return 4;
					}
				//else move = (int)(Math.random()*4+1);
			return move;
		}
		public void setHealth(int x)
		{
			health = x;
		}

		public int getRow()
		{
			return row;
		}
		public void setRow(int r)
		{
			row = r;
		}
		public void setCol(int c)
		{
			col = c;
		}
		public int getCol()
		{
			return col;
		}
		
		@Override
		public void tick() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void render(Graphics g) {
			g.drawImage(img, x, y , 32, 32, null);
			
		}

	}
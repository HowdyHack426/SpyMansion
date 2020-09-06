package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Empty extends GameObject {
	BufferedImage img;
	
	public Empty(int r, int c, int x, int y, ID id) {
		super(r, c, x, y, id);
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void setRow(int r)
	{
		row = r;
	}
	public void setCol(int c)
	{
		col = c;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		
	}

}

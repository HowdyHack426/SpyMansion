package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends GameObject {
	int xcoord;
	int ycoord;
	String type;
	BufferedImage img;
	String path = "SpyMansionSrc\\game\\main\\key.png";
	public Key(int r, int c, int x, int y, ID id) {
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
	public void setRow(int r)
	{
		row = r;
	}
	public int getRow()
	{
		return row;
	}
	public void setCol(int c)
	{
		col = c;
	}
	public int getCol() {
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

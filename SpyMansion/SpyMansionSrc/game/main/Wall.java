package game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends GameObject{
	String type;
	static int row;
	static int col;
	BufferedImage img;
	String path = "SpyMansionSrc\\game\\main\\wall_tile1.png";
	public Wall(int r, int c, int x, int y, ID id) {
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
		g.drawImage(img, x+ 32, y + 32, 32, 32, null);
		
	}
}

package game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObject{
	int x;
	int y;
	ID id;
	boolean key;
	BufferedImage img;
	String path = "SpyMansionSrc\\game\\main\\spy_char.png";


	public Player(int r, int c, int x, int y, ID id) {
		super(r,c,x, y, id);
		key = false;
//		mat[r][c] = "Player";
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
	
	public void moveR() {
		if ( this.x < 607) {
			//System.out.println("PLAYER :: " + getRow() + " " + getCol());
			this.x = x + 32;
			
		}
		
	}
	public void moveL() {
		if ( this.x >= 32 ) {
			//System.out.println("PLAYER :: " + getRow() + " " + getCol());
			this.x = x - 32;
			
		}
	}
	public void moveU() {
		if (this.y > 0) {
			//System.out.println("PLAYER :: " + getRow() + " " + getCol());
			this.y = y - 32;
			
		}
	}
	public void moveD() {
		if (this.y < 607) {
			//System.out.println("PLAYER :: " + getRow() + " " + getCol());
			this.y = y + 32;
			
		}
	}
	public void setID(ID id) {
		
	}
	public boolean hasKey() {
		return key;
	}
	public void setKey(boolean x) {
		this.key = x;
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
	public int getCol()
	{
		return col;
	}
	@Override
	public void tick() {
	}
	@Override
	public void render(Graphics g) {
//		g.setColor(Color.white);
		g.drawImage(img, x+ 32, y + 32, 32, 32, null);
//		g.fillRect(x, y, 40, 40);
	}


}

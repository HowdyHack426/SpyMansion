package game.main;

import java.awt.Graphics;

public abstract class GameObject {
	
	protected int x, y, row, col;
	protected ID id;
	protected String status;
	protected int health;
	public GameObject(int r, int c, int x, int y, ID id) {
		this.row = r;
		this.col = c;		
		this.x = x;
		this.y = y;
		this.id = id;
		this.health = 100;
		status = "NOT EMPTY";
		
		
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public ID getId() {
		return id;
	}
	public String getStatus()
	{
		return status;
	}
}

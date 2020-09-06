package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	static Player bob;
	static Key key;
	static Enemy jim;
	static GameObject[][] mat;
	static int floorNumber;
	String path = "SpyMansionSrc\\game\\main\\mansion1.png";
	File file = new File(path);
	public Game() throws FileNotFoundException {
		Window(WIDTH, HEIGHT, "Spy Mansion", this);
		handler = new Handler();
//		String[][] map = new String [20][20];
//		String path = "SpyMansionSrc\\game\\main\\Layout.txt";
//		Scanner file = new Scanner (new File(path));
//		
//			int l = file.nextInt();
//			file.nextLine();
//			for(int x = 0 ; x < l ; x++)
//				{
//					String line = file.nextLine();
//					System.out.println(x + " LINE :: " + line);
//					map[x] = line.split(" ");
//				}
//			
//			
//			for(int i = 0 ; i < map.length ; i++)
//			{
//				System.out.println();
//				for(int j = 0 ; j < map.length ; j++)
//					{
////						System.out.println("I :: " + i + " J ::  " + j);
//						if(map[i][j].equals("K"))
//							{
////								System.out.println("KEYS X AND Y :: " + i + " " + j);
//								key = new Key(i, j, j*32, i*32, ID.Key);
//								mat[i][j] = key;
//								handler.addObject(key);
//							}
//						if(map[i][j].equals("P"))
//						{
//							bob = new Player(i, j, j*32, i*32, ID.Player);
////							System.out.println("PLAYERS X AND Y " + i + " " + j);
////							System.out.println("BOB ROW :: " + bob.getRow() + " BOB COL :: " + bob.getCol());
//							mat[i][j] = bob;
//							handler.addObject(bob);
//						}
//						if(map[i][j].equals("E"))
//						{
//							jim = new Enemy(i, j, j*32, i*32, ID.Enemy);
//							jim.setRow(i);
//							jim.setCol(j);
////							System.out.println("JIM ROW :: " + jim.getRow() + " AND JIM COL :: " + jim.getCol());
//							mat[i][j] = jim;
////							System.out.println("ENEMIES X AND Y " + i + " " + j);
//							handler.addObject(jim);
//						}
//						if(map[i][j].equals("."))
//						{
//							mat[i][j] = new Empty(0,0,0,0,ID.Empty);
//						}
//					}
//			}
		
		//create a double array of Empty Objects
		mat = new GameObject[20][20];
		for(int i = 0 ; i < mat.length ; i++) {
			for(int j = 0 ; j < mat[0].length ; j++) {
				 Empty newEmpty= new Empty(i,j,i*32,j*32,ID.Empty);
				 newEmpty.setRow(i);
				 newEmpty.setCol(j);
				 mat[i][j] = newEmpty;
				 handler.addObject(newEmpty);
			}
		}
		
		//create new player variable
		bob = new Player(2, 2, 64, 64, ID.Player);
		bob.setRow(2);
		bob.setCol(2);
		mat[2][2] = bob;
		handler.addObject(bob);
		//System.out.println(mat[1][1]);
		//create enemy variable
		jim = new Enemy(16, 6, 128,480, ID.Enemy);
		jim.setRow(16);
		jim.setCol(5);
		mat[16][5] = jim;
		
		//System.out.println("JIM ROW :: " + jim.getRow() + " AND JIM COL :: " + jim.getCol());
		handler.addObject(jim);
		
		//create key variable
		key = new Key(8, 13, 384, 224, ID.Key);
		handler.addObject(key);
		mat[key.getRow()][key.getCol()] = key;
		//System.out.println(mat[2][1]);
	}

	
	public static final int WIDTH = 640, HEIGHT = 640/*WIDTH / 12 * 9*/;
	
	private Thread thread;
	private boolean running = false;
	
	private static Handler handler;
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		//int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			//frames++;
			//periodically prints the FPS of the window
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
				//frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		BufferedImage img;
		

		Graphics g = bs.getDrawGraphics();
		
		try{
		    img = ImageIO.read(file);
		    g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		}
		catch ( IOException exc ){
		    exc.printStackTrace();
		}
		
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) throws FileNotFoundException {
		new Game();
	}
	
	
	
	
	
	
	
	
private static final long serialVersionUID = 492636734070584756L;
	
	public void Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		//frame.setVisible(true);

		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent we) {
	        System.exit(0);
	      }
		});

		frame.addKeyListener(new KeyAdapter() {
		      public void keyTyped(KeyEvent ke) {
		        char keyChar = ke.getKeyChar();
		        if (keyChar == 'w') {
		        	if(mat[bob.getRow()-1][bob.getCol()].getId() != ID.Wall) {
			        	int num = jim.move();
			        	if(num == 1 && jim.getCol() + 1 < 19) {
			        		if(mat[jim.getRow()][jim.getCol() + 1].getId() != ID.Wall) {
			        			mat[jim.getRow()][jim.getCol() + 1] = jim;
				        		jim.setCol(jim.getCol() + 1);
				        		mat[jim.getRow()][jim.getCol() - 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        		
			        	}else if(num == 2 && jim.getCol() - 1 > 0) {
			        		if(mat[jim.getRow()][jim.getCol() - 1].getId() != ID.Wall) {
				        		mat[jim.getRow()][jim.getCol() - 1] = jim;
				        		jim.setCol(jim.getCol() - 1);
				        		mat[jim.getRow()][jim.getCol() + 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 3 && jim.getRow() - 1 > 0) {
			        		if(mat[jim.getRow()-1][jim.getCol()].getId() != ID.Wall) {
				        		mat[jim.getRow() - 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() - 1);
				        		mat[jim.getRow() + 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 4 && jim.getRow() + 1 < 19){
			        		if(mat[jim.getRow()+1][jim.getCol() + 1].getId() != ID.Wall) {
				        		mat[jim.getRow() + 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() + 1);
				        		mat[jim.getRow() - 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}
			        	System.out.println(jim.getRow() + "," + jim.getCol());
			        	if(bob.getRow()-1 >= 0)
			        	{
			        		if(mat[bob.getRow()-1][bob.getCol()].getId() == ID.Empty)
			        		{
			        			bob.moveU();
	//		        			System.out.println("BOB ROW BEFORE MOVE UP ::  "+ bob.getRow());
			        			mat[bob.getRow()-1][bob.getCol()] = bob;
			        			bob.setRow(bob.getRow()-1);
	//		        			System.out.println("BOB ROW AFTER MOVE UP :: " + bob.getRow());
			        			mat[bob.getRow()+1][bob.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		
			        		}else if(mat[bob.getRow()-1][bob.getCol()].getId() == ID.Key)
					        	{
					        		bob.moveU();
	//				        		System.out.println("BOB ROW BEFORE MOVE UP ::  "+ bob.getRow());
					        		mat[bob.getRow()-1][bob.getCol()] = bob;
					        		bob.setRow(bob.getRow()-1);
	//				        		System.out.println("BOB ROW BEFORE MOVE UP ::  "+ bob.getRow());
					        		mat[bob.getRow()+1][bob.getCol()] = new Empty(0,0,0,0,ID.Empty);
					        		handler.removeObject(key);
					        		
					        		bob.setKey(true);
					        		
					        	}else if(mat[bob.getRow()-1][bob.getCol()].getId() == ID.Wall) {
					        		
					        	}
	//		        		else if(mat[bob.getRow()-1][bob.getCol()].getId() == ID.Enemy) {
	//				        		bob.moveU();
	//				        		bob.setRow(bob.getRow()-1);
	//			        			mat[bob.getRow()+1][bob.getCol()] = new Empty(0,0,0,0,ID.Empty);
	//			        			System.out.println("BRUH");
	//				        		System.exit(0);
	//				        		frame.setVisible(false);
	//				        	}
			        		System.out.println(bob.getRow() + "," + bob.getCol());
			        }
			        if((bob.getRow() >= jim.getRow()-1 && bob.getRow() <= jim.getRow() + 1) && (bob.getCol() >= jim.getCol() -1 && bob.getCol() <= jim.getCol() + 1))
			        	{
			        		System.out.println("GAME OVER, THE GHOST TOOK YOUR LIFE!");
			        		System.exit(0);
			        		frame.setVisible(false);
			        	}
			        }
		        }
		        if(keyChar == 'a') {
		        	if(mat[bob.getRow()][bob.getCol()-1].getId() != ID.Wall) {
			        	int num = jim.move();
			        	if(num == 1 && jim.getCol() + 1 < 19) {
			        		if(mat[jim.getRow()][jim.getCol() + 1].getId() != ID.Wall) {
			        			mat[jim.getRow()][jim.getCol() + 1] = jim;
				        		jim.setCol(jim.getCol() + 1);
				        		mat[jim.getRow()][jim.getCol() - 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        		
			        	}else if(num == 2 && jim.getCol() - 1 > 0) {
			        		if(mat[jim.getRow()][jim.getCol() - 1].getId() != ID.Wall) {
				        		mat[jim.getRow()][jim.getCol() - 1] = jim;
				        		jim.setCol(jim.getCol() - 1);
				        		mat[jim.getRow()][jim.getCol() + 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 3 && jim.getRow() - 1 > 0) {
			        		if(mat[jim.getRow()-1][jim.getCol()].getId() != ID.Wall) {
				        		mat[jim.getRow() - 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() - 1);
				        		mat[jim.getRow() + 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 4 && jim.getRow() + 1 < 19){
			        		if(mat[jim.getRow()+1][jim.getCol() + 1].getId() != ID.Wall) {
				        		mat[jim.getRow() + 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() + 1);
				        		mat[jim.getRow() - 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}
			        	System.out.println(jim.getRow() + "," + jim.getCol());
			        	if(bob.getCol()-1 >= 0)
			        	{
			        		if(mat[bob.getRow()][bob.getCol()-1].getId() == ID.Empty)
			        		{
	//		        			System.out.println("BOB COL BEFORE MOVE LEFT ::  "+ bob.getCol());
			        			bob.moveL();
			        			//jim.move();
			        			mat[bob.getRow()][bob.getCol()-1] = bob;
			        			bob.setCol(bob.getCol()-1);
	//		        			System.out.println("BOB COL BEFORE MOVE LEFT ::  "+ bob.getCol());
			        			mat[bob.getRow()][bob.getCol()+1] = new Empty(0,0,0,0,ID.Empty);
			        			
			        		}else if(mat[bob.getRow()][bob.getCol()-1].getId() == ID.Key)
			        			{
	//		        			System.out.println("BOB COL BEFORE LEFT ::  "+ bob.getCol());
					        		bob.moveL();
					        		//jim.move();
					        		mat[bob.getRow()][bob.getCol()-1] = bob;
					        		bob.setCol(bob.getCol()-1);
	//				        		System.out.println("BOB COL AFTER MOVE LEFT ::  "+ bob.getCol());
					        		mat[bob.getRow()][bob.getCol()+1] = new Empty(0,0,0,0,ID.Empty);
					        		handler.removeObject(key);
					        		
					        		bob.setKey(true);
					        	
			        			}else if(mat[bob.getRow()][bob.getCol()-1].getId() == ID.Wall) {
					        		
					        	}
	//		        		else if(mat[bob.getRow()][bob.getCol() - 1].getId() == ID.Enemy) {
	//				        		bob.moveU();
	//				        		bob.setCol(bob.getCol()-1);
	//			        			mat[bob.getRow()][bob.getCol() + 1] = new Empty(0,0,0,0,ID.Empty);
	//			        			System.out.println("BRUH");
	//				        		System.exit(0);
	//				        		frame.setVisible(false);
	//				        	}
			        		System.out.println(bob.getRow() + "," + bob.getCol());
			        }
			        	if((bob.getRow() >= jim.getRow()-1 && bob.getRow() <= jim.getRow() + 1) && (bob.getCol() >= jim.getCol() -1 && bob.getCol() <= jim.getCol() + 1))
			        	{
			        		System.out.println("GAME OVER, THE GHOST TOOK YOUR LIFE!");
			        		System.exit(0);
			        		frame.setVisible(false);
			        	}
				    }
		        }
		        if (keyChar == 's') {
		        	if(mat[bob.getRow()+1][bob.getCol()].getId() != ID.Wall) {
			        	int num = jim.move();
			        	if(num == 1 && jim.getCol() + 1 < 19) {
			        		if(mat[jim.getRow()][jim.getCol() + 1].getId() != ID.Wall) {
			        			mat[jim.getRow()][jim.getCol() + 1] = jim;
				        		jim.setCol(jim.getCol() + 1);
				        		mat[jim.getRow()][jim.getCol() - 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        		
			        	}else if(num == 2 && jim.getCol() - 1 > 0) {
			        		if(mat[jim.getRow()][jim.getCol() - 1].getId() != ID.Wall) {
				        		mat[jim.getRow()][jim.getCol() - 1] = jim;
				        		jim.setCol(jim.getCol() - 1);
				        		mat[jim.getRow()][jim.getCol() + 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 3 && jim.getRow() - 1 > 0) {
			        		if(mat[jim.getRow()-1][jim.getCol()].getId() != ID.Wall) {
				        		mat[jim.getRow() - 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() - 1);
				        		mat[jim.getRow() + 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 4 && jim.getRow() + 1 < 19){
			        		if(mat[jim.getRow()+1][jim.getCol() + 1].getId() != ID.Wall) {
				        		mat[jim.getRow() + 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() + 1);
				        		mat[jim.getRow() - 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}
			        	System.out.println(jim.getRow() + "," + jim.getCol());
			        	if(bob.getRow()+1 < 19)
			        	{
			        		if(mat[bob.getRow()+1][bob.getCol()].getId() == ID.Empty)
			        		{
	//		        			System.out.println("BOB ROW BEFORE MOVE DOWN::  "+ bob.getRow());
			        			bob.moveD();
			        			//jim.move();
			        			mat[bob.getRow()+1][bob.getCol()] = bob;
			        			bob.setRow(bob.getRow()+1);
	//		        			System.out.println("BOB ROW AFTER MOVE DOWN ::  "+ bob.getRow());
			        			mat[bob.getRow()-1][bob.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        			
			        		}else if(mat[bob.getRow()+1][bob.getCol()].getId() == ID.Key)
				        		{
	//		        			System.out.println("BOB ROW BEFORE MOVE DOWN ::  "+ bob.getRow());
				        		bob.moveD();
				        		//jim.move();
				        		mat[bob.getRow()+1][bob.getCol()] = bob;
				        		bob.setRow(bob.getRow()+1);
	//			        		System.out.println("BOB ROW AFTER MOVE DOWN ::  "+ bob.getRow());
				        		mat[bob.getRow()-1][bob.getCol()] = new Empty(0,0,0,0,ID.Empty);
				        		handler.removeObject(key);
				        		bob.setKey(true);
				        	
				        	}else if(mat[bob.getRow()+1][bob.getCol()].getId() == ID.Wall) {
				        		
				        	}
	//		        		else if(mat[bob.getRow()+1][bob.getCol()].getId() == ID.Enemy) {
	//			        		bob.moveU();
	//			        		bob.setRow(bob.getRow()+1);
	//		        			mat[bob.getRow()-1][bob.getCol()] = new Empty(0,0,0,0,ID.Empty);
	//		        			System.out.println("BRUH");
	//			        		System.exit(0);
	//			        		frame.setVisible(false);
	//			        	}
			        		System.out.println(bob.getRow() + "," + bob.getCol());
			        	}
			        	if((bob.getRow() >= jim.getRow()-1 && bob.getRow() <= jim.getRow() + 1) && (bob.getCol() >= jim.getCol() -1 && bob.getCol() <= jim.getCol() + 1))
			        	{
			        		System.out.println("GAME OVER, THE GHOST TOOK YOUR LIFE!");
			        		System.exit(0);
			        		frame.setVisible(false);
			        	}
				    }
		        }
		        if(keyChar == 'd') {
		        	if(mat[bob.getRow()][bob.getCol() + 1].getId() != ID.Wall) {
			        	int num = jim.move();
			        	if(num == 1 && jim.getCol() + 1 < 19) {
			        		if(mat[jim.getRow()][jim.getCol() + 1].getId() != ID.Wall) {
			        			mat[jim.getRow()][jim.getCol() + 1] = jim;
				        		jim.setCol(jim.getCol() + 1);
				        		mat[jim.getRow()][jim.getCol() - 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        		
			        	}else if(num == 2 && jim.getCol() - 1 > 0) {
			        		if(mat[jim.getRow()][jim.getCol() - 1].getId() != ID.Wall) {
				        		mat[jim.getRow()][jim.getCol() - 1] = jim;
				        		jim.setCol(jim.getCol() - 1);
				        		mat[jim.getRow()][jim.getCol() + 1] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 3 && jim.getRow() - 1 > 0) {
			        		if(mat[jim.getRow()-1][jim.getCol()].getId() != ID.Wall) {
				        		mat[jim.getRow() - 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() - 1);
				        		mat[jim.getRow() + 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}else if(num == 4 && jim.getRow() + 1 < 19){
			        		if(mat[jim.getRow()+1][jim.getCol() + 1].getId() != ID.Wall) {
				        		mat[jim.getRow() + 1][jim.getCol()] = jim;
				        		jim.setRow(jim.getRow() + 1);
				        		mat[jim.getRow() - 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
			        		}
			        	}
			        	System.out.println(jim.getRow() + "," + jim.getCol());
			        	if(bob.getCol()+1 < 19)
			        	{
			        	if(mat[bob.getRow()][bob.getCol()+1].getId() == ID.Empty)
			        		{
			        			bob.moveR();
	//		        			System.out.println("BOB ROW BEFORE MOVE RIGHT ::  "+ bob.getCol());
			        			//jim.move();
			        			mat[bob.getRow()][bob.getCol()+1] = bob;
			        			bob.setCol(bob.getCol()+1);
	//		        			System.out.println("BOB ROW AFTER MOVE RIGHT ::  "+ bob.getCol());
			        			mat[bob.getRow()][bob.getCol()-1] = new Empty(0,0,0,0,ID.Empty);
			        			
			        		}else if(mat[bob.getRow()][bob.getCol()+1].getId() == ID.Key)
				        		{
				        			bob.moveR();
	//			        			System.out.println("BOB ROW BEFORE MOVE RIGHT ::  "+ bob.getCol());
				        			//jim.move();
					        		mat[bob.getRow()][bob.getCol()+1] = bob;
					        		bob.setCol(bob.getCol()+1);
	//				        		System.out.println("BOB ROW AFTER MOVE RIGHT::  "+ bob.getCol());
					        		mat[bob.getRow()][bob.getCol()-1] = new Empty(0,0,0,0,ID.Empty);
					        		handler.removeObject(key);
					        		bob.setKey(true);
					        	}else if(mat[bob.getRow()][bob.getCol() + 1].getId() == ID.Wall) {
					        		
					        	}
	//				        		else if(mat[bob.getRow()][bob.getCol() + 1].getId() == ID.Enemy) {
	////				        		bob.moveU();
	////				        		bob.setCol(bob.getCol()+1);
	////			        			mat[bob.getRow()][bob.getCol() - 1] = new Empty(0,0,0,0,ID.Empty);
	////			        			System.out.println("BRUH");
	////				        		System.exit(0);
	////				        		frame.setVisible(false);
	//				        	}
			        	
			        	System.out.println(bob.getRow() + "," + bob.getCol());
				    }
			        	if((bob.getRow() >= jim.getRow()-1 && bob.getRow() <= jim.getRow() + 1) && (bob.getCol() >= jim.getCol() -1 && bob.getCol() <= jim.getCol() + 1))
			        	{
			        		System.out.println("GAME OVER, THE GHOST TOOK YOUR LIFE!");
			        		System.exit(0);
			        		frame.setVisible(false);
			        	}
			        }
		        }
		        
		        if(bob.hasKey() && bob.getRow() == 17 && bob.getCol() == 17) {
		        	System.out.println("HOOOray, you infiltrated the ghost mansion!");
		        	System.exit(0);
		        	frame.setVisible(false);
		        }
		        if(keyChar == 'e') {
		        	int num = jim.move();
		        	if(num == 1 && jim.getCol() + 1 < 19) {
		        		if(mat[jim.getRow()][jim.getCol() + 1].getId() != ID.Wall) {
		        			mat[jim.getRow()][jim.getCol() + 1] = jim;
			        		jim.setCol(jim.getCol() + 1);
			        		mat[jim.getRow()][jim.getCol() - 1] = new Empty(0,0,0,0,ID.Empty);
		        		}
		        		
		        	}else if(num == 2 && jim.getCol() - 1 > 0) {
		        		if(mat[jim.getRow()][jim.getCol() - 1].getId() != ID.Wall) {
			        		mat[jim.getRow()][jim.getCol() - 1] = jim;
			        		jim.setCol(jim.getCol() - 1);
			        		mat[jim.getRow()][jim.getCol() + 1] = new Empty(0,0,0,0,ID.Empty);
		        		}
		        	}else if(num == 3 && jim.getRow() - 1 > 0) {
		        		if(mat[jim.getRow()-1][jim.getCol()].getId() != ID.Wall) {
			        		mat[jim.getRow() - 1][jim.getCol()] = jim;
			        		jim.setRow(jim.getRow() - 1);
			        		mat[jim.getRow() + 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
		        		}
		        	}else if(num == 4 && jim.getRow() + 1 < 19){
		        		if(mat[jim.getRow()+1][jim.getCol() + 1].getId() != ID.Wall) {
			        		mat[jim.getRow() + 1][jim.getCol()] = jim;
			        		jim.setRow(jim.getRow() + 1);
			        		mat[jim.getRow() - 1][jim.getCol()] = new Empty(0,0,0,0,ID.Empty);
		        		}
		        	}
		       		if((bob.getRow() >= jim.getRow()-1 && bob.getRow() <= jim.getRow() + 1) && (bob.getCol() >= jim.getCol() -1 && bob.getCol() <= jim.getCol() + 1))
		        		{
			        		System.out.println("GAME OVER, THE GHOST TOOK YOUR LIFE!");
			        		System.exit(0);
			        		frame.setVisible(false);
			        	
			        	}
		        	}
		  	}
			});
		start();
	}
	
}


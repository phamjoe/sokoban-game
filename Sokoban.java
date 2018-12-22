//Name: Joseph Pham
//ID: 500500254
//Course: CPS209
//Sokoban Assignment

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Graphics.*;
import javax.imageio.ImageIO;


public class Sokoban extends JPanel
	{
	
 	private Image i1,i2,i3,i4,i5,i6,i7;

  	private int count = 0;
	private Contents[][] arr;
	boolean UP, RIGHT, LEFT, DOWN;
	private LevelReader lr;
	private JLabel lab;
	int a=0,b=0,c=0,d=0;
	int curr;
	int level;
	boolean isReady=false;
  	
	

	public Sokoban(String fileName)
	{
	lr= new LevelReader();
	level=lr.readLevels(fileName);
	
	this.addKeyListener(new MyKeyListener());
	this.setFocusable(true);
	this.requestFocus();
	this.setPreferredSize(new Dimension(1000,1000));
	this.setBorder(BorderFactory.createEtchedBorder());
	this.initLevel(0);
	checkWin();
	isReady=true;

	i1 = Toolkit.getDefaultToolkit().getImage("Character.png");

        i2 = Toolkit.getDefaultToolkit().getImage("Box.png");

        i3 = Toolkit.getDefaultToolkit().getImage("goal.png");

        i4 = Toolkit.getDefaultToolkit().getImage("wall.png");

	i5 = Toolkit.getDefaultToolkit().getImage("chargoal.png");

        i6 = Toolkit.getDefaultToolkit().getImage("boxgoal.png");

	lab = new JLabel("Move Counter = 0");
	lab.setFont(new Font("Serif", Font.BOLD, 20));
        lab.setBorder(BorderFactory.createEtchedBorder());
        this.add(lab);
        
	}
	

	public void initLevel(int level){
	
		int x= lr.getWidth(level);
		int y= lr.getHeight(level);
		int i, j;
		arr=new Contents[x][y];
	
	for(i=0;i < x; i++)
	{
		for(j=0; j < y;j++)
		{
		
		Contents tile=lr.getTile(level,i,j);
		arr[i][j]=tile;
		
		}
	}
	
	repaint();
	}



	public void paintComponent(Graphics g){
	
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	

	
	if(isReady)
	{
		int width=50, height=50;
		int x=0, y=0;

	
	for(int i=0;i < arr.length;i++)
	{
	
		for(int j=0; j < arr[i].length;j++)
		{

		x = i * width; y = j * height;
		
			switch(arr[i][j]){
			case PLAYER:
			g2.drawImage(i1, x, y, this);
			break;
			
			case BOX:
			g2.drawImage(i2, x, y, this);
			break;
			
			case GOAL:
			g2.drawImage(i3, x, y, this);
			break;
		
			case WALL:
			g2.drawImage(i4, x, y, this);
			break;

			case PLAYERONGOAL:
			g2.drawImage(i5, x, y, this);
			break;

			case BOXONGOAL:
			g2.drawImage(i6, x, y, this);
			break;
			
			case EMPTY:
			g2.setColor(Color.WHITE);
			g2.fill(new Rectangle2D.Double(x, y, width, height));
			g2.draw(new Rectangle2D.Double(x,y,width,height));
			break;
			
					}


						}
					}
					
	}
	
	}

	
	public class MyKeyListener implements KeyListener{ 
	public void keyPressed(KeyEvent ke){

	
	switch(ke.getKeyCode()){
	case KeyEvent.VK_UP:
	UP = true;
	isCollision(0,-1);
	count++;
	lab.setText("Move Counter = " + count);

	break;
	
	case KeyEvent.VK_DOWN:
	DOWN = true;
	isCollision(0,1);
	count++;
	lab.setText("Move Counter = " + count);
	break;
	
	case KeyEvent.VK_RIGHT:
	RIGHT = true;
	isCollision(1,0);
	count++;
	lab.setText("Move Counter = " + count);
	break;
	
	case KeyEvent.VK_LEFT:
	LEFT = true;
	isCollision(-1,0);
	count++;
	lab.setText("Move Counter = " + count);
	break;


	case KeyEvent.VK_N:
	
	if(curr == level-1){
	initLevel(0);
	curr=0;
	count = 0;
	lab.setText("Move Counter = 0" );
	repaint();
	}

	else{
	initLevel(++curr);
	count = 0;
	lab.setText("Move Counter = 0" );
	repaint();
	}
	break;
	
	case KeyEvent.VK_R:
	initLevel(curr);
	count = 0;
	lab.setText("Move Counter = 0" );
	repaint();
	break;
	}
	
	}

        
	public void keyReleased(KeyEvent ke) {}
	public void keyTyped(KeyEvent ke) {}
	}
	
	public void isCollision(int xC,int yC){
	
	int x=0, y=0;
	outer:
	for(x=0;x<arr.length;x++){
		for(y=0;y<arr[x].length;y++){
			if(arr[x][y]==Contents.PLAYER || arr[x][y]==Contents.PLAYERONGOAL){
			a=x;
			b=y;
	break outer;
			}
		}
	}
	

	if(UP)
	{
		if(arr[x][y-1]!=Contents.WALL && arr[x][y-1]!=Contents.BOX && arr[x][y-1]!=Contents.BOXONGOAL)
		{
			movePlayer(xC,yC);
		
		}
	
	try{
	
	if(arr[x][y-1]==Contents.BOX || arr[x][y-1]==Contents.BOXONGOAL)
	{
		if(arr[x][y-2]!=Contents.WALL && arr[x][y-2]!=Contents.BOX && arr[x][y-2]!=Contents.BOXONGOAL){
			c=x;
			d=y-1;
			moveBox(xC,yC);
	
		}
	}
	}
	
	catch( ArrayIndexOutOfBoundsException e){}

	}
	if(DOWN)
	{
		if(arr[x][y+1]!=Contents.WALL && arr[x][y+1]!=Contents.BOX && arr[x][y+1]!=Contents.BOXONGOAL){
			movePlayer(xC,yC);
		}
	try
	{
		if(arr[x][y+1]==Contents.BOX || arr[x][y+1]==Contents.BOXONGOAL)
		{
			if(arr[x][y+2]!=Contents.WALL && arr[x][y+2]!=Contents.BOX && arr[x][y+2]!=Contents.BOXONGOAL)
			{
				c=x;
				d=y+1;
				moveBox(xC,yC);
			}
		}
	}
	
	catch( ArrayIndexOutOfBoundsException e){}
	
	}

	
	if(RIGHT)
	{
		if(arr[x+1][y]!=Contents.WALL && arr[x+1][y]!=Contents.BOX && arr[x+1][y]!=Contents.BOXONGOAL )
		{
			movePlayer(xC,yC);
		}
	try
	{
		if(arr[x+1][y]==Contents.BOX || arr[x+1][y]==Contents.BOXONGOAL)
		{
			if(arr[x+2][y]!=Contents.WALL && arr[x+2][y]!=Contents.BOX && arr[x+2][y]!=Contents.BOXONGOAL )
			{
				c=x+1;
				d=y;
				moveBox(xC,yC);
			}
		}
	}

	
	catch( ArrayIndexOutOfBoundsException e){}
	}

	
	if(LEFT)
	{
		if(arr[x-1][y]!=Contents.WALL && arr[x-1][y]!=Contents.BOX && arr[x-1][y]!=Contents.BOXONGOAL )
		{
			movePlayer(xC,yC);
		}
	try{
	
	if(arr[x-1][y]==Contents.BOX || arr[x-1][y]==Contents.BOXONGOAL)
		if(arr[x-2][y]!=Contents.WALL && arr[x-2][y]!=Contents.BOX && arr[x-2][y]!=Contents.BOXONGOAL)
		{
		c=x-1;
		d=y;
		moveBox(xC,yC);
		}
	}
	
	catch( ArrayIndexOutOfBoundsException e){}
	}
	
	RIGHT=false;
	DOWN=false;
	LEFT=false;
	UP=false;
	
	}
	
	private void movePlayer(int dx,int dy){
	if(arr[a][b]==Contents.PLAYERONGOAL){
	
		if(arr[a+dx][b+dy]==Contents.GOAL){
			arr[a+dx][b+dy]=Contents.PLAYERONGOAL;
		}
		
		if(arr[a+dx][b+dy]==Contents.EMPTY){
			arr[a+dx][b+dy]=Contents.PLAYER;
		}
		
	arr[a][b]=Contents.GOAL;
	}
		
	if(arr[a][b]==Contents.PLAYER){
		
		if(arr[a+dx][b+dy]==Contents.GOAL){
			arr[a+dx][b+dy]=Contents.PLAYERONGOAL;
		}
		
		if(arr[a+dx][b+dy]==Contents.EMPTY){
			arr[a+dx][b+dy]=Contents.PLAYER;
		}
		
	arr[a][b]=Contents.EMPTY;
	}
		
		repaint();
		
	}
	
	private void moveBox(int dx,int dy){

	if(arr[c][d]==Contents.BOXONGOAL){

		if(arr[c+dx][d+dy]==Contents.GOAL){
			arr[c+dx][d+dy]=Contents.BOXONGOAL;
			arr[a+dx][b+dy]=Contents.PLAYERONGOAL;
		}
	
		if(arr[c+dx][d+dy]==Contents.EMPTY){
			arr[c+dx][d+dy]=Contents.BOX;
			arr[a+dx][b+dy]=Contents.PLAYERONGOAL;
		}
		
		if(arr[a][b]==Contents.PLAYER){
			arr[a][b]=Contents.EMPTY;
		}
	
		if(arr[a][b]==Contents.PLAYERONGOAL){
			arr[a][b]=Contents.GOAL;
		}


	}

		if(arr[a][b]==Contents.PLAYERONGOAL && arr[c][d]==Contents.BOX){
			arr[a+dx][b+dy]=Contents.PLAYER;
			arr[a][b]=Contents.GOAL;
			arr[c+dx][d+dy]=Contents.BOX;

		}



	
	if(arr[c][d]==Contents.BOX){
	
		if(arr[c+dx][d+dy]==Contents.GOAL){
			arr[c+dx][d+dy]=Contents.BOXONGOAL;
			arr[a+dx][b+dy]=Contents.PLAYER;
				arr[a][b]=Contents.GOAL;
				
		}
		
		if(arr[c+dx][d+dy]==Contents.EMPTY){
			arr[c+dx][d+dy]=Contents.BOX;
			arr[a+dx][b+dy]=Contents.PLAYER;

			
		}

		if(arr[a][b]==Contents.PLAYERONGOAL){
		
			arr[a][b]=Contents.GOAL;

			
		}
		
	arr[a][b]=Contents.EMPTY;
	}
		checkWin();
		repaint();
	}

	
	
	
	private boolean checkWin(){
	int total=0;
	
	for(int x=0;x < arr.length;x++){
		for(int y=0;y < arr[x].length;y++){
			if(arr[x][y] == Contents.BOX){
				total++;
				
			}
		}
	}
	
	
	if(total==0){
	
		if(curr < level){
			initLevel(curr + 1); 
			count = 0;
		}
		
		else{
			initLevel(0);
			System.out.println("You beat the game!");
		return true;
		}
	
	total=0;
	repaint();
	
		return true;
	}
	
		return false;
	}


	
	public static void main(String[] args){
	JFrame f = new JFrame("Sokoban");
	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	f.setResizable(true);
	f.setLayout(new FlowLayout());
	f.add(new Sokoban("m1.txt"));
	f.pack();
	f.setVisible(true);
	}
}

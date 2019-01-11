/*
 
 Game Class
 David Lan and Bill Ge
 December 9, 2017
 ICS4U1
 Ms. Strelkovska
 
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;

// GamePanel class
class GamePanel extends JPanel implements Serializable, KeyListener, ActionListener{
	
	// Attributes
	private JFileChooser fc; 
	private Timer myTimer;
	private ArrayList<Spaces> collidable;
	private Rectangle rects[];
	private Player player;
	private ArrayList<Brick>bricks = new ArrayList<Brick>();
	private ArrayList<Key>key = new ArrayList<Key>();
	private ArrayList<Hammer>hammer = new ArrayList<Hammer>();
	private ArrayList<Chest>chest = new ArrayList<Chest>();
	private ArrayList<Slime>slime = new ArrayList<Slime>();
	private InventoryLabel labels[] = {new InventoryLabel(0), new InventoryLabel(2), new InventoryLabel(4)};
	private JPanel game;
	private JPanel inventory;
	private JLabel invLabel;
	
	// Constructor to select preferences
	public GamePanel() {
		
		// Make keylistener work
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
		
		// Set preferences
		collidable = new ArrayList<Spaces>();
		setSize(680,650);
		setLayout(new BorderLayout());
		
		// Create inventory
		inventory = new JPanel();
		inventory.setLayout(new GridLayout(4,1));
		invLabel = new JLabel("Inventory");
		inventory.add(invLabel);
		
		// Create game and center it
		setBackground(new Color(222,184,135));		
		
		// Add inventory to right
		add(inventory, BorderLayout.EAST);
		for(int i = 0; i < 3; i++) {
			inventory.add(labels[i]);
			labels[i].draw();
		}
		// Start game with file open prompt
		fc = new JFileChooser();
		myTimer = new Timer(30,this);
		myTimer.start();
		labels[0].setHovered(true);
	
		// Try to open a map file
		try{
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile(); 
				openMethod(file);
				rects = new Rectangle[collidable.size()];
				
				// Create tile for game based on map file
				for(int i = 0; i < collidable.size(); i++) {
					rects[i] = new Rectangle(((Spaces) (collidable.get(i))).getx(),((Spaces) (collidable.get(i))).gety(), 40, 40);
				}
				int objective = JOptionPane.showConfirmDialog(null, "Collect all the chests", "Objective", JOptionPane.CLOSED_OPTION);
			}
		}catch(Exception e) {
			System.out.println("Error");
		}
	}
	
	// Draw tiles and update screen
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			// Render all non-player instances
			for(int i = 0; i < bricks.size(); i++) {
				bricks.get(i).render(g);
			}
			for(int i = 0; i < key.size(); i++) {
				key.get(i).render(g);
			}
			for(int i = 0; i < slime.size(); i++) {
				slime.get(i).render(g);
			}
			for(int i = 0; i < hammer.size(); i++) {
				hammer.get(i).render(g);
			}
			for(int i = 0; i < chest.size(); i++) {
				chest.get(i).render(g);
			}
			
			// Player moves on x-axis and check for collision
			player.moveX();
			
			// Player stops moving if they collide with brick
			for(int i = 0; i < bricks.size(); i++) {
				if(player.intersects(bricks.get(i))) {
					player.stopX();
				}
			}
			// Check if player intersects with slime
			for(int i = 0; i < slime.size(); i++) {
				if(player.intersects(slime.get(i))) {
					// Remove slime if player selects hammer
					if(player.getHammer() && InventoryLabel.getSelected()==2) {
						slime.remove(i);
					}
					// Collide if player doesn't use hammer
					else {
						player.stopX();
					}
				
				}
			}
			// Check if player intersects with key
			for(int i = 0; i < key.size(); i++) {
				if(player.intersects(key.get(i))) {
					// Remove key and add it to inventory if player intersects
					key.remove(i);
					player.addKey();
					labels[1].setNum(player.getNKey());
					labels[1].draw();
					
				}
			}
			// Check if player intersects with chest
			for(int i = 0; i < chest.size(); i++) {
				if(player.intersects(chest.get(i))) {
					// If key is selected and player has a key, they remove the chest and lose a key
					if(player.getKeys() > 0 && InventoryLabel.getSelected() == 1) {
						chest.remove(i);
						player.removeKey();
						labels[1].setNum(player.getNKey());		
						Chest.removeChest();
						System.out.println(Chest.getNChest());	
						labels[1].draw();
					}
					// Otherwise, player collides
					else {
						player.stopX();
					}
				}
			}
			// Check if player intersects with hammer
			for(int i = 0; i < hammer.size(); i++) {
				// Add hammer to inventory if player collects it
				if(player.intersects(hammer.get(i))) {
					player.setHammer(true);
					labels[2].setNum(1);
					hammer.remove(i);
					labels[2].setYes();
					labels[2].draw();
				}
			}
			
			// Player moves on y-axis and check for collision
			player.moveY();
			
			// Player stops moving if they collide with brick
			for(int i = 0; i < bricks.size(); i++) {
				if(player.intersects(bricks.get(i))) {
					player.stopY();
				}
			}
			// Check if player intersects with slime
			for(int i = 0; i < slime.size(); i++) {
				if(player.intersects(slime.get(i))) {
					// Remove slime if player selects hammer
					if(player.getHammer() && InventoryLabel.getSelected()==2) {
						slime.remove(i);
					}
					// Collide if player doesn't use hammer
					else {
						player.stopY();
					}
				
				}
			}
			// Check if player intersects with key
			for(int i = 0; i < key.size(); i++) {
				// Remove key and add it to inventory if player intersects
				if(player.intersects(key.get(i))) {
					key.remove(i);
					player.addKey();
					labels[1].setNum(player.getNKey());
					labels[1].draw();
				
				}
			}
			// Check if player intersects with chest
			for(int i = 0; i < chest.size(); i++) {
				if(player.intersects(chest.get(i))) {
					// If key is selected and player has a key, they remove the chest and lose a key
					if(player.getKeys() > 0 && InventoryLabel.getSelected() == 1) {
						chest.remove(i);
						player.removeKey();
						labels[1].setNum(player.getNKey());		
						Chest.removeChest();
						System.out.println(Chest.getNChest());
						labels[1].draw();
					}
					// Player stops moving on y-axis if they don't have key selected
					else {
						player.stopY();
					}
				}
			}
			// Check if player intersects with hammer
			for(int i = 0; i < hammer.size(); i++) {
				// Player collects hammer if they intersect
				if(player.intersects(hammer.get(i))) {
					player.setHammer(true);
					labels[2].setNum(1);
					hammer.remove(i);
					labels[2].setYes();
					labels[2].draw();
				}
			}
			// Move and render player
			player.move();
			player.render(g);
			
		}catch(Exception e) {
			System.out.println("Player not found");
			System.exit(0);
		}
	}

	// Method to open files
	public void openMethod(File filePath){
		
		// Variables
	    FileInputStream fileIn;
	    ObjectInputStream objectIn;
	    Spaces tiles;
	    
	    // Try to read the size of the map file
		try{
		   fileIn = new FileInputStream(filePath);
		   objectIn = new ObjectInputStream(fileIn);
		   int nC = 0;
		   
		   // Add tiles to the game based on map file
		   for(int i = 0; i < 225; i++) {
			   tiles = ((Spaces) objectIn.readObject());
			  
			   // Create player instance
			   if(tiles.getType() == 6) {
				   player = new Player(tiles.getx(), tiles.gety());
				   
			   }
			   // Create brick instance
			   else if(tiles.getType() == 1) {
				  bricks.add(new Brick(tiles.getx(), tiles.gety()));
			   }
			   // Create key instance
			   else if(tiles.getType() == 2) {
				   key.add(new Key(tiles.getx(), tiles.gety()));
			   }
			   // Create chest instance
			   else if(tiles.getType() == 3) {
				  chest.add(new Chest(tiles.getx(), tiles.gety()));
				  nC++;
			   }
			   // Create hammer instance
			   else if(tiles.getType() == 4) {
				   hammer.add(new Hammer(tiles.getx(), tiles.gety()));
			   }
			   // Create slime instance
			   else if(tiles.getType() == 5) {
				  slime.add(new Slime(tiles.getx(), tiles.gety()));
			   }	  
		   }
		   // Change amount of chests
		   Chest.setNChest(nC);
		   
		   // Close files
		   fileIn.close();
		   objectIn.close();
	     } catch (Exception ex) {
	    	System.out.println("Error2");

	     }
		
	}

	public void keyPressed(KeyEvent e) {
		// Key Code
		int key = e.getKeyCode();
		
		// Move player based on the key pressed
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
			player.setVY(-4);
		}
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
			player.setVY(4);
		}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			player.setVX(-4);
		}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			player.setVX(4);
		}
		// Change player sprite to selected tool
		if(key == KeyEvent.VK_I) {
			labels[InventoryLabel.getSelected()].setHovered(false);
			InventoryLabel.setSelected(InventoryLabel.getSelected()+1);
			InventoryLabel.setSelected(InventoryLabel.getSelected()%3);
			labels[InventoryLabel.getSelected()].setHovered(true);
		}
		
	}
	public void keyReleased(KeyEvent e) {
		// Key Code
		int key = e.getKeyCode();
		
		// Player stops moving after releasing WASD keys or arrow keys
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
			player.setVY(0);
		}
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
			player.setVY(0);
		}
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			player.setVX(0);
		}
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			player.setVX(0);
		}
	}

	public void keyTyped(KeyEvent e) {}
	
	public void actionPerformed(ActionEvent e) {
		// Time delay
		if(e.getSource() == myTimer) {
			// Repaint screen
			repaint();
			// Exit game if the number of chests is 0
			if(Chest.getNChest() == 0) {
				int exit = JOptionPane.showConfirmDialog(null, "You win", "Victory!", JOptionPane.CLOSED_OPTION);
				if(exit == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		}
		
	}
}

// Game class
public class Game extends JFrame{
	
	// Attributes
	private Container c;
	private GamePanel gp;
	
	// Constructor
	public Game() {
		
		// Set preferences for game
		super("Maze Game");
		c = getContentPane();
		gp = new GamePanel();
		c.setLayout(new BorderLayout());
		c.add(gp,BorderLayout.CENTER);
		setResizable(false);
		setSize(680, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	// Main class
	public static void main(String args[]) {
		
		// Create instance of game
		Game game = new Game();
	}
}
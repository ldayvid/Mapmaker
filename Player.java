import java.awt.Graphics;
import java.awt.Rectangle;

// Player class
public class Player extends Rectangle{
	
	// Attributes
	private int velX, velY;
	private boolean hasHammer;
	private int nKeys = 0;
	
	// Constructor to initialize location of player
	public Player(int x, int y) {
		super(x, y, 40, 40);
		hasHammer = false;
	}
	// Stop player from moving on x-axis
	public void stopX() {
		x -= velX;
	}
	// Stop player from moving on y-axis
	public void stopY() {
		y -= velY;	
	}
	// Update player location and implement collision
	public void move() {
		setLocation(x, y);
		hitBounds();
	}
	// Move on x-axis
	public void moveX() {
		x += velX;
	}
	// Move on y-axis
	public void moveY() {
		y += velY;
	}
	
	// Keep player on the screen
	private void hitBounds(){
		if(x <= 0) {
			stopX();
		}
		else if(x >= 568) {
			stopX();
		}
		if(y <= 0) {
			stopY();
		}
		else if(y >= 570) {
			stopY();
		}
	}

	// Set an image for player
	public void render(Graphics g) {
		// No tool image
		if(InventoryLabel.getSelected()==1 && nKeys > 0) {
			g.drawImage(Images.getImages(8).getImage(), x, y, null);
		}
		// Hammer image
		else if(InventoryLabel.getSelected()==2 && hasHammer) {
			g.drawImage(Images.getImages(7).getImage(), x, y, null);
		}
		// Key image
		else {
			g.drawImage(Images.getImages(6).getImage(), x, y, null);
		}
	}
	
	// Add one key
	public void addKey() {
		nKeys++;
	}
	// Subtract one key
	public void removeKey() {
		nKeys--;
	}
	
	// Set has hammer to true or false
	public void setHammer(boolean a) {
		hasHammer = a;
	}
	// Setter for x-vel
	public void setVX(int velX) {
		this.velX = velX;
	}
	// Setter for y-vel
	public void setVY(int velY) {
		this.velY = velY;
	}
	
	// Getter for number of keys
	public int getKeys() {
		return nKeys;
	}
	// Getter for x-vel
	public int getVX() {
		return velX;
	}
	// Getter for y-vel
	public int getVY() {
		return velY;
	}
	// Getter for number of keys
	public int getNKey() {
		return nKeys;
	}
	// Getter for if player has a hammer
	public boolean getHammer() {
		return hasHammer;
	}
}
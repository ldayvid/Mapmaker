import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

// Spaces class
public class Spaces extends Rectangle implements Serializable {
	
	// Attributes
	private int type;
	private int x;
	private int y;
	private int gridX;
	private int gridY;
	
	// Constructor to initialize attributes
	public Spaces (int t, int newX, int newY, int gX, int gY) {
		super(newX,newY, 40, 40);
		type = t;
		x = newX;
		y = newY;
		gridX = gX;
		gridY = gY;
	}
	// Draw image of space
	public void draw(Graphics g) {
		g.drawImage(Images.getImages(type).getImage(), x, y, null);
	}
	// Getter for type
	public int getType() {
		return type;
	}
	// Getter for grid-x
	public int getGridX() {
		return gridX;
	}
	// Getter for grid-y
	public int getGridY() {
		return gridY;
	}
	// Getter for x-coordinate
	public int getx() {
		return x;
	}
	// Getter for y-coordinate
	public int gety() {
		return y;
	}
}
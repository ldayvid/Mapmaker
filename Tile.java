import java.io.Serializable;
import javax.swing.*;

// Tile class
public class Tile extends JButton implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private static int selected = 0;
	private static boolean clicked = false;
	private static int playerX = -1;
	private static int playerY = -1;
	private static boolean playExists = false;
	
	// Constructor for Tile
	public Tile(ImageIcon i) {
		super(i);
	}
	// Getter method for selected
	public static int getSelect() {
		return selected;
	}
	// Getter for player x-position
	public static int getPlayX() {
		return playerX;
	}
	// Getter for player y-position
	public static int getPlayY() {
		return playerY;
	}
	// Setter for player x-position
	public static void setPlayX(int x) {
		playerX=x;
	}
	// Setter for player y-position
	public static void setPlayY(int y) {
		playerY = y;
	}
	// Getter method for if clicked
	public static boolean getClicked() {
		return clicked;
	}
	// Setter for clicked
	public static void setClicked(boolean b) {
		clicked = b;
	}
	// Changes the button icon
	public void setIcon(ImageIcon i) {
		super.setIcon(i);
	}
	// Setter method for selected
	public static void setSelect(int x) {
		selected = x;
	}
	// Setter method for PlayExists
	public static void setPlayExists(boolean x) {
		playExists = x;
	}
	// Getter for PlayerExists
	public static boolean getPlayExists() {
		return playExists;
	}
}
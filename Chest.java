import java.awt.Graphics;
import java.awt.Rectangle;

// Chest class
public class Chest extends Rectangle{
	
	// Attribute
	private static int numChest = -1;
	
	// Constructor initialize location of a Chest
	public Chest(int x, int y) {
		super(x,y,40,40);
	}
	// Render image of Chest
	public void render(Graphics g) {
		g.drawImage(Images.getImages(3).getImage(), x, y, null);
	}
	// Remove 1 chest from counter
	public static void removeChest(){
		numChest--;
	}
	// Setter for number of chests
	public static void setNChest(int n){
		numChest = n;
	}
	// Getter for number of chests
	public static int getNChest(){
		return numChest;
	}
}
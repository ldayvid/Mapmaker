import java.awt.Graphics;
import java.awt.Rectangle;

// Slime class
public class Slime extends Rectangle{
	
	// Constructor initialize location of a Slime
	public Slime(int x, int y) {
		super(x,y,40,40);
	}

	// Render image of Slime
	public void render(Graphics g) {
		g.drawImage(Images.getImages(5).getImage(), x, y, null);
	}
}
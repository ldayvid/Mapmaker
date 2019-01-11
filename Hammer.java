import java.awt.Graphics;
import java.awt.Rectangle;

// Hammer class
public class Hammer extends Rectangle{
	
	// Constructor initialize location of a Hammer
	public Hammer(int x, int y) {
		super(x,y,40,40);
	}

	// Render image of Hammer
	public void render(Graphics g) {
		g.drawImage(Images.getImages(4).getImage(), x, y, null);
	}
}
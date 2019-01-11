import java.awt.Graphics;
import java.awt.Rectangle;

// Key class
public class Key extends Rectangle{
	
	// Constructor initialize location of a key
	public Key(int x, int y) {
		super(x,y,40,40);
	}

	// Render image of key
	public void render(Graphics g) {
		g.drawImage(Images.getImages(2).getImage(), x, y, null);
	}
}
import java.awt.Graphics;
import java.awt.Rectangle;

// Brick class
public class Brick extends Rectangle{
	
	// Constructor initialize location of a brick
	public Brick(int x, int y) {
		super(x,y,40,40);
	}

	// Render image of brick
	public void render(Graphics g) {
		g.drawImage(Images.getImages(1).getImage(), x, y, null);
	}	
}
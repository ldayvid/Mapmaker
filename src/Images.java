import javax.swing.ImageIcon;

// Images class
public class Images {
	
	// Images
	private static final ImageIcon trail = new ImageIcon("src\\Images\\Blank.png");
	private static final ImageIcon brick = new ImageIcon("src\\Images\\Brick.png");
	private static final ImageIcon chest = new ImageIcon("src\\Images\\Chest.png");
	private static final ImageIcon key = new ImageIcon("src\\Images\\Key.png");
	private static final ImageIcon hammer = new ImageIcon("src\\Images\\Hammer.png");
	private static final ImageIcon slime = new ImageIcon("src\\Images\\Slime.png");
	private static final ImageIcon player = new ImageIcon("src\\Images\\Player.png");
	private static final ImageIcon playerHammer = new ImageIcon("src\\Images\\PlayerHammer.png");
	private static final ImageIcon playerKey = new ImageIcon("src\\Images\\PlayerKey.png");
	private static final ImageIcon[]images = {trail, brick, key, chest, hammer, slime, player, playerHammer, playerKey};
	
	// Getters for images
	public static ImageIcon getImages(int i){
		return images[i];
	}
	
	// Getter for number of images
	public static int imagesN(){
		return images.length;
	}
}
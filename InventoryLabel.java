import java.awt.*;
import javax.swing.*;

// Inventory Label class
public class InventoryLabel extends JPanel{
	
	// Attributes
	private static int selected = 0;
	private int num;
	private int type;
	private String yesNo;
	private boolean hovered;
	
	// Constructor to initialize
	public InventoryLabel(int i) {
		super();
		type = i;
		setLayout(new FlowLayout());
		num = 0;
		yesNo = "N";
		hovered = false;
	}
	
	// Draw inventory label
	public void draw() {
		removeAll();
		revalidate();
		add(new JLabel(Images.getImages(type)));
		// Draw the number of keys player has in inventory
		if(type == 2) {
			add(new JLabel("" +num));
		}
		// Draw yes or no for whether or not player has a hammer
		else if(type == 4) {
			add(new JLabel(yesNo));
		}
	}
	// Draw selecting rectangle
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(hovered) {
			g.drawRect(3, 0, 55,50);
		}
	}
	// Setter for selected
	public static void setSelected(int x) {
		selected = x;
	}
	// Setter for number
	public void setNum(int i) {
		num = i;
	}
	// Setter for yes or no
	public void setYes() {
		yesNo = "Y";
	}
	// Getter for number
	public int getNum() {
		return num;
	}
	// Getter for selected
	public static int getSelected(){
		return selected;
	}
	// Getter for hovered
	public void setHovered(boolean b){
		hovered = b;
	}
}
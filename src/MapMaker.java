/*
 
 Map Maker Class
 David Lan and Bill Ge
 December 9, 2017
 ICS4U1
 Ms. Strelkovska
 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

// Map Maker class
public class MapMaker extends JFrame implements ActionListener, MouseListener, Serializable{
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private Tile field[][];
	private int fieldLength;
	private JButton selectB[];
	private JPanel p1, p2;
	private Container c;
	private JFileChooser fc; 
	
	// Constructor for map maker to set preferences
	public MapMaker() {
		
		// Initialize JFrame
		super("Map Maker");
		fc = new JFileChooser();
		fieldLength = 15;
		setResizable(false);
		setSize(700, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create grid
		createGrid();
	}
	
	// Method to create a grid
	public void createGrid() {
		
		// Initialize Attributes
		p1 = new JPanel();
		p2 = new JPanel();
		c = this.getContentPane();
		selectB = new JButton[10];
		
		// Create the field that can be manipulated
		c.setLayout(new BorderLayout(20,0));
		p1.setLayout(new GridLayout(fieldLength, fieldLength));
		field = new Tile[fieldLength][fieldLength];
				
		// Initialize buttons and add action listener
		for(int i = 0; i < fieldLength; i ++) {
			for(int j = 0; j < fieldLength; j++) {
				field[i][j] = new Tile(Images.getImages(0));	
				field[i][j].addActionListener(this);
				field[i][j].addMouseListener(this);
				p1.add(field[i][j]);
			}
		}
		
		// Add 3rd panel on top of 2nd panel
		p2.setLayout(new GridLayout(11, 1,0,5));
		Font myFont = new Font("Comic Sans MS",Font.BOLD,20);
        JLabel blockL = new JLabel("Blocks");
        blockL.setFont(myFont);
		p2.add(blockL);
		
		// Create editing buttons and add buttons to panels
		for(int i = 0; i < 7; i++) {
			selectB[i] = new JButton(Images.getImages(i));
			selectB[i].addActionListener(this);
			selectB[i].setMargin(new Insets(0, 0, 0, 0));
			p2.add(selectB[i]);
		}
		
		// Add save, load, clear buttons
		selectB[7] = new JButton("Save");
		selectB[7].addActionListener(this);
		selectB[8] = new JButton("Load");
		selectB[8].addActionListener(this);
		selectB[9] = new JButton("Clear");
		selectB[9].addActionListener(this);
		p2.add(selectB[7]);
		p2.add(selectB[8]);
		p2.add(selectB[9]);
				
		// Add panels to container
		c.add(p1, BorderLayout.CENTER);
		c.add(p2, BorderLayout.EAST);
		setVisible(true);
		repaint();
	}
	
	// Use ActionListener to check if a button has been clicked
	public void actionPerformed(ActionEvent evt) {
		
		// Choose clicked icon as block
		for(int i = 0; i < 7; i++) {
			
			// Unhighlight all blocks
			if(evt.getSource()==selectB[i]){
				for(int j = 0; j < 7; j++) {
					selectB[j].setBackground(null);
				}
				
				// Set selected and clicked
				Tile.setSelect(i);
				Tile.setClicked(false);
				
				// Highlight selected block
				selectB[i].setBackground(Color.red);
			}
		}
		
		// If user picked save
		if(evt.getSource() == selectB[7]) {
			
			// Attempt to save the file
			try{
				int returnVal = fc.showSaveDialog(this);
				
				// If user did not click cancel and picked file name
				if (returnVal == JFileChooser.APPROVE_OPTION) {  
					File file = fc.getSelectedFile();
					
					// Save the file
					saveMethod(file);
				}
		    }catch(Exception ex){
	            System.out.print("Error" + ex);
	        }		
		}
		
		// If user picked to load
		if(evt.getSource() == selectB[8]) {
			
			// Attempt to open a file
			try{
				int returnVal = fc.showOpenDialog(this);
				
				// If user did not click cancel and picked file name
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					ArrayList<Spaces> rewriteMap = openMethod(file);
					
					// Clear the map and create a new grid
					c.removeAll();
					createGrid();
					
					// Update grid with tiles based on selected map
					for(int i = 0; i < rewriteMap.size(); i++) {
						field[rewriteMap.get(i).getGridX()][rewriteMap.get(i).getGridY()].setIcon(Images.getImages(rewriteMap.get(i).getType()));
						
						// Note that the player exists if it does
						if(rewriteMap.get(i).getType() == 6) {
							Tile.setPlayX(rewriteMap.get(i).getGridX());
							Tile.setPlayY(rewriteMap.get(i).getGridY());
							Tile.setPlayExists(true);
						}
					}
				}
			}catch(Exception e){
				System.out.println("Error");
			}
		}
					
		// Clear Screen and reset clicked
		 if(evt.getSource() == selectB[9]) {
			for(int i = 0; i < fieldLength; i ++) {
				for(int j = 0; j < fieldLength; j++) {
					field[i][j].setIcon(Images.getImages(0));
				}
			}
			Tile.setClicked(false);
		}
		
		// Loop through buttons and change selected button to a specified image
		for(int i = 0; i < fieldLength; i++) {
			for(int j = 0; j < fieldLength; j++) {
				if(evt.getSource()==field[i][j]) {
					
					// If the selected button is player
					if(Tile.getSelect() == 6) {
						
						// If player exists on the map change its location to the clicked one
						if(Tile.getPlayExists()) {
							field[Tile.getPlayX()][Tile.getPlayY()].setIcon(Images.getImages(0));
							field[i][j].setIcon(Images.getImages(6));
						}
						
						// Otherwise, set its location normally
						else{
							Tile.setPlayExists(true);
							field[i][j].setIcon(Images.getImages(6));
						}
						Tile.setPlayX(i);
						Tile.setPlayY(j);
					}
					
					// If the player gets covered, it doesn't exist anymore
					else if(Tile.getSelect() != 6){
						if(field[i][j].getIcon().equals(Images.getImages(6))){
							Tile.setPlayExists(false);
							Tile.setPlayX(-1);
							Tile.setPlayY(-1);
						}
						
						// Set icon normally
						field[i][j].setIcon(Images.getImages(Tile.getSelect()));
						
						// Change clicked to its opposite state
						Tile.setClicked(!Tile.getClicked());	
						System.out.println(Tile.getClicked());
					}
				}
			}
		}
	}
	
	// Method to save files
	public void saveMethod(File filePath){
		
		  // Variables
		  FileOutputStream fout = null;
	      ObjectOutputStream oos = null;
	      
	      // Attempt to save the map
	      try {
	    	  	ArrayList<Spaces> saveMap = new ArrayList<Spaces>();
	    	  	// Add the not-trail tiles to the arraylist that will be saved
	    	  	for(int i = 0; i < fieldLength; i++) {
	    	  		for(int j = 0; j < fieldLength; j++) {
	    	  			for(int l = 0; l < Images.imagesN(); l++) {
	    	  				if(field[i][j].getIcon().equals(Images.getImages(l))) {
	    	  					saveMap.add(new Spaces(l, field[i][j].getX(), field[i][j].getY(),i,j));
	    	  				}
	    	  			}
	    	  		}
	    	  	}
	    	  	
				fout = new FileOutputStream(filePath);
				oos = new ObjectOutputStream(fout);
	
				// Write tile to the map in order to save
				for(int i = 0; i < saveMap.size(); i++) {
					oos.writeObject(saveMap.get(i));
				}
				
				// Close files
				fout.close();
				oos.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	
	// Method to open files
	public ArrayList openMethod(File filePath){
		
		// Variables
		FileInputStream fileIn = null;
	    ObjectInputStream objectIn = null;
	    ArrayList<Spaces> tiles = new ArrayList<Spaces>();
	    
	    // Attempt to open files
		try{
		   fileIn = new FileInputStream(filePath);
		   objectIn = new ObjectInputStream(fileIn);
		   
		   // Add map tiles to tiles
		   for(int i = 0; i < 225 ; i++) {
			   tiles.add((Spaces) objectIn.readObject());
		   }
         }catch(Exception e) {}
		finally {
			// Return the arraylist of tiles
			return tiles;
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	
	// Check if the mouse enters a new button
	public void mouseEntered(MouseEvent evt) {
		
		// Set button to icon if the mouse hasn't been clicked again
		if(Tile.getClicked()) {
			
			// Loop through the entire map
			for(int i = 0; i < fieldLength; i++) {
				for(int j = 0; j < fieldLength; j++) {
					if(evt.getSource()==field[i][j]) {
						
						// Set player exist to false if it gets covered by something
						if(field[i][j].getIcon().equals(Images.getImages(6)) && Tile.getSelect() != 6){
							Tile.setPlayExists(false);
						}
						
						// Set tile in map to an icon
						field[i][j].setIcon(Images.getImages(Tile.getSelect()));
					}
				}
			}
		}
		
	}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	// Main method
	public static void main(String[] args) {
		// Create instance of map maker
		MapMaker m = new MapMaker();
	}
}
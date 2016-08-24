
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Ship {
	ArrayList<String> shipHealth = new ArrayList<String>();
	private String name;
	private int size;
	
	// Constructor
	public Ship(String name, int size){
		this.name = name;
		this.size = size;
	}

	//Sets Ships onto game board in random allowed positions and returns an arraylist containing the name of the boat for each size of the ship
	public ArrayList<String> setLocation(Board gameBoard, boolean customPlacement) {
		
		String[][][] board = gameBoard.getBoard();
		
		int x = 0 , y = 0 , o = 0, tempX = 0, tempY = 0;
		String orientation = null;
		
		
		int yMax = board.length;
		int xMax = board[0].length;
		
		boolean worked = false;
			
		// worked boolean in place to allow while loop to allow start-continue used

		// Continues in if dempand this to be outside of while loop (otherwise while loop not needed)
		start:
			while(!worked) {
				
				// ends while loop if it completes
				worked = true;
			
				// Gets X,Y and orientation from inputs
				if (customPlacement == true){
					
					Scanner input = new Scanner(System.in);
					String boardName;
					boolean isNum;
					
					boardName = gameBoard.getName();
					
					System.out.println(boardName + " Placeing " + name);
					
					System.out.println("Enter the X Coordinate for the ship to be placed");
					// To Check if input is Int
					do {
						// input = int
						if (input.hasNextInt()) {
							x = tempX = (input.nextInt() - 1);
							isNum = true;
						}
						// else ask again
						else
						{
							System.out.println("Inncorrect input please enter a whole number for the X coordinate");
							isNum = false;
							// Clears scanner to  allow into if statement again
							input.next();
						}
					} while (!isNum);
					
					System.out.println("Enter the Y Coordinate for the ship to be placed");
					// To check if input is Int
					do {
						// input = int
						if (input.hasNextInt()) {
							y = tempY = (input.nextInt() - 1);
							isNum = true;
						}
						// else ask again
						else
						{
							System.out.println("Inncorrect input please enter a whole number for the Y coordinate");
							isNum = false;
							// Clears scanner to  allow into if statement again
							input.next();
						}
					} while (!isNum);
					
					// Would Prefer to use .next(); , as it would allow inputs straight to orientaion but did not like the if error finder below
					System.out.println("Enter the Orientation for the ship to be placed (0 for Horizontal 1 for Vertical ");
					o = input.nextInt();
					if ( o != 0 && o != 1){
						
						System.out.println("Inncorrect orrientation");
						continue start;
					}
					
				}
				
				// Randomly creates x,y and orientation
				if (customPlacement == false){
					
					Random rand = new Random();
					
					// Randomly choose orientaion
					o = rand.nextInt(2);					
					
					// random coordinates between 0 and xMax-1 which equate to all possible values for x or y
					x = tempX = rand.nextInt(xMax);
					y = tempY = rand.nextInt(yMax);
				
					}
				
				switch(o){
				case 0:
					orientation = "H";
					break;
				case 1:
					orientation = "V";
					break;
				}
			
				// Checks if random coordinte is currently occupied
				if (board[y][x][0] != "~"){
				
					worked = false;
					if (customPlacement == true){
						System.out.println("Initial placement is already occupied. \nPlease try again.");
					}
					continue start; // FAILED RETURN TO START	
				}
			
				// if not
				else
					switch(orientation) {
				
					// places Vertically
					case "V":
						// checks if it can fit if not fails
						if (y - size <-1){
							worked = false;
							if (customPlacement == true){
								System.out.println("Placement does not fit. \nPlease try again.");
							}
							continue start; // FAILED RETURN TO START
						}
						
						// Checks all points
						for (int i = 0; i<size ; i++){
						
							// if any are occupied
							if (board[tempY][tempX][0] != "~"){
							
								worked = false;
								if (customPlacement == true){
									System.out.println("Placement is already occupied. \nPlease try again.");
								}
								continue start; // FAILED RETURN TO START
							}
							// Else move on to next
							else
								tempY--;
						}
						// Fills Points S to show occupied and name above for hit measurements 
						for (int i = 0; i<size ; i++){
							board[y][x][0] = "T";
							board[y][x][1] = name;
							y--;
							
							// adds name to shipHealth (will do for each point of ship)
							shipHealth.add(name);
						}
						break;
				
						// Same as above but horizontally
					case "H":
						if (x + size > xMax){
							worked = false;
							if (customPlacement == true){
								System.out.println("Placement does not fit. \nPlease try again.");
							}
							continue start; // FAILED RETURN TO START
						}
					
						// Checks all points
						for (int i = 0; i<size ; i++){
						
							if (board[tempY][tempX][0] != "~"){
							
								worked = false;
								if (customPlacement == true){
									System.out.println("Placement is already occupied. \nPlease try again.");
								}
								continue start; // FAILED RETURN TO START
							}
					
							else
								tempX++;
						}
						// Fills Points
						for (int i = 0; i<size ; i++){
							board[y][x][0] = "T";
							board[y][x][1] = name;
							x++;
							
							shipHealth.add(name);
						}
					
						break;
				}
			
				// Sets new grid to board
				gameBoard.setBoard(board);
				
			}
		return shipHealth;
		}

	public int getSize() {return size;}
}

import java.util.ArrayList;
import java.util.Scanner;

public class Game { 
	
	public static void main(String[] arg){
		
		// Creates an iteration of the game object
			Game game = new Game();
			game.setup();
		}

	// Initial Set up of Board
	public void setup(){
		Scanner input = new Scanner(System.in);
		
		boolean customPlacement = false;
		String opCode;
		
		// Array list that will contain copys of each ships name equal to the ship size.
		ArrayList<String> player1ShipHealth = new ArrayList<String>();
		ArrayList<String> player2ShipHealth = new ArrayList<String>();
		
		// Creates 3d array with 1st and 2nd for board dimensions 3rd is used for hit detection
		String[][][] player1Board = new String[9][9][2];
		String[][][] player2Board = new String[9][9][2];
		
		//creates board object with the above grid
		Board player1GameBoard = new Board(player1Board , "Player 1");
		Board player2GameBoard = new Board(player2Board , "Player 2");
		
		// Each ship (Name and size) (can prob clean up at some point (low prio))
		Ship player1Ship1 = new Ship("Tug", 2);
		Ship player1Ship2 = new Ship("Submarine", 3);
		Ship player1Ship3 = new Ship("Destroyer", 4);
		Ship player1Ship4 = new Ship("Battleship", 5);
		Ship player1Ship5 = new Ship("Aircraft Carrier", 6);
		
		Ship player2Ship1 = new Ship("Tug", 2);
		Ship player2Ship2 = new Ship("Submarine", 3);
		Ship player2Ship3 = new Ship("Destroyer", 4);
		Ship player2Ship4 = new Ship("Battleship", 5);
		Ship player2Ship5 = new Ship("Aircraft Carrier", 6);

		player2GameBoard.showBoards(player1GameBoard);
		
		// Does Player 1 want to place own ships
		System.out.println("Does Player 1 wish to place there owns ships ( Y/N )");
		opCode = input.next();
		// puts in uppercase to better match op code search
		opCode = opCode.toUpperCase();
		
		switch(opCode){
		
		case "Y":
			customPlacement = true;
			break;
		case "N":
			customPlacement = false;
			break;
		default:
			System.out.println("Error In Input Reading Useing randomPlacements");
			customPlacement = false;
			break;
		}
		
		
		// Sets each ship onto the board object and fills the array list (to clean up low prio)
		player1ShipHealth.addAll(player1Ship1.setLocation(player1GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player1ShipHealth.addAll(player1Ship2.setLocation(player1GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player1ShipHealth.addAll(player1Ship3.setLocation(player1GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player1ShipHealth.addAll(player1Ship4.setLocation(player1GameBoard, customPlacement)); 
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player1ShipHealth.addAll(player1Ship5.setLocation(player1GameBoard, customPlacement)); 
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		
		//Does player 2 want to place own ships
		System.out.println("Does Player 2 wish to place there owns ships ( Y/N )");
		opCode = input.next();
		opCode = opCode.toUpperCase();
		
		switch(opCode){
		
		case "Y":
			customPlacement = true;
			break;
		case "N":
			customPlacement = false;
			break;
		default:
			System.out.println("Error In Input Reading Useing randomPlacements");
			customPlacement = false;
			break;
		}
		
		// Sets each ship onto the board object and fills the array list
		player2ShipHealth.addAll(player2Ship1.setLocation(player2GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player2ShipHealth.addAll(player2Ship2.setLocation(player2GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player2ShipHealth.addAll(player2Ship3.setLocation(player2GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player2ShipHealth.addAll(player2Ship4.setLocation(player2GameBoard, customPlacement));
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		player2ShipHealth.addAll(player2Ship5.setLocation(player2GameBoard, customPlacement)); 
		if (customPlacement == true) {player2GameBoard.showBoards(player1GameBoard);}
		
		// Show Game Board Before Hiding to test
		player2GameBoard.showBoards(player1GameBoard);
		
		
		System.out.println("\n\n\n\n\n\n");
		// Hides Ships After placemnet
		player1GameBoard.hideShips();
		player2GameBoard.hideShips();
		
		// moves gameboard and arraylist to the game method
		game(player2GameBoard, player2ShipHealth, player1GameBoard, player1ShipHealth);
	}

	// Contains gameplay loop
	public void game(Board player2GameBoard, ArrayList<String> player2ShipHealth, Board player1GameBoard, ArrayList<String> player1ShipHealth){
		
		String result = null;
		boolean player1Turn = true;
		boolean player2Turn = true;
		
		//if arraylist has values remaining keeps firing
		while (!player2ShipHealth.isEmpty() & !player1ShipHealth.isEmpty() ){
			
		// P1 Fires at Board
		
		while (player1Turn == true ){
			
			player2GameBoard.showBoards(player1GameBoard);
			System.out.println("                      Player 1 Turn");
			result = player2GameBoard.shoot(player2ShipHealth);
			
			if (player2ShipHealth.isEmpty()){
				player1Turn = false;
			}
			
			if(result == "Miss"){
				player1Turn = false;
			}
		}
		
		if (player2ShipHealth.isEmpty())
			break;
		
		// P2 Fires at Board
		
		
		while (player2Turn == true ){
	
			player2GameBoard.showBoards(player1GameBoard);
			System.out.println("                      Player 2 Turn");
			result = player1GameBoard.shoot(player1ShipHealth);
			
			if (player1ShipHealth.isEmpty()){
				player2Turn = false;
			}
			
			if(result == "Miss"){
				player2Turn = false;
			}
		}
			player1Turn = true;
			player2Turn = true;
			}
			
		// Shows Final Game Board
		player2GameBoard.showBoards(player1GameBoard);
		// Moves to endGame Method
		endGame(player2ShipHealth, player1ShipHealth);
	}

	// Ends Game
	public void endGame(ArrayList<String> player2ShipHealth, ArrayList<String> player1ShipHealth){
		
		// IF Function for when 2 boards are involved to decide a winner
		if ( player2ShipHealth.isEmpty() ){
			System.out.println("--------------------------------------------------------");
			System.out.println("               Congratulations Player 1!");
			System.out.println("             You Have Sunk The Enemy Fleet");
			System.out.println("--------------------------------------------------------");
		}
		
		if ( player1ShipHealth.isEmpty() ){
			System.out.println("--------------------------------------------------------");
			System.out.println("              Congratulations Player 2!");
			System.out.println("            You Have Sunk The Enemy Fleet");
			System.out.println("--------------------------------------------------------");
			}
	}
}

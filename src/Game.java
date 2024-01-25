import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {
	private BufferedReader br;
	private boolean playon = true;
	private HashMap<String, String> field;
	private ArrayList<Player> players;
	private Player playerOnTurn;
	
	
	public Game() {
		br = new BufferedReader(new InputStreamReader(System.in));
		field = new HashMap<String, String>();
		players = new ArrayList<Player>();
		
		// Creating the field
		createField();
		
		// player creation
		System.out.println("Give the name of player 1: ");
		players.add(new Player(pInput(), "X"));
		System.out.println("Give the name of player 2: ");
		players.add(new Player(pInput(), "O"));
		
		// set first player to be on turn
		playerOnTurn = players.get(0);
	}
	
	
	public void run() {
		String playerInput;
		String[] input;
		
		help();
		printField();
		System.out.println();
		System.out.println(playerOnTurn.getName() + ", its your turn.");
		
		// Input loop to get the input of the players
		while(playon) {
			playerInput = pInput();
			
			System.out.println();
			
			// player input op exit of null
			if(playerInput.equals(null)) {
				System.out.println("");
				
			}else {
				input = playerInput.split(" ");
				inputHandler(input);
				
			}
			
		}
		
		System.out.println();
		System.out.println("Bedankt voor het spelen.");
	}
	
	
	private String pInput() {
		String pInput = null;
		
		try {
			System.out.print("> ");
			pInput = br.readLine();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		return pInput;
	}
	
	
	private void inputHandler(String[] input) {
		
		switch(input[0]){
		
		case "help":
			help();
			break;
			
		case "exit":
			playon = false;
			break;
		
		case "field":
			printField();
			break;
			
		case "place":
			if(input.length == 2) {
				placeSign(input[1]);
				break;
			}
			
		default:
			System.out.println(input[0] + " is not a valid command.");
			
		}
	}
	
	
	private void printField() {
		System.out.println(String.format("%s | %s | %s", field.get("a1"), field.get("a2"), field.get("a3")));
		System.out.println("---------");
		System.out.println(String.format("%s | %s | %s", field.get("b1"), field.get("b2"), field.get("b3")));
		System.out.println("---------");
		System.out.println(String.format("%s | %s | %s", field.get("c1"), field.get("c2"), field.get("c3")));
	}
	
	
	private void help() {
		System.out.println("---------------");
		System.out.println("Tic Tac Toe");
		System.out.println();
		System.out.println("This game is about getting 3 of your sign (X of O) in a row.");
		System.out.println("Players take turn and give the location of the place where they want to put there sign.");
		System.out.println("A player cant put his sign at the place where the other player already has placed it.");
		System.out.println("The player who is on turn puts the column(a, b or c) and the row(1,2 or 3)");
		System.out.println("For example b2");
		System.out.println("---------------");
		System.out.println();
	}
	
	
	private void createField() {
		field.put("a1", " ");
		field.put("a2", " ");
		field.put("a3", " ");
		field.put("b1", " ");
		field.put("b2", " ");
		field.put("b3", " ");
		field.put("c1", " ");
		field.put("c2", " ");
		field.put("c3", " ");
	}
	
	
	private void placeSign(String coordinate) {
	    boolean win = false;
	    boolean draw = false;

	    // Check valid coordinate
	    if (field.containsKey(coordinate)) {
	        // Check on open field
	    	
	        if (field.get(coordinate).equals(" ")) {
	            field.put(coordinate, playerOnTurn.getSign());
	            win = checkForWin();
	            draw = checkForDraw();

	            // Check for win
	            if (win) {
	                printField();

	                // Felicitatie
	                playerOnTurn.addWin();
	                System.out.println(playerOnTurn.getName() + " wins!");
	                System.out.println(playerOnTurn.getName() + " has won " + playerOnTurn.getTotalWins() + "x");
	                System.out.println();
	                System.out.println("Wanna play again?");

	                // Check to play again
	                playOn();
	            }else if(draw){
	            	
	            	printField();
	            	System.out.println("It is a draw");
	            	System.out.println();
	                System.out.println("Wanna play again?");

	                // Check to play again
	                playOn();
	            	
	            }else {
	            
	                // Print field and player on turn
	                switchPlayer();
	                printField();
	                System.out.println();
	                System.out.println(playerOnTurn.getName() + ", it is your turn.");
	            }

	        } else {
	            System.out.println(coordinate + " is already taken");
	        }

	    } else {
	        System.out.println(coordinate + " is not a valid coordinate");
	    }
	}
	
	private void switchPlayer() {
		playerOnTurn = (playerOnTurn == players.get(0)) ? players.get(1) : players.get(0);
	}
	
	
	private boolean checkForWin() {
		String sign = playerOnTurn.getSign();

	    // Define winning combinations
	    String[][] winningCombinations = {
	            {"a1", "a2", "a3"},
	            {"b1", "b2", "b3"},
	            {"c1", "c2", "c3"},
	            {"a1", "b1", "c1"},
	            {"a2", "b2", "c2"},
	            {"a3", "b3", "c3"},
	            {"a1", "b2", "c3"},
	            {"a3", "b2", "c1"}
	    };

	    // Check for win in any of the combinations
	    for (String[] combination : winningCombinations) {
	        if (field.get(combination[0]).equals(sign) && field.get(combination[1]).equals(sign) && field.get(combination[2]).equals(sign)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	private boolean checkForDraw() {
		boolean draw = field.values().stream().allMatch(value -> !value.equals(" "));
		
		return draw;
	}
	
	private void playOn() {
		String input;
		boolean tryAgain = true;
		
		while(tryAgain) {
			input = pInput();
			
			switch(input) {
			case "yes":
				playon = true;
				createField();
				switchPlayer();
				System.out.println();
				printField();
				System.out.println(playerOnTurn.getName() + ", it is your turn.");
				tryAgain = false;
				break;
				
			case "no": case "exit":
				playon = false;
				tryAgain = false;
				break;
				
			default:
				System.out.println("Please enter yes or no.");
				
			}
		}
	}
}

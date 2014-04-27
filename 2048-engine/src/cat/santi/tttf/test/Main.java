package cat.santi.tttf.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cat.santi.tttf.TTTFEngine;
import cat.santi.tttf.TTTFEngine.Direction;
import cat.santi.tttf.TTTFEngine.State;
import cat.santi.tttf.TTTFEngine.TTTFListener;

/**
 * Main test class, implementing the {@link TTTFEngine} listeners.
 * 
 * @author Santiago Gonzalez <santiago.gon.ber@gmail.com>
 */
public class Main
implements TTTFListener {

	/**
	 * First executed method. Meant just for testing purposes.
	 * 
	 * @param args The command-line arguments. Not used.
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		//- Prepare this test instance and option object
		Main main = new Main();
		Option option = null;
		
		//- Settle listeners
		TTTFEngine.getInstance().setTTTFListener(main);
		
		//- Prepare the game
		TTTFEngine.getInstance().reset();
		
		//- Main game loop
		do {
			
			//- Print the (debug version of the) board and read the user option (blocking)
			TTTFEngine.getInstance().printBoard();
			option = main.readOption();
			
			//- Perform the user option
			switch (option) {

			case PLAY_DOWN:
				
				TTTFEngine.getInstance().play(Direction.TO_DOWN);
				break;
			case PLAY_LEFT:
				
				TTTFEngine.getInstance().play(Direction.TO_LEFT);
				break;
			case PLAY_RIGHT:
				
				TTTFEngine.getInstance().play(Direction.TO_RIGHT);
				break;
			case PLAY_TOP:
				
				TTTFEngine.getInstance().play(Direction.TO_TOP);
				break;
			case JUST_PRINT:
			case EXIT:
			default:
				
				//- DO NOTHING
			}
			
			//- Until the 'exit' option is picked
		} while (!option.equals(Option.EXIT));
		
		//- Finish execution
		System.out.println("Thanks for playing!");
		System.exit(0);
	}
	
	/**
	 * Read an option from the console.
	 * 
	 * @return The read {@link Option}.
	 */
	public Option readOption() {

		try {
			
			//- Print the available options
			System.out.print("1 -> down | 2 -> left | 3 -> right | 4 -> top | 5 -> print | 0 -> exit: ");
			
			//- Read the user option
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = br.readLine();
			
			//- Parse the given integer as an option
			return Option.values()[Integer.parseInt(line)];
		} catch(IOException ex) {
			//- The option couldn't be read due to an I/O problem.
			
			System.out.println("I/O Exception, sorry but we must abort execution");
			System.exit(0);
		} catch(NumberFormatException ex) {
			//- The given option is not a number
			
			System.out.println("Invalid option, please try again");
			return readOption();
		} catch (ArrayIndexOutOfBoundsException ex) {
			//- The given option is outside available options
			
			System.out.println("Invalid option, please try again");
			return readOption();
		}
		return Option.EXIT;
	}
	
	/**
	 * Enumeration for available options.
	 */
	private enum Option {
		
		/** Exit the game. */
		EXIT,
		/** Play a DOWN movement. */
		PLAY_DOWN,
		/** Play a LEFT movement. */
		PLAY_LEFT,
		/** Play a RIGHT movement. */
		PLAY_RIGHT,
		/** Play a TOP movement. */
		PLAY_TOP,
		/** Print the board contents. */
		JUST_PRINT;
	}
	
	//- GAME LISTENER TRIGGERS

	@Override
	public void onStateChange(State state) {
		
		//- Print information about this callback
		System.out.println(" -- onStateChange - state: " + state.toString());
	}
	
	@Override
	public void onGameFinished(boolean victory, int turns, int score) {
		
		//- Print information about this callback
		System.out.println(" -- onGameFinished - victoy: " + victory + " | turns: " + turns + " | score: " + score);
	}
	
	@Override
	public void onTileMoved(int srcRow, int srcColumn, int dstRow, int dstColumn, Direction direction, boolean merged) {
		
		//- Print information about this callback
		System.out.println(" -- onTileMoved - srcRow: " + srcRow + " | srcColumn: " + srcColumn + " | dstRow: " + dstRow + " | dstColumn: " + dstColumn + " | direction: " + direction.toString() + " | merged: " + merged);
	}
	
	@Override
	public void onTileCreated(int row, int column, int value) {
		
		//- Print information about this callback
		System.out.println(" -- onTileCreated - row: " + row + " | column: " + column + " | value: " + value);
	}
	
	@Override
	public void onNotReady() {
		
		//- Print information about this callback
		System.out.println(" -- onNotReady");
	}
	
	@Override
	public void onDisallowedMove() {
		
		//- Print information about this callback
		System.out.println(" -- onDisallowedMove");
	}
}

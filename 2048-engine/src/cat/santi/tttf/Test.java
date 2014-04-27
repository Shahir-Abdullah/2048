package cat.santi.tttf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cat.santi.tttf.TTTFEngine.Direction;
import cat.santi.tttf.TTTFEngine.OnStateChangeListener;
import cat.santi.tttf.TTTFEngine.State;

public class Test
implements OnStateChangeListener {

	public static void main(String[] args) {
		
		Test test = new Test();
		Option option = null;
		
		TTTFEngine.getInstance().setOnStateChangeListener(test);
		
		do {
			
			TTTFEngine.getInstance().__print();
			option = test.readOption();
			
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
			case INVALID:
			default:
				
				//- DO NOTHING
			}
		} while (!option.equals(Option.EXIT));
		
		System.out.println("Thanks for playing! ^_^");
	}
	
	public Option readOption() {

		try {
			
			System.out.print("1 -> down | 2 -> left | 3 -> right | 4 -> top | 5 -> print | 0 -> exit: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = br.readLine();
			
			return Option.values()[Integer.parseInt(line)];
		} catch(IOException ex) {
			
			System.out.println("I/O Exception, sorry but we must abort execution");
			System.exit(0);
		} catch(NumberFormatException ex) {
			
			System.out.println("Invalid option, please try again");
			return readOption();
		} catch (ArrayIndexOutOfBoundsException ex) {
			
			System.out.println("Invalid option, please try again");
			return readOption();
		}
		return Option.INVALID;
	}
	
	private enum Option {
		
		EXIT,
		PLAY_DOWN,
		PLAY_LEFT,
		PLAY_RIGHT,
		PLAY_TOP,
		JUST_PRINT,
		INVALID;
	}

	@Override
	public void onStateChange(State state) {
		
		System.out.println(" -- STATE: " + state.toString());
	}
}

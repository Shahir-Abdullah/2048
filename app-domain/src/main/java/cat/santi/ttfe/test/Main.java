package cat.santi.ttfe.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cat.santi.ttfe.Engine;
import cat.santi.ttfe.Engine.Direction;
import cat.santi.ttfe.Engine.Listener;
import cat.santi.ttfe.Engine.State;

/**
 * Console version of the game, mostly for debug.
 *
 * @author Santiago Gonzalez
 */
public class Main
        implements Listener {

    /**
     * First executed method. Meant just for testing purposes.
     *
     * @param args The command-line arguments. Not used.
     */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        // Prepare this test instance and option object
        Main main = new Main();
        Option option;

        // Settle listeners
        Engine.getInstance().setListener(main);

        // Prepare the game
        Engine.getInstance().reset();

        // Main game loop
        do {

            // Print the (debug version of the) board and read the user option (blocking)
            Engine.getInstance().printBoard();
            option = main.readOption();

            // Perform the user option
            switch (option) {

                case PLAY_DOWN:

                    Engine.getInstance().play(Direction.DOWN, false);
                    break;
                case PLAY_LEFT:

                    Engine.getInstance().play(Direction.LEFT, false);
                    break;
                case PLAY_RIGHT:

                    Engine.getInstance().play(Direction.RIGHT, false);
                    break;
                case PLAY_TOP:

                    Engine.getInstance().play(Direction.UP, false);
                    break;
                case RESET:

                    Engine.getInstance().reset();
                    break;
                case SKIP:
                case EXIT:
                default:

                    // DO NOTHING
            }

            // Until the 'exit' option is picked
        } while (!option.equals(Option.EXIT));

        // Finish execution
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

            // Print the available options
            System.out.println("1 -> DOWN |" +
                            " 2 -> LEFT |" +
                            " 3 -> RIGHT |" +
                            " 4 -> UP |||" +
                            " 8 -> skip |" +
                            " 9 -> reset |" +
                            " 0 -> exit"
            );
            System.out.print("Your option: ");

            // Read the user option
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();

            // Parse the given integer as an option
            switch (Integer.parseInt(line)) {
                case 1:
                    return Option.PLAY_DOWN;
                case 2:
                    return Option.PLAY_LEFT;
                case 3:
                    return Option.PLAY_RIGHT;
                case 4:
                    return Option.PLAY_TOP;
                case 9:
                    return prompt("You are about to reset the game. Are you sure?", Option.RESET);
                case 0:
                    return prompt("Do you really want to exit the game?", Option.EXIT);
                case 8:
                default:
                    return Option.SKIP;
            }
        } catch (IOException ex) {
            // The option couldn't be read due to an I/O problem.

            System.out.println("I/O Exception, sorry but we must abort execution");
            System.exit(0);
        } catch (NumberFormatException ex) {
            // The given option is not a number

            System.out.println("Invalid option, please try again");
            return readOption();
        } catch (ArrayIndexOutOfBoundsException ex) {
            // The given option is outside available options

            System.out.println("Invalid option, please try again");
            return readOption();
        }
        return Option.SKIP;
    }

    private Option prompt(String text, Option positive) {

        System.out.print(text + " (yes/no) : ");

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();

            if (line != null && !line.equals("") && line.toLowerCase().equals("yes"))
                return positive;
        } catch (IOException e) {

            System.out.println("I/O Exception, sorry but we must abort execution");
            System.exit(0);
        }
        return Option.SKIP;
    }

    @Override
    public void onStateChange(State state) {

        // Print information about this callback
        System.out.println(" -- onStateChange - state: " + state.toString());
    }

    // GAME LISTENER TRIGGERS

    @Override
    public void onGameFinished(boolean victory, int turns, int score) {

        // Print information about this callback
        System.out.println(" -- onGameFinished - victory: " + victory + " |" +
                " turns: " + turns + " |" +
                " score: " + score
        );
    }

    @Override
    public void onTileMoved(int srcRow, int srcColumn, int dstRow, int dstColumn,
                            Direction direction, boolean merged) {

        // Print information about this callback
        System.out.println(" -- onTileMoved - srcRow: " + srcRow + " |" +
                " srcColumn: " + srcColumn + " |" +
                " dstRow: " + dstRow + " |" +
                " dstColumn: " + dstColumn + " |" +
                " direction: " + direction.toString() + " |" +
                " merged: " + merged
        );
    }

    @Override
    public void onTileCreated(int row, int column, int value) {

        // Print information about this callback
        System.out.println(" -- onTileCreated - row: " + row + " |" +
                " column: " + column + " |" +
                " value: " + value
        );
    }

    @Override
    public void onNotReady() {

        // Print information about this callback
        System.out.println(" -- onNotReady");
    }

    @Override
    public void onDisallowedMove() {

        // Print information about this callback
        System.out.println(" -- onDisallowedMove");
    }

    /**
     * Enumeration for available options.
     */
    private enum Option {

        /**
         * Exit the game.
         */
        EXIT,
        /**
         * Play a DOWN movement.
         */
        PLAY_DOWN,
        /**
         * Play a LEFT movement.
         */
        PLAY_LEFT,
        /**
         * Play a RIGHT movement.
         */
        PLAY_RIGHT,
        /**
         * Play a UP movement.
         */
        PLAY_TOP,
        /**
         * Skip the turn.
         */
        SKIP,
        /**
         * Reset the game, starting a new one.
         */
        RESET,
    }
}

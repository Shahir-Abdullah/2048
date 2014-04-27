package cat.santi.tttf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cat.santi.tttf.engine.Board;
import cat.santi.tttf.engine.Square;
import cat.santi.tttf.exceptions.NoAvailableSquaresException;
import cat.santi.tttf.exceptions.NotPow2ValueException;

public class TTTFEngine {

	public static final int MIN_BORDER_SIZE = Board.MIN_BORDER_SIZE;
	public static final int DEFAULT_WIDTH 	= Board.DEFAULT_WIDTH;
	public static final int DEFAULT_HEIGHT 	= Board.DEFAULT_HEIGHT;
	public static final int VOID_VALUE 		= Square.VOID_VALUE;
	
	private static TTTFEngine instance = null;
	
	private Random random = null;
	private Board board = null;
	private int score = 0;
	private int turns = 0;
	
	private TTTFEngine() {

		//- Initialize the random if needed
		if(random == null)
			random = new Random(System.currentTimeMillis());
		
		//- Create a new board
		board = new Board();
		
		//- Clean state variables
		score = 0;
		turns = 0;
		
		//- Create two values to start
		createValueAtPosition(createRandomPow2Value(findMaxValuePlaying(8)), findRandomAvailableSquare());
		createValueAtPosition(createRandomPow2Value(findMaxValuePlaying(8)), findRandomAvailableSquare());
	}
	
	public static synchronized TTTFEngine getInstance() {
		
		if(instance == null)
			instance = new TTTFEngine();
		return instance;
	}
	
	public boolean play(Direction direction) {
		
		switch (direction) {
		
		case TO_DOWN:
			
			return playToDown();
		case TO_LEFT:
			
			return playToLeft();
		case TO_RIGHT:
			
			return playToRight();
		case TO_TOP:
			
			return playToTop();
		default:
			
			return false;
		}
	}
	
	public boolean playToDown() {
		
		for(int indexR = board.getHeight() - 2 ; indexR >= 0 ; indexR--)
			for(int indexC = 0 ; indexC < board.getWidth() ; indexC++)
				tryToMove(indexR, indexC, indexR + 1, indexC);
			
		endTurn();
		return hasMoreMovesAvailable();
	}
	
	public boolean playToTop() {
		
		for(int indexR = 1 ; indexR < board.getHeight() ; indexR++)
			for(int indexC = 0 ; indexC < board.getWidth() ; indexC++)
				tryToMove(indexR, indexC, indexR - 1, indexC);
		
		endTurn();
		return hasMoreMovesAvailable();
	}
	
	public boolean playToRight() {
		
		for(int indexC = board.getWidth() - 2 ; indexC >= 0 ; indexC--)
			for(int indexR = 0 ; indexR < board.getHeight() ; indexR++)
				tryToMove(indexR, indexC, indexR, indexC + 1);
		
		endTurn();
		return hasMoreMovesAvailable();
	}
	
	public boolean playToLeft() {

		for(int indexC = 1 ; indexC < board.getWidth() ; indexC++)
			for(int indexR = 0 ; indexR < board.getHeight() ; indexR++)
				tryToMove(indexR, indexC, indexR, indexC - 1);
		
		endTurn();
		return hasMoreMovesAvailable();
	}
	
	public int[][] getBoardValues() {
		
		return Board.toIntMatrix(board);
	}
	
	public int getTurns() {
		
		return turns;
	}

	public int getScore() {
		
		return score;
	}
	
	public boolean hasMoreMovesAvailable() {
		
		//- TODO: IMPLEMENT
		return true;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("================== TURN : %4d ===================\n\n", getTurns() + 1));
		for(int indexR = 0 ; indexR < board.getHeight() ; indexR++) {
			
			for(int indexC = 0 ; indexC < board.getWidth() ; indexC++) {
				
				final int value = board.getSquare(indexR, indexC).getValue();
				final boolean isJustCreated = board.getSquare(indexR, indexC).isJustCreated();
				
				builder.append("  ");
				if(value != Square.VOID_VALUE) {

					if(isJustCreated)
						builder.append(String.format("[%04d]", value) + " ");
					else
						builder.append(String.format(" %04d ", value) + " ");
				} else{
					
					builder.append(" ____  ");
				}
			}
			builder.append("\n\n");
		}
		builder.append("==================================================\n");
		return builder.toString();
	}
	
	private void tryToMove(int fromRow, int fromColumn, int toRow, int toColumn) {
		
		Square fromSquare = null;
		Square toSquare = null;
		try {

			//- Get involved items
			fromSquare = board.getSquare(fromRow, fromColumn);
			toSquare = board.getSquare(toRow, toColumn);
		} catch(ArrayIndexOutOfBoundsException ex) {
			
			//- End of board reached, so do nothing more
			return;
		}
		
		//- Calculate direction (in case recursive call is needed)
		Direction direction = null;
		if(fromRow < toRow)
			direction = Direction.TO_DOWN;
		else if(fromRow > toRow)
			direction = Direction.TO_TOP;
		else if(fromColumn < toColumn)
			direction = Direction.TO_RIGHT;
		else if(fromColumn > toColumn)
			direction = Direction.TO_LEFT;
		
		if(fromSquare.getValue() == VOID_VALUE) {
			
			//- No value on 'from', so do nothing
		} else if(toSquare.isVoid()) {
			
			//- No value on 'to', so move (and try to move again)
			board.setValue(fromSquare.getValue(), toRow, toColumn);
			board.setValue(VOID_VALUE, fromRow, fromColumn);
			
			switch (direction) {
			
			case TO_DOWN:
				
				tryToMove(fromRow + 1, fromColumn, toRow + 1, toColumn);
				break;
			case TO_LEFT:
				
				tryToMove(fromRow, fromColumn - 1, toRow, toColumn - 1);
				break;
			case TO_RIGHT:
				
				tryToMove(fromRow, fromColumn + 1, toRow, toColumn + 1);
				break;
			case TO_TOP:
				
				tryToMove(fromRow - 1, fromColumn, toRow - 1, toColumn);
				break;
			}
		} else if(toSquare.shouldNotMergeThisTurn()) {
			
			//- The 'to' square was already merged, so do nothing
		} else if(fromSquare.getValue() == toSquare.getValue()) {
			
			//- Same value on 'from' and 'to', so move (and sum)
			board.setValue(toSquare.getValue() * 2, toRow, toColumn, true);
			board.setValue(VOID_VALUE, fromRow, fromColumn);
		} else {
			
			//- Different values on 'from' and 'to', so do nothing
		}
	}
	
	private void endTurn() {
		
		board.endTurn();
		turns++;
		
		createValueAtPosition(createRandomPow2Value(findMaxValuePlaying(8)), findRandomAvailableSquare());
	}
	
	private void createValueAtPosition(int value, Point point) {
		
		createValueAtPosition(value, point.row, point.column);
	}
	
	private void createValueAtPosition(int value, int row, int column) {
		
		board.setValue(value, row, column, false, true);
	}
	
	private int createRandomPow2Value(int max) throws NotPow2ValueException {
		
		int multiplier = findPow2Multiplier(max);
		
		if(multiplier < 1)
			throw new NotPow2ValueException();
		
		int multiply = random.nextInt(multiplier) + 1;
		return (int)Math.pow(2, multiply);
	}
	
	private Point findRandomAvailableSquare() throws NoAvailableSquaresException {
		
		List<Point> candidates = new ArrayList<Point>();
		
		for(int indexR = 0 ; indexR < board.getHeight() ; indexR++)
			for(int indexC = 0 ; indexC < board.getWidth() ; indexC++)
				if(board.getSquare(indexR, indexC).isVoid())
					candidates.add(new Point(indexR, indexC));
		
		if(candidates.size() == 0)
			throw new NoAvailableSquaresException();
		
		return candidates.get(random.nextInt(candidates.size()));
	}
	
	private int findPow2Multiplier(int value) {
		
		int count = 1;
		do {
			
			if(value == 2)
				return count;
			
			value /= 2;
			count++;
		} while(value >= 2 && value % 2 == 0);
		return 0;
	}
	
	private int findMaxValuePlaying(int maxAllowed) {
		
		int result = 2;
		
		for(int indexR = 0 ; indexR < board.getHeight(); indexR++)
			for(int indexC = 0 ; indexC < board.getWidth(); indexC++)
				if(board.getSquare(indexR, indexC).getValue() > result && board.getSquare(indexR, indexC).getValue() < maxAllowed)
					result = board.getSquare(indexR, indexC).getValue();
		
		return result;
	}
	
	public static enum Direction {
		
		TO_DOWN,
		TO_LEFT,
		TO_RIGHT,
		TO_TOP;
	}
	
	private static class Point {
		
		int row;
		int column;
		
		public Point(int row, int column) {
			
			this.row = row;
			this.column = column;
		}
	}
	
	protected void __print() {
		
		System.out.println(toString());
	}
}

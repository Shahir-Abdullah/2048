package cat.santi.tttf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cat.santi.tttf.engine.Board;
import cat.santi.tttf.engine.Square;
import cat.santi.tttf.exceptions.NoAvailableSquaresException;
import cat.santi.tttf.exceptions.NotPow2ValueException;

public class TTTFEngine {

	public static final int DEFAULT_WIDTH 	= Board.DEFAULT_WIDTH;
	public static final int DEFAULT_HEIGHT 	= Board.DEFAULT_HEIGHT;
	public static final int VOID_VALUE 		= Square.VOID_VALUE;
	
	private static TTTFEngine instance = null;
	private final Random random;
	
	private Board board;
	
	private TTTFEngine() {
		
		board = new Board();
		random = new Random(System.currentTimeMillis());
		
		try {
			
			Point point = findRandomAvailableSquare();
			createValueAtPosition(createRandomPow2Value(4), point.row, point.column);
		} catch(NoAvailableSquaresException ex) {
			
		}
	}
	
	public static synchronized TTTFEngine getInstance() {
		
		if(instance == null)
			instance = new TTTFEngine();
		return instance;
	}
	
	public boolean playToBottom() {
		
		//- TODO: IMPLEMENT
//		for(int indexC = 0 ; indexC < board.getWidth() ; indexC++)
//			board.getSquare(indexC, 0);
		return hasMoreMovesAvailable();
	}
	
	public boolean playToLeft() {
		
		//- TODO: IMPLEMENT
		return hasMoreMovesAvailable();
	}
	
	public boolean playToRight() {
		
		//- TODO: IMPLEMENT
		return hasMoreMovesAvailable();
	}
	
	public boolean playToTop() {
		
		//- TODO: IMPLEMENT
		return hasMoreMovesAvailable();
	}
	
	public int[][] getBoardValues() {
		
		return Board.toIntMatrix(board);
	}
	
	public int getScore() {
		
		//- TODO: IMPLEMENT
		return 0;
	}
	
	public boolean hasMoreMovesAvailable() {
		
		//- TODO: IMPLEMENT
		return true;
	}
	
	private void createValueAtPosition(int value, int row, int column) {
		
		board.setValue(value, row, column);
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
		} while(value >= 2 && value % 2 != 0);
		return 0;
	}

	public void __print() {
		
		int[][] values = Board.toIntMatrix(board);
		
		for(int indexR = 0 ; indexR < board.getHeight() ; indexR++) {
			
			for(int indexC = 0 ; indexC < board.getWidth() ; indexC++) {
				
				final int value = values[indexR][indexC];
				
				if(value != Square.VOID_VALUE)
					System.out.print(value + " ");
				else
					System.out.print("· ");
			}
			System.out.println();
		}
		System.out.println("==================================================");
	}
}

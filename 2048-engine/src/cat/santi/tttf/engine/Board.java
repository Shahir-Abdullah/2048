package cat.santi.tttf.engine;

import cat.santi.tttf.exceptions.GameInitializedException;
import cat.santi.tttf.exceptions.GameNotInitializedException;

public class Board {

	public static final int MIN_BORDER_SIZE = 4;
	public static final int DEFAULT_WIDTH 	= MIN_BORDER_SIZE;
	public static final int DEFAULT_HEIGHT 	= MIN_BORDER_SIZE;
	
	private boolean initialized;
	private int width;
	private int height;
	private Square[][] squares;
	
	public Board() {
		
		reset(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Board(int width, int height) {
		
		if(width < MIN_BORDER_SIZE)
			width = MIN_BORDER_SIZE;
		if(height < MIN_BORDER_SIZE)
			height = MIN_BORDER_SIZE;
		
		reset(width, height);
	}

	public int getWidth() {
		
		return width;
	}

	public void setWidth(int width) {
		
		if(isInitialized())
			throw new GameInitializedException();
		
		this.width = width;
	}

	public int getHeight() {
		
		return height;
	}

	public void setHeight(int height) {
		
		if(isInitialized())
			throw new GameInitializedException();
		
		this.height = height;
	}
	
	public Square getSquare(int row, int column) {
		
		if(!isInitialized())
			throw new GameNotInitializedException();
		
		return squares[row][column];
	}
	
	public Square[][] getSquares() {
		
		return squares;
	}

	public void setSquares(Square[][] squares) {
		
		if(isInitialized())
			throw new GameInitializedException();
		
		this.squares = squares;
	}
	
	public boolean isInitialized() {
		
		return initialized;
	}
	
	public void setValue(int value, int row, int column) {

		setValue(value, row, column, false);
	}
	
	public void setValue(int value, int row, int column, boolean shouldNotMerge) {

		setValue(value, row, column, shouldNotMerge, false);
	}
	
	public void setValue(int value, int row, int column, boolean shouldNotMerge, boolean justCreated) {
		
		squares[row][column].setValue(value);
		squares[row][column].setNotMergeThisTurn(shouldNotMerge);
		squares[row][column].setJustCreated(justCreated);
	}
	
	public void endTurn() {
		
		for(int indexR = 0 ; indexR < getHeight() ; indexR++) {
			
			for(int indexC = 0 ; indexC < getWidth() ; indexC++) {
				
				squares[indexR][indexC].setNotMergeThisTurn(false);
				squares[indexR][indexC].setJustCreated(false);
			}
		}
	}

	public void reset(int width, int height) {
		
		initialized = false;
		
		setWidth(width);
		setHeight(height);
		setSquares(new Square[getHeight()][getWidth()]);
		for(int indexR = 0 ; indexR < getHeight() ; indexR++)
			for(int indexC = 0 ; indexC < getWidth() ; indexC++)
				squares[indexR][indexC] = new Square();
		
		initialized = true;
	}

	public static int[][] toIntMatrix(Board board) {

		if(board == null)
			return null;
		
		int[][] result = new int[board.getHeight()][board.getWidth()];
		for(int indexR = 0 ; indexR < board.getHeight() ; indexR++)
			for(int indexC = 0 ; indexC < board.getWidth() ; indexC++)
				result[indexR][indexC] = board.getSquares()[indexR][indexC].getValue();
		
		return result;
	}
}
package cat.santi.tttf.engine;

public class Square {

	public static final int VOID_VALUE = 0;
	
	private int value;
	private boolean justCreated;
	private boolean notMergeThisTurn;
	
	public Square() {
		
		setValue(VOID_VALUE);
		setNotMergeThisTurn(false);
	}

	public int getValue() {
		
		return value;
	}

	public void setValue(int value) {
		
		this.value = value;
	}
	
	public boolean isVoid() {
		
		return getValue() == VOID_VALUE;
	}
	
	public boolean shouldNotMergeThisTurn() {
		
		return notMergeThisTurn;
	}

	public void setNotMergeThisTurn(boolean notMergeThisTurn) {
		
		this.notMergeThisTurn = notMergeThisTurn;
	}
	
	public boolean isJustCreated() {
		
		return justCreated;
	}

	public void setJustCreated(boolean justCreated) {
		
		this.justCreated = justCreated;
	}
}

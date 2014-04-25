package cat.santi.tttf.engine;

public class Square {

	public static final int VOID_VALUE = 0;
	
	private int value;
	
	public Square() {
		
		setValue(VOID_VALUE);
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
}

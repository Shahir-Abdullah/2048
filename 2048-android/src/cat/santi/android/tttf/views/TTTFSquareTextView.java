package cat.santi.android.tttf.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TTTFSquareTextView extends TextView {

	public TTTFSquareTextView(Context context) {
		super(context);
	}
	
	public TTTFSquareTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TTTFSquareTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}

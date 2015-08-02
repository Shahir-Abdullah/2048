package cat.santi.ttfe.presentation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Santiago Gonzalez
 */
public class SquareTextView extends TextView {

	public SquareTextView(Context context) {
		super(context);
	}
	
	public SquareTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SquareTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//noinspection SuspiciousNameCombination
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}

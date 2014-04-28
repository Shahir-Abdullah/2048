package cat.santi.android.tttf.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

public class TTTFTilesGridView extends GridView {

	private static final String TAG = TTTFTilesGridView.class.getSimpleName();
	
	private TilesGridViewCallbacks dummyCallbacks = new TilesGridViewCallbacks() {
		
		@Override
		public void onPlayedTop() {
			Log.d(TAG, "onPlayedTop");
		}
		
		@Override
		public void onPlayedRight() {
			Log.d(TAG, "onPlayedRight");
		}
		
		@Override
		public void onPlayedLeft() {
			Log.d(TAG, "onPlayedLeft");
		}
		
		@Override
		public void onPlayedDown() {
			Log.d(TAG, "onPlayedBottom");
		}
	};
	
	private float mStartX = 0;
	private float mStartY = 0;
	
	private TilesGridViewCallbacks mCallbacks = dummyCallbacks;
	
	public TTTFTilesGridView(Context context) {
		super(context);
	}
	
	public TTTFTilesGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TTTFTilesGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			mStartX = ev.getX();
			mStartY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			
			float deltaX = ev.getX() - mStartX;
			float deltaY = ev.getY() - mStartY;
			
			if(Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > 200) {
				
				if(deltaX > 0)
					mCallbacks.onPlayedRight();
				else
					mCallbacks.onPlayedLeft();
			}
			if(Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(deltaY) > 200) {

				if(deltaY > 0)
					mCallbacks.onPlayedDown();
				else
					mCallbacks.onPlayedTop();
			}
			break;
		}
		return true;
	}
	
	public void setTilesGridViewCallbacks(TilesGridViewCallbacks callbacks) {
		
		if(callbacks == null)
			callbacks = dummyCallbacks;
		
		mCallbacks = callbacks;
	}
	
	public interface TilesGridViewCallbacks {
	
		public void onPlayedRight();
		
		public void onPlayedLeft();
		
		public void onPlayedDown();
		
		public void onPlayedTop();
	}
}

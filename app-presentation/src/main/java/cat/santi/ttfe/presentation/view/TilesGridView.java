package cat.santi.ttfe.presentation.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import cat.santi.ttfe.Engine;

/**
 * @author Santiago Gonzalez
 */
public class TilesGridView extends GridView {

    private static final float TOUCH_DISTANCE_THRESHOLD = 200;

    private float mStartX = 0;
    private float mStartY = 0;

    private TilesGridViewCallbacks mCallbacks = null;

    public TilesGridView(Context context) {
        super(context);
    }

    public TilesGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TilesGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {

        if (mCallbacks != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    mStartX = ev.getX();
                    mStartY = ev.getY();
                    break;
                case MotionEvent.ACTION_UP:

                    float deltaX = ev.getX() - mStartX;
                    float deltaY = ev.getY() - mStartY;

                    if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > TOUCH_DISTANCE_THRESHOLD) {

                        if (deltaX > 0)
                            mCallbacks.onPlayed(Engine.Direction.RIGHT);
                        else
                            mCallbacks.onPlayed(Engine.Direction.LEFT);
                    }
                    if (Math.abs(deltaX) < Math.abs(deltaY) && Math.abs(deltaY) > TOUCH_DISTANCE_THRESHOLD) {

                        if (deltaY > 0)
                            mCallbacks.onPlayed(Engine.Direction.DOWN);
                        else
                            mCallbacks.onPlayed(Engine.Direction.UP);
                    }
                    break;
            }
        }
        return true;
    }

    public void setTilesGridViewCallbacks(TilesGridViewCallbacks callbacks) {

        mCallbacks = callbacks;
    }

    public interface TilesGridViewCallbacks {

        void onPlayed(Engine.Direction direction);
    }
}

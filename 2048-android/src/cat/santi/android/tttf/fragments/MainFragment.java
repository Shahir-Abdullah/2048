package cat.santi.android.tttf.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cat.santi.android.tttf.R;
import cat.santi.android.tttf.adapters.TileAdapter;
import cat.santi.android.tttf.adapters.TileAdapter.TileAdapterCallbacks;
import cat.santi.android.tttf.views.TTTFTilesGridView;
import cat.santi.android.tttf.views.TTTFTilesGridView.TilesGridViewCallbacks;
import cat.santi.tttf.TTTFEngine;
import cat.santi.tttf.TTTFEngine.Direction;
import cat.santi.tttf.TTTFEngine.State;
import cat.santi.tttf.TTTFEngine.TTTFListener;

public class MainFragment extends AbsTTTFFragment
implements TileAdapterCallbacks, TilesGridViewCallbacks, TTTFListener {

	private static final String TAG = MainFragment.class.getSimpleName();
	
	private TextView mTVTurn;
	private TextView mTVScore;
	private TextView mTVGameState;
	private TextView mTVStatus;
	private TTTFTilesGridView mGVTiles;
	private TileAdapter mTileAdapter;
	
	public static MainFragment newInstance() {
		
		return new MainFragment();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mCallbacks = (MainFragmentCallbacks)activity;
		} catch(ClassCastException ex) {
			Log.e(TAG, "This Activity must implement [" + MainFragmentCallbacks.class.getSimpleName() + "]");
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		
		mCallbacks = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return onCreateView(R.layout.fragment_main, inflater, container, savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		TTTFEngine.getInstance().setTTTFListener(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		TTTFEngine.getInstance().removeTTTFListener();
	}

	@Override
	protected void configureActionBar() {
		
	}

	@Override
	protected void parseArguments(Bundle args) {
	
		if(args == null)
			return;
	}

	@Override
	protected void getViews(View fragmentView) {
		
		mTVTurn = (TextView)fragmentView.findViewById(R.id.tttf__fragment_main__tv_turn);
		mTVScore = (TextView)fragmentView.findViewById(R.id.tttf__fragment_main__tv_score);
		mTVGameState = (TextView)fragmentView.findViewById(R.id.tttf__fragment_main__tv_game_state);
		mTVStatus = (TextView)fragmentView.findViewById(R.id.tttf__fragment_main__tv_status);
		mGVTiles = (TTTFTilesGridView)fragmentView.findViewById(R.id.tttf__fragment_main__gv_tiles);
	}

	@Override
	protected void firstInit() {
		
	}

	@Override
	protected void init() {
		
		mTileAdapter = new TileAdapter(getActivity(), this);
		mGVTiles.setAdapter(mTileAdapter);
		mGVTiles.setTilesGridViewCallbacks(this);
		
		mTVTurn.setText(getString(R.string.ffft__main__turns, 0));
		mTVScore.setText(getString(R.string.ffft__main___score, 0));
		mTVGameState.setText("Waiting for user imput...");
		mTVStatus.setText("Good luck!");
	}

	@Override
	protected void saveState(Bundle outState) {
		
	}

	@Override
	protected void restoreState(Bundle savedInstanceState) {
		
		if(savedInstanceState == null)
			return;
	}

	@Override
	public void onPlayedRight() {

		if(TTTFEngine.getInstance().play(Direction.TO_RIGHT, false))
			mCallbacks.onUserPlay();
	}

	@Override
	public void onPlayedLeft() {
		
		if(TTTFEngine.getInstance().play(Direction.TO_LEFT, false))
			mCallbacks.onUserPlay();
	}

	@Override
	public void onPlayedDown() {
		
		if(TTTFEngine.getInstance().play(Direction.TO_DOWN, false))
			mCallbacks.onUserPlay();
	}

	@Override
	public void onPlayedTop() {
		
		if(TTTFEngine.getInstance().play(Direction.TO_TOP, false))
			mCallbacks.onUserPlay();
	}

	@Override
	public void onStateChange(State state) {

		if(state.equals(State.IDDLE))
			if(mTileAdapter != null)
				mTileAdapter.notifyDataSetChanged();
		
		if(mTVGameState != null)
			mTVGameState.setText("State: " + state.toString());
	}

	@Override
	public void onGameFinished(boolean victory, int turns, int score) {
		
		if(mTileAdapter != null)
			mTileAdapter.notifyDataSetChanged();
	
		if(mTVStatus != null)
			mTVStatus.setText(victory ? "Congratulations, you've WON!" : "Sorry, you were defeated!");
	}

	@Override
	public void onTileCreated(int row, int column, int value) {
		
	}

	@Override
	public void onTileMoved(int srcRow, int srcColumn, int dstRow, int dstColumn, Direction direction, boolean merged) {
		
	}

	@Override
	public void onNotReady() {
		
		if(mTVStatus != null)
			mTVStatus.setText("Not ready yet...");
	}

	@Override
	public void onDisallowedMove() {
		
		if(mTVStatus != null)
			mTVStatus.setText("Movement not allowed!");
	}

	@Override
	public void onTurnChanged(int turn) {
		
		if(mTVTurn != null)
			mTVTurn.setText(getString(R.string.ffft__main__turns, turn));
		
		if(mTVStatus != null)
			mTVStatus.setText(null);
	}

	@Override
	public void onScoreChanged(int score) {
		
		if(mTVScore != null)
			mTVScore.setText(getString(R.string.ffft__main___score, score));
	}
	
	private MainFragmentCallbacks dummyCallbacks = new MainFragmentCallbacks() {
		
		@Override
		public void onUserPlay() {}
	};
	
	private MainFragmentCallbacks mCallbacks = dummyCallbacks;
	
	public interface MainFragmentCallbacks {
		
		public void onUserPlay();
	}
}

package cat.santi.android.tttf.fragments;

import android.os.Bundle;
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
	private TTTFTilesGridView mGVTiles;
	private TileAdapter mTileAdapter;
	
	public static MainFragment newInstance() {
		
		return new MainFragment();
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

		TTTFEngine.getInstance().play(Direction.TO_RIGHT, false);
	}

	@Override
	public void onPlayedLeft() {
		
		TTTFEngine.getInstance().play(Direction.TO_LEFT, false);
	}

	@Override
	public void onPlayedDown() {
		
		TTTFEngine.getInstance().play(Direction.TO_DOWN, false);
	}

	@Override
	public void onPlayedTop() {
		
		TTTFEngine.getInstance().play(Direction.TO_TOP, false);
	}

	@Override
	public void onStateChange(State state) {

		if(state.equals(State.IDDLE))
			if(mTileAdapter != null)
				mTileAdapter.notifyDataSetChanged();
	}

	@Override
	public void onGameFinished(boolean victory, int turns, int score) {
		
		if(mTileAdapter != null)
			mTileAdapter.notifyDataSetChanged();
	}

	@Override
	public void onTileCreated(int row, int column, int value) {
		
	}

	@Override
	public void onTileMoved(int srcRow, int srcColumn, int dstRow, int dstColumn, Direction direction, boolean merged) {
		
	}

	@Override
	public void onNotReady() {
		
	}

	@Override
	public void onDisallowedMove() {
		
	}

	@Override
	public void onTurnChanged(int turn) {
		
		if(mTVTurn != null)
			mTVTurn.setText(getString(R.string.turns, turn));
	}

	@Override
	public void onScoreChanged(int score) {
		
		if(mTVScore != null)
			mTVScore.setText(getString(R.string.score, score));
	}
}

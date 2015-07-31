package cat.santi.ttfe.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.santi.ttfe.Engine;
import cat.santi.ttfe.Engine.Direction;
import cat.santi.ttfe.Engine.Listener;
import cat.santi.ttfe.Engine.State;
import cat.santi.ttfe.R;
import cat.santi.ttfe.adapter.TileAdapter;
import cat.santi.ttfe.view.TilesGridView;

public class MainFragment extends AbstractFragment implements
        TileAdapter.TileAdapterCallbacks,
        TilesGridView.TilesGridViewCallbacks,
        Listener {

    private static final String TAG = MainFragment.class.getSimpleName();

    private TextView mTVTurn;
    private TextView mTVScore;
    private TextView mTVGameState;
    private TextView mTVStatus;
    private TilesGridView mGVTiles;
    private TileAdapter mTileAdapter;

    private MainFragmentCallbacks mCallbacks = null;

    public static MainFragment newInstance() {

        return new MainFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallbacks = (MainFragmentCallbacks) activity;
        } catch (ClassCastException ex) {
            Log.e(TAG, "The Activity must implement [" + MainFragmentCallbacks.class + "]");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return onCreateView(R.layout.ttfe__fragment_main, inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        Engine.getInstance().setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        Engine.getInstance().removeListener();
    }

    @Override
    protected void configureActionBar() {
        // Do nothing
    }

    @Override
    protected void parseArguments(Bundle args) {
        // Do nothing
    }

    @Override
    protected void getViews(View fragmentView) {

        mTVTurn = (TextView) fragmentView.findViewById(R.id.tttf__fragment_main__tv_turn);
        mTVScore = (TextView) fragmentView.findViewById(R.id.tttf__fragment_main__tv_score);
        mTVGameState = (TextView) fragmentView.findViewById(R.id.tttf__fragment_main__tv_game_state);
        mTVStatus = (TextView) fragmentView.findViewById(R.id.tttf__fragment_main__tv_status);
        mGVTiles = (TilesGridView) fragmentView.findViewById(R.id.tttf__fragment_main__gv_tiles);
    }

    @Override
    protected void firstInit() {
        // Do nothing
    }

    @Override
    protected void init() {

        mTileAdapter = new TileAdapter(getActivity(), this);
        mGVTiles.setAdapter(mTileAdapter);
        mGVTiles.setTilesGridViewCallbacks(this);

        mTVTurn.setText(getString(R.string.ttfe__main__turns, 0));
        mTVScore.setText(getString(R.string.ttfe__main___score, 0));
        mTVGameState.setText("Waiting for user imput...");
        mTVStatus.setText("Good luck!");
    }

    @Override
    protected void saveState(Bundle outState) {
        // Do nothing
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        // Do nothing
    }

    @Override
    public void onPlayed(Direction direction) {
        if (Engine.getInstance().play(direction, false))
            if (mCallbacks != null)
                mCallbacks.onUserPlay(direction);
    }

    @Override
    public void onStateChange(State state) {

        if (state.equals(State.IDLE))
            if (mTileAdapter != null)
                mTileAdapter.notifyDataSetChanged();

        if (mTVGameState != null)
            mTVGameState.setText("State: " + state.toString());
    }

    @Override
    public void onGameFinished(boolean victory, int turns, int score) {

        if (mTileAdapter != null)
            mTileAdapter.notifyDataSetChanged();

        if (mTVStatus != null)
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

        if (mTVStatus != null)
            mTVStatus.setText("Not ready yet...");
    }

    @Override
    public void onDisallowedMove() {

        if (mTVStatus != null)
            mTVStatus.setText("Movement not allowed!");
    }

    @Override
    public void onTurnChanged(int turn) {

        if (mTVTurn != null)
            mTVTurn.setText(getString(R.string.ttfe__main__turns, turn));

        if (mTVStatus != null)
            mTVStatus.setText(null);
    }

    @Override
    public void onScoreChanged(int score) {

        if (mTVScore != null)
            mTVScore.setText(getString(R.string.ttfe__main___score, score));
    }

    public interface MainFragmentCallbacks {

        void onUserPlay(Direction direction);
    }
}

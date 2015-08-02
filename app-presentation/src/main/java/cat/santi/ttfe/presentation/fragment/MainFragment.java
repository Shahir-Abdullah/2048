package cat.santi.ttfe.presentation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cat.santi.ttfe.Engine;
import cat.santi.ttfe.presentation.R;
import cat.santi.ttfe.presentation.adapter.TileAdapter;
import cat.santi.ttfe.presentation.interaction.MainInteractor;
import cat.santi.ttfe.presentation.interaction.interactor.MainInteractorImpl;
import cat.santi.ttfe.presentation.view.TilesGridView;

/**
 * @author Santiago Gonzalez
 */
public class MainFragment extends AbstractFragment<MainInteractor> implements
        TileAdapter.TileAdapterCallbacks,
        TilesGridView.TilesGridViewCallbacks,
        Engine.Listener {

    private static final String TAG = MainFragment.class.getSimpleName();
    private static final String STATE_MOVES = "STATE_MOVES";
    private static final String STATE_SCORE = "STATE_SCORE";
    private static final String STATE_GAME_STATE = "STATE_GAME_STATE";
    private static final String STATE_STATUS = "STATE_STATUS";

    private TextView mTVMoves;
    private TextView mTVScore;
    private TextView mTVGameState;
    private TextView mTVStatus;
    private TilesGridView mGVTiles;
    private TileAdapter mTileAdapter;

    private String // Auxiliary strings to carry data across state
            auxMovesText = null,
            auxScoreText = null,
            auxStatusText = null,
            auxGameStateText = null;

    private MainFragmentCallbacks mCallbacks = null;

    public static MainFragment newInstance() {

        MainFragment instance = new MainFragment();
        instance.setRetainInstance(true);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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
    protected MainInteractor onCreateInteractor() {

        return new MainInteractorImpl(getApp());
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

        mTVMoves = (TextView) fragmentView.findViewById(R.id.ttfe__fragment_main__tv_turn);
        mTVScore = (TextView) fragmentView.findViewById(R.id.ttfe__fragment_main__tv_score);
        mTVGameState = (TextView) fragmentView.findViewById(R.id.ttfe__fragment_main__tv_game_state);
        mTVStatus = (TextView) fragmentView.findViewById(R.id.ttfe__fragment_main__tv_status);
        mGVTiles = (TilesGridView) fragmentView.findViewById(R.id.ttfe__fragment_main__gv_tiles);
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

        initViews();
    }

    @Override
    protected void saveState(Bundle outState) {

        outState.putString(STATE_MOVES, mTVMoves.getText().toString());
        outState.putString(STATE_SCORE, mTVScore.getText().toString());
        outState.putString(STATE_GAME_STATE, mTVGameState.getText().toString());
        outState.putString(STATE_STATUS, mTVStatus.getText().toString());
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {

        auxMovesText = savedInstanceState.getString(STATE_MOVES, "");
        auxScoreText = savedInstanceState.getString(STATE_SCORE, "");
        auxGameStateText = savedInstanceState.getString(STATE_GAME_STATE, "");
        auxStatusText = savedInstanceState.getString(STATE_STATUS, "");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.ttfe__main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.ttfe__menu_action_reset:

                onActionReset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPlayed(Engine.Direction direction) {

        if (Engine.getInstance().play(direction, false))
            if (mCallbacks != null)
                mCallbacks.onUserPlay(direction);
    }

    @Override
    public void onStateChange(Engine.State state) {

        if (state.equals(Engine.State.IDLE))
            if (mTileAdapter != null)
                mTileAdapter.notifyDataSetChanged();

        setState(state);
    }

    @Override
    public void onGameFinished(boolean victory, int turns, int score) {

        if (mTileAdapter != null)
            mTileAdapter.notifyDataSetChanged();

        setStatus(victory ? getString(R.string.ttfe__status_you_win) : getString(R.string.ttfe__status_you_lose));
    }

    @Override
    public void onTileCreated(int row, int column, int value) {
        // Do nothing
    }

    @Override
    public void onTileMoved(int srcRow, int srcColumn, int dstRow, int dstColumn,
                            Engine.Direction direction, boolean merged) {
        // Do nothing
    }

    @Override
    public void onNotReady() {

        setStatus(getString(R.string.ttfe__status_not_ready));
    }

    @Override
    public void onDisallowedMove() {

        setStatus(getString(R.string.ttfe__status_disallowed_move));
    }

    @Override
    public void onTurnChanged(int moves) {

        setMoves(moves);
        setStatus(null);
    }

    @Override
    public void onScoreChanged(int score) {

        setScore(score);
    }

    public void setMoves(int moves) {

        if (mTVMoves != null)
            mTVMoves.setText(getString(R.string.ttfe__main__moves, moves));
    }

    public void setScore(int score) {

        if (mTVScore != null)
            mTVScore.setText(getString(R.string.ttfe__main__score, score));
    }

    public void setState(Engine.State state) {

        if (mTVGameState != null)
            mTVGameState.setText(getString(R.string.ttfe__main_game_state, state.toString()));
    }

    public void setStatus(String status) {

        if (mTVStatus != null)
            mTVStatus.setText(status);
    }

    private void initViews() {

        mTVMoves.setText(auxMovesText != null ?
                auxMovesText : getString(R.string.ttfe__main__moves, 0));
        mTVScore.setText(auxScoreText != null ?
                auxScoreText : getString(R.string.ttfe__main__score, 0));
        mTVGameState.setText(auxGameStateText != null ?
                auxGameStateText : getString(R.string.ttfe__state_waiting));
        mTVStatus.setText(auxStatusText != null ?
                auxStatusText : getString(R.string.ttfe__status_good_luck));

        auxMovesText = null;
        auxScoreText = null;
        auxGameStateText = null;
        auxStatusText = null;
    }

    private void onActionReset() {

        getInteractor().getResetGameUC().execute();
    }

    public interface MainFragmentCallbacks {

        void onUserPlay(Engine.Direction direction);
    }
}

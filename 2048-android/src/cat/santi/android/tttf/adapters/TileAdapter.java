package cat.santi.android.tttf.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cat.santi.android.tttf.R;
import cat.santi.tttf.TTTFEngine;
import cat.santi.tttf.TTTFEngine.Tile;

public class TileAdapter extends BaseAdapter {
	
	private TileAdapterCallbacks mCallbacks;
	private LayoutInflater mInflater;
	
	public TileAdapter(Context context, TileAdapterCallbacks callbacks) {
	
		mInflater = LayoutInflater.from(context);
		mCallbacks = callbacks;
	}
	
	@Override
	public int getCount() {
		
		return TTTFEngine.getInstance().getState().equals(TTTFEngine.State.NOT_PREPARED) ? 0 : TTTFEngine.getInstance().getBoardSize();
	}

	@Override
	public Tile getItem(int position) {
		
		final Tile[][] tileMatrix = TTTFEngine.getInstance().getBoardTiles();
		
		int index = 0;
		for(int indexR = 0 ; indexR < TTTFEngine.getInstance().getBoardRows() ; indexR++)
			for(int indexC = 0 ; indexC < TTTFEngine.getInstance().getBoardColumns() ; indexC++, index++)
				if(index == position)
					return tileMatrix[indexR][indexC];
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return (long)position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Tile item = getItem(position);
		
		ViewHolder holder = null;
		if(convertView == null) {
			
			convertView = mInflater.inflate(R.layout.grid_item_tile, null);
			
			holder = new ViewHolder();
			holder.value = (TextView)convertView;
			convertView.setTag(holder);
		} else {
			
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.value.setText(String.valueOf(item.getValue() > 0 ? item.getValue() : ""));
		applyColor(holder.value);
		
		return convertView;
	}
	
	private void applyColor(TextView value) {
		
		if(value == null)
			return;
		
		String text = value.getText().toString();
		
		if(text.equals("") || text.equals("0")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_0_border);
			value.setTextColor(Color.BLACK);
		} else if(text.equals("2")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_2_border);
			value.setTextColor(Color.BLACK);
		} else if(text.equals("4")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_4_border);
			value.setTextColor(Color.BLACK);
		} else if(text.equals("8")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_8_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("16")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_16_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("32")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_32_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("64")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_64_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("128")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_128_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("256")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_256_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("512")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_512_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("1024")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_1024_border);
			value.setTextColor(Color.WHITE);
		} else if(text.equals("2048")) {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_2048_border);
			value.setTextColor(Color.WHITE);
		} else {
			value.setBackgroundResource(R.drawable.tttf__shape_tile_more_border);
			value.setTextColor(Color.WHITE);
		}
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		
		mCallbacks.onTurnChanged(TTTFEngine.getInstance().getTurns());
		mCallbacks.onScoreChanged(TTTFEngine.getInstance().getScore());
	}
	
	private static class ViewHolder {
		
		TextView value;
	}

	public interface TileAdapterCallbacks {
		
		public void onTurnChanged(int turn);
		
		public void onScoreChanged(int score);
	}
}

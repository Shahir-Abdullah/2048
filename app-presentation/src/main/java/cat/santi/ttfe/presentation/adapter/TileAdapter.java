package cat.santi.ttfe.presentation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cat.santi.ttfe.Engine;
import cat.santi.ttfe.presentation.R;

/**
 * @author Santiago Gonzalez
 */
public class TileAdapter extends BaseAdapter {
	
	private TileAdapterCallbacks mCallbacks;
	private LayoutInflater mInflater;
	
	public TileAdapter(Context context, TileAdapterCallbacks callbacks) {
	
		mInflater = LayoutInflater.from(context);
		mCallbacks = callbacks;
	}
	
	@Override
	public int getCount() {
		
		return Engine.getInstance().getState().equals(Engine.State.NOT_PREPARED) ?
				0 : Engine.getInstance().getBoardSize();
	}

	@Override
	public Engine.Tile getItem(int position) {
		
		final Engine.Tile[][] tileMatrix = Engine.getInstance().getTiles();
		
		int i = 0;
		for(int indexR = 0 ; indexR < Engine.getInstance().getBoardRows() ; indexR++)
			for(int indexC = 0 ; indexC < Engine.getInstance().getBoardColumns() ; indexC++, i++)
				if(i == position)
					return tileMatrix[indexR][indexC];
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return (long)position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Engine.Tile item = getItem(position);
		
		ViewHolder holder;
		if(convertView == null) {
			
			convertView = mInflater.inflate(R.layout.ttfe__grid_item_tile, parent, false);
			
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

		switch (text) {
			case "":
			case "0":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_0_border);
				value.setTextColor(Color.BLACK);
				break;
			case "2":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_2_border);
				value.setTextColor(Color.BLACK);
				break;
			case "4":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_4_border);
				value.setTextColor(Color.BLACK);
				break;
			case "8":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_8_border);
				value.setTextColor(Color.WHITE);
				break;
			case "16":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_16_border);
				value.setTextColor(Color.WHITE);
				break;
			case "32":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_32_border);
				value.setTextColor(Color.WHITE);
				break;
			case "64":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_64_border);
				value.setTextColor(Color.WHITE);
				break;
			case "128":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_128_border);
				value.setTextColor(Color.WHITE);
				break;
			case "256":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_256_border);
				value.setTextColor(Color.WHITE);
				break;
			case "512":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_512_border);
				value.setTextColor(Color.WHITE);
				break;
			case "1024":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_1024_border);
				value.setTextColor(Color.WHITE);
				break;
			case "2048":
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_2048_border);
				value.setTextColor(Color.WHITE);
				break;
			default:
				value.setBackgroundResource(R.drawable.ttfe__shape_tile_more_border);
				value.setTextColor(Color.WHITE);
				break;
		}
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
		
		mCallbacks.onTurnChanged(Engine.getInstance().getMovements());
		mCallbacks.onScoreChanged(Engine.getInstance().getScore());
	}
	
	private static class ViewHolder {
		
		TextView value;
	}

	public interface TileAdapterCallbacks {
		
		void onTurnChanged(int turn);
		
		void onScoreChanged(int score);
	}
}

package app.awtr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

public class DeleteWords extends ListActivity {
	
	private Word_Data_Source datasource;
	ArrayList<Word> toDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_words);
		toDelete = new ArrayList<Word>();
		datasource = new Word_Data_Source(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
		setListAdapter(new WordListAdapter(this, datasource.getAllWords()));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("awtr:delete:", "" + toDelete.size());
		datasource.close();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.d("pos: ", Integer.toString(position));
		Intent showUpdate = new Intent();
		showUpdate.setClass(this, ModifyWord.class);
		Bundle extras = new Bundle();
		extras.putString("word_name", datasource.getAllWords().get(position).word);
		showUpdate.putExtras(extras);
		startActivity(showUpdate);
	}
	
	
	private class WordListAdapter extends ArrayAdapter<Word> {
		private Context context;
		private List<Word> words;
		private LayoutInflater mInflator;
		
		public WordListAdapter(Context context, List<Word> words) {
			super(context, R.layout.word_item, words);
			this.context = context;
			this.words = words;
			mInflator = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if(convertView == null) {
				view = mInflator.inflate(R.layout.word_item,null);
				ViewHolder viewHolder = new ViewHolder();
				viewHolder.word = (TextView) view.findViewById(R.id.word_view);
				viewHolder.check = (CheckBox) view.findViewById(R.id.check_word_view);
				viewHolder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						if(!isChecked){
							toDelete.remove(words.get(position));
							Log.d("position: ", Integer.toString(position));
						}
						else{
							toDelete.add(words.get(position));
						}
						
					}
				});
				view.setTag(viewHolder);
			}
			
			ViewHolder viewHolder = (ViewHolder) view.getTag();
			viewHolder.word.setText(words.get(position).getWord());
			if(toDelete.indexOf(words.get(position)) != -1) {
				viewHolder.check.setChecked(true);
			}
			else {
				viewHolder.check.setChecked(false);
			}
			viewHolder.position = position;
			//	Log.d("positionafter: ", Integer.toString(position));
			return view;
		}
		
		private class ViewHolder {
			TextView word;
			CheckBox check;
			int position;
		}
	}
}

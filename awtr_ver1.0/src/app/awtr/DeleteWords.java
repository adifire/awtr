package app.awtr;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class DeleteWords extends ListActivity {
	
	private Word_Data_Source datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_words);
		datasource = new Word_Data_Source(this);
		datasource.open();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.word_item, R.id.word_view, datasource.getAllWordsS());
		setListAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
		
}

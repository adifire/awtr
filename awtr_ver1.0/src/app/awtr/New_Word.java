package app.awtr;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class New_Word extends Activity {
	
	protected Word_Data_Source datasource;
	EditText word_edit, definition_edit;
	Button add_word, cancel_button;
	
	private OnClickListener add_word_listener = new OnClickListener() {
		
		public void onClick(View v) {
			String word_name = word_edit.getText().toString();
			String definition = definition_edit.getText().toString();
			Log.d("awtr:word:", word_name.length()+":"+definition.length());
			
			if( word_name.length() != 0 && definition.length() != 0) {
				datasource.open();
				if (datasource.addWord(word_name, definition) != -1) {
					datasource.close();
					word_edit.setText("");
					definition_edit.setText("");
					word_edit.requestFocus();
					final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
					if (activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED) { 
						new Async(0).execute(new Word(word_name, definition));
						
					} else {
						datasource.close();
						Toast.makeText(New_Word.this, "Word Added, will be synced when online", Toast.LENGTH_LONG).show();
					} 
				}
				else {
					datasource.close();
					Toast.makeText(New_Word.this, "Some error in adding a word. Try different.", Toast.LENGTH_LONG).show(); 
				}
			}
			else {
				Toast.makeText(New_Word.this, "Either word or definition is not complete", Toast.LENGTH_LONG).show();
			}
		}
	};
	
	private OnClickListener cancel_listener = new OnClickListener() {
		
		public void onClick(View v) {
			datasource.close();
			finish();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_word);
		
		word_edit = (EditText) findViewById(R.id.word_text);
		definition_edit = (EditText) findViewById(R.id.definition_text);
		add_word = (Button) findViewById(R.id.new_word_button);
		cancel_button = (Button) findViewById(R.id.cancel_new_word_button);
		
		add_word.setOnClickListener(add_word_listener);
		cancel_button.setOnClickListener(cancel_listener);
		
		datasource = new Word_Data_Source(this);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//datasource.close();
		finish();
	}
	
	class Async extends Words_Async {

		public Async(int requestType) {
			super(requestType);
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				try {
					Log.d("words:postWord:result", result);
					JSONObject json = new JSONObject(result);
					datasource = new Word_Data_Source(getApplicationContext());
					datasource.open();
					datasource.updateWordId(new Word(json.getString("word_name"), json.getString("definition")), json.getInt("id"));
					datasource.close();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}

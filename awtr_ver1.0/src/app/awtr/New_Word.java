package app.awtr;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class New_Word extends Activity {
	
	private Word_Data_Source datasource;
	EditText word_edit, definition_edit;
	Button add_word, cancel_button;
	
	private OnClickListener add_word_listener = new OnClickListener() {
		
		public void onClick(View v) {
			String word = word_edit.getText().toString();
			String definition = definition_edit.getText().toString();
			Log.d("awtr:word:", word.length()+":"+definition.length());
			if( word.length() != 0 && definition.length() != 0) {
				if (datasource.addWord(word, definition) != -1) {
					Toast.makeText(New_Word.this, "Word Added!", Toast.LENGTH_LONG).show();
					word_edit.setText("");
					definition_edit.setText("");
					word_edit.requestFocus();
				}
				else
					Toast.makeText(New_Word.this, "Some error in adding a word. Try different.", Toast.LENGTH_LONG).show();
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
		add_word = (Button) findViewById(R.id.add_new_word_button_2);
		cancel_button = (Button) findViewById(R.id.cancel_new_word_button);
		
		add_word.setOnClickListener(add_word_listener);
		cancel_button.setOnClickListener(cancel_listener);
		
		datasource = new Word_Data_Source(this);
		datasource.open();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		datasource.close();
		finish();
	}

}

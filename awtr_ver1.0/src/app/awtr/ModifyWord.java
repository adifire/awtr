package app.awtr;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyWord extends Activity {
	
	private Word_Data_Source datasource;
	EditText word_edit, definition_edit;
	Button add_word, cancel_button;
	Word existingWord = null;
	
	private OnClickListener add_word_listener = new OnClickListener() {
		
		public void onClick(View v) {
			String word = word_edit.getText().toString();
			String definition = definition_edit.getText().toString();
			Log.d("awtr:word:", word.length()+":"+definition.length());
			if( word.length() != 0 && definition.length() != 0) {
				int i = datasource.updateWord(existingWord, word, definition);
				Log.d("i: ", Integer.toString(i));
				if (i > 0) {
					Toast.makeText(ModifyWord.this, "Word Updated!", Toast.LENGTH_LONG).show();
					//word_edit.setText("");
					//definition_edit.setText("");
					//word_edit.requestFocus();
					datasource.close();
					finish();
				}
				else
					Toast.makeText(ModifyWord.this, "Some error in adding a word. Try different.", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(ModifyWord.this, "Either word or definition is not complete", Toast.LENGTH_LONG).show();
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
		datasource.open();
		String word_name = getIntent().getExtras().getString("word_name");
		Iterator<Word> words = datasource.getAllWords().iterator();
		while(words.hasNext()) {
			Word w = words.next();
			if(word_name.equals(w.getWord()))
				existingWord = w;
		}
		if(existingWord != null) {
			word_edit.setText(existingWord.getWord());
			definition_edit.setText(existingWord.getWord());
		}
		else {
			Toast.makeText(this, "No such text, going back", Toast.LENGTH_LONG);
			datasource.close();
			finish();
		}
		add_word.setText(R.string.update_word);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		datasource.close();
		finish();
	}
	
	

}

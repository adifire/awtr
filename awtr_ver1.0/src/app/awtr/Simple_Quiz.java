package app.awtr;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Simple_Quiz extends Activity {
	
	private TextView word_text, talk_text;
	private Button option_1, option_2, option_3, option_4, next_button, back_button;
	private Word_Data_Source datasource;
	private List<Word> words;
	
	private OnClickListener option_1_listener = new OnClickListener() {
		
		public void onClick(View v) {
			check_answer(option_1.getText().toString());
		}
	};
	
	private OnClickListener option_2_listener = new OnClickListener() {
		
		public void onClick(View v) {
			check_answer(option_2.getText().toString());
		}
	};
	
	private OnClickListener option_3_listener = new OnClickListener() {
		
		public void onClick(View v) {
			check_answer(option_3.getText().toString());			
		}
	};
	
	private OnClickListener option_4_listener = new OnClickListener() {
		
		public void onClick(View v) {
			check_answer(option_4.getText().toString());			
		}
	};
	
	private OnClickListener next_listener = new OnClickListener() {
		
		public void onClick(View v) {
			get_next();
		}
	};
	
	private OnClickListener back_listener = new OnClickListener() {
		
		public void onClick(View v) {
			datasource.close();
			finish();
		}
	};
	
	private void initialize() {
		word_text = (TextView) findViewById(R.id.word_textview);
		talk_text = (TextView) findViewById(R.id.quiz_talk_text);
		option_1 = (Button) findViewById(R.id.def1);
		option_1.setOnClickListener(option_1_listener);
		option_2 = (Button) findViewById(R.id.def2);
		option_2.setOnClickListener(option_2_listener);
		option_3 = (Button) findViewById(R.id.def3);
		option_3.setOnClickListener(option_3_listener);
		option_4 = (Button) findViewById(R.id.def4);
		option_4.setOnClickListener(option_4_listener);
		next_button = (Button) findViewById(R.id.next_button);
		next_button.setOnClickListener(next_listener);
		back_button = (Button) findViewById(R.id.back_button);
		back_button.setOnClickListener(back_listener);
		datasource = new Word_Data_Source(this);
	}
	
	void setOptions() {
		Random r = new Random();
		int i = r.nextInt(4);
		int max = 200;
		max = ( 2 * getWindowManager().getDefaultDisplay().getWidth() ) / 3;
		option_1.setText(words.get((i++)%4).getDefinition());
		option_2.setText(words.get((i++)%4).getDefinition());
		option_3.setText(words.get((i++)%4).getDefinition());
		option_4.setText(words.get((i++)%4).getDefinition());
		option_1.setWidth(max);
		option_2.setWidth(max);
		option_3.setWidth(max);
		option_4.setWidth(max);
	}
	
	boolean check_answer(String option_text) {
		
		if(option_text.equals(words.get(0).getDefinition())) {
			//Toast.makeText(Simple_Quiz.this, "Bingo! Now next!", Toast.LENGTH_LONG).show();
			talk_text.setText("Righto! Next One...");
			get_next();
			return true;
		}
		//Toast.makeText(Simple_Quiz.this, "Not that one buddy!", Toast.LENGTH_LONG).show();
		talk_text.setText("Ooops, Wrong one.");
		return false;
	}
	
	void get_next() {
		words = datasource.getWords(words.get(0));
		
		word_text.setText(words.get(0).getWord());
		
		setOptions();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_quiz);
		
		initialize();
		
		datasource.open();
		// Get words from list
		words = datasource.getWords();
		
		word_text.setText(words.get(0).getWord());
		
		setOptions();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
		finish();
	}

}

package app.awtr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class BetterQuiz extends Activity implements ViewFactory {
	private Button yes_button, no_button;
	private TextView word_text, talk_text, score_text;
	private TextSwitcher definition_text_s;
	private Word_Data_Source datasource;
	private List<Word> words;
	private Iterator<String> defs;
	private String current_definition;
	Timer timer = new Timer();
	Handler mHandler;
	int score, total;
	
	private OnClickListener yes_listener = new OnClickListener() {
		
		public void onClick(View v) {
			if(check_answer(current_definition)) {
				talk_text.setVisibility(View.VISIBLE);
				talk_text.setText(R.string.correct);
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						mHandler.post(new Runnable() {
							
							public void run() {
								Toast.makeText(BetterQuiz.this, "Moving on..", Toast.LENGTH_LONG).show();
								score++;
								score_text.setText(String.valueOf(score) + " correct");
								quiz_now(new Word(words.get(0).getWord(), current_definition));
							}
						});
						
					}
				};
				timer.schedule(task, 2000);
				
			}
			else {
				talk_text.setVisibility(View.VISIBLE);
				talk_text.setText(R.string.try_again);
			}
		}
	};
	
	private OnClickListener no_listener = new OnClickListener() {
		
		public void onClick(View v) {
			if(check_answer(current_definition)) {
				talk_text.setText(R.string.wrong);
				talk_text.setVisibility(View.VISIBLE);
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						mHandler.post(new Runnable() {
							
							public void run() {
								Toast.makeText(BetterQuiz.this, "Moving on..", Toast.LENGTH_LONG).show();
								quiz_now(new Word(words.get(0).getWord(), current_definition));
							}
						});
					}
				};
				timer.schedule(task, 2000);
			}
			else
				next_def();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.better_quiz);
		yes_button = (Button) findViewById(R.id.yes_quiz_button);
		yes_button.setOnClickListener(yes_listener);
		no_button = (Button) findViewById(R.id.no_quiz_button);
		no_button.setOnClickListener(no_listener);
		word_text = (TextView) findViewById(R.id.word_name_text);
		talk_text = (TextView) findViewById(R.id.talk_text_quiz_1);
		score_text = (TextView) findViewById(R.id.score_text);
		score_text.setText("");
		mHandler = new Handler();
		score = total = 0;
		definition_text_s = (TextSwitcher) findViewById(R.id.def_switcher);
		datasource = new Word_Data_Source(this);
		datasource.open();
		//words = datasource.getWords();
		definition_text_s.setFactory(this);
		quiz_now(null);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Toast.makeText(this, "Your score is " + 
				String.valueOf(score) + "/" + String.valueOf(total) ,
				Toast.LENGTH_LONG).show();
		datasource.close();
		finish();
	}
	
	public void quiz_now(Word notThis) {
		if(notThis != null) 
			words = datasource.getWords(notThis);
		else
			words = datasource.getWords();
		talk_text.setVisibility(View.INVISIBLE);
		word_text.setText(words.get(0).getWord());
		defs = getDefinitions(words).iterator();
		total++;
		next_def();
	}

	private ArrayList<String> getDefinitions(List<Word> words2) {
		ArrayList<String> defs = new ArrayList<String>();
		Random r = new Random();
		int i = r.nextInt(4);
		for(int j = i; j < i+4; j++) {
			defs.add(words.get(j%4).getDefinition());
		}
		return defs;
	}
	
	public void next_def() {
		current_definition = defs.next();
		definition_text_s.setText(current_definition);
	}
	
	boolean check_answer(String option_text) {
		
		if(option_text.equals(words.get(0).getDefinition())) {
			//Toast.makeText(Simple_Quiz.this, "Bingo! Now next!", Toast.LENGTH_LONG).show();
			//talk_text.setText("Righto! Next One...");
			return true;
		}
		//Toast.makeText(Simple_Quiz.this, "Not that one buddy!", Toast.LENGTH_LONG).show();
		//talk_text.setText("Ooops, Wrong one.");
		return false;
	}

	public View makeView() {
		TextView t = new TextView(this);
        t.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        t.setTextSize(25);
		return t;
	}
	
}

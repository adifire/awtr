package app.awtr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Awtr_Main extends Activity {
    /** Called when the activity is first created. */
	String s = "No words in your list here. Start adding words by clicking the button above!";
	String s2 = "Great! But you still need ";
	String s3 = " words right now to build a good quiz. Till then keep adding words!";
	
	private Word_Data_Source datasource;
	private int count;
	private TextView talk;
	private Button new_quiz_button, better_quiz_button, new_word_button;
    
	private OnClickListener new_word_listener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent createNewWord = new Intent();
			createNewWord.setClass(Awtr_Main.this, New_Word.class);
			startActivity(createNewWord);
		}
	};
	
	private OnClickListener new_quiz_listener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent createNewQuiz = new Intent();
			createNewQuiz.setClass(Awtr_Main.this, Simple_Quiz.class);
			startActivity(createNewQuiz);
		}
	};
	private OnClickListener better_quiz_listener= new OnClickListener() {
		
		public void onClick(View v) {
			Intent createNewQuiz = new Intent();
			createNewQuiz.setClass(Awtr_Main.this, BetterQuiz.class);
			startActivity(createNewQuiz);
		}
	};;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        talk = (TextView) findViewById(R.id.talk_text);
        new_quiz_button = (Button) findViewById(R.id.main_quiz_button);
        better_quiz_button = (Button) findViewById(R.id.better_quiz_button);
        new_word_button = (Button) findViewById(R.id.new_word_button_1);
        
        new_word_button.setOnClickListener(new_word_listener);
        new_quiz_button.setOnClickListener(new_quiz_listener);
        better_quiz_button.setOnClickListener(better_quiz_listener);
        
        datasource = new Word_Data_Source(this);
        
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
        
        count = datasource.getWordCount();
        
        if(count == 0) {
        	talk.setText(s);
        }
        else if(count < 10) {
        	talk.setText(s2 + Integer.toString(10 - count) + s3);
        }
        else if(count >= 10) {
        	new_quiz_button.setVisibility(View.VISIBLE);
        	better_quiz_button.setVisibility(View.VISIBLE);
        	talk.setText("You have " + Integer.toString(count) + " words in your account...");
        }
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflator = getMenuInflater();
    	inflator.inflate(R.menu.main_menu, menu);
    	return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
    	case R.id.delete_menu_option:
    		if(datasource.getWordCount() > 0) {
				Intent sendAppInvite = new Intent();
				sendAppInvite.setClass(Awtr_Main.this, DeleteWords.class);
				startActivity(sendAppInvite);
    		}
    		return true;
    	case R.id.sync_menu_option:
    		Intent intent = new Intent(this, Awtr_Service.class);
    		startService(intent);
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
	}
	
}
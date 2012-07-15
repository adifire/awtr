package app.awtr;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Word_Data_Source {
	private SQLiteDatabase database;
	private WordList dbHelper;
	private String[] allColumns = { WordList.WORD, WordList.DEFINITION, "id" };
	
	public Word_Data_Source(Context context) {
		dbHelper = new WordList(context);
	}
	
	public void open() throws SQLException{
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public long addWord(String word, String definition) {
		ContentValues values = new ContentValues();
		values.put(WordList.WORD, word);
		values.put(WordList.DEFINITION, definition);
		return database.insert(WordList.DATABASE_TABLE_NAME, null, values);
	}
	
	public int updateWord(Word existing, String word_name, String definition) {
		ContentValues values = new ContentValues();
		values.put(WordList.WORD, word_name);
		values.put(WordList.DEFINITION, definition);
		Log.d("def: ", definition);
		String[] whereArgs = {existing.getWord()};
		int affected = database.update(WordList.DATABASE_TABLE_NAME, values, "WORD=?", whereArgs);
		return affected;
	}
	
	public int updateWordId(Word word, int id) {
		ContentValues values = new ContentValues();
		values.put("id", id);
		String[] whereArgs = {word.getWord()};
		return database.update(WordList.DATABASE_TABLE_NAME, values, "WORD=?", whereArgs);
	}
	
	public int updateWordId(Word word) {
		ContentValues values = new ContentValues();
		values.put("id", word.getId());
		String[] whereArgs = {word.getWord()};
		return database.update(WordList.DATABASE_TABLE_NAME, values, "WORD=?", whereArgs);
	}
	
	
	public List<Word> getWords() {
		List<Word> words = new ArrayList<Word>();
		Cursor cursor = database.query(WordList.DATABASE_TABLE_NAME, allColumns, null, null, null, null, "RANDOM()", "4");
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Word word = new Word(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
			words.add(word);
			cursor.moveToNext();
		}
		cursor.close();
		
		return words;
	}
	
	public List<Word> getWords(Word notThis) {
		List<Word> words = new ArrayList<Word>();
		Cursor cursor = database.query(WordList.DATABASE_TABLE_NAME, allColumns, null, null, null, null, "RANDOM()", "6");

		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Word word = new Word(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
			if(notThis.getWord().equals(word.getWord()) || words.size() >= 4) {
				cursor.moveToNext();
				continue;
			}
			words.add(word);
			cursor.moveToNext();
		}
		cursor.close();
		
		return words;
	}
	
	public List<Word> getAllWords() {
		List<Word> words = new ArrayList<Word>();
		Cursor cursor = database.query(WordList.DATABASE_TABLE_NAME, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Word word = new Word(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
			words.add(word);
			cursor.moveToNext();
		}
		cursor.close();
		
		return words;
	}
	
	public List<Word> getAllWordsWithoutId() {
		List<Word> words = new ArrayList<Word>();
		Cursor cursor = database.query(WordList.DATABASE_TABLE_NAME, allColumns, "ID is null", null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Word word = new Word(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
			words.add(word);
			cursor.moveToNext();
		}
		cursor.close();
		
		return words;
	}
		
	public List<String> getAllWordsS() {
		List<String> words = new ArrayList<String>();
		Cursor cursor = database.query(WordList.DATABASE_TABLE_NAME, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Word word = new Word(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
			words.add(word.getWord());
			cursor.moveToNext();
		}
		cursor.close();
		
		return words;
	}
	
	public int getWordCount() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + WordList.DATABASE_TABLE_NAME;
		Cursor cursor = database.rawQuery(sql, null);	
		cursor.moveToFirst();
		count = cursor.getInt(0);
		cursor.close();
		return count;
	}
}

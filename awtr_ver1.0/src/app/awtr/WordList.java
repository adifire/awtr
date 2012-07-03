package app.awtr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WordList extends SQLiteOpenHelper {

	public static String DATABASE_NAME = "myawtr";
	public static int DATABASE_VERSION = 2;
	public static String DATABASE_TABLE_NAME = "wordlist";
	public static String WORD_TABLE = "word";
	public static String WORD_LIST_TABLE = "list";
	public static String WORD_WORD_LIST_TABLE = "words_with_list";
	public static String WORD_ID = "word_id";
	public static String WORD = "word";
	public static String DEFINITION = "definition";
	public static String LIST_ID = "list_id";
	public static String LIST_NAME = "list_name";
	public static String LIST_DESC = "desc";
	String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
									WORD + " varchar(20) not null," + DEFINITION + " text non null);";
	String WORD_TABLE_CREATE = 
			"CREATE TABLE WORDS_WORD(id int primary key, word_name varchar(20) not null," +
			"definition text not null)"; 
	String INSERT_QUERY = "insert into " + WORD_TABLE + "(" + 
			WORD_ID + "," + DEFINITION + ") values(";
	String SELECT_QUERY = "select * from " + DATABASE_TABLE_NAME + ";";
	
	public WordList(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("awtr:", "onUpgrade");
		

	}

}

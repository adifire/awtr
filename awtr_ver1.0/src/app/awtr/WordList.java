package app.awtr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordList extends SQLiteOpenHelper {

	public static String DATABASE_NAME = "myawtr";
	public static int DATABASE_VERSION = 2;
	public static String DATABASE_TABLE_NAME = "wordlist";
	public static String WORD = "word";
	public static String DEFINITION = "definition";
	String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
									WORD + " varchar(20) not null," + DEFINITION + " text non null);";
	
	public WordList(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

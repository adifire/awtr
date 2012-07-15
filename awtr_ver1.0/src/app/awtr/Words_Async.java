package app.awtr;

import android.os.AsyncTask;
import android.util.Log;

public abstract class Words_Async extends AsyncTask<Word, Void, String> {
	private int requestType;
	public Words_Async(int requestType){
		this.requestType = requestType;
	}
	
	@Override
	protected String doInBackground(Word... params) {
		switch(requestType){
		case 0:
			return new Words_Web_API().postWord(params[0]);
		default:
			break;
		}
		return null;
	}
	
	@Override
	protected abstract void onPostExecute(String result);

}

package app.awtr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Awtr_Service extends IntentService {
	Word_Data_Source datasource;
	public Awtr_Service() {
		super("my_first_awtr_service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
		 
			datasource = new Word_Data_Source(getBaseContext());
			String result;
			datasource.open();
			ArrayList<Word> words = (ArrayList<Word>) datasource.getAllWordsWithoutId();
			datasource.close();
			for(int i=0; i < words.size(); i+= 20) {
				int start = i, end;
				if ((i+19) > words.size())
					end = words.size();
				else
					end = i + 19;
				result = new Words_Web_API().postWords(words.subList(start, end));
				Log.d("words:update:", result);
				updateWords(result);
			}
			
			/*words.size();
			Iterator<Word> iterator = words.iterator();
			while(iterator.hasNext()){
				//new ContactWebService().execute(iterator.next());
				result = null;
				Word word = iterator.next();
				//Log.d("words: ", new Words_Web_API().getWord(word.getId()));
				result = new Words_Web_API().postWord(word);
				
				if(result != null){
					try {
						Log.d("words:s", result);
						JSONObject json = new JSONObject(result);
						if(word.getWord().equals(json.getString("word_name"))){
							Log.d("words:update:",String.valueOf(datasource.updateWordId(word, json.getJSONArray("id").getInt(0))));
						}
							
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}*/
			//datasource.close();
		} else {
			Toast.makeText(getBaseContext(), "Word Added, will be synced when online", Toast.LENGTH_LONG).show();
		}
		Toast.makeText(getBaseContext() , "Word Added, will be synced when online...", Toast.LENGTH_LONG).show();
	}
	
	private void updateWords(String result) {
		datasource.open();
		try {
			JSONArray json = new JSONArray(result);
			for (int i = 0; i < json.length() ; i++) {
				Word word = new Word(json.getJSONObject(i));
				if ( datasource.updateWordId(word) == -1 ) {
					//Toast.makeText(getBaseContext(), "Houston, we have got a problem.. ", Toast.LENGTH_LONG).show();
					Log.d("word:", word.getWord());
				}
					
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		datasource.close();
	}

	class ContactWebService extends AsyncTask<Word, Void, String> {
		
		final String URI = "http://wordsawtr.appspot.com/words/";
		
		
		@Override
		protected String doInBackground(Word... params) {
			Word word = params[0];
			List<NameValuePair> passParams = new ArrayList<NameValuePair>(2);
			passParams.add(new BasicNameValuePair("word_name", word.getWord()));
			passParams.add(new BasicNameValuePair("definition", word.getDefinition()));
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URI);
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(passParams));
				
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				
				if(entity != null) {
					//Log.d("loltale_entity: ", EntityUtils.toString(entity));
					return EntityUtils.toString(entity);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject obj;
			try {
				obj = new JSONObject(result);
				Log.d("words::word:", obj.getString("word_name") + "::" + obj.getString("definition"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}

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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class Awtr_Service extends IntentService {

	public Awtr_Service() {
		super("my_first_awtr_service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		final String URI = "http://wordsawtr.appspot.com/words";
		Word_Data_Source datasource = new Word_Data_Source(getBaseContext());
		datasource.open();
		ArrayList<Word> words = (ArrayList<Word>) datasource.getAllWords();
		Iterator<Word> iterator = words.iterator();
		while(iterator.hasNext()){
			//new ContactWebService().execute(iterator.next());
			Word word = iterator.next();
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
					Log.d("awtr: ", EntityUtils.toString(entity));
					//return EntityUtils.toString(entity);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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

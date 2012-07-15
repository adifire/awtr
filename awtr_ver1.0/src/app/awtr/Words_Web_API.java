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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import android.os.AsyncTask;
import android.util.Log;

public class Words_Web_API {
	final String URI = "http://wordsawtr.appspot.com/words";//10.0.2.2:8088/words";//
	
	public String getWord(int id){
		String url = URI + "/" + String.valueOf(id);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if(entity != null) {
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
	
	public String getWords(){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URI);
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			
			if(entity != null) {
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
	
	public String postWord(Word word){
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
				//Log.d("awtr: ", EntityUtils.toString(entity));
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
	
	public String postWords(List<Word> words) {
		List<NameValuePair> passParams = new ArrayList<NameValuePair>(words.size());
		Iterator<Word> wordsIterator = words.iterator();
		while(wordsIterator.hasNext()) {
			Word word = wordsIterator.next(); 
			passParams.add(new BasicNameValuePair("word_name", word.getWord()));
			passParams.add(new BasicNameValuePair("definition", word.getDefinition()));
		}
		
		JSONArray json = new JSONArray();
		Iterator<Word> wordIter = words.iterator();
		while(wordIter.hasNext()) {
			json.put(wordIter.next().toJSON());
		}
		Log.d("words:wordnoid:", json.toString());
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URI);
		
		try {
			StringEntity s = new StringEntity(json.toString(), HTTP.UTF_8);
			s.setContentType("application/json");
			httpPost.setEntity(s);
			
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			if(entity != null) {
				//Log.d("awtr: ", EntityUtils.toString(entity));
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
	
	class Async_Post_Word extends AsyncTask<Word, Void, String> {

		@Override
		protected String doInBackground(Word... params) {
			postWord(params[0]);
			return null;
		}
		
	}
	
}

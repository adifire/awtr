package app.awtr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import android.util.Log;

public class Words_Web_API {
	final String URI = "http://wordsawtr.appspot.com/words";
	
	public String getWord(int id){
		String url = URI + "/" + String.valueOf(id);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			//httpPost.setEntity(new UrlEncodedFormEntity(passParams));
			HttpResponse response = httpClient.execute(httpPost);
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
		//String url = URI + "/" + String.valueOf(id);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URI);
		
		try {
			//httpPost.setEntity(new UrlEncodedFormEntity(passParams));
			HttpResponse response = httpClient.execute(httpPost);
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
	
}

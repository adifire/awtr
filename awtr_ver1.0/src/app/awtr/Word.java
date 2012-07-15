package app.awtr;

import org.json.JSONException;
import org.json.JSONObject;

public class Word {
	private String word;
	private String definition;
	private int id;
	
	public Word(String word, String definition) {
		setWord(word);
		setDefinition(definition);
	}
	
	public Word(String word, String definition, int id) {
		setWord(word);
		setDefinition(definition);
		setId(id);
	}
	
	public Word(JSONObject wordJSON) {
		try {
			setWord(wordJSON.getString("word_name"));
			setDefinition(wordJSON.getString("definition"));
			if (wordJSON.has("id"))
				setId(wordJSON.getInt("id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public JSONObject toJSON() {
		try {
			return new JSONObject().put("word_name", this.word).put("definition", this.definition);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

package app.awtr;

public class Word {
	String word;
	String definition;
	
	public Word(String word, String definition) {
		setWord(word);
		setDefinition(definition);
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
}

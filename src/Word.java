
public class Word implements Comparable<Object>{
	private String word;
	private int score;
	
	@Override
	public String toString() {
		return word + " " + score;
	}

	public Word(String s, int i){
		this.word = s;
		this.score = i;
	}

	public String getWord() {
		return word;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Word){
			return ((Word)o).getScore() - this.score;
		}
		return -1;
	}
}

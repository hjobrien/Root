package runner;

import java.util.HashMap;

import graphics.TileType;

public class Word implements Comparable<Object>{

	public static final HashMap<Character, Integer> letterMapping = new HashMap<Character, Integer>(26);
	
	private String word;
	private String word2 = "";
	private int score;
	private char boardLetter;
	private String position;
	
	@Override
	public String toString() {
		if (word2.equals("")){
			return processString(word) + " " + score;
		}
		return processString(word) + " " + processString(word2) + " " + score + " " + this.position;
	}
	
	private String processString(String wordToProcess) {
		String fullWord = "";
		int boardLetterIndex = wordToProcess.indexOf(boardLetter);
		System.out.println(wordToProcess + " " + boardLetterIndex);
		fullWord += wordToProcess.substring(0, boardLetterIndex);
		fullWord += "[";
		fullWord += boardLetter;
		fullWord += "]";
		
		if (boardLetterIndex < wordToProcess.length() - 1){
			fullWord += wordToProcess.substring(boardLetterIndex + 1);
		}
		return fullWord;
	}

	public Word(String s){
		this.word = s;
		score = 0;
	}

	public Word(String s, int i, char c){
		this.word = s;
		this.score = i;
		this.boardLetter = c;
	}
	
	public Word(String s, char c, TileType[] multipliers){
		this.word = s;
		this.boardLetter = c;
		setScore(multipliers);
	}
	
	public Word(String s, char c, TileType[][] multipliers){
		this.word = s;
		this.boardLetter = c;
		setScore(multipliers);
	}
	
	public Word(String s1, String s2, char c, TileType[] multipliers, String position){
		this.word = s1;
		this.word2 = s2;
		this.boardLetter = c;
		setScore(multipliers);
		this.position = position;
	}

	public String getWord() {
		return word;
	}

	public int getScore() {
		return score;
	}
	
	public char getBoardLetter(){
		return boardLetter;
	}
	
	public void setScore(TileType[] multipliers){
		this.score = getWordScore(multipliers);
	}
	
	public void setScore(TileType[][] multipliers) {
		
		int wordScore1 = getWordScore(multipliers[0]);
		int wordScore2 = getWordScore(multipliers[1]);
		
		this.score = Math.max(wordScore1, wordScore2);
	}

	private int getWordScore(TileType[] multipliers) {
		
		//might not work if there are multiple instances of the boardLetter in the string
		int indexOfBoardTile = word.indexOf(boardLetter);
		
		int wordScore = 0;
		boolean doubleWord = false;
		boolean tripleWord = false;
		boolean wordIsPlayable = true;
//		char[] scoringCharArray = getScoringCharArray();
		for (char c : word.toCharArray()){
			int letterScore = letterMapping.get(c);
			TileType factor = multipliers[Processor.LETTERS_IN_A_HAND - (indexOfBoardTile - word.indexOf(c))];
			if (factor.getValue() == 1 || factor.getValue() == 4 || factor.getValue() == 5 || 
					factor.getValue() == 7){
				wordScore += letterScore;
				if (factor.getValue() == 4){
					doubleWord = true;
				}
				if (factor.getValue() == 5){
					tripleWord = true;
				}
			} else if (factor.getValue() == 2){
				wordScore += letterScore * 2;
			} else if (factor.getValue() == 3){
				wordScore += letterScore * 3;
			} else if (factor.getValue() == 0){
				wordIsPlayable = false;
			}
		}
		
		if (!wordIsPlayable){
			return -1;
		}
		if (doubleWord){
			wordScore *= 2;
		} 
		if (tripleWord){
			wordScore *= 3;
		}

		//50 point scrabble bonus for using all letters (including board letter)
		if (!word2.equals("")){
			
			//TODO doesnt account for tile bonuses
			for (char c : word2.toCharArray()){
				wordScore += letterMapping.get(c);
			}
			
			if (word.length() == 7){
				wordScore += 50;
			}
		} else if (word.length() == 8){
			wordScore += 50;
		}
		return wordScore;
	}

	private char[] getScoringCharArray() {
		char[] scoringCharArray = new char[word.length() - 1];
		int boardLetterPosition = word.indexOf(boardLetter);
		boolean passedBoardLetter = false;
		for (int i = 0; i < word.length(); i++){
			if (i == boardLetterPosition){ 
				passedBoardLetter = true;
			} else if (!passedBoardLetter){
				scoringCharArray[i] = word.charAt(i);
			} else if (passedBoardLetter){
				scoringCharArray[i - 1] = word.charAt(i);
			}
		}
		return scoringCharArray;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Word){
			return ((Word)o).getScore() - this.score;
		}
		return -1;
	}
	
	public static void setLetterMapping(){
		letterMapping.put('a', 1);
		letterMapping.put('b', 3);
		letterMapping.put('c', 3);
		letterMapping.put('d', 2);
		letterMapping.put('e', 1);
		letterMapping.put('f', 4);
		letterMapping.put('g', 2);
		letterMapping.put('h', 4);
		letterMapping.put('i', 1);
		letterMapping.put('j', 8);
		letterMapping.put('k', 5);
		letterMapping.put('l', 1);
		letterMapping.put('m', 3);
		letterMapping.put('n', 1);
		letterMapping.put('o', 1);
		letterMapping.put('p', 3);
		letterMapping.put('q', 10);
		letterMapping.put('r', 1);
		letterMapping.put('s', 1);
		letterMapping.put('t', 1);
		letterMapping.put('u', 1);
		letterMapping.put('v', 4);
		letterMapping.put('w', 4);
		letterMapping.put('x', 8);
		letterMapping.put('y', 4);
		letterMapping.put('z', 10);
	}

	public String getPosition() {
		return position;
	}
}

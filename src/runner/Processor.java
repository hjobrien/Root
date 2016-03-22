package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import graphics.Board;
import graphics.TileType;

public class Processor {
	public static final HashMap<Character, Integer> letterMapping = new HashMap<Character, Integer>(26);
	private static final int LETTERS_IN_A_HAND = 7;
	private static final int MIN_SCORE = 10;

	public static ArrayList<Word> run(TileType[][] board, char[] handLetters, char boardLetter, int boardLetterX, 
			int boardLetterY) throws FileNotFoundException {
		setLetterMapping();
		ArrayList<String> dictionary = processDictionary();
		ArrayList<String> twoLetterDictionary = processTwoLetterDictionary();
		
		//finding the words that are generated using the board letter
		TileType[][] multipliersWithBoardLetter = getMultipliersWithBoardLetter(board, boardLetterX, 
				boardLetterY);
		ArrayList<String> allWordsWithBoardLetter = getAllWordsWithBoardLetter(dictionary, handLetters, 
				boardLetter);
		ArrayList<Word> allHighScoringWords = new ArrayList<Word>();
		for (String s : allWordsWithBoardLetter){
			Word w = new Word(s, getScore(s, boardLetter, multipliersWithBoardLetter));
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		//left down, right down, up across, down across	
		//TODO
		TileType[] multipliersLeftDown = getMultipliersDown(board, boardLetterX - 1, boardLetterY);
		ArrayList<String> allWordsLeftDown = getAllWordsWithBoardLetterEnding(twoLetterDictionary, dictionary, handLetters, 
				boardLetter);
		for (String s : allWordsLeftDown){
			Word w = new Word(s, getScore(s, boardLetterY, multipliersLeftDown));
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		TileType[] multipliersRightDown = getMultipliersDown(board, boardLetterX + 1, boardLetterY);
		ArrayList<String> allWordsRightDown = getAllWordsWithBoardLetterStarting(twoLetterDictionary, dictionary, handLetters, 
				boardLetter);
		for (String s : allWordsRightDown){
			Word w = new Word(s, getScore(s, boardLetterY, multipliersRightDown));
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		TileType[] multipliersUpAcross = getMultipliersAcross(board, boardLetterY - 1, boardLetterX);
		ArrayList<String> allWordsUpAcross = getAllWordsWithBoardLetterEnding(twoLetterDictionary, dictionary, handLetters, 
				boardLetter);
		for (String s : allWordsUpAcross){
			Word w = new Word(s, getScore(s, boardLetterX, multipliersUpAcross));
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		TileType[] multipliersDownAcross = getMultipliersAcross(board, boardLetterY + 1, boardLetterX);
		ArrayList<String> allWordsDownAcross = getAllWordsWithBoardLetterStarting(twoLetterDictionary, dictionary, handLetters, 
				boardLetter);		
		for (String s : allWordsDownAcross){
			Word w = new Word(s, getScore(s, boardLetterY, multipliersDownAcross));
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		Collections.sort(allHighScoringWords);
		
		if (allHighScoringWords.size() > 5){
			ArrayList<Word> topWords = new ArrayList<Word>();
			for (int i = 0; i < 5; i++){
				topWords.add(allHighScoringWords.get(i));
			}
			for (Word w : topWords){
				System.out.println(w.getWord() + " " + w.getScore());
			}
			return topWords;
		} else {
			for (Word w : allHighScoringWords){
				System.out.println(w.getWord() + " " + w.getScore());
			}
			return allHighScoringWords;
		}
	}

	
	private static int getScore(String s, int boardLetterIndex, TileType[] multipliers) {
		// TODO Auto-generated method stub
		return 0;
	}


	private static ArrayList<String> processTwoLetterDictionary() throws FileNotFoundException {
		ArrayList<String> dictionary = new ArrayList<String>();
		Scanner dictionaryScanner = new Scanner(new File("wordlist.txt"));
		while (dictionaryScanner.hasNextLine()){
			String nextWord = dictionaryScanner.nextLine().toLowerCase();
			if (nextWord.length() == 2){
				dictionary.add(nextWord);
			}
		}
		dictionaryScanner.close();
		return dictionary;
	}


	private static ArrayList<String> getAllWordsWithBoardLetterStarting(ArrayList<String> twoLetterDictionary, 
			ArrayList<String> dictionary, char[] handLetters, char boardLetter) {
		ArrayList<String> words = new ArrayList<String>();
		for (String s : twoLetterDictionary){
			if (s.charAt(0) == boardLetter){
				String hand = handLetters.toString();
				if (hand.contains(s.charAt(1) + "")){
					char[] newHand = getNewHand(handLetters, s.charAt(1));
					ArrayList<String> tempWords = getAllWordsWithBoardLetter(dictionary, newHand, s.charAt(1));
					for (String tempS : tempWords){
						words.add(tempS);
					}
				}
			}
		}
		return words;
	}

	private static ArrayList<String> getAllWordsWithBoardLetterEnding(ArrayList<String> twoLetterDictionary, 
			ArrayList<String> dictionary, char[] handLetters, char boardLetter) {
		ArrayList<String> words = new ArrayList<String>();
		for (String s : twoLetterDictionary){
			if (s.charAt(1) == boardLetter){
				String hand = handLetters.toString();
				if (hand.contains(s.charAt(0) + "")){
					char[] newHand = getNewHand(handLetters, s.charAt(0));
					ArrayList<String> tempWords = getAllWordsWithBoardLetter(dictionary, newHand, s.charAt(0));
					for (String tempS : tempWords){
						words.add(tempS);
					}
				}
			}
		}
		return words;
	}

	private static char[] getNewHand(char[] handLetters, char c) {
		char[] newHand = new char[handLetters.length - 1];
		boolean notFound = true;
		for (int i = 0; i < handLetters.length; i++){
			if (notFound){
				if (handLetters[i] == c){
					notFound = false;
				} else {
					newHand[i] = handLetters[i];
				}
			} else {
				newHand[i - 1] = handLetters[i];
			}
		}
		return newHand;
	}

	private static TileType[] getMultipliersAcross(TileType[][] board, int row, int column) {
		TileType[] multipliers = new TileType[Board.SIZE];
		for (int i = 0; i < Board.SIZE; i++){
			int xCoord = column - (Board.SIZE / 2) + i - 1 ;
			if (xCoord < 0 || xCoord >= Board.SIZE){
				multipliers[i] = TileType.DOESNT_EXIST;
			} else {	
				multipliers[i] = board[row][xCoord];
			}
		}
		return multipliers;
	}


	private static TileType[] getMultipliersDown(TileType[][] board, int column, int row) {
		TileType[] multipliers = new TileType[Board.SIZE];
		for (int i = 0; i < Board.SIZE; i++){
			int yCoord = row - (Board.SIZE / 2) + i - 1 ;
			if (yCoord < 0 || yCoord >= Board.SIZE){
				multipliers[i] = TileType.DOESNT_EXIST;
			} else {
				multipliers[i] = board[yCoord][column];
			}
		}
		return multipliers;
	}


	private static int getScore(String s, char boardLetter, TileType[][] multipliers) {
		
		int wordScore1 = getWordScore(s, boardLetter, multipliers, 0);
		int wordScore2 = getWordScore(s, boardLetter, multipliers, 1);
		
		return Math.max(wordScore1, wordScore2);
	}

	private static int getWordScore(String s, char boardLetter, TileType[][] multipliers, int index) {
		
		//might not work if there are multiple instances of the boardLetter in the string
		int indexOfBoardTile = s.indexOf(boardLetter);
		
		int wordScore = 0;
		boolean doubleWord = false;
		boolean tripleWord = false;
		boolean wordIsPlayable = true;
		for (char c : s.toCharArray()){
			int letterScore = letterMapping.get(c);
			TileType factor = multipliers[index][LETTERS_IN_A_HAND - (indexOfBoardTile - s.indexOf(c))];
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

		//50 point scrabble bonus for using all letters
		if (s.contains(boardLetter + "")) {
			if (s.length() == 8){
				wordScore += 50;
			}
		} else if (s.length() == 7){
			wordScore += 50;
		}
		return wordScore;
	}

	private static ArrayList<String> processDictionary() throws FileNotFoundException {
		ArrayList<String> dictionary = new ArrayList<String>();
		Scanner dictionaryScanner = new Scanner(new File("wordlist.txt"));
		while (dictionaryScanner.hasNextLine()){
			dictionary.add(dictionaryScanner.nextLine().toLowerCase());
		}
		dictionaryScanner.close();
		return dictionary;
	}

	private static ArrayList<String> getAllWordsWithBoardLetter(ArrayList<String> dictionary, 
			char[] handLetters, char boardLetter) {
		ArrayList<String> allWords = new ArrayList<String>();
		for (String s : dictionary){
			if (s.contains(boardLetter + "")){
				int i = s.length() - 1;
				boolean allLettersInHand = true;
				while (allLettersInHand && i >= 0){
					allLettersInHand = check(s.charAt(i), handLetters, boardLetter);
					i--;
				}
				if (allLettersInHand){
					if (noDuplicates(s, handLetters, boardLetter)){
						allWords.add(s);
					}
				}
			}
		}
		return allWords;
	}
	
	private static boolean noDuplicates(String s, char[] handLetters, char boardLetter) {
		ArrayList<Character> allLetters = new ArrayList<Character>();
		for (char c : handLetters){
			allLetters.add(c);
		}
		allLetters.add(boardLetter);
		
		for (int i = s.length() - 1; i >= 0; i--){
			int j = allLetters.size() - 1;
			boolean charFound = false;
			while (!charFound && j >= 0){
				if (s.charAt(i) == allLetters.get(j)){
					allLetters.remove(j);
					charFound = true;
				}
				j--;
			}
			if (!charFound){
				return false;
			}
		}
		
		return true;
	}

	public static TileType[][] getMultipliersWithBoardLetter(TileType[][] board, int boardLetterX, 
			int boardLetterY){
		TileType[][] multipliers = new TileType[2][15];

		for (int i = 0; i < Board.SIZE; i++){
			
			//horizontal
			int xCoord = boardLetterX - (Board.SIZE / 2) + i;
			if (xCoord < 0 || xCoord >= Board.SIZE){
				multipliers[0][i] = TileType.DOESNT_EXIST;
			} else {	
				multipliers[0][i] = board[boardLetterY][xCoord];
			}
			
			//vertical
			int yCoord = boardLetterY - (Board.SIZE / 2) + i;
			if (yCoord < 0 || yCoord >= Board.SIZE ){
				multipliers[1][i] = TileType.DOESNT_EXIST;
			} else {
				multipliers[1][i] = board[yCoord][boardLetterX];
			}
		}
		
		return multipliers;
	}

	private static boolean check(char wordLetter, char[] handLetters, char boardLetter) {
		if (wordLetter == boardLetter){
			return true;
		} 
		for (int i = 0; i < handLetters.length; i++){
			if (wordLetter == handLetters[i]){
				return true;
			}
		}
		return false;
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
}

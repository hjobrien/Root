package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import graphics.Board;
import graphics.TileType;

public class Processor {
	public static final int LETTERS_IN_A_HAND = 7;
	public static final int MIN_SCORE = 1;
	public static final int ANSWERS_TO_SHOW = 5;

	public static ArrayList<Word> run(TileType[][] board, char[] handLetters, char boardLetter, int boardLetterX, 
			int boardLetterY) throws FileNotFoundException {
		Word.setLetterMapping();
		ArrayList<String> dictionary = processDictionary();
		ArrayList<String> twoLetterDictionary = processTwoLetterDictionary();
		
		//finding the words that are generated using the board letter
		TileType[][] multipliersWithBoardLetter = getMultipliersWithBoardLetter(board, boardLetterX, 
				boardLetterY);
		ArrayList<String> allWordsWithBoardLetter = getAllWordsWithBoardLetter(dictionary, handLetters, 
				boardLetter);
		ArrayList<Word> allHighScoringWords = new ArrayList<Word>();
		for (String s : allWordsWithBoardLetter){
			Word w = new Word(s, boardLetter, multipliersWithBoardLetter);
			if (w.getScore() >= MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		//TODO
		//left down, right down, up across, down across	
		TileType[] multipliersLeftDown = getMultipliersDown(board, boardLetterX - 1, boardLetterY);
		ArrayList<Word> allWordsLeftDown = getAllWordsWithBoardLetterEnding(twoLetterDictionary, dictionary, handLetters, 
				boardLetter, multipliersLeftDown);
		for (Word w : allWordsLeftDown){
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		TileType[] multipliersRightDown = getMultipliersDown(board, boardLetterX + 1, boardLetterY);
		ArrayList<Word> allWordsRightDown = getAllWordsWithBoardLetterStarting(twoLetterDictionary, dictionary, handLetters, 
				boardLetter, multipliersRightDown);
		for (Word w : allWordsRightDown){
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		TileType[] multipliersUpAcross = getMultipliersAcross(board, boardLetterY - 1, boardLetterX);
		ArrayList<Word> allWordsUpAcross = getAllWordsWithBoardLetterEnding(twoLetterDictionary, dictionary, handLetters, 
				boardLetter, multipliersUpAcross);
		for (Word w : allWordsUpAcross){
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		TileType[] multipliersDownAcross = getMultipliersAcross(board, boardLetterY + 1, boardLetterX);
		ArrayList<Word> allWordsDownAcross = getAllWordsWithBoardLetterStarting(twoLetterDictionary, dictionary, handLetters, 
				boardLetter, multipliersDownAcross);		
		for (Word w : allWordsDownAcross){
			if (w.getScore() > MIN_SCORE){
				allHighScoringWords.add(w);
			}
		}
		
		if (allHighScoringWords.isEmpty()){
			allHighScoringWords.add(new Word("No words"));
		}
		
		Collections.sort(allHighScoringWords);
		
		if (allHighScoringWords.size() > ANSWERS_TO_SHOW){
			ArrayList<Word> topWords = new ArrayList<Word>();
			for (int i = 0; i < ANSWERS_TO_SHOW; i++){
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


	private static ArrayList<Word> getAllWordsWithBoardLetterStarting(ArrayList<String> twoLetterDictionary, 
			ArrayList<String> dictionary, char[] handLetters, char boardLetter, TileType[] multipliers) {
		
		ArrayList<Word> words = new ArrayList<Word>();
		for (String s : twoLetterDictionary){
			if (s.charAt(0) == boardLetter){
				String hand = handLetters.toString();
				if (hand.contains(s.charAt(1) + "")){
					char[] newHand = getNewHand(handLetters, s.charAt(1));
					ArrayList<String> tempWords = getAllWordsWithBoardLetter(dictionary, newHand, s.charAt(1));
					for (String tempS : tempWords){
						words.add(new Word(tempS, "" + s.charAt(0) + s.charAt(1), s.charAt(1), multipliers));
					}
				}
			}
		}
		return words;
	}

	private static ArrayList<Word> getAllWordsWithBoardLetterEnding(ArrayList<String> twoLetterDictionary, 
			ArrayList<String> dictionary, char[] handLetters, char boardLetter, TileType[] multipliers) {
		ArrayList<Word> words = new ArrayList<Word>();
		for (String s : twoLetterDictionary){
			if (s.charAt(1) == boardLetter){
				String hand = handLetters.toString();
				if (hand.contains(s.charAt(0) + "")){
					char[] newHand = getNewHand(handLetters, s.charAt(0));
					ArrayList<String> tempWords = getAllWordsWithBoardLetter(dictionary, newHand, s.charAt(0));
					for (String tempS : tempWords){
						words.add(new Word(tempS, "" + s.charAt(0) + s.charAt(1), s.charAt(0), multipliers));
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
					if (i >= 6){
						System.out.println("error");
					} else {
						newHand[i] = handLetters[i];
					}
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
			int xCoord = column - (Board.SIZE / 2) + i;
			if (xCoord < 0 || xCoord >= Board.SIZE || row < 0 || row >= Board.SIZE){
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
			int yCoord = row - (Board.SIZE / 2) + i;
			if (yCoord < 0 || yCoord >= Board.SIZE || column < 0 || column >= Board.SIZE){
				multipliers[i] = TileType.DOESNT_EXIST;
			} else {
				multipliers[i] = board[yCoord][column];
			}
		}
		return multipliers;
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

	
}

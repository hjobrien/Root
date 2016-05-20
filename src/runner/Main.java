package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import graphics.TileType;
@Deprecated
public class Main {
	
	public static final HashMap<Character, Integer> letterMapping = new HashMap<Character, Integer>(26);
	private static final int LETTERS_IN_A_HAND = 7;
	private static final int MIN_SCORE = 10;

	public static void main(String[] args) throws FileNotFoundException {
		Word.setLetterMapping();
		ArrayList<String> dictionary = processDictionary();
		Scanner console = new Scanner(System.in);
		System.out.print("Enter by hand or from file? (0 for hand, other for file) ");
		if (!console.nextLine().equals("0")){
			console = new Scanner(new File("sampleBoard.txt"));
		}
		for (;;){ 
			TileType[][] multipliers = getBoardSurroundings(console);
			char[] handLetters = getHandLetters(console);
			char boardLetter = getBoardLetter(console);
			ArrayList<String> allWords = getAllWords(dictionary, handLetters, boardLetter);
			ArrayList<Word> allHighScoringWords = new ArrayList<Word>();
			for (String s : allWords){
				Word w = new Word(s, boardLetter, multipliers);
				if (w.getScore() > MIN_SCORE){
					allHighScoringWords.add(w);
				}
			}
			
			Collections.sort(allHighScoringWords);
			for (Word w : allHighScoringWords){
				System.out.println(w);
			}
			
			System.out.println("--------------------------------");
		}
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

	private static char getBoardLetter(Scanner console) {
		System.out.print("Board letter: ");
		char c = console.next().charAt(0);
		while (c > 122 || c < 97){
			System.out.print("please type again: ");
			c = console.next().charAt(0);
		}
		//since this is the last part of the data entry, a blank line lets you see the results clearly
		System.out.println();
		return c;
	}

	private static char[] getHandLetters(Scanner console) {
		char[] handLetters = new char[LETTERS_IN_A_HAND];
		for (int i = 0; i < LETTERS_IN_A_HAND; i++){
			System.out.print("Hand letter " + (i + 1) + ": ");
			char c = console.next().charAt(0);
			while (c > 122 || c < 97){
				System.out.print("please type again: ");
				c = console.next().charAt(0);
			}
			handLetters[i] = c;
		}
		return handLetters;
	}

	private static ArrayList<String> getAllWords(ArrayList<String> dictionary, char[] handLetters, char boardLetter) {
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

	public static TileType[][] getBoardSurroundings(Scanner console){
		System.out.println("First Type horizontal multiplyers, then vertical ones");
		System.out.println("1: blank, 2: double letter, 3: triple letter, 4: double word, 5: triple word.");
		TileType[][] multipliers = new TileType[2][15];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 15; j++){
				if(j == LETTERS_IN_A_HAND){
					multipliers[i][j] = TileType.BOARD_LETTER;
				}else{
					String s;
					if (i == 0){
						s = "Horizontal";
					} else {
						s = "Vertical";
					}
					System.out.print(s + " #" + (j + 1) + ": ");
					int a = console.nextInt();
					while (a > 5 || a < 0){
						System.out.print("please type again: ");
						a = console.nextInt();
					}
					switch (a){
					case 1:
						multipliers[i][j] = TileType.BLANK;
						break;
					case 2:
						//double letter
						multipliers[i][j] = TileType.DOUBLE_LETTER;
						break;
					case 3:
						//triple letter
						multipliers[i][j] = TileType.TRIPLE_LETTER;
						break;
					case 4:
						//double word
						multipliers[i][j] = TileType.DOUBLE_WORD;
						break;
					case 5:
						//triple word
						multipliers[i][j] = TileType.TRIPLE_WORD;
						break;
					case 0:
						//no tile space (off board)
						multipliers[i][j] = TileType.DOESNT_EXIST;
						break;
					default:
							throw new RuntimeException();
					}
				}
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

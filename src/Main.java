import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	
	public static final HashMap<Character, Integer> letterMapping = new HashMap<Character, Integer>(26);
	private static final int LETTERS_IN_A_HAND = 7;
	private static final int MIN_SCORE = 20;

	public static void main(String[] args) throws FileNotFoundException {
		setLetterMapping();
		ArrayList<String> dictionary = processDictionary();
		Scanner console = new Scanner(System.in);
		for (;;){
			char[] handLetters = getHandLetters(console);
			char boardLetter = getBoardLetter(console);
			ArrayList<String> allWords = getAllWords(dictionary, handLetters, boardLetter);
			for (String s : allWords){
				int score = getScore(s);
				if (score > MIN_SCORE){
					System.out.println(s + score);
				}
			}
			System.out.println("--------------------------------");
		}
	}
	
	private static int getScore(String s) {
		// TODO Auto-generated method stub
		return 0;
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
		return console.next().charAt(0);
	}

	private static char[] getHandLetters(Scanner console) {
		char[] handLetters = new char[LETTERS_IN_A_HAND];
		for (int i = 0; i < LETTERS_IN_A_HAND; i++){
			System.out.print("Hand letter " + (i + 1) + ": ");
			handLetters[i] = console.next().charAt(0);
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
					allWords.add(s);
				}
			}
		}
		return allWords;
	}
	public static void getBoardSurroundings(Scanner console){
		System.out.println("First Type horizontal multiplyers, then vertical ones");
		String[][] multiplyers = new String[2][15];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 15; j++){
				if(j == 8){
					multiplyers[i][j] = "Letter";
				}else{
					System.out.print("Next: ");
					int a = console.nextInt();
					switch (a){
					case 1:
						multiplyers[i][j] = "Blank";
						break;
					case 2:
						multiplyers[i][j] = "Double Letter";
						break;
					case 3:
						multiplyers[i][j] = "Triple Letter";
						break;
					case 4:
						multiplyers[i][j] = "Double word";
						break;
					case 5:
						multiplyers[i][j] = "Triple word";
						break;
					default:
							multiplyers[i][j] = "error";
					}
				}
			}
		}
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

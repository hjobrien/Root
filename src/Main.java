import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	
	public static final HashMap<Character, Integer> letterMapping = new HashMap<Character, Integer>(26);
	private static final int LETTERS_IN_A_HAND = 7;
	private static final int MIN_SCORE = 10;

	public static void main(String[] args) throws FileNotFoundException {
		setLetterMapping();
		ArrayList<String> dictionary = processDictionary();
		Scanner console = new Scanner(System.in);
		System.out.print("Enter by hand or from file? (0 for hand, other for file) ");
		if (!console.nextLine().equals("0")){
			console = new Scanner(new File("sampleBoard.txt"));
		}
		for (;;){
			String[][] multipliers = getBoardSurroundings(console);
			char[] handLetters = getHandLetters(console);
			char boardLetter = getBoardLetter(console);
			ArrayList<String> allWords = getAllWords(dictionary, handLetters, boardLetter);
			for (String s : allWords){
				int score = getScore(s, boardLetter, multipliers);
				if (score > MIN_SCORE){
					System.out.println(s + " " + score);
				}
			}
			System.out.println("--------------------------------");
		}
	}
	
	//might not work if there are multiple instances of the boardLetter in the string
	private static int getScore(String s, char boardLetter, String[][] multipliers) {
		
		//what happens if there are two occurrences of boardLetter in the string?
		int indexOfBoardTile = s.indexOf(boardLetter);
		
		int wordScore1 = 0;
		boolean doubleWord = false;
		boolean tripleWord = false;
		for (char c : s.toCharArray()){
			int letterScore = letterMapping.get(c);
			String factor = multipliers[0][8 - (indexOfBoardTile - s.indexOf(c))];
			if (factor.equals("dl")){
				wordScore1 += letterScore * 2;
			} else if (factor.equals("tl")){
				wordScore1 += letterScore * 3;
			} else if (factor.equals("dw")){
				doubleWord = true;
			} else if (factor.equals("tw")){
				tripleWord = true;
			} else {
				wordScore1 += letterScore;
			}
		}
		if (doubleWord){
			wordScore1 *= 2;
		} 
		if (tripleWord){
			wordScore1 *= 3;
		}
		
		int wordScore2 = 0;
		doubleWord = false;
		tripleWord = false;
		
		for (char c : s.toCharArray()){
			int letterScore = letterMapping.get(c);
			String factor = multipliers[1][8 - (indexOfBoardTile - s.indexOf(c))];
			if (factor.equals("dl")){
				wordScore2 += letterScore * 2;
			} else if (factor.equals("tl")){
				wordScore2 += letterScore * 3;
			} else if (factor.equals("dw")){
				doubleWord = true;
			} else if (factor.equals("tw")){
				tripleWord = true;
			} else {
				wordScore2 += letterScore;
			}
		}
		if (doubleWord){
			wordScore2 *= 2;
		} 
		if (tripleWord){
			wordScore2 *= 3;
		}
		
		return Math.max(wordScore1, wordScore2);
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

	public static String[][] getBoardSurroundings(Scanner console){
		System.out.println("First Type horizontal multiplyers, then vertical ones");
		System.out.println("1: blank, 2: double letter, 3: triple letter, 4: double word, 5: triple word.");
		String[][] multipliers = new String[2][15];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 15; j++){
				if(j == 8){
					multipliers[i][j] = "Letter";
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
						multipliers[i][j] = "Blank";
						break;
					case 2:
						//double letter
						multipliers[i][j] = "dl";
						break;
					case 3:
						//triple letter
						multipliers[i][j] = "tl";
						break;
					case 4:
						//double word
						multipliers[i][j] = "dw";
						break;
					case 5:
						//triple word
						multipliers[i][j] = "tw";
						break;
					default:
							multipliers[i][j] = "error";
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

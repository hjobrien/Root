import java.util.HashMap;

public class Main {
	
	//should contain all the letters in the player's pile PLUS the tile on the board that
	//you wish to use in your word
	char[] letters = new char[]{
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
	};
	
	public static final HashMap<Character, Integer> letterMapping = new HashMap<Character, Integer>(26);

	public static void main(String[] args) {
		setLetterMapping();
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

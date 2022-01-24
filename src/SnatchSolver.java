import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;

// Returns all valid words in a playsnatch.io game 

public class SnatchSolver {

	public static boolean isAnagram (String dict, String word) {

		int[] dictLetters = new int[26];
		int[] wordLetters = new int[26];

		try {
			for (int i = 0; i<dict.length(); i++) {
				dictLetters[(int)dict.charAt(i)-97]++;
			}
			for (int i = 0; i<word.length(); i++) {
				wordLetters[(int)word.charAt(i)-97]++;
			}
			for (int i = 0; i<26; i++) {
				if (dictLetters[i]>wordLetters[i]) {
					return false;
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}


	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner (System.in);

		//Initializes the dictionary into an ArrayList
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary5.txt"));
		ArrayList<String> words = new ArrayList<String>();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			if (curr.length()>=4) {
				words.add(curr.toLowerCase());
			}
		}
		inFile.close();


		System.out.println("Paste all the currently played words, with \"123\" when finished.");
		System.out.print("\t");

		HashSet <String> playedWords = new HashSet<String>();
		while (true) {
			String word = in.nextLine().toLowerCase();
			if (word.equals("123")) {
				break;
			}
			else if (!word.contains(" ") && word.length()>=4){
				playedWords.add(word);
			}
		}

		System.out.println("Paste all the current board letters, with \"123\" when finished.");
		System.out.print("\t");

		String boardLetters = "";
		while (true) {
			String letter = in.nextLine().toLowerCase();
			if (letter.equals("123")) {
				break;
			}
			else if (letter.length() == 1){
				boardLetters+=(letter);
			}
		}
		
		HashSet <String> results = new HashSet<String>();
		
		for (String word : playedWords) {
			for (String dictWord : words) {
				if (dictWord.length()>word.length() && isAnagram (dictWord, word+boardLetters) && isAnagram(word, dictWord)) {
					results.add(dictWord);
				}
			}
		}
		
		long start = System.currentTimeMillis();
		
		
		for (String word : results) {
			System.out.println(word);
		}
		
		
	}
}

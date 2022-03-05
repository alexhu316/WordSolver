import java.util.*;
import java.io.*;

//Finds ways to reconstruct a group of letters into multiple words 
// Made By Alex Hu --> Delay Exam Hub

public class FullAnagrams {

	static int minLetters = 4;
	
	//determines whether a dictionary word is contained within a given word
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
	
	public static boolean isAnagram (String dict, int[] wordLetters) {

		int[] dictLetters = new int[26];

		try {
			for (int i = 0; i<dict.length(); i++) {
				dictLetters[(int)dict.charAt(i)-97]++;
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
	
	
	
	
	public static void makeAnas(HashSet<ArrayList> solutions, ArrayList<String> currentList, ArrayList<String>wordBank, int[] lettersLeft) {
		
		boolean done = true;
		for (int i = 0; i< 26; i++) {
			if (lettersLeft[i]!=0) {
				done=false;
			}
		}
		if (done) {

			solutions.add(currentList);
			return;
		}
		for (String word : wordBank){
			if (isAnagram(word, lettersLeft)){
				int[] copy = new int[26];
				for (int i = 0; i<26; i++) {
					copy[i]=lettersLeft[i];
				}
				for (int i = 0; i<word.length(); i++) {
					lettersLeft[(int)word.charAt(i)-97]--;
				}
				ArrayList<String> newList = new ArrayList<String>();
				for (String worde : currentList) {
					newList.add(worde);
				}
				if (newList.contains(word)) {
					newList.add(Collections.binarySearch(newList, word), word);
				}
				else {
					newList.add(-1-Collections.binarySearch(newList, word), word);
				}
				
				String letters = "";
				for (int i = 0; i< 26; i++) {
					for (int j = lettersLeft[i]; j>0; j--) {
						letters+=(char)(i+97);
					}
				}
				ArrayList<String> newWordBank = new ArrayList<String>();
				for (String oldWord : wordBank) {
					if (isAnagram(oldWord, letters)) {
						newWordBank.add(oldWord);
					}
				}
				
				makeAnas(solutions, newList, newWordBank, lettersLeft);
				for (int i = 0; i<26; i++) {
					lettersLeft[i]=copy[i];
				}
			}
		}		
	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Scanner in = new Scanner (System.in);

		//Initializes the dictionary into an ArrayList
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary5.txt"));
		ArrayList<String> words = new ArrayList<String>();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			if (curr.length()>=minLetters) {
				words.add(curr.toLowerCase());
			}
		}
		inFile.close();


		System.out.println("What word would you like to find the anagram(s) of?");
		System.out.print("\t");
		
		String input = in.nextLine().toLowerCase();
			
		long start = System.currentTimeMillis();
		
		HashSet<ArrayList> solutions = new HashSet<ArrayList>();
		int[] letters = new int[26];
		for (int i = 0; i<input.length(); i++) {
			letters[input.charAt(i)-97]++;
		}
		
		
		ArrayList<String> wordBank = new ArrayList<String>();
		for (String word : words) {
			if (isAnagram(word, input)) {
				wordBank.add(word);
			}
		}
		
		makeAnas(solutions, new ArrayList<String>(), wordBank, letters);
		
		ArrayList<String> sortedSolutions = new ArrayList<String>();
		for (ArrayList solved : solutions) {
			sortedSolutions.add(solved.toString());
		}
		Collections.sort(sortedSolutions);
		for (String solved : sortedSolutions) {
			System.out.println(solved);
	}	
		long finish = System.currentTimeMillis();

		System.out.println("\n\nElapsed time: "+((finish-start)/1000.0)+"s");

	}



}

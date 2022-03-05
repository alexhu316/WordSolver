import java.util.*;
import java.io.*;

//Finds ways to reconstruct a group of letters into multiple words

public class TwentyLettersSolver {

	static int minLetters = 2;
	static int topScore = 0;
	static int[] letterPoints = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 
			1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

	public static String firstAna(String s, ArrayList<String> dict) {

		for (String word : dict) {
			if (s.length() == word.length() && isAnagram(s, word)) { 
				return word;
			}
		}
		return ""; //should never happen
	}

	public static String bestNextWord(HashSet<String>wordBank) {
		int highest = 0;
		String best = "";
		for (String word : wordBank) {
			if (wordScore(word) > highest) {
				highest = wordScore(word);
				best = word;
			}
		}
		return best;
	}

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

	// finds the score of a Twenty-Letters solution
	public static int evalList (ArrayList<String> list) {
		int score = 0;

		for (String word : list) {
			int curScore = 0;
			for (int i = 0; i < word.length(); i++){
				char c = word.charAt(i);        
				curScore += letterPoints[c - 97];
			}
			curScore *= word.length();
			score += curScore;
		}
		return score;
	}

	//determines if a dictionary word (dict) is contained within a word
	//from a list representing the number of each letter
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


	public static int potentialScore(int[] lettersLeft) {
		int totalLetters = 0;
		int score = 0;
		for (int i = 0; i < 26; i++) {
			score += lettersLeft[i] * letterPoints[i];
			totalLetters += lettersLeft[i] ;
		}
		return score * totalLetters;
	}


	public static int wordScore(String word) {
		int score = 0;
		for (char c : word.toCharArray()) {
			score += letterPoints[c - 97];
		}
		return score * word.length();
	}


	public static void solve (HashSet<TwentyLettersScore> solutions, ArrayList<String> currentList, int currentScore,
			HashSet<String>wordBank, int[] lettersLeft) {


		boolean done = true;
		for (int i = 0; i< 26; i++) {
			if (lettersLeft[i]!=0) {
				done=false;
			}
		}

		if (done) {
			topScore = Math.max(currentScore, topScore);
			solutions.add(new TwentyLettersScore(currentScore, currentList));
			return;
		}

		// one standalone call that greedily picks the best possible next word
		String greedyBestWord = bestNextWord(wordBank);
		if (!greedyBestWord.isEmpty()) {
			int[] copyOne = new int[26];
			for (int i = 0; i<26; i++) {
				copyOne[i]=lettersLeft[i];
			}
			for (int i = 0; i<greedyBestWord.length(); i++) {
				lettersLeft[greedyBestWord.charAt(i)-97]--;
			}
			ArrayList<String> newListOne = new ArrayList<String>();
			for (String wordCopy: currentList) {
				newListOne.add(wordCopy);
			}
			if (Collections.binarySearch(newListOne, greedyBestWord) >= 0) {
				newListOne.add(Collections.binarySearch(newListOne, greedyBestWord), greedyBestWord);
			}
			else {
				newListOne.add(-1-Collections.binarySearch(newListOne, greedyBestWord), greedyBestWord);
			}

			HashSet<String> newWordBankOne = new HashSet<String>();
			for (String oldWord : wordBank) {
				if (isAnagram(oldWord, lettersLeft)) {
					newWordBankOne.add(oldWord);
				}
			}

			int potentialScoreOne = potentialScore(lettersLeft);
			int wordScoreOne = wordScore(greedyBestWord);
			if (currentScore + potentialScoreOne + wordScoreOne >= topScore) {
				solve(solutions, newListOne, currentScore + wordScoreOne, newWordBankOne, lettersLeft);
			}
			for (int i = 0; i<26; i++) {
				lettersLeft[i]=copyOne[i];
			}
		}

		for (String word : wordBank){

			//Makes a copy of everything, modifies it for deeper recursive calls with each new viable word
			int[] copy = new int[26];
			for (int i = 0; i<26; i++) {
				copy[i]=lettersLeft[i];
			}
			for (int i = 0; i<word.length(); i++) {
				lettersLeft[(int)word.charAt(i)-97]--;
			}
			ArrayList<String> newList = new ArrayList<String>();
			for (String wordCopy: currentList) {
				newList.add(wordCopy);
			}
			if (Collections.binarySearch(newList, word) >= 0) {
				newList.add(Collections.binarySearch(newList, word), word);
			}
			else {
				newList.add(-1-Collections.binarySearch(newList, word), word);
			}

			HashSet<String> newWordBank = new HashSet<String>();
			for (String oldWord : wordBank) {
				if (isAnagram(oldWord, lettersLeft)) {
					newWordBank.add(oldWord);
				}
			}

			int potentialScore = potentialScore(lettersLeft);
			int wordScore = wordScore(word);
			if (currentScore + potentialScore + wordScore >= topScore) {
				solve(solutions, newList, currentScore + wordScore, newWordBank, lettersLeft);
			}
			for (int i = 0; i<26; i++) {
				lettersLeft[i]=copy[i];
			}
		}		
	}


	public static String alphabetize(String s) {
		char[] tempArray = s.toCharArray();
		Arrays.sort(tempArray);
		return String.valueOf(tempArray);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Scanner in = new Scanner (System.in);

		//Initializes the dictionary into an ArrayList
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/bigdict.txt"));
		HashSet<String> words = new HashSet<String>();
		ArrayList<String> realWords = new ArrayList<String>();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			if (curr.length()>=minLetters) {
				realWords.add(curr.toLowerCase());
				words.add(alphabetize(curr.toLowerCase()));
			}
		}
        inFile = new BufferedReader(new FileReader("Dictionaries/bigdict.txt"));
        while ((curr=inFile.readLine())!=null) {
			if (curr.length()>=minLetters) {
                words.add(alphabetize(curr.toLowerCase());
                if (Collections.binarySearch(realWords, curr.toLowerCase()) >= 0) {
                    newList.add(Collections.binarySearch(realWords, curr.toLowerCase()), curr.toLowerCase());
                }
                else {
                    newList.add(-1-Collections.binarySearch(realWords, curr.toLowerCase()), curr.toLowerCase());
                }
			}
		}
		inFile.close();

		// Reads in letters from console
		System.out.println("Enter the puzzle's letters.");
		System.out.print("\t");
		String input = in.nextLine().toLowerCase();

		long start = System.currentTimeMillis();

		HashSet<TwentyLettersScore> solutions = new HashSet<TwentyLettersScore>();
		int[] letters = new int[26];
		for (int i = 0; i<input.length(); i++) {
			letters[input.charAt(i)-97]++;
		}


		HashSet<String> wordBank = new HashSet<String>();
		for (String word : words) {
			if (isAnagram(word, input)) {
				wordBank.add(word);
			}
		}

		solve(solutions, new ArrayList<String>(), 0, wordBank, letters);

		
		ArrayList<TwentyLettersScore> arraySolutions = new ArrayList<TwentyLettersScore>();
		for (TwentyLettersScore solution : solutions) {
			arraySolutions.add(solution);
		}
		
		Collections.sort(arraySolutions);

		for (TwentyLettersScore solved : arraySolutions.subList(Math.max(0, solutions.size()-1), solutions.size())) {
			ArrayList<String> realSolutions = new ArrayList<String>();
			for (String word : solved.words) {
				realSolutions.add(firstAna(word, realWords));
			}
			solved.words = realSolutions;
			System.out.println(solved);
		}	
		
		long finish = System.currentTimeMillis();

		System.out.println("\n\nElapsed time: "+((finish-start)/1000.0)+"s");

	}
}

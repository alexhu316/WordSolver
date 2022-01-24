import java.util.*;
import java.io.*;


public class AnagramSolver {

	public static int MAXLETTERS = 12;
	public static int MINLETTERS = 4;

	public static int CHARBREAK = 70;

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


	//Finds and prints all anagrams of all lengths
	//of a given word, formatting by length and by alphabet
	public static void printAnas(String word, List<String>words){

		ArrayList<String> anagrams = new ArrayList<String>();

		for (int i = 0; i<words.size();i++) {
			if (words.get(i).length()<=MAXLETTERS&&words.get(i).length()>=MINLETTERS) {
				if (isAnagram(words.get(i),word)){
					anagrams.add(words.get(i));
				}
			}
		}

		Collections.sort(anagrams);
		Collections.sort(anagrams, compareLength);

		int counter=0;
		int formatLetters=MAXLETTERS+1;

		for (int i = 0 ; i<anagrams.size(); i++) {
			if (anagrams.get(i).length()<formatLetters) {
				counter=0;
				formatLetters=anagrams.get(i).length();
				System.out.println("\n\n"+anagrams.get(i).length()+" letters:");
				System.out.print("\t");
			}
			System.out.print(anagrams.get(i)+"  ");
			counter+=anagrams.get(i).length()+2;
			if (counter>CHARBREAK&&i+1<anagrams.size()) {
				counter=0;
				System.out.println();
				System.out.print("\t");
			}
		}
	}

	//Allows the ArrayList to be sorted by length
	static Comparator<String> compareLength = new Comparator <String>(){
		public int compare(String s1, String s2) {
			return Integer.compare(s2.length(), s1.length());
		}
	};

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Scanner in = new Scanner (System.in);

		//Initializes the dictionary into an ArrayList
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary 3.txt"));
		ArrayList<String> words = new ArrayList<String>();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			words.add(curr.toLowerCase());
		}
		inFile.close();


		System.out.println("What word would you like to find the anagram(s) of?");
		System.out.print("\t");

		String word = in.nextLine().toLowerCase();

		System.out.print("Max letters:  ");
		MAXLETTERS = in.nextInt();

		System.out.print("Min letters:  ");
		MINLETTERS = in.nextInt();

		long start = System.currentTimeMillis();

		printAnas(word,words);

		long finish = System.currentTimeMillis();

		System.out.println("\n\nElapsed time: "+((finish-start)/1000.0)+"s");

	}



}

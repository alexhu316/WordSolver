import java.util.*;
import java.io.*;

public class BlankSolver {

	//determines whether a dictionary word is contained within a given word
	public static boolean isPossible (String dict, String word) {

		if (word.length()!=dict.length()) {
			return false;
		}
		for (int i = 0; i<word.length();i++) {
			if (word.charAt(i)!='_') {
				if (word.charAt(i)!=dict.charAt(i)) {
					return false;
				}
			}
		}
		return true;
	}

	public static void printPossibles(String word, List<String>words){

		ArrayList<String> possibles = new ArrayList<String>();

		for (int i = 0; i<words.size();i++) {
			if (isPossible(words.get(i),word)){
				possibles.add(words.get(i));
			}
		}

		Collections.sort(possibles);

		for (int i = 0 ; i<possibles.size(); i++) {
			System.out.println(i+") "+possibles.get(i));
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Scanner in = new Scanner (System.in);

		//Initializes the dictionary into an ArrayList
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary1.txt"));
		ArrayList<String> words = new ArrayList<String>();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			words.add(curr.toLowerCase());
		}
		inFile.close();


		System.out.println("What blank would you like to find the words of?");
//		System.out.print("\t");

		for (int i = 0; i<3; i++) {
		String word = in.nextLine().toLowerCase();

		printPossibles(word, words);
		}
	}


}





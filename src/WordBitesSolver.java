import java.util.*;
import java.io.*;

// Returns all valid words in GamePiegon's Word Bites game.

public class WordBitesSolver {

	public static int CHARBREAK = 100;
	public static int MINLETTERS = 5;
	public static int MAXLETTERS = 16;

	
	//Brute-force combines letters that are within the horizontal/vertical direction, as
	//those pairs must keep their order. Returns whether it is possible to construct
	//a specific dictionary word with the letter tiles in the given direction
	public static boolean isAnagramFirst (String dict, String word, ArrayList<String>unsures, int singles) {

		int[] dictLetters = new int[26];
		int[] wordLetters = new int[26];

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

		if (unsures.size()!=1) {

			String withoutUnsures = word.substring(0,singles)+word.substring(singles+2*unsures.size(),word.length());
			String binaryTF = "";
			for (int i = 0; i<unsures.size(); i++) {
				binaryTF+="0";
			}

			for (int j = 0; j<Math.pow(unsures.size(), 2); j++) {
				String holder = withoutUnsures;
				for (int n = 0; n<unsures.size(); n++) {
					if (binaryTF.charAt(n)=='0') {
						withoutUnsures+=unsures.get(n);
					}
				}
				boolean doesntContainUnsures = true;
				for (int i = 0; i<unsures.size(); i++) {
					if (binaryTF.charAt(i)=='1') {
						if(dict.contains(unsures.get(i))) {
							doesntContainUnsures=false;
						}
					}
				}
				if(doesntContainUnsures&&!isAnagram(dict,withoutUnsures)) {
					return false;
				}
				withoutUnsures=holder;
				binaryTF=binaryUp(binaryTF,unsures.size());
			}
			return true;
		}
		
		else {
			String withoutIt = word.substring(0,singles)+word.substring(singles+2,word.length());
			
			if (!dict.contains(unsures.get(0))&&!isAnagram(dict,withoutIt)) {
				return false;
			}
			return true;
		}
		
	}

	
	//Determines whether a dictionary word is an anagram of a set of letters
	public static boolean isAnagram (String dict, String word) {

		int[] dictLetters = new int[26];
		int[] wordLetters = new int[26];

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
		return true;
	}

	
	//takes in a number in binary and returns a binary number one higher. Only works for the amount of digits inputted  
	public static String binaryUp(String s,int n){	
		String str="";

		int deci = 0;

		for (int i =0; i<s.length();i++) {
			if (s.charAt(s.length()-1-i)=='1') {
				deci+=Math.pow(2, i);
			}
		}
		deci++;
		for (int i = n; i>=0; i--) {

			if (deci>=(Math.pow(2, i))) {
				str=str+1;
				deci=deci-(int)(Math.pow(2, i));
			}
			else {
				str=str+0;
			}
		}
		str=str.substring(str.length()-n, str.length());
		return str;
	}

	//Brute-force checks all possible combinations of letters in the horizontal direction
	public static ArrayList<String> findHorizontal (ArrayList<Character> singles, ArrayList<String> horis, ArrayList<String> verts, ArrayList<String> dict){

		ArrayList<String> valids = new ArrayList<String>();

		String letters = "";
		for (int i = 0; i<singles.size();i++) {
			letters+=singles.get(i);
		}
		for (int i = 0; i<horis.size();i++) {
			letters+=horis.get(i);
		}
		String lettersCopy=letters;


		String binaryTF = "";

		for (int i = 0; i<verts.size(); i++) {
			binaryTF+="0";
		}

		for (int i = 0; i<Math.pow(2,verts.size()); i++){
			for (int j = 0; j<verts.size(); j++) {

				if (binaryTF.charAt(j)=='0'){
					letters+=verts.get(j).charAt(0);
				}
				else {
					letters+=verts.get(j).charAt(1);
				}

			}

			binaryTF = binaryUp(binaryTF, binaryTF.length());

			for (int j = 0; j<dict.size(); j++) {
				if (isAnagramFirst(dict.get(j),letters,horis,singles.size())&&!valids.contains(dict.get(j))&&dict.get(j).length()<=8) {
					valids.add(dict.get(j));
				}
			}
			letters=lettersCopy;
		}

		return valids;
	}

	
	//Brute-force checks all possible combinations of letters in the vertical direction
	public static ArrayList<String> findVertical (ArrayList<Character> singles, ArrayList<String> horis, ArrayList<String> verts, ArrayList<String> dict){

		ArrayList<String> valids = new ArrayList<String>();

		String letters = "";
		for (int i = 0; i<singles.size();i++) {
			letters+=singles.get(i);
		}
		for (int i = 0; i<verts.size();i++) {
			letters+=verts.get(i);
		}
		String lettersCopy=letters;


		String binaryTF = "";

		for (int i = 0; i<horis.size(); i++) {
			binaryTF+="0";
		}

		for (int i = 0; i<Math.pow(2,horis.size()); i++){
			for (int j = 0; j<horis.size(); j++) {

				if (binaryTF.charAt(j)=='0'){
					letters+=horis.get(j).charAt(0);
				}
				else {
					letters+=horis.get(j).charAt(1);
				}
			}

			binaryTF = binaryUp(binaryTF, binaryTF.length());

			for (int j = 0; j<dict.size(); j++) {
				if (isAnagramFirst(dict.get(j),letters,verts,singles.size())&&!valids.contains(dict.get(j))) {
					valids.add(dict.get(j));
				}
			}
			letters=lettersCopy;
		}

		return valids;
	}


	//Allows the ArrayList to be sorted by length
	static Comparator<String> compareLength = new Comparator <String>(){
		public int compare(String s1, String s2) {
			return Integer.compare(s2.length(), s1.length());
		}
	};
	
	public static boolean allGood(String s) {
		for (int i =0; i<s.length(); i++) {
			if (s.charAt(i)>122||s.charAt(i)<97) {
				return false;
			}
		}
		return true;
		
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		//Initializes the dictionary into an ArrayList
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary 3.txt"));
		ArrayList<String> words = new ArrayList<String>();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			if (curr.length()>=MINLETTERS&&curr.length()<=MAXLETTERS) {
				if (allGood(curr))
				words.add((curr).toLowerCase());
			}
		}
		inFile.close();


		//Takes in user input of the letters on the board
		ArrayList<Character> singles = new ArrayList<Character>();
		ArrayList<String> horis = new ArrayList<String>();
		ArrayList<String> verts = new ArrayList<String>();

		Scanner in =new Scanner (System.in);

				System.out.println("Enter all single letters, on separate lines, with \"end\" once there are no more.");
		String sing = "e";
		while (sing.length()==1) {
			sing=in.nextLine();
			if (sing.length()==1) {
				singles.add(sing.charAt(0));
			}
		}

				System.out.println("Enter all horizontal pairs, on separate lines, with \"end\" once there are no more.");
		String hori = "ee";
		while (hori.length()==2) {
			hori=in.nextLine();
			if (hori.length()==2) {
				horis.add(hori);
			}
		}

				System.out.println("Enter all vertical pairs, on separate lines, with \"end\" once there are no more.");
		String vert = "ee";
		while (vert.length()==2) {
			vert=in.nextLine();
			if (vert.length()==2) {
				verts.add(vert);
			}
		}

		long start = System.currentTimeMillis();


		ArrayList<String> sols = new ArrayList<String>();
		ArrayList<String> horiSols = new ArrayList<String>();
		ArrayList<String> vertSols = new ArrayList<String>();

		horiSols=findHorizontal (singles,horis,verts,words);

		for (int i = 0; i<horiSols.size(); i++) {
			if (!sols.contains(horiSols.get(i)+" (H) ")) {
				sols.add(horiSols.get(i)+" (H) ");
			}
		}

		vertSols=findVertical (singles,horis,verts,words);

		for (int i = 0; i<vertSols.size(); i++) {
			if ((!sols.contains(vertSols.get(i)+" (V) "))&&(!sols.contains(vertSols.get(i)+" (H) "))) {
				sols.add(vertSols.get(i)+" (V) ");
			}
		}

		Collections.sort(sols);
		Collections.sort(sols, compareLength);

		int counter=0;
		int formatLetters=17;

		for (int i = 0 ; i<sols.size(); i++) {
			if (sols.get(i).length()<formatLetters) {
				counter=0;
				formatLetters=sols.get(i).length();
				System.out.println("\n\n"+(sols.get(i).length()-5)+" letters:");
				System.out.print("\t");
			}
			System.out.print(sols.get(i)+"  ");
			counter+=sols.get(i).length()+2;
			if (counter>CHARBREAK&&i+1<sols.size()) {
				counter=0;
				System.out.println();
				System.out.print("\t");
			}
		}

		long finish = System.currentTimeMillis();

		System.out.println("\n\nElapsed time: "+((finish-start)/1000.0)+"s");

	}

}

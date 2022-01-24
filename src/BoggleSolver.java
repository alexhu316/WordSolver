import java.util.*;
import java.io.*;


public class BoggleSolver {

	final static int BOARDSIZE = 8;

	final static int MAXLETTERS = 20;
	final static int MINLETTERS = 3;

	final static int CHARBREAK = 70;


	static class TrieNode{
		TrieNode[] children = new TrieNode[26];

		boolean endOfWord;

		TrieNode() {
			endOfWord = false;
			for (int i = 0; i<26; i++) {
				children[i]=null;
			}
		}
	}

	static TrieNode root;  


	//Adds words into the trie. Adds one letter at a time, 
	//jumping onto that node to see where to add the next letter.
	//On the last letter, declare that node as the end of a word.
	static void add (String s) 
	{
		TrieNode curNode = root;

		int index; 
		for (int i = 0; i<s.length();i++) {
			index = s.charAt(i) - 'a'; 
			if (curNode.children[index] == null) {
				curNode.children[index] = new TrieNode(); 
			}
			curNode = curNode.children[index];	
		}
		curNode.endOfWord=true;
	}


	//A check to determine whether to continue traversing 
	//through the board by determining whether the branch
	//of the tree has ended with those letters
	static boolean goodSoFar(String s) {

		TrieNode curNode = root;

		for (int i =0;i<s.length();i++) {
			if (curNode.children[s.charAt(i)-97]==null) {
				return false;
			}
			curNode=curNode.children[s.charAt(i)-97];
		}
		return true;

	}

	//Determines whether the current word being made is a real word
	static boolean check (String s) {

		TrieNode curNode = root;

		for (int i =0;i<s.length();i++) {
			if (curNode.children[s.charAt(i)-97]==null) {
				return false;
			}
			curNode=curNode.children[s.charAt(i)-97];
		}

		if (!(curNode.endOfWord)) {
			return false;
		}
		return true;
	}

	//Allows the ArrayList to be sorted by size
	static Comparator<String> compareLength = new Comparator <String>(){
		public int compare(String s1, String s2) {
			return Integer.compare(s2.length(), s1.length());
		}
	};

	//DFS algorithm that traverses through the board
	//creating any possible string of letters which may be a word
	//in the trie and dictionary. Calls itself on all adjacent squares
	//terminating with a dead end or if the string is not in the trie
	public static void traverse (char[][] board, String word, int x, int y, boolean[][]avail, TrieNode root, ArrayList<String> valids, int letters ) {

		if (x<0 || x>=BOARDSIZE || y<0 || y>=BOARDSIZE) {
			return;
		}
		if (!avail[x][y]) {
			return;
		}
		if (letters>MAXLETTERS) {
			return;
		}
		if (!goodSoFar(word)) {
			return;
		}

		word+=board[x][y];
		if (board[x][y]=='q') {
			word+="u";
		}
		letters++;

		if (check(word)&&!valids.contains(word)&&letters>=MINLETTERS) {
			valids.add(word);
		}

		avail[x][y]=false;
		boolean[][] availCopy = avail;

		traverse(board,word,x-1,y-1,availCopy,root,valids,letters); //LU
		traverse(board,word,x-1,y,availCopy,root,valids,letters); //L
		traverse(board,word,x-1,y+1,availCopy,root,valids,letters); //LD
		traverse(board,word,x,y+1,availCopy,root,valids,letters); //D
		traverse(board,word,x+1,y+1,availCopy,root,valids,letters); //RD
		traverse(board,word,x+1,y,availCopy,root,valids,letters); //R
		traverse(board,word,x+1,y-1,availCopy,root,valids,letters); //RU
		traverse(board,word,x,y-1,availCopy,root,valids,letters); //U
		avail[x][y]=true;
	}



	public static void main(String[] args) throws FileNotFoundException, IOException {


		Scanner in = new Scanner (System.in);
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary 3.txt"));


		//Adding all words from the dictionary into the trie
		root = new TrieNode();
		String curr="";
		while ((curr=inFile.readLine())!=null) {
			if (curr.length()<=MAXLETTERS) {
				try {
				add(curr.toLowerCase());
				}
				catch (Exception e) {
					
				}
			}
		}
		inFile.close();


		ArrayList<String> valids = new ArrayList<String>();

		System.out.println("Enter your board:");
		for (int i =0; i<Math.pow(BOARDSIZE,2); i++) {
			System.out.print("_");
		}
		System.out.println();
		String input = in.nextLine().toLowerCase();;

		while (input.length()<Math.pow(BOARDSIZE,2)){
			System.out.println("Invalid board. Please print again:");
			for (int i =0; i<Math.pow(BOARDSIZE,2); i++) {
				System.out.print("_");
			}
			System.out.println();
			input = in.nextLine().toLowerCase();
		}

		long start = System.currentTimeMillis();

		

		//initializing the arrays that represent letters
		//in the board and whether they have already been visited
		char[][] board = new char[BOARDSIZE][BOARDSIZE];
		for (int i = 0; i<BOARDSIZE; i++) {
			for (int j = 0; j<BOARDSIZE; j++) {
				board [j][i]=input.charAt(0);
				input=input.substring(1);
			}
		}
		boolean[][] avail = new boolean[BOARDSIZE][BOARDSIZE];
		for (int i = 0; i<BOARDSIZE; i++) {
			for (int j = 0; j<BOARDSIZE; j++) {
				avail [j][i]=true;
			}
		}

		for (int i = 0; i<BOARDSIZE; i++) {
			for (int j = 0; j<BOARDSIZE; j++) {
				traverse(board,"",j,i,avail, root, valids, 0);
			}
		}

		//sorts the ArrayList of solutions by size,
		//formats them so one length displays in one block
		Collections.sort(valids);
		Collections.sort(valids, compareLength);

		int counter=0;
		int formatLetters=MAXLETTERS+1;

		for (int i = 0 ; i<valids.size(); i++) {
			if (valids.get(i).length()<formatLetters) {
				counter=0;
				formatLetters=valids.get(i).length();
				System.out.println("\n\n"+valids.get(i).length()+" letters:");
				System.out.print("\t");
			}
			System.out.print(valids.get(i)+"  ");
			counter+=valids.get(i).length()+2;
			if (counter>CHARBREAK&&i+1<valids.size()) {
				counter=0;
				System.out.println();
				System.out.print("\t");
			}
		}

		long finish = System.currentTimeMillis();

		System.out.println("\n\nElapsed time: "+((finish-start)/1000.0)+"s");
	}
}
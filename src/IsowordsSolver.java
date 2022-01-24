import java.io.*;
import java.util.*;

// Returns all valid words in the game Isowords.

public class IsowordsSolver {

	final static int MAXLETTERS = 20;
	final static int MINLETTERS = 6;

	final static int CHARBREAK = 70;

	static ArrayList<Block> blocks = new ArrayList<Block>();
	static TrieNode root;  
	static HashSet<String> valids = new HashSet<String>();

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


	//Allows the ArrayList to be sorted by size
	static Comparator<String> compareLength = new Comparator <String>(){
		public int compare(String s1, String s2) {
			return Integer.compare(s2.length(), s1.length());
		}
	};

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


	public static void traverse(Letter letter, String word) {

		if (letter.letter<97||letter.letter>122) {
			return;
		}

		if (!goodSoFar(word)){
			return;
		}

		if (letter.used) {
			return;
		}

		letter.used=true;

		if (check(word)&&word.length()>4) {
			valids.add(word);
		}

		for (Block b : blocks) {


			if (letter.isBeside(b.left)) {
				if (b.left.letter=='q')
					traverse(b.left, word+b.left.letter+'u');
				else if (b.left.letter!='.')
					traverse(b.left, word+b.left.letter);
			}
			if (letter.isBeside(b.right)) {
				if (b.right.letter=='q')
					traverse(b.right, word+b.right.letter+'u');
				else if (b.right.letter!='.')
					traverse(b.right, word+b.right.letter);

			}
			if (letter.isBeside(b.top)) {
				if (b.top.letter=='q')
					traverse(b.top, word+b.top.letter+'u');
				else if (b.top.letter!='.')
					traverse(b.top, word+b.top.letter);
			}
		}
		letter.used=false;
	}

	public static void main(String[] args) throws IOException {


		Scanner in = new Scanner (System.in);
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary 3.txt"));


		//Adding all words from the dictionary into the trie and dic
		root = new TrieNode();
		String curr="";

		while ((curr=inFile.readLine())!=null) {
			if (curr.length()>=3) {
				try {
					add(curr.toLowerCase());
				}
				catch(ArrayIndexOutOfBoundsException e) {
				}
			}

		}
		inFile.close();

		//Initializes ArrayList of all blocks

		while (true) {
			try {
				System.out.println("(1) Enter starting blocks\n(2) Enter new blocks");
				int choice = Integer.parseInt(in.nextLine());

				if (choice==1) {
					System.out.println("Enter the entire starting cube");

					String input = in.nextLine();
					Queue<Character> starts = new LinkedList<Character>();
					
					for (int i = 0; i<12; i++) {
						starts.add(input.charAt(i));
					}
					for (int i = 12; i<15; i++) {
						starts.add(input.charAt(i+6));
						starts.add(input.charAt(i));
					}
					starts.add(input.charAt(15));
					starts.add(input.charAt(21));
					starts.add(input.charAt(16));
					starts.add(input.charAt(22));
					starts.add(input.charAt(17));
					for (int i = 23; i<27; i++) {
						starts.add(input.charAt(i));
					}

					blocks.add(new Block(0,0,0,'.',starts.remove(),'.'));
					blocks.add(new Block(1,0,0,'.',starts.remove(),'.'));
					blocks.add(new Block(2,0,0,'.',starts.remove(),starts.remove()));
					blocks.add(new Block(2,0,1,'.','.',starts.remove()));
					blocks.add(new Block(2,0,2,'.','.',starts.remove()));
					blocks.add(new Block(0,1,0,'.',starts.remove(),'.'));
					blocks.add(new Block(1,1,0,'.',starts.remove(),'.'));
					blocks.add(new Block(2,1,0,'.',starts.remove(),starts.remove()));
					blocks.add(new Block(2,1,1,'.','.',starts.remove()));
					blocks.add(new Block(2,1,2,'.','.',starts.remove()));
					blocks.add(new Block(0,2,0,starts.remove(),starts.remove(),'.'));
					blocks.add(new Block(1,2,0,starts.remove(),starts.remove(),'.'));
					blocks.add(new Block(2,2,0,starts.remove(),starts.remove(),starts.remove()));
					blocks.add(new Block(2,2,1,starts.remove(),'.',starts.remove()));
					blocks.add(new Block(2,2,2,starts.remove(),'.',starts.remove()));
					blocks.add(new Block(0,2,1,starts.remove(),'.','.'));
					blocks.add(new Block(1,2,1,starts.remove(),'.','.'));
					blocks.add(new Block(1,2,2,starts.remove(),'.','.'));
					blocks.add(new Block(0,2,2,starts.remove(),'.','.'));		

					break;
				}
				else if (choice==2) {
					do {

						System.out.println("Enter the location of this block \"x y z\"");

						String s = in.nextLine();
						if (s.length()==1) {
							break;
						}
						int x = Integer.parseInt(s.charAt(0)+"");
						int y = Integer.parseInt(s.charAt(2)+"");
						int z = Integer.parseInt(s.charAt(4)+"");

						System.out.println("Enter the letters on this block \"T L R\"");

						s = in.nextLine();

						char t = s.charAt(0);
						char l = s.charAt(2);
						char r = s.charAt(4);

						blocks.add(new Block(x,y,z,t,l,r));

					} while (true);
					break;
				}
				else throw new NumberFormatException();

			}
			catch (NumberFormatException e) {
				blocks.clear();
				System.out.print("Invalid.");
			}
			catch (StringIndexOutOfBoundsException e) {
				blocks.clear();
				System.out.print("Invalid.");
			}
		}


		while (true) {

			int option=0;

			try {

				System.out.println("(1) Remove a block");
				System.out.println("(2) Add/Edit a block");
				System.out.println("(3) Print words");

				option = Integer.parseInt(in.nextLine());

			}
			catch (NumberFormatException e) {

			}

			if (option==1) {
				try {
					System.out.println("Enter the location of this block \"x y z\"");

					String s = in.nextLine();
					int x = Integer.parseInt(s.charAt(0)+"");
					int y = Integer.parseInt(s.charAt(2)+"");
					int z = Integer.parseInt(s.charAt(4)+"");

					Block remover = new Block (x,y,z,'.','.','.');

					for (int i = 0; i<blocks.size(); i++) {
						if (remover.equals(blocks.get(i))) {
							blocks.remove(i);
							break;
						}
					}
				}
				catch (NumberFormatException e) {

				}
				catch (StringIndexOutOfBoundsException e) {

				}
			}

			if (option==2) {
				try {
					System.out.println("Enter the location of this block \"x y z\"");

					String s = in.nextLine();
					int x = Integer.parseInt(s.charAt(0)+"");
					int y = Integer.parseInt(s.charAt(2)+"");
					int z = Integer.parseInt(s.charAt(4)+"");

					System.out.println("Enter the letters on this block \"T L R\"");

					s = in.nextLine();

					char t = s.charAt(0);
					char l = s.charAt(2);
					char r = s.charAt(4);

					Block changer = new Block(x,y,z,t,l,r);
					for (int i = 0; i<blocks.size(); i++) {
						if (changer.equals(blocks.get(i))) {
							blocks.remove(i);
							blocks.add(changer);
							break;
						}
					}
				}
				catch (NumberFormatException e) {

				}
				catch (StringIndexOutOfBoundsException e) {

				}
			}

			if (option==3) {

				valids.clear();
				for (int i = 0; i<blocks.size(); i++) {
					if (blocks.get(i).left.letter>=97&&blocks.get(i).left.letter<=122) {
						traverse(blocks.get(i).left, blocks.get(i).left.letter+"");
					}
					if (blocks.get(i).right.letter>=97&&blocks.get(i).right.letter<=122) {
						traverse(blocks.get(i).right, blocks.get(i).right.letter+"");
					}
					if (blocks.get(i).top.letter>=97&&blocks.get(i).top.letter<=122) {
						traverse(blocks.get(i).top, blocks.get(i).top.letter+"");
					}		

				}

				ArrayList<String>printer = new ArrayList<String>(valids);	

				Collections.sort(printer);
				Collections.sort(printer, compareLength);


				int counter=0;
				int formatLetters=MAXLETTERS+1;

				for (int i = 0 ; i<valids.size(); i++) {
					if (printer.get(i).length()<formatLetters) {
						counter=0;
						formatLetters=printer.get(i).length();
						System.out.println("\n\n"+printer.get(i).length()+" letters:");
						System.out.print("\t");
					}
					System.out.print(printer.get(i)+"  ");
					counter+=printer.get(i).length()+2;
					if (counter>CHARBREAK&&i+1<printer.size()) {
						counter=0;
						System.out.println();
						System.out.print("\t");
					}
				}
				System.out.println("\n");
			}
		}
	}

}


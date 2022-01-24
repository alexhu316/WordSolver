import java.util.*;
import java.io.*;

public class ContainsSearch {


	final static int CHARBREAK = 70;
	
	
	public static void contains(String section, List<String>words, int high, int low, int mode) {

		ArrayList<String> goods = new ArrayList<String>();

		if(mode==0) {
			for (int i = 0; i<words.size(); i++) {
				if (words.get(i).contains(section)&&words.get(i).length()>=low&&words.get(i).length()<=high) {
					goods.add(words.get(i));
				}
			}
		}
		else if(mode==1) {
			for (int i = 0; i<words.size(); i++) {
				if (words.get(i).length()>=section.length()&&words.get(i).substring(0,section.length()).equals(section)&&words.get(i).length()>=low&&words.get(i).length()<=high) {
					goods.add(words.get(i));
				}
			}
		}
		else if(mode==2) {
			for (int i = 0; i<words.size(); i++) {
				if (words.get(i).length()>=section.length()&&words.get(i).substring(words.get(i).length()-section.length(),words.get(i).length()).equals(section)&&words.get(i).length()>=low&&words.get(i).length()<=high) {
					goods.add(words.get(i));
				}
			}
		}

		for (int i = high; i>=low; i--) {
			ArrayList<String>prints = new ArrayList<String>();
			for (int n = 0; n<goods.size(); n++) {
				if (goods.get(n).length()==i) {
					prints.add(goods.get(n));
				}
			}
			System.out.println();
			if (!prints.isEmpty()) {
				int counter =0;
				System.out.println(i+" letters:");
				System.out.print("\t");

				for (int n = 0 ; n<prints.size(); n++) {
					System.out.print(prints.get(n)+"  ");
					counter+=prints.get(n).length()+2;
					if (counter>CHARBREAK&&i+1<prints.size()) {
						counter=0;
						System.out.println();
						System.out.print("\t");
					}
				}	
				System.out.println();
			}
		}
	}


	public static void main(String[] args) throws FileNotFoundException, IOException {

		Scanner in = new Scanner (System.in);
		BufferedReader inFile = new BufferedReader(new FileReader("Dictionaries/dictionary.txt"));

		List<String> words = new ArrayList<String>();

		String curr="";
		while ((curr=inFile.readLine())!=null) {
			words.add(curr);
		}
		inFile.close();

		System.out.println("Choose a function:\n1 - Contains\n2 - Starts with\n3 - Ends with");

		int choice = Integer.parseInt(in.nextLine());
		
		long start =0;

		 if (choice==1) {
			System.out.println("Return all words that contain _____");
			System.out.print("\t");

			String section = in.nextLine().toLowerCase();

			System.out.print("Max letters:  ");
			int high = in.nextInt();

			System.out.print("Min letters:  ");
			int low = in.nextInt();

			start = System.currentTimeMillis();
			
			contains(section, words, high, low, 0);
		}
		else if (choice==2) {
			System.out.println("Return all words that start with _____");
			System.out.print("\t");

			String section = in.nextLine().toLowerCase();

			System.out.print("Max letters:  ");
			int high = in.nextInt();

			System.out.print("Min letters:  ");
			int low = in.nextInt();
			
			start = System.currentTimeMillis();

			contains(section, words, high, low, 1);
		}
		else if (choice==3) {
			System.out.println("Return all words that end with _____");
			System.out.print("\t");

			String section = in.nextLine().toLowerCase();

			System.out.print("Max letters:  ");
			int high = in.nextInt();

			System.out.print("Min letters:  ");
			int low = in.nextInt();
			
			start = System.currentTimeMillis();

			contains(section, words, high, low, 2);
		}
		 
			long finish = System.currentTimeMillis();

			System.out.println("\n\nElapsed time: "+((finish-start)/1000.0)+"s");
		
	}
}

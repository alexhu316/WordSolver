import java.util.ArrayList;

public class TwentyLettersScore implements Comparable{
	
	public int score;
	public ArrayList<String> words;
	
	
	public TwentyLettersScore (int n, ArrayList<String> list) {
		this.score=n;
		this.words=list;
	}

	
	public String toString() {
		return this.score+": "+this.words.toString();
	}


	@Override
	public int compareTo(Object o) {
		TwentyLettersScore other = (TwentyLettersScore) o;
		return this.score - other.score;
	}
	
	@Override
    public boolean equals(Object o) {
		TwentyLettersScore other = (TwentyLettersScore) o;
		return (this.score == other.score && this.words.equals(other.words));
	}

	@Override
    public int hashCode() {
       return 17 + 31 * score + words.hashCode();
    }
	
}


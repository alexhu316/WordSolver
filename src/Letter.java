import java.util.*;


public class Letter {
	
	
	public char letter;
	public int code;
	public int x;
	public int y;
	public int z;
	public int s;
	public boolean used;
	
	//left bottom front is (0,0,0). right top back is (3,3,3). 

	public int[] c;

	
	public ArrayList<Integer> list;
	
	public Letter (char ch, int x, int y, int z, int s) {
		
		this.letter=ch;
		this.x=x;
		this.y=y;
		this.z=z;
		this.s=s;
		this.list=new ArrayList<Integer>();
		this.c = new int[4];
		this.used=false;
		
		//top
		if (this.s==1) {
			c[0]=x*100     +(y+1)*10 +z; //left corner	
			c[1]=(x+1)*100 +(y+1)*10 +z; //bottom corner
			c[2]=(x+1)*100 +(y+1)*10 +(z+1); //right corner
			c[3]=x*100     +(y+1)*10 +(z+1); //top corner
		}
		//left
		else if (this.s==2) {
			c[0]=x*100     +y*10     +z; //bottom left corner	
			c[1]=(x+1)*100 +y*10     +z; //bottom right corner
			c[2]=(x+1)*100 +(y+1)*10 +z; //top right corner
			c[3]=x*100     +(y+1)*10 +z; //top left corner
		}
		//right
		else if (this.s==3) {
			c[0]=(x+1)*100 +y*10     +z; //bottom left corner	
			c[1]=(x+1)*100 +y*10     +(z+1); //bottom right corner
			c[2]=(x+1)*100 +(y+1)*10 +(z+1); //top right corner
			c[3]=(x+1)*100 +(y+1)*10 +z; //top left corner
		}	
	}

	
	public boolean isBeside(Letter letter) {
		
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<4; j++) {
				if (this.c[i]==letter.c[j])
					return true;
			}
		}
		return false;	
	}
	
	
	public String toString() {
		return ""+this.letter;
	}

}

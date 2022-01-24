
public class Block {

	
	//left bottom front is (0,0,0). right top back is (2,2,2). 
	public int x; //left to right
	public int y; //bottom to top
	public int z; //front to back
	
	//letters on top, left, and right of a block
	public Letter top;
	public Letter left;
	public Letter right;
	
	public Block (int x, int y, int z, char topL, char leftL, char rightL) {
		
		this.x=x;
		this.y=y;
		this.z=z;
		this.top=new Letter(topL, x,y,z,1);
		this.left=new Letter(leftL, x,y,z,2);
		this.right=new Letter(rightL, x,y,z,3);
		
	}
	
	
	public boolean equals(Object o) {
		Block o1 = (Block) o;
		if (this.x==o1.x&&this.y==o1.y&&this.z==o1.z) {
			return true;
		}
		return false;
		
	}


	public String toString() {
		return (x+" "+y+" "+z+" "+"\n"+top+" "+left+" "+right);
	}

}

import java.util.Arrays;
import java.util.Random;



public class Board {
	public static int EMPTY = -1;
	
	private int[] boardData;
	private int width,height;
	private int freeSlots;
	
	Random r;

	public Board(){
		//standard 2048
		this(4,4);
		generatePiece();
		generatePiece();
	}
	public Board(int width, int height){
		boardData = new int[width * height];
		this.width = width;
		this.height = height;
		freeSlots = width * height;
		r = new Random();
		Arrays.fill(boardData, EMPTY);
	}
	public Board(Board other){
		this(other.width,other.height);
		System.arraycopy(other.boardData, 0, boardData, 0, other.boardData.length);
	}
	
	/**
	 * Creates a copy of the board and moves all of the pieces on the board 
	 * towards direction as far as possible
	 * @param d The direction the pieces should move
	 */
	public Board move(Direction d){
		Board b = new Board(this);
		boolean[] merged = new boolean[width*height];
		for(int i = 0; i < boardData.length; i++){
			b.move(merged,d,getX(i),getY(i));
		}
		if(!this.equals(b)){
			b.generatePiece();
		}
		return b;
	}
	/**
	 * Moves the piece on position (x,y) towards direction as far as possible
	 * @param b The board
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	private void move(boolean[] merged, Direction d, int x, int y){
		int x2 = x + d.x,y2 = y + d.y;
		//continue only if tile and tile ahead is within the board and that tile hasn't merged yet
		if(isInsideBoard(x, y) && isInsideBoard(x2,y2) 
				&& !merged[getIndex(x, y)] && !merged[getIndex(x2, y2)]){
			move(merged,d,x2,y2);
			int value = getValue(x, y), value2 = getValue(x2, y2);
			if(value != EMPTY){
				if(value2 == EMPTY){
					/* Continue to move tile */
					setValue(x2,y2,value); //move towards direction
					setValue(x,y,EMPTY); //set empty on old
					move(merged,d,x2,y2);
					move(merged,d,x - d.x, y - d.y);
				} else if(value2 == value){ 
					/* Merge tiles */
					merged[getIndex(x2, y2)] = true;
					setValue(x2,y2, value + 1);
					setValue(x,y,EMPTY); //set empty on old
					freeSlots++;
					move(merged,d,x - d.x, y - d.y);
				}
			}
		}
	}
	
	private boolean isInsideBoard(int x, int y) {
		return 0 <= x && x < width && 0 <= y && y < height;
	}
	public int getValue(int x,int y){
		return boardData[getIndex(x, y)];
	}
	public void setValue(int x, int y, int value){
		boardData[getIndex(x, y)] = value;
	}
	private int getIndex(int x, int y){
		if(!isInsideBoard(x, y)){
			throw new IllegalArgumentException("x or y outside board x:" + x + ", y:" + y);
		}
		return x + y * width;
	}
	private int getX(int index){
		return index % width;
	}
	private int getY(int index){
		return index / height;
	}
	public void generatePiece(){
		if(freeSlots > 0){
			int current;
			do{
				current = r.nextInt(width * height); 
			}
			while(getValue(getX(current),getY(current)) != EMPTY);
			int value = r.nextInt(10) == 0? 2 : 1;
			setValue(getX(current), getY(current), value);
			freeSlots--;
		}
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("######\n");
		for(int i = 0; i < height; i++){
			sb.append("#");
			for(int j = 0; j < width; j++){
				int value = getValue(j, i);
				if (value > 9){
					char c = 'A';
					c += value - 10;
					sb.append(c);
				}else if(value != EMPTY){
					sb.append(value);
				}else{
					sb.append(" ");
				}
			}
			sb.append("#\n");
		}
		sb.append("######\n");
		return sb.toString();
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}else if(obj == null || getClass() != obj.getClass()){
			return false;
		}else{
			Board b = (Board)obj;
			boolean equal = b.width == width && b.height == height;
			for(int i = 0; i < boardData.length && equal; i++){
				equal = boardData[i] == b.boardData[i];
			}
			return equal;
		}
	}
}

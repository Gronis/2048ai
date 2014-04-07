import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		String command = "";
		Board b = new Board();
		Scanner sc = new Scanner(System.in);
		while(command != "exit"){
			System.out.println(b);
			command = sc.nextLine();
			switch(command){
			case "up":
			case "u":
				b = b.move(Direction.UP);
				break;
				
			case "down":
			case "d":
				b = b.move(Direction.DOWN);
				break;
				
			case "left":
			case "l":
				b = b.move(Direction.LEFT);
				break;
				
			case "right":
			case "r":
				b = b.move(Direction.RIGHT);
				break;
			case "simulate":
				b = AI.simulate(b,true);
				break;
			case "restart":
				b = new Board();
				break;
			}
		}
		
	}

}

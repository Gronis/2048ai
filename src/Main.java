import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		String[] inputLine = null;
		String command = "";
		Board b = new Board();
		Scanner sc = new Scanner(System.in);
		while(!command.equals("exit")){
			System.out.println(b);
			command = sc.nextLine();
			inputLine = command.split(" ");
			command = inputLine[0];
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
				if(inputLine.length > 1){
					System.out.println("Simulating " + inputLine[1] + " times...");
					int num = Integer.parseInt(inputLine[1]);
					Board best = new Board();
					for(int i = 0; i < num; i++){
						Board temp = AI.simulate(new Board(), false);
						if(temp.getHighest() > best.getHighest()){
							best = temp;
						}
					}
					b = best;
				}else{
					b = AI.simulate(b,true);
				}
				break;
			case "restart":
				b = new Board();
				break;
			case "step":
			case "":
				b = AI.simulateStep(b, false);
				break;
			}
		}
		sc.close();
		
	}

}

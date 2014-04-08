import java.util.Random;


public class AI {
	/**
	 * Makes this AI to simulate a Board b until it is game over.
	 * @param b The board to simulate
	 * @param print Whenever the simulation should be printed 
	 * 	(makes simulation slower but the user can follow the progress)
	 * 
	 * @return true if reached 2048 before game over. false otherwise
	 */
	public static Board simulate(Board b, boolean print){
		while(!b.isGameOver()){
			b = simulateStep(b, print);
		}
		return b;
	}
	public static Board simulateStep(Board b, boolean print){
		Board bestNewBoard = b.move(Direction.DOWN);
		Direction bestDirection = Direction.DOWN;
		for(Direction d : Direction.values()){
			Board newBoard = b.move(d);
			if(bestNewBoard.equals(b)){
				bestNewBoard = newBoard;
				bestDirection = d;
			} else if(bestNewBoard.getValue() > newBoard.fill(1).getValue() &&
					!newBoard.equals(b)){
				bestNewBoard = newBoard.fill(1);
				bestDirection = d;
			}
		}
		b = b.move(bestDirection);
		if(print){
			System.out.println(b);
		}
		return b;
	}
}

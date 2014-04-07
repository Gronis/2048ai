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
		Random r = new Random();
		while(!b.isGameOver()){
			Direction d = Direction.values()[r.nextInt(4)];
			b = b.move(d);
			System.out.println(b);
		}
		return b;
	}
}

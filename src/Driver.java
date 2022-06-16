public class Driver {

	public static void main(String[] args){

		final int playerCount = 3;
		final int dataPoints = 100;

		int[] dataToCP = new int[dataPoints];
		int input;
		
		for(int i = 0; i < 100; i++){
			GameBoard b = new GameBoard(playerCount);
			b.initialize();
			input = b.startGame();
			dataToCP[i] = input;
			System.out.println("This was game number: " + i);
		}
		
	}

}

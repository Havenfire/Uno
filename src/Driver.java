public class Driver {

	public static void main(String[] args){

		final int playerCount = 3;
		final int dataPoints = 500;

		int[] dataToCP = new int[dataPoints];
		int input;
		
		for(int i = 0; i < dataPoints; i++){
			GameBoard b = new GameBoard(playerCount);
			b.initialize();
			input = b.startGame();
			dataToCP[i] = input;
		}
		System.out.println("This data has THIS many data points: " + dataToCP.length);
		for(int i = 0; i < dataPoints; i++){
			System.out.println(dataToCP[i]);
		}
		
	}

}

public class Driver {

	public static void main(String[] args){

		int playerCount = 3;
		
		GameBoard b = new GameBoard(playerCount);
		for(int i = 0; i < 1; i++){
			b.initialize();
			b.startGame();
		}
		
	}

}

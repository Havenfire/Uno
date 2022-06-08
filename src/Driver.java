
public class Driver {

	public static void main(String[] args) {

		int playerCount = 1;
		GameBoard b = new GameBoard(playerCount);
		b.initialize();
		b.startGame();
	}

}

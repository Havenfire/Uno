package GameCode;

public class UnoGame {

	int playerCount;
	int dataPoints;
	int input;
	int[] dataCollection;
	public UnoGame(int initPlayerCount, int desiredDataPoints){
		playerCount = initPlayerCount;
		dataPoints = desiredDataPoints;
		dataCollection = new int[dataPoints];

	}

	public int[] runSimulation(){
		for(int i = 0; i < dataPoints; i++){
			GameBoard b = new GameBoard(playerCount, false, false, false);
			b.initialize();
			input = b.startGame();
			dataCollection[i] = input;
		}

		return dataCollection;
	}

	public int getPlayerCount(){
		return playerCount;
	}

	public int getDataCount(){
		return dataPoints;
	}

}

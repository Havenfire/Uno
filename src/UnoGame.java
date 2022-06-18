import py4j.GatewayServer;

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
			GameBoard b = new GameBoard(playerCount);
			b.initialize();
			input = b.startGame();
			dataCollection[i] = input;
		}

		return dataCollection;
	}
	 

	public static void main(String[] args){


		GatewayServer server = new GatewayServer(new UnoGame(3, 500));
		server.start();
		System.out.println("Server Started");
 
	 }

}

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

	private static final int STARTING_HAND_SIZE = 7;
	ArrayList<Card> deck = new ArrayList<Card>();
	final String[] cardNumberTypes = {"1","2","3","4","5","6","7","8","9"};
	final String[] cardColors = {"red","yellow","green","blue"};	
	int playerCount;
	Agent[] playerTurn;
	Card inPlay;
	boolean gameOver = false;
	String gameColor;
	String gameVal;
	
	
	GameBoard(int newPlayerCount){
		// if(newPlayerCount > 8 || newPlayerCount < 2) {
		// 	throw new Error("Check Number of Players");
		// }
		
		playerCount = newPlayerCount;
	}
	
	public void initialize() {
		playerTurn = new Agent[playerCount];
		for(int i = 0; i < playerCount; i++) {
			RandomAgent r = new RandomAgent(0);
			playerTurn[i] = r;			
		}
		
		createDeck();
		shuffleDeck();
		dealHands();
	}
	
	public void startGame() {
		inPlay = dealCard();
		System.out.println("First Card is " + inPlay);
		gameColor = inPlay.color;
		gameVal = inPlay.cVal;
		Card playerCard;

		while(!gameOver) {
			for(int i = 0; i < playerCount; i++) {
				System.out.println(playerTurn[i].getHand());
				

				playerCard = playerTurn[i].decideCard(inPlay);
				if(playerCard == null){
					playerTurn[i].drawCard(dealCard());;
				}

			}
			
			
			
			for(int j = 0; j < playerCount; j++) {
				if(playerTurn[j].hand.size() == 0) {
					gameOver = true;
					System.out.println("Player Number " + j + " has won!");
				}
			}
			
			
		}
	}
	

	private Card dealCard() {
		return deck.remove(0);
	}
		
	private void createDeck() {
		
		for(int i = 0; i < cardColors.length; i++) {
			for(int j = 0; j < cardNumberTypes.length; j++) {
				deck.add(new Card(cardNumberTypes[j], cardColors[i]));
				deck.add(new Card(cardNumberTypes[j], cardColors[i]));
			}		
			deck.add(new Card("0", cardColors[i]));
			deck.add(new Card("skip", cardColors[i]));
			deck.add(new Card("skip", cardColors[i]));
			deck.add(new Card("draw2", cardColors[i]));
			deck.add(new Card("draw2", cardColors[i]));
			deck.add(new Card("reverse", cardColors[i]));
			deck.add(new Card("reverse", cardColors[i]));
			deck.add(new Card("wildCard", "black"));
			deck.add(new Card("draw4", "black"));			
		}
		
	}
	
	private void shuffleDeck() {
		Collections.shuffle(deck);
	}

	private void dealHands() {
		for(int i = 0; i < playerCount; i++) {
			for(int j = 0; j < STARTING_HAND_SIZE; j++) {
				playerTurn[i].drawCard(dealCard());
			}
		}
	}
	
}

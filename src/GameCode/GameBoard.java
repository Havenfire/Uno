package GameCode;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

	private static final int STARTING_HAND_SIZE = 7;
	ArrayList<Card> deck = new ArrayList<Card>();
	ArrayList<Card> discard = new ArrayList<Card>();
	final String[] cardNumberTypes = {"1","2","3","4","5","6","7","8","9"};
	final String[] cardColors = {"red","yellow","green","blue"};	
	int playerCount;
	Agent[] playerTurn;
	Card inPlay;
	boolean gameOver;
	String gameColor;
	String gameVal;
	int gameDirection;
	int currentTurn = Integer.MAX_VALUE;
	int victim;
	Card inQuestion;
	int numberOfTurns;
	
	GameBoard(int newPlayerCount){
		if(newPlayerCount > 8 || newPlayerCount < 2) {
			throw new Error("Check Number of Players");
		}
		
		playerCount = newPlayerCount;
	}
	
	public void initialize(){
		playerTurn = new Agent[playerCount];
		for(int i = 0; i < playerCount; i++) {
			RandomAgent r = new RandomAgent(i);
			playerTurn[i] = r;			
		}
		gameDirection = 1;
		numberOfTurns = 0;
		gameOver = false;
		
		createDeck();
		Collections.shuffle(deck);;
		dealHands();
	}
	
	public int startGame(){
		inPlay = dealCard();
		discard.add(inPlay);
		System.out.println("First Card is " + inPlay);
		gameColor = inPlay.color;
		gameVal = inPlay.cVal;
		Card playerCard;


		while(!gameOver) {


			//currentTurn is set initially to 0, then sets itself to a new turn
			currentTurn = whoTurn(currentTurn);
			numberOfTurns++;
			if(numberOfTurns > 1000){
				System.out.println("Game Lasted Too Long");
				break;
			}

			System.out.println("");
			System.out.println("It is Player Number " + currentTurn + "'s Turn");
			
			System.out.println(playerTurn[currentTurn].getHand());

			//player plays a card OR draws
			System.out.println("Current Board Values: " + gameVal + " " + gameColor);
			playerCard = (playerTurn[currentTurn]).playCard(gameVal,gameColor);

			//Cannot play, draws a card
			if(playerCard == null){
				playerTurn[currentTurn].drawCard(dealCard());
				if(gameOver){
					break;
				}
				System.out.println("Player Number " + currentTurn + " drew a " + "card");
			} else {
				//Plays a card
				System.out.println("Player Number " + currentTurn + " played a " + playerCard);

				if(playerCard.cVal.equals("Draw 2")){
					victim = whoTurn(currentTurn);
					playerTurn[victim].drawCard(dealCard());
					playerTurn[victim].drawCard(dealCard());
					System.out.println("Player Number " + victim + " drew 2 cards!");
					currentTurn = whoTurn(currentTurn);

					inPlay = playerCard;
					discard.add(inPlay);
					gameColor = playerCard.color;
					gameVal = playerCard.cVal;
				}

				else if(playerCard.cVal.equals("Reverse")){
					gameDirection *= -1;
					System.out.println("Game direction has been reversed!");
					if(playerCount == 2){
						currentTurn = whoTurn(currentTurn);
					}
					
					
					inPlay = playerCard;
					discard.add(inPlay);
					gameColor = playerCard.color;
					gameVal = playerCard.cVal;
				}

				else if(playerCard.cVal.equals("Skip")){
					victim = whoTurn(currentTurn);
					System.out.println("Player Number " + victim + " has been skipped!");
					currentTurn = whoTurn(currentTurn);

					inPlay = playerCard;
					discard.add(inPlay);
					gameColor = playerCard.color;
					gameVal = playerCard.cVal;
				}

				else if(playerCard.cVal.equals("Wild Card")){
					gameColor = playerTurn[currentTurn].playedWildCard();
					System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
				
					inPlay = playerCard;
					inPlay.setColor(gameColor);
					discard.add(inPlay);
					gameVal = playerCard.cVal;
				}

				else if(playerCard.cVal.equals("Draw 4")){
					gameColor = playerTurn[currentTurn].playedWildCard();
					System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
					victim = whoTurn(currentTurn);
					playerTurn[victim].drawCard(dealCard());
					playerTurn[victim].drawCard(dealCard());
					playerTurn[victim].drawCard(dealCard());
					playerTurn[victim].drawCard(dealCard());
					currentTurn = whoTurn(currentTurn);
					System.out.println("Player Number " + victim + " drew 4 cards!");
					
					inPlay = playerCard;
					inPlay.setColor(gameColor);
					discard.add(inPlay);
					gameVal = playerCard.cVal;

				}

				else {
					inPlay = playerCard;
					discard.add(inPlay);
					gameColor = playerCard.color;
					gameVal = playerCard.cVal;
				}


				
			}

			//checks if the player has won
			for(int i = 0; i < playerCount; i++)
				if(playerTurn[i].hand.size() == 0) {
					gameOver = true;
					System.out.println("Player Number " + i + " has won!");
					System.out.println("This game lasted " + numberOfTurns + " Turns!");
					break;
				}
		
		}

		if(deck.size() == 0 && discard.size() == 0){
			System.out.println("Game has gone infinite");
			return -1;
		}
		return numberOfTurns;
	}
	

	private Card dealCard(){
		
		if(deck.size() != 0){
			inQuestion = deck.remove(0);
			return inQuestion;
		}

		if(deck.size() == 0){
			shuffleDeck();
			System.out.println("Deck has been shuffled");
		}

		if(deck.size() == 0 && discard.size() == 0){
			gameOver = true;
		}

		return inQuestion;
	}
		
	private void createDeck() {
		
		for(int i = 0; i < cardColors.length; i++) {
			for(int j = 0; j < cardNumberTypes.length; j++) {
				deck.add(new Card(cardNumberTypes[j], cardColors[i]));
				deck.add(new Card(cardNumberTypes[j], cardColors[i]));
			}		
			deck.add(new Card("0", cardColors[i]));
			deck.add(new Card("Skip", cardColors[i]));
			deck.add(new Card("Skip", cardColors[i]));
			deck.add(new Card("Draw 2", cardColors[i]));
			deck.add(new Card("Draw 2", cardColors[i]));
			deck.add(new Card("Reverse", cardColors[i]));
			deck.add(new Card("Reverse", cardColors[i]));
			deck.add(new Card("Wild Card", ""));
			deck.add(new Card("Draw 4", ""));			
		}
		
	}
	
	private void shuffleDeck() {
		deck.addAll(discard);
		discard.clear();
		Collections.shuffle(deck);
		deck.remove(inPlay);
		discard.add(inPlay);
	}

	private void dealHands() {
		for(int i = 0; i < playerCount; i++) {
			for(int j = 0; j < STARTING_HAND_SIZE; j++) {
				playerTurn[i].drawCard(dealCard());
			}
		}
	}

	private int whoTurn(int lastTurn){
		int currentTurn = lastTurn + gameDirection;
		if(currentTurn < 0){
			return playerCount-1;
		}

		if(currentTurn > playerCount-1){
			return 0;
		}
		return lastTurn + gameDirection;
	}
	
}

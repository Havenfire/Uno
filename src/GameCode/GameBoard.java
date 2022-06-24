package GameCode;

import java.util.ArrayList;
import java.util.Collections;

public class GameBoard {

	private static final int STARTING_HAND_SIZE = 7;
	ArrayList<FlipCard> deck = new ArrayList<FlipCard>();
	ArrayList<FlipCard> discard = new ArrayList<FlipCard>();

	int playerCount;
	Agent[] playerTurn;
	FlipCard inPlay;
	boolean gameOver;
	String gameColor;
	String gameVal;
	int gameDirection;
	int currentTurn = Integer.MAX_VALUE;
	int victim;
	FlipCard inQuestion;
	int numberOfTurns;
	boolean isFlipped;

	//custom rules
	boolean stackingDrawCardsRule;
	boolean drawUntilPlayableRule;
	boolean drawnCardPlayableRule;

	
	GameBoard(int newPlayerCount, boolean setStackingDrawCardsRule, boolean setDrawUntilPlayableRule, boolean setDrawnCardPlayableRule){
		if(newPlayerCount > 8 || newPlayerCount < 2) {
			throw new Error("Check Number of Players");
		}
		stackingDrawCardsRule = setStackingDrawCardsRule; //TODO
		drawUntilPlayableRule = setDrawUntilPlayableRule;
		drawnCardPlayableRule = setDrawnCardPlayableRule;
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
		FlipCard playerCard;


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
				if(drawUntilPlayableRule){
					while(playerCard == null){
						playerTurn[currentTurn].drawCard(dealCard());
						System.out.println("Player Number " + currentTurn + " drew a " + "card");
						playerCard = (playerTurn[currentTurn]).playCard(gameVal,gameColor);
					}
					activatePlapCard(playerCard);

				} else {
					//draws a card
					playerTurn[currentTurn].drawCard(dealCard());
					if(drawnCardPlayableRule){
						playerCard = (playerTurn[currentTurn]).playCard(gameVal,gameColor);
						//if drawn card is playable
						if(playerCard != null){
							activatePlapCard(playerCard);
						}
					}

					if(gameOver){
						break;
					}
					System.out.println("Player Number " + currentTurn + " drew a " + "card");
				
				}
			} else {
				//Plays a card
				activatePlapCard(playerCard);
			
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
	
	
	private void activatePlapCard(FlipCard thePlayerCard){
		System.out.println("Player Number " + currentTurn + " played a " + thePlayerCard);

		if(thePlayerCard.cVal.equals("Draw 2")){
			victim = whoTurn(currentTurn);
			playerTurn[victim].drawCard(dealCard());
			playerTurn[victim].drawCard(dealCard());
			System.out.println("Player Number " + victim + " drew 2 cards!");
			currentTurn = whoTurn(currentTurn);

			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("Reverse")){
			gameDirection *= -1;
			System.out.println("Game direction has been reversed!");
			if(playerCount == 2){
				currentTurn = whoTurn(currentTurn);
			}
			
			
			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("Skip")){
			victim = whoTurn(currentTurn);
			System.out.println("Player Number " + victim + " has been skipped!");
			currentTurn = whoTurn(currentTurn);

			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("Wild Card")){
			gameColor = playerTurn[currentTurn].playedWildCard();
			System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
		
			inPlay = thePlayerCard;
			inPlay.setColor(gameColor);
			discard.add(inPlay);
			gameVal = thePlayerCard.cVal;
		}

		else if(thePlayerCard.cVal.equals("Draw 4")){
			gameColor = playerTurn[currentTurn].playedWildCard();
			System.out.println("Player Number " + currentTurn + " changed the color to " + gameColor + "!");
			victim = whoTurn(currentTurn);
			playerTurn[victim].drawCard(dealCard());
			playerTurn[victim].drawCard(dealCard());
			playerTurn[victim].drawCard(dealCard());
			playerTurn[victim].drawCard(dealCard());
			currentTurn = whoTurn(currentTurn);
			System.out.println("Player Number " + victim + " drew 4 cards!");
			
			inPlay = thePlayerCard;
			inPlay.setColor(gameColor);
			discard.add(inPlay);
			gameVal = thePlayerCard.cVal;

		}

		else {
			inPlay = thePlayerCard;
			discard.add(inPlay);
			gameColor = thePlayerCard.color;
			gameVal = thePlayerCard.cVal;
		}
	}

	private FlipCard dealCard(){
		
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
		
		//8 Flip Cards
		deck.add(new FlipCard("flip", "green", "3", "lBlue"));
		deck.add(new FlipCard("flip", "green", "drawX", "wild"));
		deck.add(new FlipCard("flip", "red", "8", "pink"));
		deck.add(new FlipCard("flip", "red", "3", "purple"));
		deck.add(new FlipCard("flip", "blue", "6", "purple"));
		deck.add(new FlipCard("flip", "blue", "7", "purple"));
		deck.add(new FlipCard("flip", "yellow", "4", "pink"));
		deck.add(new FlipCard("flip", "yellow", "8", "orange"));

		
		
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
